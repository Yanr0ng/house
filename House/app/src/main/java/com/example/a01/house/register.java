package com.example.a01.house;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.a01.house.adapter.ViewPagerFragmentAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class register extends AppCompatActivity {
    EditText eacc,epwd,ename,ephone,email,eaddress;
    Button btnreg,btncancel;
    String acc,pwd,name,phone,mail,g="",address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //EMAIL_ADDRESS = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%-\\+]{1,256}"+"\\@"+"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");
        eacc=findViewById(R.id.reg_acc);
        epwd=findViewById(R.id.reg_pwd);
        ename=findViewById(R.id.reg_name);
        ephone=findViewById(R.id.reg_phone);
        email=findViewById(R.id.reg_mail);
        //eaddress=findViewById(R.id.reg_address);
        btnreg=findViewById(R.id.btn_reg);


        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reg();
                if(g.equals("good")){
                    postdata();
                }else{
                    reg();
                }

            }
        });
        btncancel=findViewById(R.id.btn_regcancel);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go= new Intent(register.this,Log.class);
                startActivity(go);
            }
        });

    }
    private void reg(){
        acc=eacc.getText().toString();
        pwd=epwd.getText().toString();
        name=ename.getText().toString();
        phone=ephone.getText().toString();
        mail=email.getText().toString();
        //address=eaddress.getText().toString();


        if (TextUtils.isEmpty(acc)) {
            eacc.setError("輸入帳號");
            eacc.requestFocus();
        }
        else if (TextUtils.isEmpty(pwd)) {
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
        }
        else{
            g="good";
        }
    }

    public void postdata(){
        acc=eacc.getText().toString();
        pwd=epwd.getText().toString();
        name=ename.getText().toString();
        phone=ephone.getText().toString();
        mail=email.getText().toString();
        //address=eaddress.getText().toString();
        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://140.126.146.26/chu/api/cus/create",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        //Log.d("Response", response);
                        Toast.makeText(register.this, "註冊成功", Toast.LENGTH_SHORT).show();
                        Intent go= new Intent(register.this,Log.class);
                        startActivity(go);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", response);
                        Toast.makeText(register.this, "註冊失敗", Toast.LENGTH_SHORT).show();
                        eacc.setError("帳號已被使用");
                        eacc.requestFocus();
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
        Mysingleton.getInstance(register.this).addTorequestque(postRequest);
    }
}
