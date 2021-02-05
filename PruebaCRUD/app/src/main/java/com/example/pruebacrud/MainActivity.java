package com.example.pruebacrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText textemail;
    private EditText textcontrasenia;
    private Button buttonregistrar, buttonlogin;
    private ProgressDialog progressDialog;
    //private Button buttonlogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicializamos el objeto firebaseAuth
        mAuth = FirebaseAuth.getInstance();

        //Referenciamos views
        textemail = (EditText) findViewById(R.id.txt_email);
        textcontrasenia = (EditText) findViewById(R.id.txt_contrasenia);

        buttonregistrar = (Button) findViewById(R.id.btn_registrar);
        buttonlogin = (Button) findViewById(R.id.btn_ingresar);

        progressDialog = new ProgressDialog(this);

        buttonregistrar.setOnClickListener(this);
        buttonlogin.setOnClickListener(this);
    }

    private void registrarUsuario(){
        //Obtenemos email y contraseña
        String email = textemail.getText().toString().trim();
        String contrasenia = textcontrasenia.getText().toString().trim();

        //Verificamos q no este vacio
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Se debe ingresar un email",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(contrasenia)){
            Toast.makeText(this, "Se debe ingresar una contraseña",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Realizando registro...");
        progressDialog.show();

        //Creación nuevo usuario
        mAuth.createUserWithEmailAndPassword(email, contrasenia)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Se ha registrado el email",Toast.LENGTH_LONG).show();
                        }else{
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {//Excepcion para usuarios ya creados
                                Toast.makeText(MainActivity.this, "Usuario ya registrado", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MainActivity.this,"No se pudo registrar el usuario",Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    private void ingresarUsuario(){
        //Obtenemos email y contraseña
        String email = textemail.getText().toString().trim();
        String contrasenia = textcontrasenia.getText().toString().trim();

        //Verificamos q no este vacio
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Se debe ingresar un email",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(contrasenia)){
            Toast.makeText(this, "Se debe ingresar una contraseña",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Ingresando...");
        progressDialog.show();

        //Consultar usuario
        mAuth.signInWithEmailAndPassword(email, contrasenia)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            Toast.makeText(MainActivity.this,"Bienvenido",Toast.LENGTH_LONG).show();
                            Intent intencion = new Intent(getApplication(),AgendaActivity.class);
                            //intencion.putExtra(AgendaActivity.user, email);
                            startActivity(intencion);

                        }else{
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {//Excepcion para usuarios ya creados
                                Toast.makeText(MainActivity.this, "Usuario ya registrado", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MainActivity.this,"No se pudo registrar el usuario",Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }


    @Override
    public void onClick(View view){

        switch (view.getId()){

            case R.id.btn_registrar:
                //Invocamos el metodo
                registrarUsuario();
                break;

            case R.id.btn_ingresar:
                ingresarUsuario();
                break;
        }


    }
}