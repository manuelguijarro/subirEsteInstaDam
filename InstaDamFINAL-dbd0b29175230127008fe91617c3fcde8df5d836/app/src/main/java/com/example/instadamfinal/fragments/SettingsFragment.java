package com.example.instadamfinal.fragments;

import static android.app.Activity.RESULT_OK;

import static com.example.instadamfinal.activities.MainActivity.emailUsuarioStatic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
import com.example.instadamfinal.controllers.DBController;
import com.example.instadamfinal.controllers.EmailController;
import com.example.instadamfinal.controllers.FireStorageController;
import com.example.instadamfinal.controllers.PasswordController;
import com.example.instadamfinal.db.DataBaseHelper;
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
    private TextView textViewMensajeAlerta;
    private EditText editTextTextNombreUsuarioInput;
    private EditText editTextTextEmailUsuarioInput;
    private EditText editTextTextPasswordInput;
    private Button buttonSubirImagenUsuario;
    private Button buttonActualizarDatosUsuario;

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

        //Primero cargamos la base de datos de firebase y luego cargamos todo lo demas.

        cargarDatosUsuarioFirebase(view);

        cargarRecursosFragmento(view);

        cargarEventosOnClickBotones();


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

    private void cargarRecursosFragmento(View view) {
        //TextViews
        textViewNombreUsuario = view.findViewById(R.id.textViewNombreUsuario);
        textViewEmailUsuario = view.findViewById(R.id.textViewEmailUsuario);
        textViewMensajeAlerta = view.findViewById(R.id.textViewMensajeAlertaSettings);
        //Inputs
        editTextTextNombreUsuarioInput = view.findViewById(R.id.editTextTextNombreUsuario);
        editTextTextEmailUsuarioInput = view.findViewById(R.id.editTextTextEmailUsuario);
        editTextTextPasswordInput = view.findViewById(R.id.editTextTextNombreUsuario);
        //Imagenes
        imageViewSubirImagenActualizarInput = view.findViewById(R.id.imageViewSubirImagenActualizar);
        //Botones
        buttonSubirImagenUsuario = view.findViewById(R.id.buttonSubirImagen);
        buttonActualizarDatosUsuario = view.findViewById(R.id.buttonActualizarDatos);
    }

    private void cargarEventosOnClickBotones() {
        buttonSubirImagenUsuario.setOnClickListener(this::actualizarImagenSubida);
        buttonActualizarDatosUsuario.setOnClickListener(this::actualizarDatosUsuario);
    }



    private void actualizarImagenSubida(View view) {
        seleccionarImagenDeGaleria();
    }
    private void seleccionarImagenDeGaleria() {
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
                    Uri imagenGaleriaSeleccionada = data.getData();
                    InputStream imageStream = getActivity().getContentResolver().openInputStream(imagenGaleriaSeleccionada);


                    Bitmap imagenGaleriaBitmap = BitmapFactory.decodeStream(imageStream);

                    imageViewSubirImagenActualizarInput.setImageBitmap(imagenGaleriaBitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private void actualizarDatosUsuario(View view) {
        String nombreUsuario = editTextTextNombreUsuarioInput.getText().toString();
        String emailUsuario = editTextTextEmailUsuarioInput.getText().toString();
        String passwordUsuario = editTextTextPasswordInput.getText().toString();

        if (!nombreUsuario.isEmpty() && !emailUsuario.isEmpty()&& !passwordUsuario.isEmpty())
            if (EmailController.comprobarEmail(emailUsuario)&&
                    PasswordController.comprobarPassword(passwordUsuario)){
                //Ahora hemos verificado que los campos no estan vacios, y que el email y contraseña
                //coinciden con los requisitos.
                DataBaseHelper dataBaseHelper = new DataBaseHelper(this.getContext());
                boolean resultadoExisteEmail = dataBaseHelper.verificarExisteEmailUsuarioHelper(emailUsuario);

                if (!resultadoExisteEmail){
                    //Actualizamos los datos de sqlite

                    //Actualizamos los datos de firebase.


                    //actualizamos el usuario.


                }else
                //Existe el usuario, no podemos actualizar los datos
                    mostrarMensajeAlerta("El e-mail ya existe, no puedes utilizar un e-mail existente.");
            }else
                mostrarMensajeAlerta("El email o contraseña tiene que contener los requisitos mínimos.");
        else
            mostrarMensajeAlerta("No puede haber ningun campo vacío,rellena todos los campos.");
    }
    private void mostrarMensajeAlerta(String mensaje) {
        textViewMensajeAlerta.setText(mensaje);
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
                    cargarDatosActualPerfil();
                } else
                    Log.e(FragmentManager.TAG, "El objeto Usuario es null");

            } else
                Log.e(FragmentManager.TAG, "El documento no existe");
        });
    }

    private void cargarImagenActualPerfil(View view) {

        FireStorageController.descargarImagen(getContext(), usuarioLogeado.getUrlImagenPerfil(), bitmap -> {
            if (bitmap != null) {
                // Aquí es donde debes establecer la imagen en el ImageView
                imageViewPerfilUsuario = view.findViewById(R.id.imageViewPerfilUsuario);
                imageViewPerfilUsuario.post(() -> {
                    imageViewPerfilUsuario.setImageBitmap(bitmap);
                    imageViewPerfilUsuario.setVisibility(View.VISIBLE);
                });
            } else {
                // Maneja el caso en que la descarga falla o no hay imagen
                // Podrías mostrar una imagen predeterminada o hacer otra acción aquí
            }
        });
    }
    private void cargarDatosActualPerfil() {
        textViewNombreUsuario.setText(usuarioLogeado.getUserName());
        textViewEmailUsuario.setText(usuarioLogeado.getEmail());
    }
}
