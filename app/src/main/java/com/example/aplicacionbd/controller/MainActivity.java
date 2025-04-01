package com.example.aplicacionbd.controller;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.aplicacionbd.R;
import com.example.aplicacionbd.model.Managerbd;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText etCodigo, etNombre;
    Button btnInsertar, btnActualizar, btnEliminar;
    ListView listViewCiudades;
    ArrayList<String> listaCiudades;
    ArrayAdapter<String> adapter;
    Managerbd managerbd;
    private View etDescripcion;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCodigo = findViewById(R.id.etCodigo);
        etNombre = findViewById(R.id.etNombre);
        btnInsertar = findViewById(R.id.btnInsertar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);
        listViewCiudades = findViewById(R.id.listViewCiudades);
        etDescripcion = findViewById(R.id.btnInsertar);

        managerbd = new Managerbd(this);
        listaCiudades = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCiudades);
        listViewCiudades.setAdapter(adapter);

        cargarCiudades();

        // Insertar Ciudad
        btnInsertar.setOnClickListener(v -> {
            int codigo = Integer.parseInt(etCodigo.getText().toString());
            String nombre = etNombre.getText().toString();
            String descripcion = String.valueOf(etDescripcion.getTextDirection());

            long resultado = managerbd.insertCiudad(codigo, nombre, descripcion);
            if (resultado > 0) {
                Toast.makeText(this, "Ciudad insertada", Toast.LENGTH_SHORT).show();
                cargarCiudades();
            } else {
                Toast.makeText(this, "Error al insertar", Toast.LENGTH_SHORT).show();
            }
        });
        // Actualizar Ciudad
        btnActualizar.setOnClickListener(v -> {
            int codigo = Integer.parseInt(etCodigo.getText().toString());
            String nuevoNombre = etNombre.getText().toString();
            String nuevaDescripcion = String.valueOf(etDescripcion.getTextDirection());

            int resultado = managerbd.updateCiudad(codigo, nuevoNombre, nuevaDescripcion);
            if (resultado > 0) {
                Toast.makeText(this, "Ciudad actualizada", Toast.LENGTH_SHORT).show();
                cargarCiudades();
            } else {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        });

        // Eliminar Ciudad
        btnEliminar.setOnClickListener(v -> {
            int codigo = Integer.parseInt(etCodigo.getText().toString());
            int resultado = managerbd.deleteCiudad(codigo);
            if (resultado > 0) {
                Toast.makeText(this, "Ciudad eliminada", Toast.LENGTH_SHORT).show();
                cargarCiudades();
            } else {
                Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show();
            }
        });

        // Seleccionar ciudad de la lista
        listViewCiudades.setOnItemClickListener((parent, view, position, id) -> {
            String selected = listaCiudades.get(position);
            String[] parts = selected.split(" - ");
            etCodigo.setText(parts[0]);
            etNombre.setText(parts[1].split(" \\(")[0]); // Obtener nombre sin paréntesis
            etDescripcion.setTextDirection(Integer.parseInt(parts[1].split(" \\(")[1].replace(")", ""))); // Obtener descripción
        });

    }

    // Método para cargar ciudades en la lista
    private void cargarCiudades() {
        listaCiudades.clear();
        Cursor cursor = managerbd.getCiudades();
        if (cursor.moveToFirst()) {
            do {
                int codigo = cursor.getInt(0);
                String nombre = cursor.getString(1);
                String descripcion = cursor.getColumnCount() > 2 ? cursor.getString(2) : "Sin descripción"; // Evita el error
                String ciudad = codigo + " - " + nombre + " - " + descripcion;
                listaCiudades.add(ciudad);
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }


}
