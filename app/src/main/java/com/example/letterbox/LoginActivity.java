package com.example.letterbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText email, password;
    private Button login, navigateToSignup, forgotPswd;

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        email = findViewById(R.id.email_login);
        password = findViewById(R.id.pswd_login);

        navigateToSignup = findViewById(R.id.signup_login);
        navigateToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                setResult(RESULT_OK);
                finish();
            }
        });

        login = findViewById(R.id.login_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_username = email.getText().toString();
                String txt_password = password.getText().toString();
                if(TextUtils.isEmpty(txt_username) && TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(LoginActivity.this, "Every Field is required", Toast.LENGTH_SHORT).show();
                } else {
                    auth.signInWithEmailAndPassword(txt_username, txt_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful()) {
                                        try {
                                            throw task.getException();
                                        }catch (FirebaseAuthInvalidUserException e) {
                                            Toast.makeText(LoginActivity.this, "Wrong Email", Toast.LENGTH_SHORT).show();
                                        }catch(FirebaseAuthInvalidCredentialsException e) {
                                            Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                        } catch(Exception e) {
                                            Toast.makeText(LoginActivity.this, "Some Error occured", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, LoadingActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        setResult(RESULT_OK);
//                                        CustomIntent.customType(LogIn.this, "right-to-left");
                                        finish();
                                    }
                                }
                            });
                }
            }
        });

        forgotPswd = findViewById(R.id.forgot_pswd);
        forgotPswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Reset Password")
                        .setMessage("Enter your Email:")
                        .setView(resetMail)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (TextUtils.isEmpty(resetMail.getText().toString())) {
                                    Toast.makeText(LoginActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                                } else {
                                    FirebaseAuth.getInstance().sendPasswordResetEmail(resetMail.getText().toString())
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (!task.isSuccessful()) {
                                                        Toast.makeText(LoginActivity.this, "Email not registered", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(LoginActivity.this, "Password reset mail has been sent to you.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            }
                        });
                builder.create().show();
            }
        });
    }
}