package com.example.sistema_de_rastreo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CrearCuenta extends AppCompatActivity {

    private EditText nombres, apellidos, dni, correo, password, confirmarPassword, celular;
    private Button crearCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        nombres = findViewById(R.id.editTextText);
        apellidos = findViewById(R.id.editTextText2);
        dni = findViewById(R.id.editTextNumber);
        correo = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword4);
        confirmarPassword = findViewById(R.id.editTextTextPassword5);
        celular = findViewById(R.id.editTextNumber1);
        crearCuenta = findViewById(R.id.button3);

        crearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InsertarUsuarioTask().execute(
                        nombres.getText().toString(),
                        apellidos.getText().toString(),
                        dni.getText().toString(),
                        correo.getText().toString(),
                        password.getText().toString(),
                        celular.getText().toString()
                );
            }
        });
    }

    private class InsertarUsuarioTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String nombres = params[0];
            String apellidos = params[1];
            String dni = params[2];
            String correo = params[3];
            String password = params[4];
            String celular = params[5];

            try {
                URL url = new URL("http://192.168.18.5/crud_android2/insertar_usuario.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                String postData = "nombres=" + nombres +
                        "&apellidos=" + apellidos +
                        "&dni=" + dni +
                        "&correo=" + correo +
                        "&password=" + password +
                        "&celular=" + celular;

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(postData.getBytes());
                    os.flush();
                }

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                return response.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Toast.makeText(CrearCuenta.this, result, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(CrearCuenta.this, MainActivity.class);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(CrearCuenta.this, "Error al insertar usuario", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

