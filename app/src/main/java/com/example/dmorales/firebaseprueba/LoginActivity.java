package com.example.dmorales.firebaseprueba;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsuario;
    EditText edtPassword;
    Button btnIngresar;
    String TAG = "FIREBASE AUTETICACION";
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsuario = (EditText) findViewById(R.id.login_edt_usuario);
        edtPassword= (EditText) findViewById(R.id.login_edt_password);
        btnIngresar = (Button) findViewById(R.id.login_btn_ingresar);


        firebaseAuth = FirebaseAuth.getInstance();
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion(edtUsuario.getText().toString()+ "@fisi.edu.pe", edtPassword.getText().toString());
            }
        });

    }

    public void iniciarSesion(String email, String password)
    {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            ingresar(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            ingresar(null);
                        }

                        // ...
                    }
                });
    }

    public void ingresar(FirebaseUser user)
    {
        if(user != null){
            Intent intent =  new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("usuario",user.getEmail().substring(0,user.getEmail().indexOf("@")));
            startActivity(intent);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        ingresar(currentUser);
    }
}
