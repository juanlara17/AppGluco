package com.ingenieria.appgluco;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;



public class MainActivity extends ActionBarActivity {

    Button  paciente, medico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paciente = (Button)findViewById(R.id.btPaciente);
        medico = (Button)findViewById(R.id.btMedico);

        paciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Patient.class);
                startActivity(i);
            }
        });

        medico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ix = new Intent(MainActivity.this, Medico.class);
                startActivity(ix);
            }
        });
    }
}
