package com.ingenieria.appgluco;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ingenieria.appgluco.Class.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Medico extends ActionBarActivity {

    EditText user, pass;
    Button login;
    TextView registrar;
    String URL_connect = "http://appgluco.esy.es/appgluco/login.php";
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico);
        user = (EditText)findViewById(R.id.etUserM);
        pass = (EditText)findViewById(R.id.etPasswordM);
        login = (Button)findViewById(R.id.btLoginM);
        registrar = (TextView)findViewById(R.id.tvRegistroM);


        /********************Button login******************/
        login.setOnClickListener(new View.OnClickListener() {
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
                Intent i = new Intent(Medico.this, Register.class);
                startActivity(i);
            }
        });
    }

    private void err_login() {
        Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        SystemClock.sleep(500);
        vibrator.vibrate(200);
        Toast.makeText(this, "Error: Cedula y/o Contraseña Incorrectos", Toast.LENGTH_SHORT).show();
    }

    public boolean loginstatus (String username, String password){
        int success;

        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
        postparameters2send.add(new BasicNameValuePair("username", username));
        postparameters2send.add(new BasicNameValuePair("password", password));

        try {
            JSONObject json = jsonParser.makeHttpRequest(URL_connect, "POST",
                    postparameters2send);
            SystemClock.sleep(950);
            success = json.getInt(TAG_SUCCESS);

            if(success == 1){
                JSONObject json_data;

                Log.d("Login Successful!", json.toString());
                // save user data
                SharedPreferences sp = PreferenceManager
                        .getDefaultSharedPreferences(Medico.this);
                return true;
            }else{
                Log.e("JSON", "ERROR");
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    class asynclogin extends AsyncTask<String, String, String> {


        String userA, passA;

        protected void onPreExecute(){
            pDialog = new ProgressDialog(Medico.this);
            pDialog.setMessage("Autenticando....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            userA = strings[0];
            passA = strings[1];

            if(loginstatus(userA,passA) == true){
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
                user.setText("");
                pass.setText("");
                /*Intent i = new Intent(Medico.this, DatosMedico.class);
                i.putExtra("user", userA);
                startActivity(i);*/
            }else{
                Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                SystemClock.sleep(500);
                vibrator.vibrate(200);
                Toast.makeText(getApplication(), "Error: Cedula y/o Contraseña Incorrectos", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private boolean checklogindata(String usuario, String contraseña) {
        if (usuario.equals("") || contraseña.equals("")) {
            Log.e("Login UI", "checklogindata user or pass error");
            return false;
        }else{
            return true;        }
    }


}
