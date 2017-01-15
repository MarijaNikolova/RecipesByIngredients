package com.recipesbyingredients.com.recipesbyingredients.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.recipesbyingredients.com.recipesbyingredients.models.Ingredient;
import com.recipesbyingredients.com.recipesbyingredients.models.Recipe;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Data source for the ingredient table.
 */
public class IngredientsDataSource {

    private SQLiteDatabase database;
    private SqlLiteHelper dbHelper;
    private String[] allColumns= { SqlLiteHelper.COLUMN_ID_INGREDIENTS,
            SqlLiteHelper.COLUMN_NAME_INGREDIENTS
    };

    public IngredientsDataSource(Context context) {
        dbHelper = new SqlLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean containsIngredient(String name){

        Cursor cursor = database.query(SqlLiteHelper.TABLE_INGREDIENTS,
                allColumns, SqlLiteHelper.COLUMN_NAME_INGREDIENTS + "=?", new String []{name},
                null, null, null);

        return cursor.moveToFirst();
    }

    public void insertIngredient(Ingredient ingredient) {
        ContentValues values = new ContentValues();
        values.put(SqlLiteHelper.COLUMN_NAME_INGREDIENTS, ingredient.getName());

        long insertId = database.insert(SqlLiteHelper.TABLE_INGREDIENTS, null,
                values);
        Cursor cursor = database.query(SqlLiteHelper.TABLE_INGREDIENTS,
                allColumns, SqlLiteHelper.TABLE_INGREDIENTS + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        cursor.close();

    }

    public void deleteIngredient(Ingredient ingredient) {
        String name = ingredient.getName();
        System.out.println("Location deleted with name: " + name);
        database.delete(SqlLiteHelper.TABLE_INGREDIENTS, SqlLiteHelper.COLUMN_NAME_INGREDIENTS
                + " = " + name, null);
    }
    public void deleteAllIngredients(){
        System.out.println("All locations deleted");
        database.delete(SqlLiteHelper.TABLE_INGREDIENTS, null, null);
    }

    public ArrayList<Ingredient> getAllIngredients() {
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

        Cursor cursor = database.query(SqlLiteHelper.TABLE_INGREDIENTS,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Ingredient ingredient = cursorToIngredient(cursor);
            ingredients.add(ingredient);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return ingredients;
    }

    private Ingredient cursorToIngredient(Cursor cursor) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(cursor.getLong(0));
        ingredient.setName(cursor.getString(1));
        return ingredient;
    }
}
