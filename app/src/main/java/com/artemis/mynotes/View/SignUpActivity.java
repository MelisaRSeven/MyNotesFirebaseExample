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

import com.artemis.mynotes.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
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

    String nameId = signUpNameEdit.getText().toString();
    String surnameId = signUpSurnameEdit.getText().toString().trim();
    String emailId = signUpEmailEdit.getText().toString().trim();
    String passwordId = signUpPasswordEdit.getText().toString().trim();
    String passwordRepeatId = signUpPasswordRepeatEdit.getText().toString().trim();

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
                if(!nameId.isEmpty() && !surnameId.isEmpty() && !emailId.isEmpty() && !passwordId.isEmpty() && !passwordRepeatId.isEmpty()) {
                    if(passwordId.equals(passwordRepeatId)) {
                        signUpOperation(emailId, passwordId);
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

    private void signUpOperation(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if(task.isSuccessful()) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                addProfileDatabase(nameId, surnameId, emailId);
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
        String userId = mRef.push().getKey();

        if(userId !=null) {
            mRef.child(userId).child("1").setValue(name);
            mRef.child(userId).child("2").setValue(surname);
            mRef.child(userId).child("3").setValue(email);
        }
    }

    private void closeKeyboard(View view) {
        InputMethodManager keyboardManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        keyboardManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}