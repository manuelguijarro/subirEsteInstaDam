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
import com.google.firebase.storage.FirebaseStorage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class FirebaseManager {
    public interface MyResponseListener {
        void onSuccess();
        void onFailure();
    }
    public interface OnImageDownloadListener {
        void onImageDownload(Bitmap bitmap);
    }
    private static final String TAG = "FirebaseManager";

    public static void uploadImage(Context context, Bitmap imageBitmap, String imageName, MyResponseListener listener) {
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
    public static void downloadImage(Context context,String imageName, OnImageDownloadListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://instadam-76807.appspot.com");
        StorageReference storageRef = storage.getReference().child("imagenes").child(imageName);
        final long BYTES = 10*(1024 * 1024);
        storageRef.getBytes(BYTES).addOnSuccessListener(bytes -> {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            listener.onImageDownload(bitmap);
        }).addOnFailureListener(exception -> {
            Log.e(TAG, "downloadImage: Failed to download image", exception);
            Toast.makeText(context, "Error al descargar la imagen de perfil", Toast.LENGTH_SHORT).show();
            listener.onImageDownload(null);
        });
    }


    /*

    // Descargar una imagen de Firebase Storage
    public static void downloadImage(String imageName, final OnImageDownloadListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://instadam-76807.appspot.com");
        StorageReference storageRef = storage.getReference().child("imagenes").child(imageName);


        final long BYTES = 10*(1024 * 1024);
        storageRef.getBytes(BYTES).addOnSuccessListener(bytes -> {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            listener.onImageDownload(bitmap);
        }).addOnFailureListener(exception -> {
            Log.e(TAG, "downloadImage: Failed to download image", exception);
            listener.onImageDownload(null);
        });
    }


    */
}