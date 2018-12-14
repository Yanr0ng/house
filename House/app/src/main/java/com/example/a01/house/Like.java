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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Like extends AppCompatActivity {
    RecyclerView recyclerView;
    List<StoreMember1> mDatas=new ArrayList<>();
    private RequestQueue requestQueue;
    DividerItemDecoration dividerItemDecoration;
    String JSON_URL="http://140.126.146.26/chu/api/Stores/findall";
    public TextView txv;
    int count;
    String[] ssid= new String[10];
    String cuserid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        cuserid=Log.cuserid;
        //getData();
        getlike();
        recyclerView=findViewById(R.id.recycle_like);
        ViewAdapter myadapter= new ViewAdapter(this,mDatas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL); //分線控制
        //recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(myadapter);
        myadapter.notifyDataSetChanged();

    }
    public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.MyViewHolder> {
        private Context context;
        private List<StoreMember1> mDatas;

        public ViewAdapter(Context context, List<StoreMember1> dd) {
            this.context = context;
            this.mDatas = dd;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {

            View view;
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view=inflater.inflate(R.layout.store_row_item,parent,false);
            final MyViewHolder holder = new MyViewHolder(view);
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer a=holder.getAdapterPosition();
                    Toast.makeText(v.getContext(), "click" +(a),Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, StoreDetailActivity.class);
                    //i.putExtra("store_name", mDatas.get(a).getSname());
                    //i.putExtra("store_phone", mDatas.get(a).getSphone());
                    //i.putExtra("store_inf", mDatas.get(a).getSinf());
                    i.putExtra("store_id",mDatas.get(a).getSuserid());
                    i.putExtra("surl","http://140.126.146.26/chuhouse/images/"+mDatas.get(a).getSphotoname());
                    context.startActivity(i);
                }
            });
            return holder;
    }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.tv_name.setText(mDatas.get(position).getSname());
            holder.tv_phone.setText(mDatas.get(position).getSphone());
            holder.tv_inf.setText(mDatas.get(position).getSinf());
            holder.tv_class.setText(mDatas.get(position).getSaddress());
            String Url ="http://140.126.146.26/chuhouse/images/"+mDatas.get(position).getSphotoname();
            Context mcontext=holder.img_store.getContext();
            Glide.with(mcontext).load(Url).into(holder.img_store);

        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            View view;
            TextView tv_name;
            TextView tv_class;
            TextView tv_phone;
            TextView tv_inf;
            LinearLayout container;
            ImageView img_store;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                container = itemView.findViewById(R.id.container);
                tv_name = itemView.findViewById(R.id.store_name);
                tv_class = itemView.findViewById(R.id.store_class);
                tv_phone = itemView.findViewById(R.id.store_phone);
                tv_inf = itemView.findViewById(R.id.store_inf);
                img_store=itemView.findViewById(R.id.storepic);
            }
        }
    }
    public void getData() {

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,JSON_URL,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    Integer a=0;
                    Intent intent= getIntent();
                    //String[] ssid=new String[10];
                    //ssid=intent.getStringArrayExtra("ssid");

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        StoreMember1 storeMember1 = new StoreMember1();

                        if(jsonObject.getString("suserid").toString().trim().equals(ssid[a])) {
                            a++;
                            storeMember1.setSuserid(jsonObject.getString("suserid"));
                            storeMember1.setSname(jsonObject.getString("sname"));
                            storeMember1.setSphone(jsonObject.getString("sphone"));
                            storeMember1.setSphotoname(jsonObject.getString("sphotoname"));
                            storeMember1.setSaddress(jsonObject.getString("saddress"));
                            //storeMember1.setSclass(jsonObject.getString("sclass"));
                            storeMember1.setSinf(jsonObject.getString("sinf"));
                            mDatas.add(storeMember1);
                        }
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                setmRecyclerView(mDatas);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void setmRecyclerView(List<StoreMember1> mDatas) {
        ViewAdapter myadapter= new ViewAdapter(this,mDatas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL); //分線控制
        //recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(myadapter);
        myadapter.notifyDataSetChanged();
    }
    public void getlike() {
        final String JSON_URL = "http://140.126.146.26/chu/api/like/find/"+cuserid;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,JSON_URL,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Integer a=0;
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jo = response.getJSONObject(i);
                        ssid[a]=jo.getString("suserid").toString().trim(); a++;
                    }
                    getData();
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


}
