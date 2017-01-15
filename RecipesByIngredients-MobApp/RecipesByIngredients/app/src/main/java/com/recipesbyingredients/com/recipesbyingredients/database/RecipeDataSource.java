package com.recipesbyingredients.com.recipesbyingredients.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.recipesbyingredients.com.recipesbyingredients.models.Recipe;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Data source for the recipes table.
 */
public class RecipeDataSource {

    private SQLiteDatabase database;
    private SqlLiteHelper dbHelper;
    private String[] allColumns= { SqlLiteHelper.COLUMN_ID_RECIPES,
            SqlLiteHelper.COLUMN_TITTLE_RECIPES,
            SqlLiteHelper.COLUMN_INSTRUCTIONS_RECIPES,
            SqlLiteHelper.COLUMN_RECIPE_YIELD_RECIPES,
            SqlLiteHelper.COLUMN_URL_RECIPES,
            SqlLiteHelper.COLUMN_IMAGE_URL_RECIPES,
            SqlLiteHelper.COLUMN_CATEGORY_RECIPES,
            SqlLiteHelper.COLUMN_TIME_OF_COOKING_RECIPES,
            SqlLiteHelper.COLUMN_INGREDIENTS_RECIPES
    };

    public RecipeDataSource(Context context) {
        dbHelper = new SqlLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean containsRecipe(String id){

        Cursor cursor = database.query(SqlLiteHelper.TABLE_SAVED_RECIPES,
                allColumns, SqlLiteHelper.COLUMN_ID_RECIPES + "=?", new String []{id},
                null, null, null);

        return cursor.moveToFirst();
    }

    public void insertRecipe(Recipe recipe) {
        ContentValues values = new ContentValues();
        values.put(SqlLiteHelper.COLUMN_ID_RECIPES, recipe.getId());
        values.put(SqlLiteHelper.COLUMN_URL_RECIPES, recipe.getUrl());
        values.put(SqlLiteHelper.COLUMN_CATEGORY_RECIPES, recipe.getCategory());
        values.put(SqlLiteHelper.COLUMN_IMAGE_URL_RECIPES, recipe.getImageUrl());
        values.put(SqlLiteHelper.COLUMN_TITTLE_RECIPES, recipe.getTitle());
        values.put(SqlLiteHelper.COLUMN_TIME_OF_COOKING_RECIPES, recipe.getTimeOfCooking());
        values.put(SqlLiteHelper.COLUMN_RECIPE_YIELD_RECIPES, recipe.getRecipeYield());
        values.put(SqlLiteHelper.COLUMN_INSTRUCTIONS_RECIPES, recipe.getInstructions());
        values.put(SqlLiteHelper.COLUMN_INGREDIENTS_RECIPES, recipe.getIngredientsString());

        long insertId = database.insert(SqlLiteHelper.TABLE_SAVED_RECIPES, null,
                values);
        Cursor cursor = database.query(SqlLiteHelper.TABLE_SAVED_RECIPES,
                allColumns, SqlLiteHelper.COLUMN_ID_RECIPES + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        cursor.close();

    }

    public void deleteRecipe(Recipe recipe) {
        long id = recipe.getId();
        System.out.println("Location deleted with id: " + id);
        database.delete(SqlLiteHelper.TABLE_SAVED_RECIPES, SqlLiteHelper.COLUMN_ID_RECIPES
                + " = " + id, null);
    }
    public void deleteAllRecipes(){
        System.out.println("All locations deleted");
        database.delete(SqlLiteHelper.TABLE_SAVED_RECIPES, null, null);
    }

    public ArrayList<Recipe> getAllRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();

        Cursor cursor = database.query(SqlLiteHelper.TABLE_SAVED_RECIPES,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Recipe recipe = cursorToRecipe(cursor);
            recipes.add(recipe);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return recipes;
    }

    private Recipe cursorToRecipe(Cursor cursor) {
        Recipe recipe = new Recipe();
        recipe.setId(cursor.getLong(0));
        recipe.setTitle(cursor.getString(1));
        recipe.setUrl(cursor.getString(2));
        recipe.setImageUrl(cursor.getString(3));
        recipe.setRecipeYield(cursor.getString(4));
        recipe.setCategory(cursor.getString(5));
        recipe.setTimeOfCooking(cursor.getString(6));
        recipe.setInstructions(cursor.getString(7));
        recipe.setIngredientsString(cursor.getString(8));
        return recipe;
    }
}
