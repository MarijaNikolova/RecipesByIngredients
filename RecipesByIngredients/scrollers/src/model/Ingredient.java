package model;

import com.sun.deploy.util.StringUtils;
import org.jsoup.helper.StringUtil;

/**
 * Class that represents the ingredients.
 */
public class Ingredient implements Comparable{

     private String name;
    private String amount;
    private String measure;
    private String description;

    public String getName() {
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(Object o) {
        if (this.name != null) {
            return ((Ingredient)o).name.compareTo(this.name);
        }
        return ((Ingredient)o).description.compareTo(this.description);
    }
}
