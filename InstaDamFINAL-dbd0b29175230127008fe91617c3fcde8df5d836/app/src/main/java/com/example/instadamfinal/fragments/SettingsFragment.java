package com.example.instadamfinal.fragments;

import static android.app.Activity.RESULT_OK;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.example.instadamfinal.activities.MainActivity.idUsuario;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instadamfinal.R;
import com.example.instadamfinal.controllers.FirebaseManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class SettingsFragment extends Fragment {
    private ImageView imageViewPerfilUsuario;
    private Bitmap imagenDescargadaPerfil;
    private TextView textViewNombreUsuario;
    private TextView textViewEmailUsuario;

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

        cargarImagenActualPerfil(view);
        cargarDatosActualPerfil(view);













        //cargarAtributos(view);
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

    private void cargarDatosActualPerfil(View view) {
        textViewNombreUsuario = view.findViewById(R.id.textViewNombreUsuario);
        textViewEmailUsuario = view.findViewById(R.id.textViewEmailUsuario);
        //
    }

    //ESTE METODO LO USAMOS PARA CARGAR CORRECTAAMENTE LA IMAGEN
    private void cargarImagenActualPerfil(View view) {
        imageViewPerfilUsuario = view.findViewById(R.id.imageViewPerfilUsuario);

        FirebaseManager.downloadImage(getContext(), "imagen_archivo", new FirebaseManager.OnImageDownloadListener() {
            @Override
            public void onImageDownload(Bitmap bitmap) {
                if (bitmap != null) {
                    imagenDescargadaPerfil = bitmap;
                    // Aquí es donde debes establecer la imagen en el ImageView
                    imageViewPerfilUsuario.post(new Runnable() {
                        @Override
                        public void run() {
                            imageViewPerfilUsuario.setImageBitmap(imagenDescargadaPerfil);
                            imageViewPerfilUsuario.setVisibility(View.VISIBLE);
                        }
                    });
                } else {
                    // Maneja el caso en que la descarga falla o no hay imagen
                    // Podrías mostrar una imagen predeterminada o hacer otra acción aquí
                }
            }
        });
    }

    private void cargarAtributos(View view) {
        /*
        imagenPerfilActualizar = view.findViewById(R.id.imagenPerfilActualizar);
        botonEnviarForm = view.findViewById(R.id.botonEnviarForm);
        buttonSubirImagen = view.findViewById(R.id.buttonSubirImagen);
        editTextUserName2 = view.findViewById(R.id.editTextUserName2);
        editTextEmailAddress2 = view.findViewById(R.id.editTextEmailAddress2);
        editTextPassword2 = view.findViewById(R.id.editTextPassword2);*/
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

                    //imagenPerfilActualizar.setImageBitmap(imagenSubirActualizar);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
