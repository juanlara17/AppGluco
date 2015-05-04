package com.ingenieria.appgluco;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Register extends ActionBarActivity {

    EditText name, lastName, celular, id, password;
    Button  register;
    public ProgressDialog progressDialog;
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    public String URL_register = "http://appgluco.esy.es/appgluco/register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText)findViewById(R.id.etNombre);
        lastName = (EditText)findViewById(R.id.etApellido);
        celular = (EditText)findViewById(R.id.etCelular);
        id = (EditText)findViewById(R.id.etId);
        password = (EditText)findViewById(R.id.etContraseña);
        register = (Button)findViewById(R.id.btregister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Nombre = name.getText().toString();
                String Apellido = lastName.getText().toString();
                String User = id.getText().toString();
                String Contraseña = password.getText().toString();
                String Telefono = celular.getText().toString();

                if (Nombre.equals("") || Apellido.equals("") || User.equals("") || Contraseña.equals("") || Telefono.equals("")){
                    error_registro();
                }else{
                    new CreateUser().execute(Nombre, Apellido, User, Contraseña, Telefono);
                }
            }
        });
    }

    private void error_registro() {
        Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        SystemClock.sleep(500);
        vibrator.vibrate(200);
        Toast.makeText(this, "Registro de los campos se encuentra incompleto", Toast.LENGTH_SHORT).show();
    }


    class CreateUser extends AsyncTask <String, String, String>{

        String Nombre, Apellido, User, Contraseña, Telefono;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Register.this);
            progressDialog.setMessage("Creando Usuario");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            int success;

            Nombre = strings[0];
            Apellido = strings[1];
            User = strings[2];
            Contraseña = strings[3];
            Telefono = strings[4];

            try {
                ArrayList<NameValuePair>    postparameters5send = new ArrayList<NameValuePair>();
                postparameters5send.add(new BasicNameValuePair("nombre", Nombre));
                postparameters5send.add(new BasicNameValuePair("apellido", Apellido));
                postparameters5send.add(new BasicNameValuePair("username", User));
                postparameters5send.add(new BasicNameValuePair("password", Contraseña));
                postparameters5send.add(new BasicNameValuePair("telefono", Telefono));

                JSONObject json = jsonParser.makeHttpRequest(URL_register, "POST", postparameters5send);

                Log.d("Registering", json.toString());
                success = json.getInt(TAG_SUCCESS);

                if (success == 1){
                    Log.d("User created", json.toString());
                    finish();
                    return json.getString(TAG_MESSAGE);
                }else {
                    Log.d("Registro Failure!", json.getString(TAG_MESSAGE));
                    return  json.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            Toast.makeText(getApplication(), "Usuario creado", Toast.LENGTH_SHORT).show();
        }
    }
}
