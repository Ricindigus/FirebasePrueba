package com.example.dmorales.firebaseprueba;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dmorales.firebaseprueba.pojos_firebase.Curso;
import com.example.dmorales.firebaseprueba.pojos_firebase.Persona;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView txtId;
    TextView txtNombres, txtApellidos, txtCorreo, txtTelefono;
    FirebaseFirestore firebaseFirestore;
    String idUsuario;
    Button btnCerrarSesion;
    RecyclerView recyclerView;
    ListenerRegistration firestoreListener;
    String TAG = "FIREBASE FIRESTORE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idUsuario = getIntent().getExtras().getString("usuario");
        firebaseFirestore = FirebaseFirestore.getInstance();

        txtId =  (TextView) findViewById(R.id.txtId);
        txtNombres =  (TextView) findViewById(R.id.txtNombres);
        txtApellidos =  (TextView) findViewById(R.id.txtApellidos);
        txtCorreo =  (TextView) findViewById(R.id.txtCorreo);
        txtTelefono =  (TextView) findViewById(R.id.txtTelefono);
        btnCerrarSesion = (Button) findViewById(R.id.btnCerrarSesion);
        recyclerView =  (RecyclerView) findViewById(R.id.recycler);

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
            }
        });


        firestoreListener = firebaseFirestore.collection("cursos")
                .whereEqualTo("coordinador",idUsuario)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        List<Curso> cursos = new ArrayList<>();
                        for (DocumentSnapshot doc : value) {
                            cursos.add(doc.toObject(Curso.class));
                        }
                        CursoAdapter cursoAdapter =  new CursoAdapter(cursos,MainActivity.this,firebaseFirestore);
                        recyclerView.setAdapter(cursoAdapter);
                    }
                });

        mostrarDatosPersona(idUsuario);
        cargarCursos(idUsuario);
    }

    public void mostrarDatosPersona(final String id){
        DocumentReference docRef = firebaseFirestore.collection("personas").document(id);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Persona persona = documentSnapshot.toObject(Persona.class);
                txtId.setText(id);
                txtNombres.setText(persona.getNombres());
                txtApellidos.setText(persona.getApellidos());
                txtCorreo.setText(persona.getCorreo());
                txtTelefono.setText(persona.getTelefono());
            }
        });
    }

    public void cargarCursos(String id){
        firebaseFirestore.collection("cursos").whereEqualTo("coordinador",id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Curso> cursos = new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                cursos.add(document.toObject(Curso.class));
                            }
                            recyclerView.setHasFixedSize(true);
                            CursoAdapter cursoAdapter =  new CursoAdapter(cursos,MainActivity.this,firebaseFirestore);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(cursoAdapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        firestoreListener.remove();
    }

    //    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        if(currentUser!=null){
//            idUsuario =  currentUser.getEmail();
//            idUsuario = idUsuario.substring(0,idUsuario.indexOf("@"));
//            mostrarDatosPersona(idUsuario);
//            cargarCursos(idUsuario);
//        }
//    }

}
