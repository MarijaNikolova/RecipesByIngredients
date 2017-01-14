package com.recipesbyingredients.com.recipesbyingredients.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.recipesbyingredients.R;
import com.recipesbyingredients.com.recipesbyingredients.models.Ingredient;
import com.recipesbyingredients.com.recipesbyingredients.models.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Recipes list adapter used for the list in My recipes fragment.
 */
public class MyRecipesRecipesAdapter extends BaseAdapter{

    private List<Recipe> recipes;
    private Context context;
    private LayoutInflater layoutInflater;

    public MyRecipesRecipesAdapter(Context context, List<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public Object getItem(int position) {
        return recipes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView==null){
            convertView = layoutInflater.inflate(R.layout.my_recipes_recipes_list_item_layout,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.recipeTitle = (TextView) convertView.findViewById(R.id.my_recipes_recipes_list_item_text_view);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        Recipe recipe = recipes.get(position);
        viewHolder.recipeTitle.setText(recipe.getTitle());
        return convertView;
    }

    private class ViewHolder{
        public TextView recipeTitle;
    }
}
