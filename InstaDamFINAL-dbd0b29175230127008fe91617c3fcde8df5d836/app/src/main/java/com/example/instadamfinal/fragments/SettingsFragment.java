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
    private ImageView imagenPerfilActualizar;
    private Button botonEnviarForm;
    private Button buttonSubirImagen;

    private EditText editTextUserName2;
    private EditText editTextEmailAddress2;
    private EditText editTextPassword2;
    private Bitmap imagenSubirActualizar;


    private static final int SELECT_PHOTO = 100;

    public SettingsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void cargarAtributos(View view) {
        imagenPerfilActualizar = view.findViewById(R.id.imagenPerfilActualizar);
        botonEnviarForm = view.findViewById(R.id.botonEnviarForm);
        buttonSubirImagen = view.findViewById(R.id.buttonSubirImagen);
        editTextUserName2 = view.findViewById(R.id.editTextUserName2);
        editTextEmailAddress2 = view.findViewById(R.id.editTextEmailAddress2);
        editTextPassword2 = view.findViewById(R.id.editTextPassword2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        cargarAtributos(view);
        //Con esto cargariamos la imagen de el almacenamiento cuando el usuario hace click
        buttonSubirImagen.setOnClickListener(v -> selectImageFromGallery());
        // Si el usuario hace click en el boton de enviar datos, entonces la foto
        //se tendrá que subir al servidor y tambien tenerla alojada en una referencia
        //en base de datos, para poder referenciar a la url.
        botonEnviarForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseManager.uploadImage(imagenSubirActualizar,"imagen_archivo");
            }
        });
        return view;
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
                    imagenSubirActualizar = BitmapFactory.decodeStream(imageStream);

                    // Cargar la imagen en un ImageView (para propósitos de demostración)
                    imagenPerfilActualizar.setImageBitmap(imagenSubirActualizar);




                    Map<String, Object> foto_perfil = new HashMap<>();
                    foto_perfil.put("foto_perfil_id", 1);
                    foto_perfil.put("id_usuario", idUsuario);
                    //foto_perfil.put("url_imagen", nombreUrlFotoPerfil);

                    /*
                    FirebaseManager.addDocument("foto_perfiles", foto_perfil,
                            aVoid -> Log.d(TAG, "Document added successfully"),
                            e -> Log.e(TAG, "Error adding document", e));
*/
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
