package com.ingenieria.appgluco;

import android.content.SyncAdapterType;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    EditText user, pass;
    Button  Login;
    TextView   registrar;
    Httppostaux    post;

    String IP_Server = "ftp.taxy.co";
    String URL_connect = "ftp://ftp.taxy.co/acces.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        post = new Httppostaux();

        user = (EditText)findViewById(R.id.etUser);
        pass = (EditText)findViewById(R.id.etPassword);
        Login = (Button)findViewById(R.id.btLogin);
        registrar = (TextView)findViewById(R.id.tvRegistro);


        /********************Button login******************/
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = user.getText().toString();
                String contraseña = pass.getText().toString();

                if(checklogindata (usuario, contraseña)== true){

                    new asynclogin.execute(usuario, contraseña);
                }else{
                    err_login();
                }
            }
        });

        /**********Para registrar un nuevo usuario************/
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void err_login() {
        Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
        Toast.makeText(this, "Error: Cedula y/o Contraseña Incorrectos", Toast.LENGTH_SHORT).show();
    }

    public boolean loginstatus (String user, String pass){
        int logstatus = 1;

        ArrayList<NameValuePair>    postparameters2send = new ArrayList<NameValuePair>();
                                    postparameters2send.add(new BasicNameValuePair("usuario", user));
                                    postparameters2send.add(new BasicNameValuePair("contraseña", pass));

        JSONArray jdata = post.getserverdata(postparameters2send, URL_connect);
        SystemClock.sleep(1000);

        if (jdata != null && jdata.length() > 0){
            JSONObject json_data;

            try {
                json_data = jdata.getJSONObject(0);
                logstatus = json_data.getInt("loginstatus");
                Log.e("loginstatus", "loginstatus = " + logstatus);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (logstatus == 0){
                Log.e("loginstatus", "invalido");
                return false;
            }else{
                Log.e("loginstatus", "valido");
                return true;
            }
        }else{
            Log.e("JSON", "Error");
            return false;
        }
    }

    private boolean checklogindata(String usuario, String contraseña) {
        if (usuario.equals("") || contraseña.equals("")){
            Log.e("Login UI", "checklogindata user or pass erro");
            return false;
        }else{
            return true;
        }
    }

    class asynclogin extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }

}
