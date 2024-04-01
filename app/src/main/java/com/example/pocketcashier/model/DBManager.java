package com.example.pocketcashier.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_database";
    private static final int DATABASE_VERSION = 1;

    // Sentencia SQL para crear la tabla de productos
    private static final String SQL_CREATE_CATEGORY_TABLE =
            "CREATE TABLE IF NOT EXISTS Categories (\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    name TEXT NOT NULL,\n" +
                    "    image TEXT\n" +
                    ");";

    private static final String SQL_CREATE_PRODUCTS_TABLE =
            "CREATE TABLE IF NOT EXISTS Products (\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    name TEXT NOT NULL,\n" +
                    "    unit_price REAL NOT NULL,\n" +
                    "    serial_number TEXT NOT NULL,\n" +
                    "    quantity INTEGER NOT NULL\n" +
                    ");\n";





    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DBManager(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crea todas las tablas si no existen
        db.execSQL(SQL_CREATE_CATEGORY_TABLE);
        db.execSQL(SQL_CREATE_PRODUCTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Implementa la lógica de actualización de la base de datos si es necesario
        // Aquí puedes realizar cambios en la estructura de la base de datos
        // como añadir nuevas columnas o modificar las existentes
    }


}

