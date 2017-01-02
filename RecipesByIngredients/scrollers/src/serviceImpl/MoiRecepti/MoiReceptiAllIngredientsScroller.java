package serviceImpl.MoiRecepti;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import model.Constants;
import model.Ingredient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import service.AllIngredientsScroller;

import java.io.*;
import java.nio.Buffer;
import java.util.*;

/**
 * Scroller for all ingredients from MoiRecepti.mk.
 */
public class MoiReceptiAllIngredientsScroller implements AllIngredientsScroller{

    private PrintWriter printWriter;

    public MoiReceptiAllIngredientsScroller(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    @Override
    public List<Ingredient> getIngredientsFromRecipe(Document document) {

        List<Ingredient> ingredients = Lists.newArrayList();

        Elements ingredientsList =
                document.select(Constants.MOI_RECEPTI_GET_ALL_INGREDIENTS_FOR_A_GIVEN_RECIPE_QUERY);

        for (Element ingredientElement : ingredientsList) {

            Ingredient ingredient = new Ingredient();

            String ingredientText = ingredientElement.text();

            ingredient.setDescription(ingredientText);

            ingredients.add(ingredient);
        }

        return ingredients;
    }

    @Override
    public void writeIngredientsToFile(List<Ingredient> ingredients) {

        for (Ingredient ingredient : ingredients) {
            String ingredientDescription = ingredient.getDescription();
            printWriter.println(ingredientDescription);
        }
    }

    public void writeAllIngredientsToFile() throws FileNotFoundException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(Constants.MOI_RECEPTI_ALL_RECIPES_FILE_NAME));
        int counter = 1;
        String line = "";
        try {
            while ((line = bufferedReader.readLine()) != null) {

                String recipeUrl = line;
                Document document = Jsoup.connect(recipeUrl).get();
                List<Ingredient> ingredientList = getIngredientsFromRecipe(document);
                writeIngredientsToFile(ingredientList);
                System.out.println(counter);
                ++counter;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TreeSet<String> separateIngredients() throws IOException {

        BufferedReader bufferedReader =
                new BufferedReader(
                        new FileReader(
                                new File(Constants.MOI_RECEPTI_ALL_INGREDIENTS_FILE_NAME)));

        TreeSet<String> ingredientsSet = Sets.newTreeSet();

        int counter = 0;
        String line = "";
        while((line = bufferedReader.readLine()) != null) {
            ingredientsSet.add(line);
            counter++;
        }
        System.out.println("Total number of combinations " + counter);
        TreeSet<String> cleanedIngredients =  processIngredients(ingredientsSet);
        //printAllIngredients(cleanedIngredients);
        return cleanedIngredients;
    }

    public TreeSet<String> processIngredients(TreeSet<String> stringTreeSet) {

        Iterator<String> iterator = stringTreeSet.iterator();
        TreeSet<String> ingredients = Sets.newTreeSet();

        while (iterator.hasNext()) {
            String ingredient = iterator.next();
            ingredient = ingredient.toLowerCase();
            if (ingredient.length() > 1 && Character.isDigit(ingredient.charAt(0))) {

                if (ingredient.matches("^(\\d+)([-/])(\\d+)(\\s)([абвгдѓежзѕијклљмнњопрстќуфхцчџш]+)")) {
                    //4-5 jabolka ili 1/2 kromid
                    String ingredientPart = ingredient.replaceAll("(\\d+)([-/])(\\d+)(\\s)", "");
                    ingredientPart = ingredientPart.replaceAll(" ", "");
                    ingredients.add(ingredientPart);

                } else if (ingredient.matches("^(\\d+)([-/])(\\d+)(\\s)([абвгдѓежзѕијклљмнњопрстќуфхцчџш]+)(\\s)([абвгдѓежзѕијклљмнњопрстќуфхцчџш]+)")) {

                    //4-5 seri domati ili 5-6 kocki mraz
                    String ingredientPart = ingredient.replaceAll("(\\d+)([-/])(\\d+)(\\s)([абвгдѓежзѕијклљмнњопрстќуфхцчџш]+)(\\s)", "");
                    ingredientPart = ingredientPart.replaceAll(" ", "");
                    ingredients.add(ingredientPart);
                } else if (ingredient.matches("^(\\d+)([абвгдѓежзѕијклљмнњопрстќуфхцчџш]+)(\\.)(\\s)([абвгдѓежзѕијклљмнњопрстќуфхцчџш]+)")) {

                    //900ml. mleko
                    String ingredientPart = ingredient.replaceAll("(\\d+)([абвгдѓежзѕијклљмнњопрстќуфхцчџш]+)(\\.)(\\s)", "");
                    ingredientPart = ingredientPart.replaceAll(" ", "");
                    ingredients.add(ingredientPart);

                } else if (ingredient.matches("^(\\d+)(\\s)([абвгдѓежзѕијклљмнњопрстќуфхцчџш]+)(\\.)(\\s)([абвгдѓежзѕијклљмнњопрстќуфхцчџш]+)")) {

                    //900 ml. brasno
                    String ingredientPart = ingredient.replaceAll("(\\d+)(\\s)([абвгдѓежзѕијклљмнњопрстќуфхцчџш]+)(\\.)(\\s)", "");
                    ingredientPart = ingredientPart.replaceAll(" ", "");
                    ingredients.add(ingredientPart);

                } else if (ingredient.matches("^(\\d+)(\\s)([абвгдѓежзѕијклљмнњопрстќуфхцчџш]+)(\\s)([абвгдѓежзѕијклљмнњопрстќуфхцчџш]+)")) {

                    //8 ml mleko ili 9 lazici seker
                    String ingredientPart = "";
                    if (ingredient.contains("јапонск")) {
                        ingredientPart = ingredient.replaceAll("(\\d+)(\\s)", "");
                    } else {
                        ingredientPart = ingredient.replaceAll("(\\d+)(\\s)([абвгдѓежзѕијклљмнњопрстќуфхцчџш]+)(\\s)", "");
                        ingredientPart = ingredientPart.replaceAll(" ", "");
                    }
                    // iscisti gi pridavki kako varen, vareni, pogolemo, rendani, lupeni, izlupeni, сецкани, сечкани
                    ingredients.add(ingredientPart);

                } else if (ingredient.matches("^(\\d+)(\\s)(\\S+)")) {

                    // 9 piperki
                    String ingredientPart = ingredient.replaceAll("(\\d+)(\\s)", "");
                    ingredientPart = ingredientPart.replaceAll("(\\()(.*)(\\))", " ");
                    if (ingredientPart.matches("[абвгдѓежзѕијклљмнњопрстќуфхцчџш]+")) {
                        ingredients.add(ingredientPart);
                    }
                } else if (ingredient.matches("^(\\d)(\\s)([абвгдѓежзѕијклљмнњопрстќуфхцчџш]+)(\\s)(.*)")) {

                    String ingredientPart = ingredient.replaceAll("^(\\d+)(\\s)", "");
                    if (ingredientPart.matches("[абвгдѓежзѕијклљмнњопрстќуфхцчџш ]+")) {

                        if (!ingredientPart.matches("(.*)(\\s)(со)(\\s)(.*)")
                                && !ingredientPart.matches("(.*)(\\s)(од)(\\s)(.*)")
                                && !ingredientPart.matches("(.*)(\\s)(за)(\\s)(.*)")
                                && !ingredientPart.matches("(.*)(\\s)(во)(\\s)(.*)")
                                && !ingredientPart.matches("(.*)(\\s)(по)(\\s)(.*)")) {
                           // ingredientAlreadyCounted(ingredientPart, ingredients);
                        }
                    }
                } else if (ingredient.matches("^(\\d+)(\\s)([абвгдѓежзѕијклљмнњопрстќуфхцчџш]+)(\\s)(.*)")) {

                    // 50 gr meleni bademi
                    String ingredientPart = ingredient.replaceAll("^(\\d+)(\\s)([абвгдѓежзѕијклљмнњопрстќуфхцчџш]+)(\\s)", "");
                    //  ingredientAlreadyCounted(ingredientPart, ingredients);

                } else if (ingredient.matches("^(\\d+)([\\.,])(\\d+)(\\s)(.*)")) {
                    //3.40 casi mleko
                    String ingredientPart = ingredient.replaceAll("(\\d+)([\\.,])(\\d+)(\\s)", "");
                    ingredientPart = ingredientPart.replaceAll("(\\))(.*)(\\))", "");
                    //ingredientAlreadyCounted(ingredientPart, ingredients);
                    // додади ликер, свинско каре, свинска кртина, сируп

                } else if (ingredient.matches("^(\\d+)([\\-/])(\\d+)([абвгдѓежзѕијклљмнњопрстќуфхцчџш]+)(\\s)([абвгдѓежзѕијклљмнњопрстќуфхцчџш]+)")) {
                    //8-10 grama seker 1/3 jabolko
                    String ingredientPart = ingredient.replaceAll("^(\\d+)([\\-/])(\\d+)([абвгдѓежзѕијклљмнњопрстќуфхцчџш]+)(\\s)", "");
                    ingredients.add(ingredientPart);

                } else if (ingredient.matches("^(\\d+)([абвгдѓежзѕијклљмнњопрстќуфхцчџш]+)([//. ])([абвгдѓежзѕијклљмнњопрстќуфхцчџш]+)")) {
                    //50ml cokoladno mleko ili 50gr. seckani orevi
                    String ingredientPart = ingredient.replaceAll("^(\\d+)([абвгдѓежзѕијклљмнњопрстќуфхцчџш]+)([//. ])", "");
                    ingredients.add(ingredientPart);
                } else {
                    //   ingredientAlreadyCounted(ingredient, ingredients);
                }
            } else {

                if (ingredient.matches("^([абвгдѓежзѕијклљмнњопрстќуфхцчџш]+)(\\s)(.*)")) {
                    //treba da se izbegnat zatoa sto se mnogu malku a ima dosta zborovi kako dekoracija, dodatoci i slicno.

                    if (!ingredient.startsWith("за")
                            && !ingredient.startsWith("по потреба")
                            && !ingredient.startsWith("малку")
                            && !ingredient.startsWith("по желба")
                            && !ingredient.startsWith("според потреба")
                            && !ingredient.startsWith("по вкус")
                            && !ingredient.startsWith("сол")
                            && !ingredient.startsWith("околу")
                            && !ingredient.startsWith("неколку")
                            && !ingredient.startsWith("по ваш избор")
                            && !ingredient.contains("вкус")
                            && !ingredient.contains("фил")
                            && !ingredient.contains("декорација")) {
                        // System.out.println(ingredient);

                        //ingredientAlreadyCounted(ingredient, ingredients);

                    }

                }

            }

        }
        System.out.println(ingredients.toString());
        System.out.println(ingredients.size());
        return ingredients;
    }


    private void ingredientAlreadyCounted(String ingredientDetails, TreeSet<String> ingredientsSet){

        Iterator<String> ingredientIterator = ingredientsSet.iterator();
        boolean isIngredientContained = false;
        String ingredient = "";
        while (ingredientIterator.hasNext()) {
            ingredient = ingredientIterator.next();
            if (ingredientDetails.contains(ingredient)) {
               isIngredientContained = true;
            }
        }
        if (!isIngredientContained)
        System.out.println("Inngredients not counted " + ingredientDetails);
    }

    private void printAllIngredients(TreeSet<String> ingredientsSet ) {
        Iterator<String> ingredientIterator = ingredientsSet.iterator();
        String ingredient = "";
        while (ingredientIterator.hasNext()) {
            ingredient = ingredientIterator.next();
            System.out.println(ingredient);
        }
    }

    public  TreeMap<String, List<String>> getCleanIngredientsSet() throws IOException {

        BufferedReader bufferedReader = null;
        TreeMap<String, List<String>> ingredients = Maps.newTreeMap();

        try {
            bufferedReader = new BufferedReader(new FileReader(Constants.MOI_RECEPTI_CLEANED_INGREDIENTS_FILE_NAME));
            String line = "";
            while( (line = bufferedReader.readLine()) != null) {
                if (line.contains("-")) {
                    line = line.replaceAll(" ", "");
                    String [] contents = line.split("-");
                    String key = contents[0];
                    String[] synonymsStrings = contents[1].split(",");
                    List<String> synonyms = Arrays.asList(synonymsStrings);
                    ingredients.put(key, synonyms);
                } else {
                    ingredients.put(line, null);
                }
            }
            System.out.println(ingredients.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bufferedReader.close();
            return ingredients;
        }
    }
}
