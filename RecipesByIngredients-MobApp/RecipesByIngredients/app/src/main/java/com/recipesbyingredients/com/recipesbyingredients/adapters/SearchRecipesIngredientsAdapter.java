package com.recipesbyingredients.com.recipesbyingredients.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.recipesbyingredients.R;
import com.recipesbyingredients.com.recipesbyingredients.models.Ingredient;

import java.util.ArrayList;

/**
 * Ingredients list adapter used for the list in Search fragment.
 */
public class SearchRecipesIngredientsAdapter extends BaseAdapter {

    private ArrayList<Ingredient> ingredients;
    private Context context;
    private LayoutInflater layoutInflater;

    public SearchRecipesIngredientsAdapter(Context context, ArrayList<Ingredient> ingredients) {
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
            convertView = layoutInflater.inflate(R.layout.search_recipes_ingredients_list_item_layout,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.isIngredientChecked = (CheckBox) convertView.findViewById(R.id.search_fragment_item_list_layout_checkbox);
            viewHolder.ingredientName = (TextView) convertView.findViewById(R.id.search_fragment_item_list_layout_textview);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        Ingredient ingredient = ingredients.get(position);
        viewHolder.ingredientName.setText(ingredient.getName());
        viewHolder.isIngredientChecked.setChecked(ingredient.isChecked());
        setOnClickListenerToCheckBox(viewHolder, ingredient);
        return convertView;
    }

    private class ViewHolder{
        public CheckBox isIngredientChecked;
        public TextView ingredientName;
    }

    private void setOnClickListenerToCheckBox(ViewHolder viewHolder, final Ingredient ingredient) {
        viewHolder.isIngredientChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ingredient.setIsChecked(isChecked);
            }
        });
    }
}
