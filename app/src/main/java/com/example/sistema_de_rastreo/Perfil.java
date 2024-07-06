package com.example.sistema_de_rastreo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Perfil extends AppCompatActivity {

    private EditText editTextNombre;
    private EditText editTextApellidos;
    private EditText editTextDni;
    private EditText editTextCorreo;
    private EditText editTextCelular;
    private Button buttonGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellidos = findViewById(R.id.editTextApellidos);
        editTextDni = findViewById(R.id.editTextDni);
        editTextCorreo = findViewById(R.id.editTextCorreo);
        editTextCelular = findViewById(R.id.editTextCelular);
        buttonGuardar = findViewById(R.id.buttonGuardar);

        // Obtener datos del usuario (esto se puede mejorar obteniendo el ID del usuario actual)
        int usuarioId = 40; // Supongamos que el ID del usuario es 1

        obtenerDatosUsuario(usuarioId);

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarDatosUsuario(usuarioId);
            }
        });
    }

    private void obtenerDatosUsuario(int usuarioId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.18.5/crud_android2/obtener_datos_usuario.php?usuario_id=" + usuarioId);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                    in.close();

                    JSONObject usuario = new JSONObject(response.toString());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                editTextNombre.setText(usuario.getString("nombres"));
                                editTextApellidos.setText(usuario.getString("apellidos"));
                                editTextDni.setText(usuario.getString("dni"));
                                editTextCorreo.setText(usuario.getString("correo"));
                                editTextCelular.setText(usuario.getString("celular"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void actualizarDatosUsuario(int usuarioId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.18.5/crud_android2/actualizar_datos_usuario.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);

                    OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                    String params = "usuario_id=" + usuarioId +
                            "&correo=" + editTextCorreo.getText().toString() +
                            "&celular=" + editTextCelular.getText().toString();
                    out.write(params);
                    out.flush();
                    out.close();

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                    in.close();

                    JSONObject respuesta = new JSONObject(response.toString());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Aquí puedes mostrar un mensaje de éxito o manejar la respuesta
                            Log.d("Perfil", respuesta.toString());
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
