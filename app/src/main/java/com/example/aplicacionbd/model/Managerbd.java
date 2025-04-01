package com.example.aplicacionbd.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Managerbd {
    Dbhelper dbHelper;
    SQLiteDatabase db;

    public Managerbd(Context context) {
        dbHelper = new Dbhelper(context);
    }

    // Método para abrir la base de datos en modo escritura
    public void openDbWr() {
        db = dbHelper.getWritableDatabase();
    }

    // Método para abrir la base de datos en modo lectura
    public void openDbRd() {
        db = dbHelper.getReadableDatabase();
    }

    // Insertar Ciudad
    public long insertCiudad(int codigo, String nombre, String descripcion) {
        openDbWr();
        ContentValues valores = new ContentValues();
        valores.put("codigo", codigo);
        valores.put("nombre", nombre);
        valores.put("descripcion", descripcion); // Nuevo campo
        long result = db.insert("Ciudad", null, valores);
        db.close();
        return result;
    }

    // Actualizar Ciudad
    public int updateCiudad(int codigo, String nuevoNombre, String nuevaDescripcion) {
        openDbWr();
        ContentValues valores = new ContentValues();
        valores.put("nombre", nuevoNombre);
        int result = db.update("Ciudad", valores, "codigo=?", new String[]{String.valueOf(codigo)});
        db.close();
        return result;
    }

    // Eliminar Ciudad
    public int deleteCiudad(int codigo) {
        openDbWr();
        int result = db.delete("Ciudad", "codigo=?", new String[]{String.valueOf(codigo)});
        db.close();
        return result;
    }

    // Obtener todas las ciudades
    public Cursor getCiudades() {
        openDbRd();
        return db.rawQuery("SELECT * FROM Ciudad", null);
    }
}
