package com.artemis.mynotes.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.artemis.mynotes.Model.UserModel;
import com.artemis.mynotes.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser;
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("MyNote");

    @BindView(R.id.signUp_name_edit)
    EditText signUpNameEdit;
    @BindView(R.id.signUp_surname_edit)
    EditText signUpSurnameEdit;
    @BindView(R.id.signUp_email_edit)
    EditText signUpEmailEdit;
    @BindView(R.id.signUp_password_edit)
    EditText signUpPasswordEdit;
    @BindView(R.id.signUp_password_repeat_edit)
    EditText signUpPasswordRepeatEdit;
    @BindView(R.id.signUp_button)
    Button signUpButton;
    @BindView(R.id.signUp_already_have_account)
    TextView signUpAlreadyHaveAccount;
    @BindView(R.id.signUp_RL)
    RelativeLayout signUpRL;

    String nameId, surnameId, emailId, passwordId, passwordRepeatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        signUpRL.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus) {
                closeKeyboard(v);
            }
        });
    }

    @OnClick({R.id.signUp_button, R.id.signUp_already_have_account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.signUp_button:
                nameId = signUpNameEdit.getText().toString();
                surnameId = signUpSurnameEdit.getText().toString();
                emailId = signUpEmailEdit.getText().toString().trim();
                passwordId = signUpPasswordEdit.getText().toString().trim();
                passwordRepeatId = signUpPasswordRepeatEdit.getText().toString().trim();
                if(!nameId.isEmpty() && !surnameId.isEmpty() && !emailId.isEmpty() && !passwordId.isEmpty() && !passwordRepeatId.isEmpty()) {
                    if(passwordId.equals(passwordRepeatId)) {
                        signUpOperation(emailId, passwordId, nameId, surnameId);
                    }else {
                        Toast.makeText(this, "Passwords does not match!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.signUp_already_have_account:
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                break;
        }
    }

    private void signUpOperation(String email, String password, String name, String surname) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if(task.isSuccessful()) {
                mUser = FirebaseAuth.getInstance().getCurrentUser();
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                addProfileDatabase(name, surname, email);
                Toast.makeText(SignUpActivity.this, "Signed up successfully! ", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(this, e -> {
            if(e instanceof FirebaseAuthException) {
                if(((FirebaseAuthException)e).getErrorCode().equals("ERROR_INVALID_EMAIL")) {
                    Toast.makeText(SignUpActivity.this, "Invalid Mail or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addProfileDatabase(String name, String surname, String email) {
        String userId = mUser.getUid();

        UserModel userModel = new UserModel(name, surname, email);
        mRef.child("user").child(userId).setValue(userModel);
    }

    private void closeKeyboard(View view) {
        InputMethodManager keyboardManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        keyboardManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
