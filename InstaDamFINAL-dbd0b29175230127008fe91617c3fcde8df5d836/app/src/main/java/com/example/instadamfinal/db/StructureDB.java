package com.example.instadamfinal.db;

public class StructureDB {
    public static final String PERSONAL_DATA_TABLE = "personalData";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "userName";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASS = "password";

    public static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " +StructureDB.PERSONAL_DATA_TABLE + " (" +
                StructureDB.COLUMN_ID + " INTEGER PRIMARY KEY," +
                StructureDB.COLUMN_USERNAME + " TEXT," +
                StructureDB.COLUMN_EMAIL + " TEXT," +
                StructureDB.COLUMN_PASS + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXITS " + StructureDB.PERSONAL_DATA_TABLE;
}
