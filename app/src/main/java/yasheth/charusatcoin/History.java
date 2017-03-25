package yasheth.charusatcoin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;

public class History extends Fragment {
    StringRequest request;
    RequestQueue requestqueue;
    Map<Integer, ArrayList<String>> map;
    private ProgressDialog progress;
    SharedPreferences sharedpreferences;
    String url="http://charusatcoin-thakkaraakash.rhcloud.com/GetTransactions.php";


    public History() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        final RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);
        //call to getTransactions
        map = new HashMap<Integer, ArrayList<String>>();
        requestqueue = Volley.newRequestQueue(getContext());
        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jObject;
                try {
                    System.out.println("Inside Try!");

                    jObject = new JSONObject(response);
                    JSONArray jArray=jObject.getJSONArray("gottransactions");
                    for(int i=0;i<jArray.length();i++){
                        JSONObject jObject1=jArray.getJSONObject(i);
                        map.put(i,new ArrayList<String>(Arrays.asList("ID : "+jObject1.getString("transaction_id"),"Amount : Rs "+jObject1.getString("transaction_amount"),"Shop : "+jObject1.getString("shop_name"),"Date : "+jObject1.getString("transaction_date").substring(0,10))));
                        System.out.println("Iterated "+i);
                    }
                    MyAdapter adapter = new MyAdapter(map);
                    rv.setAdapter(adapter);

                    LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                    rv.setLayoutManager(llm);

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {@Override
        protected Map<String, String> getParams() throws AuthFailureError {
            SharedPreferences prefs = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
            final String name = prefs.getString("name", "No name defined");
            HashMap<String, String> hashmap = new HashMap<String, String>();
            hashmap.put("username", name);
            return hashmap;
        }
        };
        requestqueue.add(request);
        return rootView;
    }
}