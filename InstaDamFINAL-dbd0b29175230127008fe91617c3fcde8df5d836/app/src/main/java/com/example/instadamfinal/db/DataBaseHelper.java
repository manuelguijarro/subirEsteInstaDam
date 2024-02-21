package com.example.instadamfinal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.instadamfinal.controllers.PasswordController;
import com.example.instadamfinal.models.Usuario;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "users";
    private static final int DATABASE_VERSION = 1;

    public DataBaseHelper (@Nullable Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(StructureDB.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(StructureDB.SQL_DELETE_ENTRIES);
        onCreate(db);
    }


    public String checkEmailAndPasswordHelper(String email, String password) {
        //Aqui ciframos la contraseña, para compararla con la cifrada previamente
        String newPassword = "";
        PasswordController passwordController = new PasswordController();
        newPassword =  passwordController.get_SHA_512_SecurePassword(password,"ambgk");
        Cursor cursor = this.getReadableDatabase().query(
                StructureDB.PERSONAL_DATA_TABLE,
                new String[]{StructureDB.COLUMN_USERNAME, StructureDB.COLUMN_EMAIL, StructureDB.COLUMN_PASS},
                StructureDB.COLUMN_EMAIL + " = ? AND " + StructureDB.COLUMN_PASS + " = ?",
                new String[]{email, newPassword},
                null,
                null,
                StructureDB.COLUMN_EMAIL + " DESC"
        );

        String userName = "";
        while (cursor.moveToNext()){
            userName = cursor.getString(cursor.getColumnIndexOrThrow(StructureDB.COLUMN_USERNAME));
        }

        cursor.close();
        return userName;
    }

    public boolean checkEmailHelper(String email){
        Cursor cursor = this.getReadableDatabase().query(
                StructureDB.PERSONAL_DATA_TABLE,
                new String[]{StructureDB.COLUMN_EMAIL},
                StructureDB.COLUMN_EMAIL+ " = ?",
                new String[]{email},
                null,
                null,
                StructureDB.COLUMN_EMAIL + " DESC"
        );

        int cursorQuanty = cursor.getCount();
        cursor.close();

        return  cursorQuanty > 0;

    }

    public boolean createNewUserHelper(String userName,String email,String password){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //Aqui ciframos la contraseña
        PasswordController passwordController = new PasswordController();
        String newPassword = passwordController.get_SHA_512_SecurePassword(password,"ambgk");
        values.put(StructureDB.COLUMN_USERNAME,userName);
        values.put(StructureDB.COLUMN_EMAIL,email);
        values.put(StructureDB.COLUMN_PASS,newPassword);
        long numID = db.insert(StructureDB.PERSONAL_DATA_TABLE,null,values);
        return numID != -1;
    }


    public Usuario buscarUsuarioPorEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                StructureDB.PERSONAL_DATA_TABLE,
                new String[]{StructureDB.COLUMN_USERNAME, StructureDB.COLUMN_EMAIL},
                StructureDB.COLUMN_EMAIL + " = ?",
                new String[]{email},
                null,
                null,
                null
        );

        Usuario usuario = null;

        if (cursor != null && cursor.moveToFirst()) {
            String nombreUsuario = cursor.getString(cursor.getColumnIndexOrThrow(StructureDB.COLUMN_USERNAME));
            String emailUsuario = cursor.getString(cursor.getColumnIndexOrThrow(StructureDB.COLUMN_EMAIL));
            cursor.close();
        }

        return usuario;
    }
}

