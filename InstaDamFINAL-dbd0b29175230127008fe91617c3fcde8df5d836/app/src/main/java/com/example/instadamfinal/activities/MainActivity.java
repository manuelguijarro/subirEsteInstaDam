package com.example.instadamfinal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.instadamfinal.R;
import com.example.instadamfinal.db.DataBaseHelper;
import com.example.instadamfinal.fragments.HomeFragment;
import com.example.instadamfinal.fragments.PostFragment;
import com.example.instadamfinal.fragments.SearchFragment;
import com.example.instadamfinal.fragments.SettingsFragment;
import com.example.instadamfinal.models.Usuario;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    public static int idUsuario = 1;

    public static Usuario usuarioLogeado;
    public static ImagenUsuario imagenUsuario ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //DBController dbController = new DBController();
        Intent intent = getIntent();
        String emailUsuario = intent.getStringExtra("key");
        //Crear un metodo en el controlador
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getBaseContext());

        usuarioLogeado = dataBaseHelper.buscarUsuarioPorEmail(emailUsuario);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        try {
            DocumentReference docRef = db.collection("foto_perfiles").document("IMG_USER_"+emailUsuario);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {


                    imagenUsuario = documentSnapshot.toObject(ImagenUsuario.class);
                    loadInitialFragment();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }




        FirebaseApp.initializeApp(this);
        loadToolbar();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

       bottomNavigationView.setOnItemSelectedListener(this::cambioFragmento);
    }


    private boolean cambioFragmento(MenuItem item) {
        int idItemn = item.getItemId();
        if (idItemn == R.id.menu_home){
            fragment = new HomeFragment();
        }
        if(idItemn == R.id.menu_seach){
            fragment = new SearchFragment();
        }
        if (idItemn == R.id.menu_add_post){
            fragment = new PostFragment();
        }
        if (idItemn == R.id.menu_settings){
            fragment = new SettingsFragment();
        }
        if (fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView,fragment)
                    .commit();
        }
        return false;
    }
    private void loadInitialFragment() {
        // Aquí cargas el fragmento inicial o realizas otras operaciones después de obtener la imagen
        // Por ejemplo, podrías cargar el fragmento HomeFragment
        fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commit();
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.bottom_menu, menu);
        return true;
    }

    private void loadToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}