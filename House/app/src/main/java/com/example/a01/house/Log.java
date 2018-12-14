package com.example.a01.house;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class Log extends AppCompatActivity {
    public static EditText edt_password;
    public static EditText edt_account;
    Button btn_log;
    public String zxc="";
    public String accountParse="";
    MySQLiteManager mydb=new MySQLiteManager(this);
    public static String name,cuserid,phone;
    TextView txv_join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        mydb= new MySQLiteManager(this);
        edt_account = (EditText) findViewById(R.id.account);
        edt_password = (EditText) findViewById(R.id.password);
        txv_join = findViewById(R.id.txv_join);
        txv_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Log.this,register.class);
                startActivity(intent);
            }
        });
        btn_log = findViewById(R.id.btn_login);
        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData2 process=new fetchData2();
                process.execute();
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
                account1 = Log.edt_account.getText().toString().trim();
                password1 = Log.edt_password.getText().toString().trim();

                for (int i = 0; i < JA.length(); i++) {
                    JSONObject JO = (JSONObject) JA.get(i);
                    if(JO.get("cuserid").toString().trim().equals(account1.toString().trim()) && JO.get("cpwd").toString().trim().equals(password1.toString().trim()) && JO.getString("creport").toString().trim().equals("3")){
                        accountParse = "aaa";
                        break;
                    }
                    else if (JO.get("cuserid").toString().trim().equals(account1.toString().trim()) && JO.get("cpwd").toString().trim().equals(password1.toString().trim())) {
                        name = JO.get("cname").toString();
                        cuserid=JO.get("cuserid").toString();
                        phone=JO.getString("cphone");
                        accountParse = "abc";
                        break;
                        //dataParsed = JO.get("Bid").toString();
                    }
                    else {
                        accountParse = "error";
                    }
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
            String zxc = accountParse;
            if(zxc.equals("aaa")){
                Toast.makeText(Log.this,"帳號已被封鎖",Toast.LENGTH_SHORT).show();
            }else if(zxc.equals("error")){
                Toast.makeText(Log.this,"登入失敗",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(Log.this,"登入成功",Toast.LENGTH_SHORT).show();

                Intent go = new Intent(Log.this,MainActivity.class);
                Bundle bundle=new Bundle();
                //bundle.putString("ID",name);
                //bundle.putString("cuserid",cuserid);
                go.putExtras(bundle);
                startActivity(go);
                Log.this.finish();
            }
        }
    }
}/*boolean isInserted = mydb.insterInfo(name.toString(),"123",cuserid.toString());
                if (isInserted=true){
                    Toast.makeText(Log.this,"成功",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(Log.this,"XXX",Toast.LENGTH_LONG).show();
                }*/