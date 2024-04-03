package com.example.pocketcashier.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_database";
    private static final int DATABASE_VERSION = 1;

    // Sentencia SQL para crear la tabla de productos

    private static final String SQL_CREATE_PRODUCTS_TABLE =
            "CREATE TABLE IF NOT EXISTS Products (\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    name TEXT NOT NULL,\n" +
                    "    unit_price REAL NOT NULL,\n" +
                    "    quantity INTEGER NOT NULL,\n" +
                    "    serial_number TEXT NOT NULL,\n" +
                    "    image_path TEXT\n" +
                    ");";






    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DBManager(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Implementa la lógica de actualización de la base de datos si es necesario
        // Aquí puedes realizar cambios en la estructura de la base de datos
        // como añadir nuevas columnas o modificar las existentes
    }





    @SuppressLint("Range")
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM Products";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all rows and add to list
        if (cursor.moveToFirst()) {
            do {

                Product product = new Product(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getDouble(cursor.getColumnIndex("unit_price")),
                        cursor.getString(cursor.getColumnIndex("serial_number")),
                        cursor.getInt(cursor.getColumnIndex("quantity")), // quantity
                            cursor.getString(cursor.getColumnIndex("image_path"))
                );
                // Adding product to list
                productList.add(product);
            } while (cursor.moveToNext());
        }

        // Close cursor and database connection
        cursor.close();
        db.close();

        // Return products list
        return productList;
    }

    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("unit_price", product.getUnitPrice());
        values.put("serial_number", product.getSerialNumber());
        values.put("quantity", product.getQuantity());
        values.put("image_path", product.getImagePath());

        // Insert the values into the Products table
        db.insert("Products", null, values);

        // Close the database connection
        db.close();
    }


    public void deleteProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Define the WHERE clause to specify the product to delete
        String selection = "id = ?";
        String[] selectionArgs = { String.valueOf(product.getId()) };

        // Perform the deletion
        db.delete("Products", selection, selectionArgs);

        // Close the database connection
        db.close();
    }


    public void updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("unit_price", product.getUnitPrice());
        values.put("serial_number", product.getSerialNumber());
        values.put("quantity", product.getQuantity());
        values.put("image_path", product.getImagePath());

        // Define the WHERE clause to specify the product to update
        String selection = "id = ?";
        String[] selectionArgs = { String.valueOf(product.getId()) };

        // Perform the update
        db.update("Products", values, selection, selectionArgs);

        // Close the database connection
        db.close();
    }


    public int getNextProductId() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(id) FROM Products", null);

        int nextId = 1; // Default starting ID
        if (cursor != null && cursor.moveToFirst()) {
            nextId = cursor.getInt(0) + 1;
            cursor.close();
        }

        db.close();
        return nextId;
    }

}

