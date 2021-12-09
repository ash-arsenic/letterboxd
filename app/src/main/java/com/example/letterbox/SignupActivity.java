package com.example.letterbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignupActivity extends AppCompatActivity {


    private Button signup, navigateToLogin;
    private TextInputEditText name, email, password;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.user_name);
        email = findViewById(R.id.email_signup);
        password = findViewById(R.id.pswd_signup);

        navigateToLogin = findViewById(R.id.login_signup);
        navigateToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                setResult(RESULT_OK);
//                CustomIntent.customType(SignupActivity.this, "right-to-left");
                finish();
            }
        });

        auth = FirebaseAuth.getInstance();

        signup = findViewById(R.id.signup_btn);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_firstName = name.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                if(TextUtils.isEmpty(txt_firstName) && TextUtils.isEmpty(txt_email) && TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(SignupActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    auth.createUserWithEmailAndPassword(txt_email, txt_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful()) {
                                        try {
                                            throw task.getException();
                                        }catch(FirebaseAuthInvalidCredentialsException e) {
                                            Toast.makeText(SignupActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                                        } catch(FirebaseAuthUserCollisionException e) {
                                            Toast.makeText(SignupActivity.this, "Already Exist", Toast.LENGTH_SHORT).show();
                                        } catch(Exception ex) {
                                            Toast.makeText(SignupActivity.this, "Some Error occurred, please try again!", Toast.LENGTH_SHORT).show();
                                        }
                                    }  else {
                                        auth.getCurrentUser().sendEmailVerification();
                                        Toast.makeText(SignupActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        setResult(RESULT_OK);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });name = findViewById(R.id.user_name);
        email = findViewById(R.id.email_signup);
        password = findViewById(R.id.pswd_signup);

        navigateToLogin = findViewById(R.id.login_signup);
        navigateToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                setResult(RESULT_OK);
                finish();
            }
        });

        auth = FirebaseAuth.getInstance();

        signup = findViewById(R.id.signup_btn);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_firstName = name.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                if(TextUtils.isEmpty(txt_firstName) && TextUtils.isEmpty(txt_email) && TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(SignupActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    auth.createUserWithEmailAndPassword(txt_email, txt_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful()) {
                                        try {
                                            throw task.getException();
                                        }catch(FirebaseAuthInvalidCredentialsException e) {
                                            Toast.makeText(SignupActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                                        } catch(FirebaseAuthUserCollisionException e) {
                                            Toast.makeText(SignupActivity.this, "Already Exist", Toast.LENGTH_SHORT).show();
                                        } catch(Exception ex) {
                                            Toast.makeText(SignupActivity.this, "Some Error occurred, please try again!", Toast.LENGTH_SHORT).show();
                                        }
                                    }  else {
                                        auth.getCurrentUser().sendEmailVerification();
                                        Toast.makeText(SignupActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        setResult(RESULT_OK);
//                                        CustomIntent.customType(SignupActivity.this, "right-to-left");
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }
}