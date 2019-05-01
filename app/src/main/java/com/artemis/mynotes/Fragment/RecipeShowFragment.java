package com.artemis.mynotes.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.artemis.mynotes.Adapter.RecipeAdapter;
import com.artemis.mynotes.Model.RecipeModel;
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

public class RecipeShowFragment extends Fragment {
    RecipeAdapter recipeAdapter;

    ListView recipeListView;

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = mDatabase.getReference("MyRecipe");

    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_show, container, false);

        recipeListView = view.findViewById(R.id.recipe_list_view);

        ArrayList<RecipeModel> recipeList = new ArrayList<>();
        recipeAdapter = new RecipeAdapter(getLayoutInflater(), recipeList);

        return view;
    }

}
