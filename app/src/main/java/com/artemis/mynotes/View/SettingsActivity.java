package com.artemis.mynotes.View;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.artemis.mynotes.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_change_email:

                break;
            case R.id.settings_change_password:
                break;
            case R.id.settings_change_name:
                break;
            case R.id.settings_device_settings:
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);

                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);

                startActivity(intent);
                break;
            case R.id.settings_delete_account:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Logging Out")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            mUser.delete().addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SettingsActivity.this, "Account Deleted...", Toast.LENGTH_SHORT).show();
                                }
                            });
                            if(mUser == null) {
                                startActivity(new Intent(SettingsActivity.this, SignInActivity.class));
                            }
                        }).setNegativeButton("No", null)
                        .show();
                break;
        }
    }
}
