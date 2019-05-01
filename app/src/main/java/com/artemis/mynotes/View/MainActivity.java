package com.artemis.mynotes.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.artemis.mynotes.Fragment.AddNoteFragment;
import com.artemis.mynotes.Fragment.NotesFragment;
import com.artemis.mynotes.Fragment.ProfileFragment;
import com.artemis.mynotes.Fragment.RecipeAddFragment;
import com.artemis.mynotes.Fragment.RecipeShowFragment;
import com.artemis.mynotes.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseAuth oUser = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_content, new AddNoteFragment());
        transaction.commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        Fragment selectedFragment = null;

        if (id == R.id.nav_notes) {
            selectedFragment = new NotesFragment();

        }else if (id == R.id.nav_add_notes) {
            selectedFragment = new AddNoteFragment();

        }else if (id == R.id.nav_profile) {
            selectedFragment = new ProfileFragment();

        }else if (id == R.id.nav_add_recipe) {
            selectedFragment = new RecipeAddFragment();

        }else if (id == R.id.nav_show_recipe) {
            selectedFragment = new RecipeShowFragment();

        }else if (id == R.id.nav_log_out) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Logging Out")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        oUser.signOut();
                        if(oUser.getCurrentUser() == null) {
                            startActivity(new Intent(MainActivity.this, SignInActivity.class));
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();

        }else if (id == R.id.nav_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        }

        if (selectedFragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_content, selectedFragment);
            transaction.commit();
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
