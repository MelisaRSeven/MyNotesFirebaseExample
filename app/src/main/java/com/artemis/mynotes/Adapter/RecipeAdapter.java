package com.artemis.mynotes.Adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.artemis.mynotes.Model.RecipeModel;
import com.artemis.mynotes.Model.UserModel;
import com.artemis.mynotes.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecipeAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<RecipeModel> recipeList;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRef = firebaseDatabase.getReference("MyRecipe");

    public RecipeAdapter(LayoutInflater layoutInflater, ArrayList<RecipeModel> recipeList) {
        this.layoutInflater = layoutInflater;
        this.recipeList = recipeList;
    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public Object getItem(int position) {
        return recipeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = layoutInflater.inflate(R.layout.recipe_layout_design, null);

        TextView recipeName = view.findViewById(R.id.design_recipe_name);

        TextView recipeIngredients = view.findViewById(R.id.design_recipe_ingredients);

        TextView recipeHowTo = view.findViewById(R.id.design_recipe_how_to);

        getCurrentRecipeInfo(recipeName, recipeIngredients, recipeHowTo);

        return view;
    }

    private void getCurrentRecipeInfo(TextView name, TextView ingredients, TextView howTo) {

        ValueEventListener profileListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RecipeModel recipe = dataSnapshot.getValue(RecipeModel.class);
                if (recipe != null) {
                    name.setText(recipe.getRecipeName());
                    ingredients.setText(recipe.getIngredients());
                    howTo.setText(recipe.getHowToDo());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        mRef.addValueEventListener(profileListener);
    }
}
