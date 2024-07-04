package com.example.sistema_de_rastreo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Botonera extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_botonera, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Aquí puedes añadir la lógica de los botones
        view.findViewById(R.id.ibMapa).setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), pantalla_principal.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        });

        view.findViewById(R.id.ibRastreo).setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), Mapa.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        });

        view.findViewById(R.id.ibDashboard).setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), dashboard.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        });

        view.findViewById(R.id.ibPerfile).setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), Perfil.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        });

        view.findViewById(R.id.ibReporte).setOnClickListener(v ->{
            Intent i = new Intent(getActivity(), Reporte.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        });
    }
}