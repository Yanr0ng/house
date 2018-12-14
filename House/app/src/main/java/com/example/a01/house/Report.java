package com.example.a01.house;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Report extends AppCompatActivity {
    String orderguid,cname,cuserid,suserid,edt_content;
    TextView txv_st,txv_cu;
    EditText edt;
    Button btn,btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        txv_st=findViewById(R.id.txv_st);
        txv_cu=findViewById(R.id.txv_cu);

        edt=findViewById(R.id.edt_report);

        btn=findViewById(R.id.btn_re);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postdata();
                Report.this.finish();
                /*Intent go=new Intent(Report.this,MainActivity.class);
                startActivity(go);*/

            }
        });
        btn1=findViewById(R.id.btn_can);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Report.this.finish();
                /*Intent go=new Intent(Report.this,MainActivity.class);
                startActivity(go);*/
            }
        });
        orderguid=getIntent().getStringExtra("orderguid");
        cname=getIntent().getStringExtra("cname");
        cuserid=getIntent().getStringExtra("cuserid");
        suserid=getIntent().getStringExtra("suserid");
        txv_st.setText("訂單編號:"+orderguid);
        txv_cu.setText("回報人:"+cname);

    }
    public void postdata(){
        //final String qty=edt.getText().toString().trim();
        //final String name=txvname.getText().toString().trim();
        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://140.126.146.26/chu/api/report/create",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        //Log.d("Response", response);
                        Toast.makeText(Report.this, "success", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", response);
                        Toast.makeText(Report.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Calendar mCal = Calendar.getInstance();
                String s = DateFormat.format("yyyy-MM-dd kk:mm:ss", mCal.getTime()).toString().trim();    // kk:24小時制, hh:12小時制
                edt_content=edt.getText().toString();
                Map<String, String> params = new HashMap<String, String>();
                params.put("orderguid",orderguid);
                params.put("cuserid",cuserid);
                //params.put("receiver",cname);
                params.put("suserid",suserid);
                params.put("crcontent",edt_content);
                return params;
            }
        };
        Mysingleton.getInstance(Report.this).addTorequestque(postRequest);
    }
}
