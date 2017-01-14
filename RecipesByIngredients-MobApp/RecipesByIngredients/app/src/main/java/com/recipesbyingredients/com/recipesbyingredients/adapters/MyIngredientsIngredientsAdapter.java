package com.recipesbyingredients.com.recipesbyingredients.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.recipesbyingredients.R;
import com.recipesbyingredients.com.recipesbyingredients.models.Ingredient;

import java.util.ArrayList;

/**
 * Ingredients list adapter used for the list in My ingredients fragment.
 */
public class MyIngredientsIngredientsAdapter extends BaseAdapter {

    private ArrayList<Ingredient> ingredients;
    private Context context;
    private LayoutInflater layoutInflater;

    public MyIngredientsIngredientsAdapter(Context context, ArrayList<Ingredient> ingredients) {
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
            convertView=layoutInflater.inflate(R.layout.my_ingredients_ingredients_list_item_layout,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.ingredientName= (TextView) convertView.findViewById(R.id.my_ingredients_ingredients_list_item_text);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        Ingredient ingredient = ingredients.get(position);
        viewHolder.ingredientName.setText(ingredient.getName());
        return convertView;
    }

    private class ViewHolder{
        public TextView ingredientName;
    }
}
