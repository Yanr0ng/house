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

public class showmenu extends AppCompatActivity {
    RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<Menu> mData= new ArrayList<>();
    DividerItemDecoration dividerItemDecoration;
    String JSON_URL ="http://140.126.146.26/chu/api/menu/findall";
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showmenu);
        recyclerView=findViewById(R.id.rec_showmenu);
        getData();
        aViewAdapter myadapter= new aViewAdapter(this,mData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL); //分線控制
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(myadapter);
        myadapter.notifyDataSetChanged();
        Button button=findViewById(R.id.btn_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showmenu.this.finish();
            }
        });


    }
    public class aViewAdapter extends RecyclerView.Adapter<aViewAdapter.MyViewHolder> {
        private Context context;
        private List<Menu> mData;

        public aViewAdapter(Context context, List<Menu> mData) {
            this.context = context;
            this.mData = mData;
        }
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view;
            LayoutInflater inflater = LayoutInflater.from(context);
            view=inflater.inflate(R.layout.menulist,parent,false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            //OrderList orderList = mData.get(position);
            Menu menu= mData.get(position);
            holder.odf_name.setText(String.valueOf(menu.getMname()));
            holder.odf_price.setText(String.valueOf(menu.getMprice()));
            holder.odf_type.setText(String.valueOf(menu.getMtype()));
            //holder.odf_cname.setText(String.valueOf(menu.getCname()));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            TextView odf_name;
            TextView odf_price;
            TextView odf_type;
            //TextView odf_cname;
            //LinearLayout view_container;
            public MyViewHolder(@NonNull View itemView) {

                super(itemView);
                //view_container= itemView.findViewById(R.id.odlist_contain);
                //odf_cname=itemView.findViewById(R.id.odf_cname);
                odf_name=itemView.findViewById(R.id.odf_name);
                odf_price=itemView.findViewById(R.id.odf_price);
                odf_type=itemView.findViewById(R.id.odf_type);
            }
        }
    }
    public void getData() {
        id= getIntent().getExtras().getString("store_id");

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,JSON_URL,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        if(jsonObject.getString("suserid").equals(id)){
                            if (jsonObject.getString("historymenu").equals("0")){
                                Menu menu= new Menu();
                                menu.setMtype(jsonObject.getString("mtype"));
                                menu.setMname(jsonObject.getString("mname"));
                                menu.setMprice(jsonObject.getString("mprice"));
                                //menu.setOstatus(jsonObject.getString("ostatus"));
                                mData.add(menu);
                            }
                        }
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
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

    private void setmRecyclerView(List<Menu> mData) {
        aViewAdapter myadapter= new aViewAdapter(this,mData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL); //分線控制
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(myadapter);
        myadapter.notifyDataSetChanged();
    }
}
