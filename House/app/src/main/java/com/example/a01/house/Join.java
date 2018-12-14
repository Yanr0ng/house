package com.example.a01.house;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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

public class Join extends AppCompatActivity {
    Button btn2,btn1;
    EditText edt;
    String code,g;
    public String orderguid,suserid,ido,id;
    String cname,cuserid;
    private RequestQueue requestQueue;
    ActionBar ab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        ab = getSupportActionBar();
        //ab.setTitle("join");
        //ab.setDisplayHomeAsUpEnabled(true);
        edt=findViewById(R.id.edt_code);
        btn2=findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Join.this.finish();
            }
        });
        btn1=findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set();
            }
        });
    }
    private void set(){
        code=edt.getText().toString();
        if (TextUtils.isEmpty(code)) {
            edt.setError("請輸入code");
            edt.requestFocus();
        }else{
            fetchData2 f=new fetchData2();
            f.execute();
        }
    }
    /*de=edt.getText().toString();
    String JSON_URL="http://140.126.146.26/chu/api/order/find/one/"+code;*/
    class fetchData2 extends AsyncTask<Void, Void, Void> {
        String data = "";
        String dataParsed = "";
        String singleParsed = "";
        String fb_findnumber = "";
        String asd = "";      // up
        //String name = "";   // up
        String account1="";
        String password1="";
        //String cuserid="";

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                code=edt.getText().toString();
                URL url = new URL("http://140.126.146.26/chu/api/order/find/one/"+code);
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
                    JSONObject JO = (JSONObject) JA.get(i);

                        orderguid = JO.get("orderguid").toString();
                        suserid=JO.get("suserid").toString();
                        //dataParsed = JO.get("Bid").toString();
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

        @Override
        protected void onPostExecute(Void aVoid) {       //尋找u_id 前兩個字元為fb
            super.onPostExecute(aVoid);
            /*String zxc = accountParse;
            if(zxc.equals("error")){
                Toast.makeText(Log.this,"登入失敗",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(Log.this,"登入成功",Toast.LENGTH_SHORT).show();*/


                Intent go = new Intent(Join.this,OrderPage.class);
                Bundle bundle=new Bundle();
                bundle.putString("store_id",suserid);
                bundle.putString("orderguid",orderguid);
                go.putExtras(bundle);
                startActivity(go);
                finish();

        }
    }
}
