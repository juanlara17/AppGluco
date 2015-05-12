package com.ingenieria.appgluco;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ingenieria on 08/05/2015.
 */
public class Grafica extends Activity {
    private XYPlot mySimpleXYPlot;
    private Number[] grafica = new Number[100];
    Button Ingresar;
    Number num;
    TextView Numero;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafica);

        mySimpleXYPlot = (XYPlot) findViewById(R.id.mySimpleXYPlot);
        Ingresar = (Button)findViewById(R.id.button);
        Numero = (TextView)findViewById(R.id.editText);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                FuncionParaEsteHilo();
            }
        }, 0, 2000);

        Ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grafica = new Number[]{1, 3, 4, 2, 1};
                for (int a = 0; a < grafica.length - 1; a++) {
                    grafica[a] = grafica[a + 1];
                }
                grafica[grafica.length - 1] = Math.floor(Math.random() * 10 + 1);

            }
        });
    }

    private void FuncionParaEsteHilo() {
        this.runOnUiThread(Accion);
    }

    private Runnable Accion;

    {
        Accion = new Runnable() {
            @Override
            public void run() {

                mySimpleXYPlot.setDomainLabel("Time (Secs)");
                mySimpleXYPlot.setRangeLabel("Glucometro");

                //v.add(num);
                String word = String.valueOf(num);
                Numero.setText(word);

                Iterator<XYSeries> iterator1 = mySimpleXYPlot.getSeriesSet().iterator();
                while(iterator1.hasNext()) {
                    XYSeries setElement = iterator1.next();
                    mySimpleXYPlot.removeSeries(setElement);
                }

                // Añadimos Línea Número UNO:
                XYSeries series1 = new SimpleXYSeries(
                        Arrays.asList(grafica),  // Array de datos
                        SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Sólo valores verticales
                        "Series1"); // Nombre de la primera serie

                // Modificamos los colores de la primera serie
                mySimpleXYPlot.addSeries(series1, new LineAndPointFormatter
                        (Color.rgb(0, 79, 136), // Color de la línea
                                Color.rgb(14, 0, 135), // Color del punto
                                Color.TRANSPARENT, // Relleno
                                new PointLabelFormatter()));

                mySimpleXYPlot.redraw();
            }
        };
    }
}
