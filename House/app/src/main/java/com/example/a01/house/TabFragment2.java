package com.example.a01.house;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class TabFragment2 extends Fragment {

    TextView textView;
    RecyclerView recyclerView;
    public List<Category> mDatas= new ArrayList<>();
    private RequestQueue requestQueue;
    DividerItemDecoration dividerItemDecoration;
    //public aViewAdapter adapter;
    private final String JSON_URL = "http://140.126.146.26/chu/api/cate/findall";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment2, container, false);
        recyclerView=view.findViewById(R.id.rec_cate);
        getData();
        setmRecyclerView(mDatas);
        return view;
    }
    public class aViewAdapter extends RecyclerView.Adapter<aViewAdapter.MyViewHolder> {
        private Context context;
        private List<Category> mDatas;

        public aViewAdapter(Context context, List<Category> mDatas) {
            this.context = context;
            this.mDatas = mDatas;
        }
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {

            View view;
            LayoutInflater inflater = LayoutInflater.from(context);
            view=inflater.inflate(R.layout.category_row_item,parent,false);
            final MyViewHolder viewHolder=new MyViewHolder(view);
            viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(parent.getContext(), CateStore.class);
                    i.putExtra("cateid",mDatas.get(viewHolder.getAdapterPosition()).getCateid());
                    //i.putExtra("surl","http://140.126.146.26/chuhouse/images/"+mDatas.get(viewHolder.getAdapterPosition()).getSphotoname());
                    parent.getContext().startActivity(i);
                }
            });

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            //OrderList orderList = mData.get(position);
            Category category=mDatas.get(position);
            holder.txv.setText(String.valueOf(category.getSclass()));

        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            TextView txv;
            LinearLayout view_container;

            public MyViewHolder(@NonNull View itemView) {

                super(itemView);
                view_container = itemView.findViewById(R.id.container);
                txv = itemView.findViewById(R.id.txv_cate);

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
                        //OrderList orderList = new OrderList();
                        Category category= new Category();
                        //if (jsonObject.getString("isapproved").equals("1")){

                            category.setCateid(jsonObject.getString("cateid"));
                            category.setSclass(jsonObject.getString("sclass"));

                            mDatas.add(category);



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
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    private void setmRecyclerView(List<Category> mDatas) {
        aViewAdapter myadapter= new aViewAdapter(getContext(),mDatas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL); //分線控制
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(myadapter);
        myadapter.notifyDataSetChanged();
    }
}
