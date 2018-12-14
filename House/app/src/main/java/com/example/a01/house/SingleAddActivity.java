package com.example.a01.house;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SingleAddActivity extends AppCompatActivity {
    TextView txvname,txvprice;
    Button btn,bbtn;
    EditText edt;
    String id, name,price,orderguid,cuserid,mid;
    public static String mqty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_add);

        Intent intent= getIntent();
        id= intent.getStringExtra("store_id");
        mid=intent.getStringExtra("mid");
        name = intent.getStringExtra("food_name");
        price = intent.getStringExtra("food_price");
        cuserid=Log.cuserid;
        //cuserid=intent.getStringExtra("cuserid");

        txvname=findViewById(R.id.txv_foodname);
        txvprice=findViewById(R.id.txv_price);
        txvprice.setText("價格:"+price);
        txvname.setText(name);
        edt=findViewById(R.id.edt_qty);
        btn=findViewById(R.id.btn_abc);
        bbtn=findViewById(R.id.btn_cde);

        fetchDataorder fetchDataorder=new fetchDataorder();
        fetchDataorder.execute();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mqty =edt.getText().toString();
                if (TextUtils.isEmpty(mqty)) {
                    edt.setError("輸入數量");
                    edt.requestFocus();
                }else{
                    postdata();
                    SingleAddActivity.this.finish();
                }

            }
        });
        bbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleAddActivity.this.finish();
            }
        });

    }
    public void postdata(){
        final String qty=edt.getText().toString().trim();
        final String name=txvname.getText().toString().trim();
        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://140.126.146.26/chu/api/detail/create",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        //Log.d("Response", response);
                        Toast.makeText(SingleAddActivity.this, "success", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", response);
                        Toast.makeText(SingleAddActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("orderguid",orderguid);
                params.put("cuserid",cuserid);
                params.put("mid",mid);
                params.put("mqty",qty);

                //params.put("domain", "http://itsalif.info");

                return params;
            }

        };
        Mysingleton.getInstance(SingleAddActivity.this).addTorequestque(postRequest);
    }
    class fetchDataorder extends AsyncTask<Void, Void, Void> {
        String data = "";
        String dataParsed = "";
        String singleParsed = "";
        String fb_findnumber = "";
        String asd = "";      // up
        String name = "";   // up
        String account1 = "";
        String password1 = "";

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL url = new URL("http://140.126.146.26/chu/api/order/findall");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;
                }
                JSONArray JA = new JSONArray(data);       //迴圈顯示每筆資料

                for (int i = 0; i < JA.length(); i++) {
                    JSONObject JO = JA.getJSONObject(i);
                    orderguid = JO.get("orderguid").toString().trim();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
