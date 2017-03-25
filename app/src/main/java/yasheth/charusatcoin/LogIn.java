package yasheth.charusatcoin;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LogIn extends AppCompatActivity {

    EditText username,password;
    RequestQueue requestqueue;
    SharedPreferences sharedpreferences;
    public static final String userkey = "userkey";
    private ProgressDialog progress;
    //String url="http://192.168.0.102:8888/Login.php";
    //String url="http://192.168.43.114:8888/Login.php";
    String url="http://charusatcoin-thakkaraakash.rhcloud.com/Login.php";
    StringRequest request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        username = (EditText) findViewById(R.id.et_username);
        password = (EditText) findViewById(R.id.et_password);
        requestqueue = Volley.newRequestQueue(this);

    }

   public void onClick(View v){
        if(v.getId()==R.id.btnlogin){
            progress = new ProgressDialog(this);
            progress.setTitle("Please Wait!!");
            progress.setMessage("Logging in..");
            progress.setCancelable(true);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.show();
            //Toast.makeText(getApplicationContext(), "Sign In", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(getApplicationContext(), Vendor.class));
           request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
               @Override
               public void onResponse(String response) {
                   try {
                       JSONObject jsonObject = new JSONObject(response);
                       if (jsonObject.names().get(0).equals("logged")) {
                           SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
                           editor.putString("name", username.getText().toString());
                           editor.commit();
                           Toast.makeText(getApplicationContext(), jsonObject.getString("logged"), Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(getApplicationContext(), Home.class));
                           progress.dismiss();
                            finish();
                       } else if (jsonObject.names().get(0).equals("vlogged")) {
                           SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
                           editor.putString("name", username.getText().toString());
                           editor.commit();
                           Toast.makeText(getApplicationContext(), jsonObject.getString("vlogged"), Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(getApplicationContext(), Vendor.class));
                           progress.dismiss();
                       } else if (jsonObject.names().get(0).equals("error")) {
                           Toast.makeText(getApplicationContext(), jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                           progress.cancel();}
                       else {
                           Toast.makeText(getApplicationContext(), jsonObject.getString("empty"), Toast.LENGTH_SHORT).show();
                           progress.cancel();
                       }
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }

               }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {

               }
           }) {
               @Override
               protected Map<String, String> getParams() throws AuthFailureError {
                   HashMap<String, String> hashmap = new HashMap<String, String>();
                   hashmap.put("username", username.getText().toString());
                   hashmap.put("password", password.getText().toString());
                   return hashmap;
               }
           };
           requestqueue.add(request);
       }
       if(v.getId()==R.id.btnsignup){
           startActivity(new Intent(getApplicationContext(), SignUp.class));
       }
   }
    public void onBackPressed() {
        alertMessage();
    }
    public void alertMessage() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which)
            { switch (which){
                case DialogInterface.BUTTON_POSITIVE: // Yes button clicked
                    android.os.Process.killProcess(android.os.Process.myPid());
                    finish();
                    break;
                case DialogInterface.BUTTON_NEGATIVE: // No button clicked do nothing
                    Toast.makeText(LogIn.this, "Good Choice", Toast.LENGTH_LONG).show();
                    break;
            }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to Exit? :(") .setPositiveButton("Yes", dialogClickListener) .setNegativeButton("No", dialogClickListener).show();
    }



}
