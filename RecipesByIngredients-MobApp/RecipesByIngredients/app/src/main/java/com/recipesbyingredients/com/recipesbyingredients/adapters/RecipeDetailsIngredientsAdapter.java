package com.recipesbyingredients.com.recipesbyingredients.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.recipesbyingredients.R;
import com.recipesbyingredients.com.recipesbyingredients.models.IngredientDescription;

import java.util.List;

/**
 * Adapter for the ingredients in the details view.
 */
public class RecipeDetailsIngredientsAdapter extends BaseAdapter{

    private List<IngredientDescription> ingredients;
    private Context context;
    private LayoutInflater layoutInflater;

    public RecipeDetailsIngredientsAdapter(Context context, List<IngredientDescription> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public Object getItem(int position) {
        return ingredients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView==null){
            convertView = layoutInflater.inflate(R.layout.my_recipes_recipes_details_ingredients_item_layout,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.ingredientDescription = (TextView) convertView.findViewById(R.id.my_recipes_ingredients_list_item_text_view);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        IngredientDescription ingredientDescription = ingredients.get(position);
        viewHolder.ingredientDescription.setText(ingredientDescription.getDescription());
        return convertView;
    }

    private class ViewHolder{
        public TextView ingredientDescription;
    }
}
