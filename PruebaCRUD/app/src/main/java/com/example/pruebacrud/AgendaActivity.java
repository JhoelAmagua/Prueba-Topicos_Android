package com.example.pruebacrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgendaActivity extends AppCompatActivity {

    private Button boton_enviar, mostrar, eliminar;
    private EditText dato, descrip, date;
    private TextView datos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        dato = findViewById(R.id.txt_dato);
        descrip = findViewById(R.id.txt_descri);
        date = findViewById(R.id.txt_date);
        datos = findViewById(R.id.text1);

        boton_enviar = findViewById(R.id.btn_enviar);
        boton_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarFirebase(dato.getText().toString(), descrip.getText().toString(), date.getText().toString());
                dato.setText("");
                descrip.setText("");
                date.setText("");
            }
        });

        mostrar=findViewById(R.id.mostrar);
        mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrar();
            }
        });

        eliminar=findViewById(R.id.btn_delete);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });
    }

    public void enviarFirebase (String t, String d, String f){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new user with a first, middle, and last name
        Map<String, Object> user = new HashMap<>();
        user.put("dato", t);
        user.put("descrip", d);
        user.put("date", f);

        // Add a new document with a generated ID
        db.collection("tasks")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
    }

    public void mostrar (){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("tasks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                datos.setText("Tarea 1" + " => " + document.getData());
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void eliminar(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("tasks").document("gjXekXyS7HScER4U17gF").delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AgendaActivity.this,"Eliminado",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AgendaActivity.this,"No eliminado",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}