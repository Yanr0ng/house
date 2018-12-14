package com.example.a01.house;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Random extends AppCompatActivity {
    MySQLiteManager mydb = new MySQLiteManager(this);
    private RequestQueue requestQueue;
    private String mname, suserid,sname,mprice,mtype;
    TextView txv, txv1,txvtype,txvprice;
    Button btn, btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);
        txv = findViewById(R.id.txv_rd);
        txv1 = findViewById(R.id.txv_rd1);
        txvprice=findViewById(R.id.txv_price);
        txvtype=findViewById(R.id.txv_type);
        btn = findViewById(R.id.btn_rd);
        btn1 = findViewById(R.id.btn_rd1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getmenu();
                btn1.setVisibility(View.VISIBLE);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(Random.this, StoreDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("store_id", suserid);
                go.putExtras(bundle);
                startActivity(go);
            }
        });
    }

    public void show(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
    }

    public void getmenu() {
        final String JSON_URL = "http://140.126.146.26/chu/api/scmm/findall/";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    int a = (int) (Math.random() * response.length());
                    JSONObject jo = response.getJSONObject(a);
                    suserid = jo.getString("suserid").toString().trim();
                    sname=jo.getString("sname").toString().trim();
                    mname = jo.getString("mname").toString().trim();
                    mprice=jo.getString("mprice").toString().trim();
                    mtype=jo.getString("mtype").toString().trim();
                    txv.setText(mname);
                    txv1.setText(sname);
                    txvprice.setText(" "+mprice);
                    txvtype.setText(mtype);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
