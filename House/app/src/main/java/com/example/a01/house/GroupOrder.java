package com.example.a01.house;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class GroupOrder extends AppCompatActivity {
    Button btn2,btn1;
    EditText edt;
    public String code,g;
    String last_spl_id,spl_id,ido,id;
    String cname,cuserid,self;
    String ok="";
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_order);
        fetchData a= new fetchData();
        a.execute();
        id=getIntent().getStringExtra("store_id");
        cname=Log.name;
        cuserid=Log.cuserid;
        self=getIntent().getStringExtra("self");

        edt=findViewById(R.id.code);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupOrder.this.finish();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code=edt.getText().toString();
                getData();

            }
        });
    }
    private void post(){
            if(self.equals("self")){
                postdata1();
                Intent go=new Intent(GroupOrder.this,Invite.class);
                Bundle bundle=new Bundle();
                //bundle.putString("cname",cname);
                bundle.putString("code",code);
                bundle.putString("orderguid",spl_id);
                bundle.putString("store_id",id);
                go.putExtras(bundle);
                startActivity(go);
            } else{
                postdata();
                Intent go=new Intent(GroupOrder.this,Invite.class);
                Bundle bundle=new Bundle();
                //bundle.putString("cname",cname);
                bundle.putString("code",code);
                bundle.putString("orderguid",spl_id);
                bundle.putString("store_id",id);
                go.putExtras(bundle);
                startActivity(go);
        }
    }
    private void set(){
        code=edt.getText().toString();
        if (TextUtils.isEmpty(code)) {
            edt.setError("請輸入code");
            edt.requestFocus();
        }else if(ok.equals("no")){
            edt.setError("code已被使用");
            edt.requestFocus();
        }else{
            post();
        }
    }
    class fetchData extends AsyncTask<Void, Void, Void> {
        String data = "";
        String dataParsed = "";
        String singleParsed = "";
        String fb_findnumber = "";
        String asd = "";      // up
        String name = "";   // up
        String account1="";
        String password1="";

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
                    ido = JO.get("orderguid").toString().trim();
                }
                last_spl_id=ido;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {       //尋找u_id 前兩個字元為fb
            super.onPostExecute(aVoid);
            int id_number;
            try {
                String[] split_id = last_spl_id.split("d");
                id_number = Integer.parseInt(split_id[1]) + 1;
                spl_id = "od" + id_number;
            }catch (Exception e){
                e.getStackTrace();
            }
        }
    }
    public void postdata(){
        //final String qty=edt.getText().toString().trim();
        //final String name=txvname.getText().toString().trim();
        final String address=getIntent().getStringExtra("address");
        final String receiver=getIntent().getStringExtra("receiver");
        final String oway=getIntent().getStringExtra("oway");
        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://140.126.146.26/chu/api/order/create",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        //Log.d("Response", response);
                        Toast.makeText(GroupOrder.this, "success", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", response);
                        //Toast.makeText(GroupOrder.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Calendar mCal = Calendar.getInstance();
                String s = DateFormat.format("yyyy-MM-dd kk:mm:ss", mCal.getTime()).toString().trim();    // kk:24小時制, hh:12小時制

                Map<String, String> params = new HashMap<String, String>();
                params.put("orderguid",spl_id);
                params.put("cuserid",cuserid);
                params.put("receiver",receiver);
                params.put("phone",Log.phone);
                params.put("suserid",id);
                params.put("opwd",code);
                params.put("orderdate",s);
                params.put("address",address);
                params.put("ostatus","尚未接受");
                params.put("oway",oway);
                params.put("note","");
                params.put("isapproved","0");
                return params;
            }
        };
        Mysingleton.getInstance(GroupOrder.this).addTorequestque(postRequest);
    }
    public void postdata1(){
        //final String qty=edt.getText().toString().trim();
        //final String name=txvname.getText().toString().trim();
        final String address=getIntent().getStringExtra("address");
        final String receiver=getIntent().getStringExtra("receiver");
        final String oway=getIntent().getStringExtra("oway");
        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://140.126.146.26/chu/api/order/create",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        //Log.d("Response", response);
                        Toast.makeText(GroupOrder.this, "success", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", response);
                        //Toast.makeText(GroupOrder.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Calendar mCal = Calendar.getInstance();
                String s = DateFormat.format("yyyy-MM-dd kk:mm:ss", mCal.getTime()).toString().trim();    // kk:24小時制, hh:12小時制

                Map<String, String> params = new HashMap<String, String>();
                params.put("orderguid",spl_id);
                params.put("cuserid",cuserid);
                params.put("receiver",Log.name);
                params.put("phone",Log.phone);
                params.put("suserid",id);
                params.put("opwd",code);
                params.put("orderdate",s);
                params.put("address","");
                params.put("ostatus","尚未接受");
                params.put("oway",oway);
                params.put("note","");
                params.put("isapproved","0");
                return params;
            }
        };
        Mysingleton.getInstance(GroupOrder.this).addTorequestque(postRequest);
    }
    public void getData() {
        code=edt.getText().toString();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,"http://140.126.146.26/chu/api/order/findall",null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        if (jsonObject.getString("opwd").equals(code)){
                            ok="no";
                        }else{
                            ok="ok";
                        }
                    }
                }catch (JSONException e) {
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
        set();
    }

}
