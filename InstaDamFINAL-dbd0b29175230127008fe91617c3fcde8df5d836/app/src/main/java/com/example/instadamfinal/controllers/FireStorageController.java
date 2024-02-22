package com.example.instadamfinal.controllers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.example.instadamfinal.listeners.DescargaImagenUsuarioListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FireStorageController {
    public static void descargarImagen(Context context, String imagenNombre, DescargaImagenUsuarioListener descargaImagenUsuarioListener) {
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://instadam-76807.appspot.com");
        StorageReference storageRef = storage.getReference().child("imagenes").child(imagenNombre);
        final long BYTES = 10*(1024 * 1024);
        storageRef.getBytes(BYTES).addOnSuccessListener(bytes -> {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            descargaImagenUsuarioListener.imagenDescargada(bitmap);
        }).addOnFailureListener(e -> {
            Toast.makeText(context, "Error al descargar la imagen de perfil", Toast.LENGTH_SHORT).show();
            descargaImagenUsuarioListener.imagenDescargada(null);
        });
    }
}
