package yasheth.charusatcoin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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

public class Wallet extends Fragment {

    int cc;
    StringRequest request;
    RequestQueue requestqueue;
    private ProgressDialog progress;
    SharedPreferences sharedpreferences;
    String url="http://charusatcoin-thakkaraakash.rhcloud.com/GetCoins.php";

    public Wallet() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle     savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wallet, container, false);
        TextView tvuser=(TextView)rootView.findViewById(R.id.tvuser);
        final TextView tvcc=(TextView)rootView.findViewById(R.id.tvcc);
        progress = new ProgressDialog(getContext());
        progress.setTitle("Please Wait!!");
        progress.setMessage("Fetching Balance...");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        SharedPreferences prefs = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        final String name = prefs.getString("name", "No name defined");
        getCC(tvcc,name);
        tvuser.setText(name);
        return rootView;

    }

    public void getCC(final TextView tvcc,final String name){
        requestqueue = Volley.newRequestQueue(getContext());
        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                JSONObject jObject;
                try {

                    jObject = new JSONObject(response);
                    cc =Integer.valueOf(jObject.getString("gotcoins"));
                    tvcc.setText(" "+cc+" ");
                    progress.dismiss();
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashmap = new HashMap<String, String>();
                hashmap.put("username", name);
                return hashmap;
            }
        };
        requestqueue.add(request);
    }
}