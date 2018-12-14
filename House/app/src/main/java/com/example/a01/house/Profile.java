package com.example.a01.house;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {
    EditText eacc,epwd,ename,ephone,email,eaddress;
    String acc,pwd,name,phone,mail,g="",address;
    String apwd,aname,aphone,amail;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        eacc=findViewById(R.id.reg_acc);
        epwd=findViewById(R.id.reg_pwd);
        ename=findViewById(R.id.reg_name);
        ephone=findViewById(R.id.reg_phone);
        email=findViewById(R.id.reg_mail);
        button=findViewById(R.id.btn_edit);
        eacc.setFocusable(false);
        epwd.setFocusable(false);
        ename.setFocusable(false);
        ephone.setFocusable(false);
        email.setFocusable(false);

        fetchData2 process=new fetchData2();
        process.execute();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button.getText().toString().trim().equals("編輯")){
                    setedit();
                    button.setText("完成");
                }else if(button.getText().toString().trim().equals("完成")){
                    postdata();
                }
            }
        });

    }
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
                URL url = new URL("http://140.126.146.26/chu/api/Cus/findall");
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
                    if (JO.getString("cuserid").equals(Log.cuserid)){
                        aname = JO.get("cname").toString();
                        apwd=JO.get("cpwd").toString();
                        aphone=JO.getString("cphone");
                        amail=JO.getString("cemail");
                    }
                }
                eacc.setText(Log.cuserid);
                ename.setText(aname);
                epwd.setText(apwd);
                ephone.setText(aphone);
                email.setText(amail);
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

        }
    }
    private void setedit(){
        epwd.setFocusableInTouchMode(true);
        epwd.setFocusable(true);
        ename.setFocusableInTouchMode(true);
        ename.setFocusable(true);
        ephone.setFocusableInTouchMode(true);
        ephone.setFocusable(true);
        email.setFocusableInTouchMode(true);
        email.setFocusable(true);
    }
    private void setedit1(){
        epwd.setFocusableInTouchMode(false);
        epwd.setFocusable(false);
        ename.setFocusableInTouchMode(false);
        ename.setFocusable(false);
        ephone.setFocusableInTouchMode(false);
        ephone.setFocusable(false);
        email.setFocusableInTouchMode(false);
        email.setFocusable(false);
    }
    private void setedit2(){
        pwd=epwd.getText().toString();
        name=ename.getText().toString();
        phone=ephone.getText().toString();
        mail=email.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            epwd.setError("輸入密碼");
            epwd.requestFocus();
        }
        else if (TextUtils.isEmpty(name)) {
            ename.setError("輸入名字");
            ename.requestFocus();
        }
        else if (TextUtils.isEmpty(phone)) {
            ephone.setError("輸入電話");
            ephone.requestFocus();
        }
        else if (TextUtils.isEmpty(mail)) {
            email.setError("輸入email");
            email.requestFocus();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            email.setError("輸入正確的email");
            email.setText("");
            email.requestFocus();
        }else {
            setedit1();
            button.setText("編輯");
        }
    }
    public void postdata(){
        acc=Log.cuserid;
        pwd=epwd.getText().toString();
        name=ename.getText().toString();
        phone=ephone.getText().toString();
        mail=email.getText().toString();
        //address=eaddress.getText().toString();
        StringRequest postRequest = new StringRequest(Request.Method.PUT, "http://140.126.146.26/chu/api/cus/update",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        //Log.d("Response", response);
                        Toast.makeText(Profile.this, "編輯成功", Toast.LENGTH_SHORT).show();
                        setedit2();
                        /*Intent go= new Intent(register.this,Log.class);
                        startActivity(go);*/

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", response);
                        /*Toast.makeText(register.this, "註冊失敗", Toast.LENGTH_SHORT).show();
                        eacc.setError("帳號已被使用");
                        eacc.requestFocus();*/
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Calendar mCal = Calendar.getInstance();
                String s = DateFormat.format("yyyy-MM-dd kk:mm:ss", mCal.getTime()).toString().trim();    // kk:24小時制, hh:12小時制

                Map<String, String> params = new HashMap<String, String>();
                params.put("cuserid",acc);
                params.put("cpwd",pwd);
                params.put("cname",name);
                params.put("cphone",phone);
                params.put("cemail",mail);
                //params.put("caddress",address);
                return params;
            }
        };
        Mysingleton.getInstance(Profile.this).addTorequestque(postRequest);
    }
}
