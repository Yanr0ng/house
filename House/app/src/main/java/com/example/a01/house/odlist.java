package com.example.a01.house;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class odlist extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<DetailList> mData= new ArrayList<>();
    DividerItemDecoration dividerItemDecoration;
    String JSON_URL ="http://140.126.146.26/chu/api/dtlist/findall";
    public String orderguid,cname,cuserid,suserid;
    String ttprice;
    Button btn_score,btn_report;
    Integer qty1=0,price1=0,tt1=0,ttt=000;
    TextView txv_ttt;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odlist);
        recyclerView=findViewById(R.id.rec_odlist);
        orderguid=getIntent().getStringExtra("orderguid");
        txv_ttt=findViewById(R.id.txv_ttt);
        //cname=getIntent().getStringExtra("cname");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //cuserid=getIntent().getStringExtra("cuserid");
        cuserid=Log.cuserid;
        cname=Log.name;
        suserid=getIntent().getStringExtra("suserid");
        btn_report=findViewById(R.id.btn_report);
        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go=new Intent(odlist.this,Report.class);
                Bundle bundle=new Bundle();
                bundle.putString("orderguid",orderguid);
                bundle.putString("cname",cname);
                bundle.putString("cuserid",cuserid);
                bundle.putString("suserid",suserid);
                go.putExtras(bundle);
                startActivity(go);
            }
        });
        btn_score=findViewById(R.id.btn_score);
        btn_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go=new Intent(odlist.this,Score.class);
                Bundle bundle=new Bundle();
                bundle.putString("orderguid",orderguid);
                bundle.putString("cname",cname);
                bundle.putString("cuserid",cuserid);
                bundle.putString("suserid",suserid);
                go.putExtras(bundle);
                startActivity(go);

            }
        });
        getData();
    }
    public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.MyViewHolder> {
        private Context context;
        private List<DetailList> mData;

        public ViewAdapter(Context context, List<DetailList> mData) {
            this.context = context;
            this.mData = mData;
        }
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view;
            LayoutInflater inflater = LayoutInflater.from(context);
            view=inflater.inflate(R.layout.od_row_item,parent,false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            DetailList detailList = mData.get(position);
            holder.odf_name.setText(String.valueOf(detailList.getMname()));
            holder.odf_price.setText(String.valueOf(detailList.getMprice()));
            holder.odf_qty.setText(String.valueOf(detailList.getMqty()));
            holder.odf_cname.setText(String.valueOf(detailList.getCname()));
            holder.odf_type.setText(String.valueOf(detailList.getMtype()));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            TextView odf_name;
            TextView odf_price;
            TextView odf_qty;
            TextView odf_cname;
            TextView odf_type;
            LinearLayout view_container;
            public MyViewHolder(@NonNull View itemView) {

                super(itemView);
                view_container= itemView.findViewById(R.id.odlist_contain);
                odf_cname=itemView.findViewById(R.id.odf_cname);
                odf_name=itemView.findViewById(R.id.odf_name);
                odf_price=itemView.findViewById(R.id.odf_price);
                odf_qty=itemView.findViewById(R.id.odf_qty);
                odf_type=itemView.findViewById(R.id.odf_type);
            }
        }
    }
    public void getData() {

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,JSON_URL,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        DetailList detailList = new DetailList();
                        if (jsonObject.getString("orderguid").equals(orderguid)){
                            detailList.setCuserid(jsonObject.getString("cuserid"));
                            detailList.setCname(jsonObject.getString("cname"));
                            detailList.setMid(jsonObject.getString("mid"));
                            detailList.setMprice(jsonObject.getString("mprice"));
                            detailList.setMname(jsonObject.getString("mname"));
                            detailList.setMqty(jsonObject.getString("mqty"));
                            detailList.setOrderguid(jsonObject.getString("orderguid"));
                            detailList.setMtype(jsonObject.getString("mtype"));
                            mData.add(detailList);
                            qty1=Integer.parseInt(jsonObject.getString("mqty"));
                            price1=jsonObject.getInt("mprice");
                            tt1=qty1*price1;
                            ttt=ttt+tt1;
                        }

                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                ttprice=String.valueOf(ttt);
                txv_ttt.setText("總價:"+ttprice);
                setmRecyclerView(mData);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void setmRecyclerView(List<DetailList> mData) {
        ViewAdapter myadapter= new ViewAdapter(this,mData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL); //分線控制
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(myadapter);
    }
}