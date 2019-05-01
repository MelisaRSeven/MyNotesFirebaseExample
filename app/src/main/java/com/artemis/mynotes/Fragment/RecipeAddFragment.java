package com.artemis.mynotes.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.artemis.mynotes.Model.RecipeModel;
import com.artemis.mynotes.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RecipeAddFragment extends Fragment {
    EditText recipeName, recipeIngredients, recipeHowTo;
    Button saveButton;

    FirebaseDatabase mDataBase = FirebaseDatabase.getInstance();
    DatabaseReference mRef = mDataBase.getReference("MyRecipe");

    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);

        recipeName = view.findViewById(R.id.recipe_name);
        recipeIngredients = view.findViewById(R.id.recipe_ingredients);
        recipeHowTo = view.findViewById(R.id.recipe_how_to);
        saveButton = view.findViewById(R.id.recipe_save);

        saveButton.setOnClickListener(v -> {
            String recipeNameId = recipeName.getText().toString();
            String recipeIngredientsId = recipeIngredients.getText().toString();
            String recipeHowToId = recipeHowTo.getText().toString();

            saveData(recipeNameId, recipeIngredientsId, recipeHowToId);
        });

        return view;
    }

    private void saveData(String recipeName, String recipeIngredient, String recipeHowTo) {
        RecipeModel recipeModel = new RecipeModel(recipeName, recipeIngredient, recipeHowTo);

        String recipeId = mRef.push().getKey();
        String mUid = mUser.getUid();

        if(recipeId != null) {
            mRef.child("recipe").child(mUid).child(recipeId).setValue(recipeModel);
        }
    }

}
