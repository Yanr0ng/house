package com.example.a01.house;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

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

public class StoreDetailActivity extends AppCompatActivity {
    MySQLiteManager mydb=new MySQLiteManager(this);
    String last_spl_id;
    String spl_id;
    String ido;
    String id;
    private RequestQueue requestQueue;
    String name,phone,inf,pic,surl,address,sclass,hour;
    TextView tv_name,tv_phone,tv_desc,tv_address,tv_class;
    ImageView imgv;
    public String cname,cuserid,cateid,edtaddress,g,cphone,edtreceiver;
    Context context;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_detail);
        tv_name=findViewById(R.id.astore_name);
        tv_phone=findViewById(R.id.astore_phone);
        tv_address=findViewById(R.id.astore_address);
        tv_desc=findViewById(R.id.a_desc);
        tv_class=findViewById(R.id.astore_class);
        imgv=findViewById(R.id.astorepic);
        //getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cphone=Log.phone;
        cname=Log.name;
        cuserid=Log.cuserid;
        id= getIntent().getExtras().getString("store_id");
        surl=getIntent().getExtras().getString("surl");
        Glide.with(this).load(surl).into(imgv);
        getData();
        fetchDataorder a = new fetchDataorder();
        a.execute();

        //getname();
        CollapsingToolbarLayout collapsingToolbarLayout= findViewById(R.id.collapsingtoolbar);
        collapsingToolbarLayout.setTitleEnabled(true);
        Button btn_menu=findViewById(R.id.btn_menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StoreDetailActivity.this, showmenu.class);
                Bundle bundle=new Bundle();
                bundle.putString("store_id",id);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        Button btn_like=findViewById(R.id.btn_like);
        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                //dialog.setTitle("Title"); //設定dialog 的title顯示內容
                dialog.setIcon(android.R.drawable.ic_dialog_alert);//設定dialog 的ICON
                dialog.setCancelable(false); //關閉 Android 系統的主要功能鍵(menu,home等...)
                dialog.setMessage("是否要加入最愛");
                dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // 按下"是"以後要做的事情
                    }
                });
                dialog.setNegativeButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addlike();
                    }
                });
                dialog.show();
            }
        });
        Button button=findViewById(R.id.btn_singleorder);
        Button button1=findViewById(R.id.btn_grouporder);
        final LayoutInflater layoutInflater = LayoutInflater.from(StoreDetailActivity.this);
        //final View edittext=layoutInflater.inflate(R.layout.edittext,null);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                final View edittext=layoutInflater.inflate(R.layout.edittext,null);
                //dialog.setTitle("Title"); //設定dialog 的title顯示內容
                dialog.setView(edittext);
                dialog.setIcon(android.R.drawable.ic_dialog_alert);//設定dialog 的ICON
                dialog.setCancelable(false); //關閉 Android 系統的主要功能鍵(menu,home等...)
                dialog.setMessage("請選擇取餐方式");
                dialog.setPositiveButton("外送", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        EditText edt=(EditText)edittext.findViewById(R.id.edt_address);
                        EditText edt1=(EditText)edittext.findViewById(R.id.edt_receiver);
                            edtaddress=edt.getText().toString();
                            edtreceiver=edt1.getText().toString();
                            if (TextUtils.isEmpty(edtaddress)) {
                                Toast.makeText(StoreDetailActivity.this, "請輸入地址", Toast.LENGTH_SHORT).show();
                            }else if(TextUtils.isEmpty(edtreceiver)){
                                Toast.makeText(StoreDetailActivity.this, "請輸入收件人", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Intent i = new Intent(StoreDetailActivity.this, GroupOrder.class);
                                Bundle bundle=new Bundle();
                                bundle.putString("store_id",id);
                                bundle.putString("orderguid",spl_id);
                                bundle.putString("address",edtaddress);
                                bundle.putString("oway","2.png");
                                bundle.putString("receiver",edtreceiver);
                                bundle.putString("self","no");
                                i.putExtras(bundle);
                                startActivity(i);
                            }
                    }
                });
                dialog.setNegativeButton("自取", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(StoreDetailActivity.this, GroupOrder.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("store_id",id);
                        bundle.putString("orderguid",spl_id);
                        bundle.putString("address","");
                        bundle.putString("oway","1.png");
                        bundle.putString("self","self");
                        i.putExtras(bundle);
                        startActivity(i);
                    }
                });
                dialog.show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                final View edittext=layoutInflater.inflate(R.layout.edittext,null);
                //dialog.setTitle("Title"); //設定dialog 的title顯示內容
                dialog.setView(edittext);
                dialog.setIcon(android.R.drawable.ic_dialog_alert);//設定dialog 的ICON
                dialog.setCancelable(false); //關閉 Android 系統的主要功能鍵(menu,home等...)
                dialog.setMessage("請選擇取餐方式");
                dialog.setPositiveButton("外送", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        EditText edt=(EditText)edittext.findViewById(R.id.edt_address);
                        EditText edt1=(EditText)edittext.findViewById(R.id.edt_receiver);
                        edtreceiver=edt1.getText().toString();
                        edtaddress=edt.getText().toString();
                        if (TextUtils.isEmpty(edtaddress)) {
                            Toast.makeText(StoreDetailActivity.this, "請輸入地址", Toast.LENGTH_SHORT).show();
                        }else if(TextUtils.isEmpty(edtreceiver)){
                            Toast.makeText(StoreDetailActivity.this, "請輸入收件人", Toast.LENGTH_SHORT).show();
                        }else{
                            postdata1();
                            Intent i = new Intent(StoreDetailActivity.this, OrderPage.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("store_id",id);
                            bundle.putString("orderguid",spl_id);
                            bundle.putString("address",edtaddress);
                            bundle.putString("receiver",edtreceiver);
                            //bundle.putString("oway","2.png");
                            i.putExtras(bundle);
                            startActivity(i);
                            finish();
                        }
                    }
                });
                dialog.setNegativeButton("自取", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        postdata();

                        Intent i = new Intent(StoreDetailActivity.this, OrderPage.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("store_id",id);
                        bundle.putString("orderguid",spl_id);
                        bundle.putString("address","");
                        //bundle.putString("oway","1.png");
                        i.putExtras(bundle);
                        startActivity(i);
                        finish();
                    }
                });
                dialog.show();
            }
        });
        /*String Url ="http://140.126.146.26/chuhouse/images/"+storeMember.getSphotoname();
        Context mcontext=holder.img_store.getContext();
        Glide.with(mcontext).load(Url).into(holder.img_store);*/
    }



    public void postdata(){
        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://140.126.146.26/chu/api/order/create",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        //Log.d("Response", response);
                        Toast.makeText(StoreDetailActivity.this, "success", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", response);
                        Toast.makeText(StoreDetailActivity.this, "error", Toast.LENGTH_SHORT).show();
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
                params.put("address","自取");
                params.put("receiver",cname);
                params.put("suserid",id);
                params.put("phone",Log.phone);
                params.put("orderdate",s);
                params.put("cphone",cphone);
                params.put("ostatus","尚未接受");
                params.put("note","");
                params.put("isapproved","0");
                params.put("oway","1.png");
                return params;
            }
        };
        Mysingleton.getInstance(StoreDetailActivity.this).addTorequestque(postRequest);
    }
    public void postdata1(){
        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://140.126.146.26/chu/api/order/create",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        //Log.d("Response", response);
                        Toast.makeText(StoreDetailActivity.this, "success", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", response);
                        Toast.makeText(StoreDetailActivity.this, "error", Toast.LENGTH_SHORT).show();
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
                params.put("address",edtaddress);
                params.put("receiver",edtreceiver);
                params.put("phone",Log.phone);
                params.put("suserid",id);
                params.put("orderdate",s);
                params.put("ostatus","尚未接受");
                params.put("note","");
                params.put("isapproved","0");
                params.put("oway","2.png");
                return params;
            }
        };
        Mysingleton.getInstance(StoreDetailActivity.this).addTorequestque(postRequest);
    }


    class fetchDataorder extends AsyncTask<Void, Void, Void> {
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
    public void getData() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,"http://140.126.146.26/chu/api/Stores/findall",null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        if (jsonObject.getString("suserid").equals(id)){
                            //jsonObject.getString("suserid");
                            name=jsonObject.getString("sname");
                            phone=jsonObject.getString("sphone");
                            inf=jsonObject.getString("sinf");
                            pic=jsonObject.getString("sphotoname");
                            cateid=jsonObject.getString("cateid");
                            String sphotoname=jsonObject.getString("sphotoname");
                            //sclass=jsonObject.getString("sclass");
                            address=jsonObject.getString("saddress");
                            hour=jsonObject.getString("shour");
                            tv_address.setText(address);
                            tv_name.setText(name);
                            tv_desc.setText("開放時間:"+hour+"\n\n"+inf);
                            tv_phone.setText(phone);
                            getSclass();
                            String Url ="http://140.126.146.26/chuhouse/images/"+sphotoname;
                            Context mcontext=StoreDetailActivity.this;
                            Glide.with(mcontext).load(Url).into(imgv);
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
    }
    public void getSclass() {
        String J_URL="http://140.126.146.26/chu/api/scmm/find/"+cateid;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,J_URL,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                            sclass=jsonObject.getString("sclass");
                            tv_class.setText(sclass);
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
    }
    public void addlike(){
        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://140.126.146.26/chu/api/like/create",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        //Log.d("Response", response);
                        Toast.makeText(StoreDetailActivity.this, "added", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", response);
                        Toast.makeText(StoreDetailActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Calendar mCal = Calendar.getInstance();
                String s = DateFormat.format("yyyy-MM-dd kk:mm:ss", mCal.getTime()).toString().trim();    // kk:24小時制, hh:12小時制

                Map<String, String> params = new HashMap<String, String>();
                params.put("suserid",id);
                params.put("cuserid",cuserid);
                return params;
            }
        };
        Mysingleton.getInstance(StoreDetailActivity.this).addTorequestque(postRequest);
    }

}
