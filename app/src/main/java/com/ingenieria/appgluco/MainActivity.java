package com.ingenieria.appgluco;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SyncAdapterType;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    EditText user, pass;
    Button  Login;
    TextView   registrar;
    Httppostaux    post;
    String userFTP = "juanlara@taxy.co";
    String passFTP = "6P9#DTH-$+hK";
    String IP_Server = "ftp.taxy.co";
    String URL_connect = "ftp://ftp.taxy.co/acces.php";
    boolean result_back;
    private ProgressDialog pDialog;
    public FTPClient mFTPCLiente;
    public HttpClient httpClient;


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

                if (checklogindata(usuario, contraseña) == true) {
                    new asynclogin().execute(usuario, contraseña);
                } else {
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
        Toast.makeText(this, "Error: Cedula y/o Contraseña Incorrectos", Toast.LENGTH_LONG).show();
    }

    public boolean loginstatus (String username, String password){
        int logstatus = -1;

        ArrayList<NameValuePair>    postparameters2send = new ArrayList<NameValuePair>();
                                    postparameters2send.add(new BasicNameValuePair("usuario", username));
                                    postparameters2send.add(new BasicNameValuePair("password", password));

        String response = null;

        try {
            response = CustomHttpClient.executeHttpPost(URL_connect, postparameters2send);
            String res = response.toString();
            res = res.replaceAll("\\s+","");

            if (res.equals("1")){
                Log.e("Registro: ", "Valido");
                return true;
            }else {
                Log.e("Registro: ", "Invalido");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean checklogindata(String usuario, String contraseña) {
        if (usuario.equals("") || contraseña.equals("")){
            Log.e("Login UI", "checklogindata user or pass error");
            return false;
        }else{
            return true;
        }
    }

    class asynclogin extends AsyncTask<String, String, String>{

        String user, pass;

        protected void onPreExecute(){
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Autenticando....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            user = strings[0];
            pass = strings[1];

            try {
                FTPClient ftpClient = new FTPClient();
                ftpClient.connect(InetAddress.getByName(IP_Server));
                ftpClient.login(userFTP, passFTP);
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                Toast.makeText(getApplication(), "INGRESO CON EXITO A LA BASE DE DATOS",Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(getApplication(), "FALLA EN EL INGRESO",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            if(loginstatus(user,pass) == true){
                return "ok";
            }else{
                //err_login();
                return "err";
            }
        }

        protected void onPostExecute (String result){
            pDialog.dismiss();
            Log.e("onPostExecute= ", "" + result);

            if (result.equals("ok")){
                Intent i = new Intent(MainActivity.this, Patient.class);
                i.putExtra("user", user);
                startActivity(i);
            }
        }
    }

}
