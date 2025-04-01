package com.example.aplicacionbd.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Dbhelper extends SQLiteOpenHelper {
    public Dbhelper(@Nullable Context context) {
        super(context,Constantes.NAME_BD,null,Constantes.NUM_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constantes.SENTENCIA_TABLA);
        db.execSQL(Constantes.SENTENCIA_TABLA2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Ciudad");

        onCreate(db);

    }
}
