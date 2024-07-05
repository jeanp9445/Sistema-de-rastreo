package com.example.sistema_de_rastreo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    Button btnLogin;
    TextView tvCrearCuenta, tvRecuperarCuenta;
    EditText txtCorreo, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtCorreo = findViewById(R.id.txtCorreo);
        txtPassword = findViewById(R.id.txtPassw);
        tvCrearCuenta = findViewById(R.id.tvCrearc);
        tvRecuperarCuenta = findViewById(R.id.textView8);
        btnLogin = findViewById(R.id.btnLog);

        btnLogin.setOnClickListener(this);
        tvCrearCuenta.setOnClickListener(this);
        tvRecuperarCuenta.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            iniciarSesion();
        } else if (v == tvCrearCuenta) {
            crearCuenta();
        } else if (v == tvRecuperarCuenta) {
            recuperarCuenta();
        }
    }

    private void iniciarSesion() {
        String correo = txtCorreo.getText().toString();
        String password = txtPassword.getText().toString();

        if (correo.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, llene todos los campos", Toast.LENGTH_LONG).show();
        } else {
            new LoginTask().execute(correo, password);
        }
    }

    private void crearCuenta() {
        Intent intent = new Intent(MainActivity.this, CrearCuenta.class);
        startActivity(intent);
    }

    private void recuperarCuenta() {
        Intent intent = new Intent(MainActivity.this, RecuperarCuenta.class);
        startActivity(intent);
    }

    // AsyncTask para manejar la operación de inicio de sesión en segundo plano
    private class LoginTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String correo = params[0];
            String password = params[1];
            String serverUrl = "http://192.168.18.5/crud_android2/iniciar_sesion.php"; // Cambia esto a la URL de tu servidor

            try {
                URL url = new URL(serverUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                // Crear los datos del POST
                String postData = "correo=" + URLEncoder.encode(correo, "UTF-8") +
                        "&password=" + URLEncoder.encode(password, "UTF-8");

                // Escribir los datos en la salida
                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(postData);
                writer.flush();
                writer.close();
                os.close();

                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder content = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();
                    connection.disconnect();
                    return content.toString();
                } else {
                    return null;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Log.d(TAG, "Respuesta del servidor: " + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getBoolean("success")) {
                        Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, pantalla_principal.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error en el procesamiento de la respuesta", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Error de conexión", Toast.LENGTH_LONG).show();
            }
        }
    }
}

