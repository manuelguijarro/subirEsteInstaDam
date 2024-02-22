package com.example.instadamfinal.fragments;

import static android.app.Activity.RESULT_OK;

import static com.example.instadamfinal.activities.MainActivity.emailUsuarioStatic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instadamfinal.R;
import com.example.instadamfinal.controllers.FirebaseManager;
import com.example.instadamfinal.models.Usuario;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class SettingsFragment extends Fragment {
    private ImageView imageViewPerfilUsuario;
    private ImageView imageViewSubirImagenActualizarInput;
    private Bitmap imagenDescargadaPerfil;
    private TextView textViewNombreUsuario;
    private TextView textViewEmailUsuario;
    private EditText editTextTextNombreUsuarioInput;
    private EditText editTextTextEmailUsuarioInput;
    private EditText editTextTextPasswordInput;
    private Usuario usuarioLogeado;

    private static final int SELECT_PHOTO = 100;

    public SettingsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Mostrar Toast de carga(no funciona correctamente en emulador)
        Toast.makeText(getContext(), "Cargando datos...", Toast.LENGTH_SHORT).show();

        cargarDatosUsuarioFirebase(view);










        //Con esto cargariamos la imagen de el almacenamiento cuando el usuario hace click
        //buttonSubirImagen.setOnClickListener(v -> selectImageFromGallery());

        //PRIMERO VAMOS A MEJORAR LA DESCARGA DE IMAGEN.
        // Si el usuario hace click en el boton de enviar datos, entonces la foto
        //se tendrá que subir al servidor y tambien tenerla alojada en una referencia
        //en base de datos, para poder referenciar a la url.
       /* botonEnviarForm.setOnClickListener(v -> {
            if (imagenSubirActualizar != null) {
                FirebaseManager.uploadImage(getContext(),imagenSubirActualizar, "imagen_archivo", new FirebaseManager.MyResponseListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getContext(), "Imagen subida correctamente", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(getContext(), "Error al subir la imagen", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getContext(), "No has seleccionado ninguna imagen", Toast.LENGTH_SHORT).show();
            }
        });*/
        return view;
    }

    @SuppressLint("RestrictedApi")
    private void cargarDatosUsuarioFirebase(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference usuariosDBRef = db.collection("usuarios_db").document("usuario_" + emailUsuarioStatic);

        usuariosDBRef.get().addOnSuccessListener(documentSnapshot -> {
            // Ocultar Toast cuando los datos se hayan descargado(no funciona emulador)
            Toast.makeText(getContext(), "Datos cargados", Toast.LENGTH_SHORT).show();

            if (documentSnapshot.exists()) {
                Usuario usuario = documentSnapshot.toObject(Usuario.class);
                // Verifica si el objeto Usuario es null
                if (usuario != null) {
                    // Usa el objeto Usuario aquí
                    //Realizamos desde aqui los metodos porque nos aseguramso que el usuario se a cargado de la base de datos
                    usuarioLogeado = usuario;
                    cargarImagenActualPerfil(view);
                    cargarDatosActualPerfil(view);
                    cargarRecursosFragmento(view);
                    cargarEventosOnClickBotones(view);

                } else
                    Log.e(FragmentManager.TAG, "El objeto Usuario es null");

            } else
                Log.e(FragmentManager.TAG, "El documento no existe");
        });
    }

    //ESTE METODO LO USAMOS PARA CARGAR CORRECTAAMENTE LA IMAGEN
    private void cargarImagenActualPerfil(View view) {
        imageViewPerfilUsuario = view.findViewById(R.id.imageViewPerfilUsuario);

        FirebaseManager.downloadImage(getContext(), usuarioLogeado.getUrlImagenPerfil(), bitmap -> {
            if (bitmap != null) {
                imagenDescargadaPerfil = bitmap;
                // Aquí es donde debes establecer la imagen en el ImageView
                imageViewPerfilUsuario.post(() -> {
                    imageViewPerfilUsuario.setImageBitmap(imagenDescargadaPerfil);
                    imageViewPerfilUsuario.setVisibility(View.VISIBLE);
                });
            } else {
                // Maneja el caso en que la descarga falla o no hay imagen
                // Podrías mostrar una imagen predeterminada o hacer otra acción aquí
            }
        });
    }
    private void cargarDatosActualPerfil(View view) {
        textViewNombreUsuario = view.findViewById(R.id.textViewNombreUsuario);
        textViewEmailUsuario = view.findViewById(R.id.textViewEmailUsuario);
        //aqui usamos los datos del Usuario:usuarioLogeado
        textViewNombreUsuario.setText(usuarioLogeado.getUserName());
        textViewEmailUsuario.setText(usuarioLogeado.getEmail());
    }

    private void cargarRecursosFragmento(View view) {
        editTextTextNombreUsuarioInput = view.findViewById(R.id.editTextTextNombreUsuario);
        editTextTextEmailUsuarioInput = view.findViewById(R.id.editTextTextEmailUsuario);
        editTextTextPasswordInput = view.findViewById(R.id.editTextTextPassword);
        imageViewSubirImagenActualizarInput = view.findViewById(R.id.imageViewSubirImagenActualizar);
    }

    private void selectImageFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK) {
            if (data != null) {
                try {
                    Uri selectedImage = data.getData();
                    InputStream imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                   // imagenSubirActualizar = BitmapFactory.decodeStream(imageStream);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
