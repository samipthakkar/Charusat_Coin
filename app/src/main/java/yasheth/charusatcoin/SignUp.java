package yasheth.charusatcoin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private EditText etemail,etusername,etpass,etconfirmpass,etfullname;
    String email="",pass="",confpass="",fullname="",username="";
    RequestQueue requestqueue;
//    String url="http://192.168.43.210:8888/Signup.php";
    String url="http://charusatcoin-thakkaraakash.rhcloud.com/Signup.php";
    StringRequest request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
        etemail = (EditText) findViewById(R.id.et_email);
        etpass = (EditText) findViewById(R.id.et_password);
        etconfirmpass = (EditText) findViewById(R.id.et_confpassword);
        etfullname = (EditText) findViewById(R.id.et_name);
        etusername = (EditText) findViewById(R.id.et_username);
        requestqueue = Volley.newRequestQueue(this);

    }

    public void onClick(View v){
        if(v.getId()== R.id.btnlogin){
            Intent i = new Intent(SignUp.this, LogIn.class);
            startActivity(i);
        }
        if(v.getId()== R.id.btnsignup) {

            int flag = 0;

            email = etemail.getText().toString();
            pass = etpass.getText().toString();
            confpass = etconfirmpass.getText().toString();
            fullname = etfullname.getText().toString();
            username = etusername.getText().toString();


            if (!isRequired(fullname)) {
                etfullname.setError("Please enter your Full Name");
                flag++;
            }
            if (!isRequired(username)) {
                etusername.setError("Please enter username");
                flag++;
            }

            if (!isValidEmail(email)) {
                etemail.setError("Invalid Email");
                flag++;
            }

            if (!isValidPassword(pass)) {
                etpass.setError("Invalid Password");
                flag++;
            }

            if (!pass.equals(confpass)) {
                etconfirmpass.setError("The Confirmation Password doesnt match");
                flag++;
            }


            if (flag == 0) {
                request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("inside onResponse "+response);
                        try {
                            JSONObject jsonObject =new JSONObject(response);
                            if (jsonObject.names().get(0).equals("registered"))
                            {
                                System.out.println("inside 1 if");
                                Toast.makeText(getApplicationContext(),jsonObject.getString("registered"),Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(SignUp.this, LogIn.class);
                                startActivity(i);

                            }
                            else
                            {
                                System.out.println("inside else");
                                Toast.makeText(getApplicationContext(),jsonObject.getString("error"),Toast.LENGTH_SHORT).show();

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
                        hashmap.put("fullname", fullname);
                        hashmap.put("username", username);
                        hashmap.put("user_emailid", email);
                        hashmap.put("password", pass);

                        return hashmap;
                    }
                };
                requestqueue.add(request);
            }
        }


    }

    private boolean isRequired(String det) {
        if (det.length() != 0) {
            return true;
        }
        return false;
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }
}

