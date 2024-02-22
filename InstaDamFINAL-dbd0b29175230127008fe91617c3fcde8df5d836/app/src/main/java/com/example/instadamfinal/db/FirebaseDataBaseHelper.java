package com.example.instadamfinal.db;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.instadamfinal.models.Publicacion;
import com.example.instadamfinal.models.Usuario;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class FirebaseDataBaseHelper {
    @SuppressLint("RestrictedApi")
    public void crearNuevoUsuarioFirebaseHelper(String nombreUsuario, String emailUsuario){
        //Aqui crear una clase independiente para utilizar todo de firebase.
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //Publicaciones iniciales/ejemplos
        Publicacion publicacionEjemplo = new Publicacion(0,"default_user.jpg");
        Publicacion publicacionEjemplo2 = new Publicacion(50,"vacaciones_2023.jpg");
        List<Publicacion> publicaciones = new LinkedList<>();
        publicaciones.add(publicacionEjemplo);
        publicaciones.add(publicacionEjemplo2);
                /*
                De esta manera, Firebase adapta el objeto Usuario, que en su interior tiene la listas de publicaciones
                .Es la manera optima y mas recomendada para subir los datos que ofrece firebase.
                NO RECOMIENDA EL USO DE SET Y MAPS
                 */
        Usuario usuarioModelo = new Usuario(nombreUsuario,emailUsuario,"default_user.jpg",
                new Timestamp(new Date()),publicaciones);

                /*
                Aqui añadiriamos un nuevo documento a la base de datos usuarios db, con los datos del usuario
                que hemos añadido a la base de datos local, porque con este usuario de firebase administraremos
                las publicaciones.

                 */
        db.collection("usuarios_db").document("usuario_"+emailUsuario)
                .set(usuarioModelo)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e -> Log.w(TAG, "Error writing document", e));
    }



    public interface MyCallback {
        void onCallback(Usuario usuario);
    }

    public void cargarDatosUsuarioFirebaseHelper(){

    }
}
