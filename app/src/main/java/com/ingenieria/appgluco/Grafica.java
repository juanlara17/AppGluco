package com.ingenieria.appgluco;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.util.Arrays;

public class Grafica extends Activity {

    private XYPlot  mySimpleXYPlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafica);

        mySimpleXYPlot = (XYPlot)findViewById(R.id.mySimpleXYPlot);

        Number [] serie1 = {1,8,5,2,7,4};
        Number [] serie2 = {4,6,3,8,2,10};

        XYSeries series1Line = new SimpleXYSeries(Arrays.asList(serie1),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"Series1");
        XYSeries series2Line = new SimpleXYSeries(Arrays.asList(serie2),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"Series2");

 /*       LineAndPointFormatter serie1Format = new LineAndPointFormatter(
                Color.rgb(0, 200, 0),                   // Color de la línea
                Color.rgb(0, 100, 0),                   // Color del punto
                Color.rgb(150, 190, 150));

        mySimpleXYPlot.addSeries(series1Line, serie1Format);

        mySimpleXYPlot.addSeries(series2Line, new LineAndPointFormatter(
                Color.rgb(0, 0, 200),
                Color.rgb(0, 0, 100),
                Color.rgb(150, 150, 190)));
*/


    }

}
