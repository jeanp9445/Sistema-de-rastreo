package com.example.sistema_de_rastreo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn1;
    TextView crearc, recCuent;

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

        recCuent = (TextView) findViewById(R.id.tvCC);
        crearc = (TextView) findViewById(R.id.tvCrearc);
        btn1 = (Button) findViewById(R.id.btnLog);
        btn1.setOnClickListener(this);
        crearc.setOnClickListener(this);
        recCuent.setOnClickListener(this);
    }

    public void Loguearse(){
        Intent intent = new Intent(MainActivity.this, pantalla_principal.class);
        startActivity(intent);
    }

    public void CrearCuenta(){
        Intent intent = new Intent(MainActivity.this, CrearCuenta.class);
        startActivity(intent);
    }

    public void RecuperarCuenta(){
        Intent intent = new Intent(MainActivity.this, RecuperarCuenta.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(v == btn1){
            Loguearse();
        }else if(v == crearc){
            CrearCuenta();
        }else if(v == recCuent){
            RecuperarCuenta();
        }
    }
}