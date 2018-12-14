package com.example.a01.house;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

public class OrderPage extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RequestQueue requestQueue;
    private List<Menu> mData= new ArrayList<>();
    DividerItemDecoration dividerItemDecoration;
    public static TextView txv_total;
    String name,price,qty,mqty;
    public String id,orderguid;
    private final Handler handler=new Handler();
    private boolean run= false;
    public String fname,fprice;
    public int a=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_page);
        mRecyclerView=findViewById(R.id.recycle_order);
        Button button=findViewById(R.id.btn_add);

        Button button1=findViewById(R.id.btn_cancel);
        Button button2=findViewById(R.id.btn_cart);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go=new Intent(OrderPage.this,odcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("orderguid",orderguid);
                go.putExtras(bundle);
                startActivity(go);
            }
        });

        Intent intent= getIntent();
        id= intent.getStringExtra("store_id");
        orderguid=intent.getStringExtra("orderguid");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                //dialog.setTitle("Title"); //設定dialog 的title顯示內容
                dialog.setIcon(android.R.drawable.ic_dialog_alert);//設定dialog 的ICON
                dialog.setCancelable(false); //關閉 Android 系統的主要功能鍵(menu,home等...)
                dialog.setMessage("是否要提交訂單");
                dialog.setPositiveButton("否", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // 按下"是"以後要做的事情
                    }
                });
                dialog.setNegativeButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        putdata();
                        Intent go = new Intent(OrderPage.this,MainActivity.class);
                        startActivity(go);

                    }
                });
                dialog.show();

            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                //dialog.setTitle("Title"); //設定dialog 的title顯示內容
                dialog.setIcon(android.R.drawable.ic_dialog_alert);//設定dialog 的ICON
                dialog.setCancelable(false); //關閉 Android 系統的主要功能鍵(menu,home等...)
                dialog.setMessage("是否要取消訂單");
                dialog.setPositiveButton("否", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // 按下"是"以後要做的事情
                    }
                });
                dialog.setNegativeButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent go = new Intent(OrderPage.this,MainActivity.class);
                        startActivity(go);

                    }
                });
                dialog.show();
            }
        });

        dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), LinearLayoutManager.VERTICAL); //分線控制
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        /*name = intent.getStringExtra("food_name");
        price = intent.getStringExtra("food_price");
        qty= intent.getStringExtra("qty");*/

        txv_total=findViewById(R.id.txv_total);
        txv_total.setVisibility(View.INVISIBLE);
        //txv_total.append(name+"   價錢"+price+"   數量"+mqty);
        getData();

    }




    public class RecycelerViewAdapter extends RecyclerView.Adapter<RecycelerViewAdapter.MyViewHolder>{
        List<Menu> mData;
        Context context;

        public RecycelerViewAdapter(List<Menu> mData, Context context) {
            this.mData = mData;
            this.context = context;
        }

        @NonNull
        @Override

        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v;
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            v = inflater.inflate(R.layout.order_row_item, parent, false);
            final MyViewHolder viewHolder = new MyViewHolder(v);
            viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer a = viewHolder.getAdapterPosition();
                    //Toast.makeText(v.getContext(), "click" +(a),Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context,SingleAddActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("store_id",id);
                    bundle.putString("mid",mData.get(a).getMid());
                    bundle.putString("food_name",mData.get(a).getMname());
                    bundle.putString("food_price",mData.get(a).getMprice());
                    //bundle.putString("orderguid",orderguid);
                    fname=mData.get(a).getMname();
                    fprice=mData.get(a).getMprice();
                    i.putExtras(bundle);
                    context.startActivity(i);
                    //txv_total.setVisibility(View.VISIBLE);
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.f_price.setText(mData.get(position).getMprice());
            holder.f_name.setText(mData.get(position).getMname());
            holder.f_type.setText(mData.get(position).getMtype());
            //holder.f_qty.setText(mData.get(position).getMqty());
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            TextView f_name;
            TextView f_price;
            TextView f_type;
            LinearLayout view_container;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                //spinner=itemView.findViewById(R.id.spinner);
                view_container= itemView.findViewById(R.id.container_order);
                //f_qty=itemView.findViewById(R.id.f_qty);
                f_name=itemView.findViewById(R.id.f_name);
                f_price=itemView.findViewById(R.id.f_price);
                f_type=itemView.findViewById(R.id.f_type);
                //f_qty=itemView.findViewById(R.id.f_qty);
            }
        }
    }


    public void getData() {
        //final String name = getIntent().getExtras().getString("name");
        //final String id=getIntent().getExtras().getString("store_id");


        final String JSON_URL = "http://140.126.146.26/chu/api/Menu/find/"+id;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,JSON_URL,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    final String id = getIntent().getExtras().getString("store_id");
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jo = response.getJSONObject(i);
                        Menu menu= new Menu();
                        if(jo.getString("historymenu").equals("0")) {
                            menu.setMid(jo.getString("mid"));
                            menu.setMname(jo.getString("mname"));
                            menu.setMtype(jo.getString("mtype"));
                            menu.setMprice(jo.getString("mprice"));
                            mData.add(menu);
                        }
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                //txv.setText("\n"+"精選店家:");
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
    private void setmRecyclerView(List<Menu> mData){
        RecycelerViewAdapter myadapter= new RecycelerViewAdapter(mData,this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(myadapter);
    }
    public void putdata(){
        id= getIntent().getStringExtra("store_id");
        orderguid=getIntent().getStringExtra("orderguid");
        final String oway=getIntent().getStringExtra("oway");
        StringRequest postRequest = new StringRequest(Request.Method.PUT, "http://140.126.146.26/chu/api/order/update",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        //Log.d("Response", response);
                        Toast.makeText(OrderPage.this, "success", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", response);
                        //Toast.makeText(OrderPage.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("orderguid",orderguid);
                params.put("isapproved","1");
                //params.put("oway",oway);
                return params;
            }
        };
        Mysingleton.getInstance(OrderPage.this).addTorequestque(postRequest);
    }
}
