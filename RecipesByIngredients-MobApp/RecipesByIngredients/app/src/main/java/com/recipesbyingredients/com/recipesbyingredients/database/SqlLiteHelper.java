package com.recipesbyingredients.com.recipesbyingredients.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Helper for sql lite database.
 */
public class SqlLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_INGREDIENTS = "ingredients";
    public static final String TABLE_SAVED_RECIPES = "saved_recipes";
    public static final String TABLE_SEARCHED_RECIPES = "searched_recipes";

    public static final String COLUMN_ID_INGREDIENTS = "id";
    public static final String COLUMN_NAME_INGREDIENTS = "name";

    public static final String COLUMN_ID_RECIPES = "id";
    public static final String COLUMN_TITTLE_RECIPES = "tittle";
    public static final String COLUMN_URL_RECIPES="url";
    public static final String COLUMN_IMAGE_URL_RECIPES="image_url";
    public static final String COLUMN_INSTRUCTIONS_RECIPES="instructions";
    public static final String COLUMN_CATEGORY_RECIPES="category";
    public static final String COLUMN_RECIPE_YIELD_RECIPES="recipe_yield";
    public static final String COLUMN_TIME_OF_COOKING_RECIPES="time_of_cooking";
    public static final String COLUMN_INGREDIENTS_RECIPES="ingredients";

    public static final String COLUMN_ID_SEARCHED_RECIPES="id";

    private static final String DATABASE_NAME = "recipesbyingredientsdatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_FILE_LOCATION="create-db.sql";

    // Ingredients table creation sql statement
    private static final String CREATE_INGREDIENTS_TABLE = "create table "
            + TABLE_INGREDIENTS + "( " + COLUMN_ID_INGREDIENTS
            + " integer primary key, " + COLUMN_NAME_INGREDIENTS
            + " text not null " +
            ");";

    // Recipes table creation sql statement
    private static final String CREATE_RECIPES_TABLE = "create table "
            + TABLE_SAVED_RECIPES + "( " + COLUMN_ID_RECIPES
            + " integer primary key, " + COLUMN_TITTLE_RECIPES
            + " text, " + COLUMN_URL_RECIPES +
            " text, " + COLUMN_IMAGE_URL_RECIPES +
            " text, " + COLUMN_RECIPE_YIELD_RECIPES +
            " text, " + COLUMN_CATEGORY_RECIPES +
            " text, " + COLUMN_TIME_OF_COOKING_RECIPES +
            " text, " + COLUMN_INSTRUCTIONS_RECIPES +
            " text, " + COLUMN_INGREDIENTS_RECIPES +
            " text " +
            ");";

    // Searched recipes creation sql statement
    private static final String CREATE_SEARCHED_RECIPES_TABLE = "create table "
            + TABLE_SEARCHED_RECIPES + "( " + COLUMN_ID_SEARCHED_RECIPES
            + " integer primary key " +
            ");";

    public SqlLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_INGREDIENTS_TABLE);
        database.execSQL(CREATE_RECIPES_TABLE);
        database.execSQL(CREATE_SEARCHED_RECIPES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SqlLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVED_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEARCHED_RECIPES);
        onCreate(db);
    }
}
