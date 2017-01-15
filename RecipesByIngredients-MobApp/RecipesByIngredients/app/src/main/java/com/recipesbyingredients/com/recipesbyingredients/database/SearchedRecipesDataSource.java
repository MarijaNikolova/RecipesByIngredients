package com.recipesbyingredients.com.recipesbyingredients.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.recipesbyingredients.com.recipesbyingredients.models.Recipe;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Data source for searched recipes data source.
 */
public class SearchedRecipesDataSource {

    private SQLiteDatabase database;
    private SqlLiteHelper dbHelper;
    private String[] allColumns= { SqlLiteHelper.COLUMN_ID_RECIPES
    };

    public SearchedRecipesDataSource(Context context) {
        dbHelper = new SqlLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean containsRecipe(String id){

        Cursor cursor = database.query(SqlLiteHelper.TABLE_SEARCHED_RECIPES,
                allColumns, SqlLiteHelper.COLUMN_ID_SEARCHED_RECIPES + "=?", new String []{id},
                null, null, null);

        return cursor.moveToFirst();
    }

    public void insertRecipe(Recipe recipe) {
        ContentValues values = new ContentValues();
        values.put(SqlLiteHelper.COLUMN_ID_SEARCHED_RECIPES, recipe.getId());

        long insertId = database.insert(SqlLiteHelper.TABLE_SEARCHED_RECIPES, null,
                values);
        Cursor cursor = database.query(SqlLiteHelper.TABLE_SEARCHED_RECIPES,
                allColumns, SqlLiteHelper.COLUMN_ID_SEARCHED_RECIPES + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        cursor.close();

    }

    public void deleteRecipe(Recipe recipe) {
        long id = recipe.getId();
        System.out.println("Location deleted with id: " + id);
        database.delete(SqlLiteHelper.TABLE_SEARCHED_RECIPES, SqlLiteHelper.COLUMN_ID_SEARCHED_RECIPES
                + " = " + id, null);
    }
    public void deleteAllRecipes(){
        System.out.println("All locations deleted");
        database.delete(SqlLiteHelper.TABLE_SEARCHED_RECIPES, null, null);
    }

    public ArrayList<Recipe> getAllRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();

        Cursor cursor = database.query(SqlLiteHelper.TABLE_SEARCHED_RECIPES,
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
        return recipe;
    }

}
