package com.recipesbyingredients.com.recipesbyingredients.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recipesbyingredients.R;

/**
 * Fragment that represents the my ingredients view.
 */
public class MyIngredientsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.my_ingredients_layout, container, false);
        return myView;
    }
}


