package com.example.sistema_de_rastreo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class pantalla_principal extends AppCompatActivity implements View.OnClickListener{

    ImageButton ibtn1;
    VideoView vfondo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_principal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        vfondo = (VideoView) findViewById(R.id.vvUno);
        ibtn1 = (ImageButton) findViewById(R.id.ibMapa);
        ibtn1.setOnClickListener(this);

        String vd = "android.resource://"+ getPackageName() + "/" + R.raw.v1;

        vfondo.setVideoURI(Uri.parse(vd));

        vfondo.setOnCompletionListener(mediaPlayer -> {
            vfondo.start();
        });

        vfondo.start();
    }

    public void encontrarDisp(){
        Intent intent = new Intent(pantalla_principal.this, Mapa.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(v == ibtn1){
            encontrarDisp();
        }
    }
}