package com.example.a01.house;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {
    String name,cuserid;
    RecyclerView recyclerView;
    List<OrderList> mData=new ArrayList<>();
    private RequestQueue requestQueue;
    DividerItemDecoration dividerItemDecoration;
    String JSON_URL="http://140.126.146.26/chu/api/order/findall";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        name= getIntent().getStringExtra("ID");
        cuserid= getIntent().getStringExtra("cuserid");
        recyclerView=findViewById(R.id.recycle_history);

        getData();
    }
    public class aViewAdapter extends RecyclerView.Adapter<aViewAdapter.MyViewHolder>{
        private Context context;
        private List<OrderList> mData;

        public aViewAdapter(Context context, List<OrderList> mData) {
            this.context = context;
            this.mData = mData;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            LayoutInflater inflater = LayoutInflater.from(context);
            view=inflater.inflate(R.layout.order_check,parent,false);
            final MyViewHolder viewHolder = new MyViewHolder(view);
            viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer a = viewHolder.getAdapterPosition();
                    //Toast.makeText(v.getContext(), "click" +(a),Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context,odlist.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("orderguid",mData.get(a).getOrderguid());
                    bundle.putString("suserid",mData.get(a).getSuserid());
                    i.putExtras(bundle);
                    context.startActivity(i);
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            OrderList orderList = mData.get(position);
            holder.oc_cusername.setText(name);
            //holder.oc_cusername.setText(String.valueOf(orderList.getCuserid()));
            holder.oc_storename.setText(String.valueOf(orderList.getSuserid()));
            holder.oc_orderguid.setText("/訂單編號"+String.valueOf(orderList.getOrderguid()));
            holder.oc_orderdate.setText(String.valueOf(orderList.getOrderdate()));
            holder.oc_ostatus.setText("/已完成");
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            TextView oc_cusername;
            TextView oc_storename;
            TextView oc_orderguid;
            TextView oc_orderdate;
            TextView oc_ostatus;
            LinearLayout view_container;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                view_container=itemView.findViewById(R.id.oc_container);
                oc_cusername = itemView.findViewById(R.id.oc_cusername);
                oc_storename = itemView.findViewById(R.id.oc_storename);
                oc_orderguid = itemView.findViewById(R.id.oc_orderguid);
                oc_orderdate = itemView.findViewById(R.id.oc_orderdate);
                oc_ostatus = itemView.findViewById(R.id.oc_ostatus);
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
                        OrderList orderList = new OrderList();
                        if (jsonObject.getString("cuserid").equals(Log.cuserid)){
                        if (jsonObject.getString("isapproved").equals("2")) {
                            orderList.setOrderdate(jsonObject.getString("orderdate"));
                            orderList.setCuserid(jsonObject.getString("cuserid"));
                            orderList.setSuserid(jsonObject.getString("suserid"));
                            orderList.setOrderguid(jsonObject.getString("orderguid"));
                            orderList.setOstatus(jsonObject.getString("ostatus"));
                            mData.add(orderList);
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

    private void setmRecyclerView(List<OrderList> mData) {
        aViewAdapter myadapter= new aViewAdapter(this,mData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL); //分線控制
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(myadapter);
    }
}
