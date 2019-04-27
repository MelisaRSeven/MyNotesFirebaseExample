package com.artemis.mynotes.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.artemis.mynotes.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPasswordActivity extends AppCompatActivity {
    FirebaseAuth forgetPass = FirebaseAuth.getInstance();

    @BindView(R.id.forgetPass_email_edit)
    EditText forgetPassEmailEdit;
    @BindView(R.id.forgetPass_send_button)
    Button forgetPassSendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.forgetPass_send_button)
    public void onViewClicked() {
        forgetPassSendButton.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus) {
                closeKeyboard(v);
            }
        });

        String emailID = forgetPassEmailEdit.getText().toString().trim();

        forgetPass.sendPasswordResetEmail(emailID).addOnCompleteListener(this, task -> {
            if(task.isSuccessful()) {
                Toast.makeText(ForgetPasswordActivity.this, "E-Mail Sent", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(ForgetPasswordActivity.this, "Failed to send reset password e-mail!", Toast.LENGTH_SHORT).show();
            }
        });

        startActivity(new Intent(ForgetPasswordActivity.this, SignInActivity.class));
    }

    private void closeKeyboard(View view) {
        InputMethodManager keyboardManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        keyboardManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
