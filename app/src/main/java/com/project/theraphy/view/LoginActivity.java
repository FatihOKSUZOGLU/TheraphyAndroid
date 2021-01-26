package com.project.theraphy.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.theraphy.R;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText emailText , passText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        emailText= findViewById(R.id.edit_email);
        passText= findViewById(R.id.edit_pass);
        mAuth = FirebaseAuth.getInstance();

    }
    public void Login(View view){
        String mail = emailText.getText().toString();
        String pass = passText.getText().toString();
        if (mail.contains("@") & mail.contains(".com") & pass.length()>=6 ) {
            mAuth.signInWithEmailAndPassword(mail, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this, "Login failed!",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        }else {
            Toast.makeText(LoginActivity.this, "Login failed!",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public void Register(View view){
        Intent intent = new Intent(getApplicationContext(),Register.class);
        startActivity(intent);

    }
    public void Back(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);

    }

}