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
import android.widget.TextView;
import android.widget.Toast;

import com.artemis.mynotes.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();


    @BindView(R.id.signIn_email_edit)
    EditText signInEmailEdit;
    @BindView(R.id.signIn_password_edit)
    EditText signInPasswordEdit;
    @BindView(R.id.signIn_forget_password)
    TextView signInForgetPassword;
    @BindView(R.id.signIn_button)
    Button signInButton;
    @BindView(R.id.signIn_ask_account)
    TextView signInAskAccount;
    @BindView(R.id.signIn_sign_up)
    TextView signInSignUp;
    @BindView(R.id.signIn_RL)
    RelativeLayout signInRL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        if(mUser != null) {
            startActivity(new Intent(SignInActivity.this, MainActivity.class));
        }

        signInRL.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus) {
                closeKeyboard(v);
            }
        });
    }

    @OnClick({R.id.signIn_forget_password, R.id.signIn_button, R.id.signIn_ask_account, R.id.signIn_sign_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.signIn_forget_password:
                startActivity(new Intent(SignInActivity.this, ForgetPasswordActivity.class));
                break;
            case R.id.signIn_button:
                String emailId = signInEmailEdit.getText().toString().trim();
                String passwordId = signInPasswordEdit.getText().toString().trim();

                if(!emailId.isEmpty() && !passwordId.isEmpty()) {
                    loginOperation(emailId, passwordId);
                }else {
                    Toast.makeText(this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.signIn_ask_account:
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                break;
            case R.id.signIn_sign_up:
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                break;
        }
    }

    private void loginOperation(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                startActivity(new Intent(SignInActivity.this, MainActivity.class));
            }
        }).addOnFailureListener(e -> {
            if(e instanceof FirebaseAuthInvalidCredentialsException) {
                Toast.makeText(SignInActivity.this, "Invalid E-Mail or Password", Toast.LENGTH_SHORT).show();
            }else if(e instanceof FirebaseAuthInvalidUserException) {
                Toast.makeText(SignInActivity.this, "Invalid E-Mail or Password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void closeKeyboard(View view) {
        InputMethodManager keyboardManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        keyboardManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
