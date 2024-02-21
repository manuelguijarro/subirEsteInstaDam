package com.example.instadamfinal.controllers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.FirebaseStorage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class FirebaseManager {

    private static final String TAG = "FirebaseManager";

    // Subir una imagen a Firebase Storage
    public static String uploadImage(Bitmap imageBitmap, String imageName) {
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://instadam-76807.appspot.com");

        StorageReference storageRef = storage.getReference().child("imagenes").child(imageName);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        String urlImagenPerfil = "gs://instadam-76807.appspot.com/imagenes/"+imageName;
        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnFailureListener(exception -> {
            Log.e(TAG, "uploadImage: Failed to upload image", exception);

        }).addOnSuccessListener(taskSnapshot -> {
            Log.d(TAG, "uploadImage: Image uploaded successfully");

        });
        return urlImagenPerfil;
    }

    // Descargar una imagen de Firebase Storage
    public static void downloadImage(String imageName, final OnImageDownloadListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://instadam-76807.appspot.com");
        StorageReference storageRef = storage.getReference().child("imagenes").child(imageName);


        final long ONE_MEGABYTE = 1024 * 1024;
        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            listener.onImageDownload(bitmap);
        }).addOnFailureListener(exception -> {
            Log.e(TAG, "downloadImage: Failed to download image", exception);
            listener.onImageDownload(null);
        });
    }

    // Interfaz para manejar la descarga de imágenes
    public interface OnImageDownloadListener {
        void onImageDownload(Bitmap bitmap);
    }

    // Conexión a Firebase Firestore
    /*
    public static FirebaseFirestore getFirestoreInstance() {
        return FirebaseFirestore.getInstance();
    }

    public static void addDocument(String collectionName, Map<String, Object> documentData,
                                   OnSuccessListener<Void> successListener,
                                   OnFailureListener failureListener) {
        FirebaseFirestore db = getFirestoreInstance();
        db.collection(collectionName).document()
                .set(documentData)
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener);
    }
    */

}