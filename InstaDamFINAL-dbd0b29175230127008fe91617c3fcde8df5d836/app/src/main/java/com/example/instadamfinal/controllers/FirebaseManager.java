package com.example.instadamfinal.controllers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FirebaseManager {
    public interface respuestaSubirImagenListener {
        void onSuccess();
        void onFailure();
    }

    private static final String TAG = "FirebaseManager";

    public static void uploadImage(Context context, Bitmap imageBitmap, String imageName, respuestaSubirImagenListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://instadam-76807.appspot.com");
        StorageReference storageRef = storage.getReference().child("imagenes").child(imageName);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                //Log.d(TAG, "Upload is " + progress + "% done");
                Toast.makeText(context, "Upload is " + progress + "% done", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(exception -> {
            Log.e(TAG, "uploadImage: Failed to upload image", exception);
            listener.onFailure();
        }).addOnSuccessListener(taskSnapshot -> {
            Log.d(TAG, "uploadImage: Image uploaded successfully");
            listener.onSuccess();
        });
    }
    public interface OnImagesDownloadListener {
        void onImagesDownloaded(List<Bitmap> bitmaps);
    }




    public static void downloadImages(Context context, List<String> imageUrls, OnImagesDownloadListener listener) {
        List<Bitmap> bitmaps = new ArrayList<>();

        FirebaseStorage storage = FirebaseStorage.getInstance("gs://instadam-76807.appspot.com");

        for (String imageUrl : imageUrls) {
            StorageReference storageRef = storage.getReference().child("imagenes").child(imageUrl);
            final long BYTES = 10 * (1024 * 1024);

            storageRef.getBytes(BYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    bitmaps.add(bitmap);

                    // Verificar si hemos descargado todas las imágenes
                    if (bitmaps.size() == imageUrls.size()) {
                        listener.onImagesDownloaded(bitmaps);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Error al descargar una imagen", Toast.LENGTH_SHORT).show();

                    // Si falla la descarga de una imagen, simplemente omitimos esa imagen
                    // Verificar si hemos descargado todas las imágenes
                    if (bitmaps.size() == imageUrls.size()) {
                        listener.onImagesDownloaded(bitmaps);
                    }
                }
            });
        }
    }
}