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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.List;

public class TabFragment1 extends Fragment {
    private RecyclerView mRecyclerView;
    private final String JSON_URL = "http://140.126.146.26/chu/api/Stores/findall";
    public List<StoreMember> mDatas= new ArrayList<>();
    private RequestQueue requestQueue;
    DividerItemDecoration dividerItemDecoration;
    TextView txv;
    String data = "";
    String dataParsed = "";
    String singleParsed = "";
    String fb_findnumber = "";
    String getid="";
    String name = "";   // up

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment1, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_tf1);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        getData();//初始化数据
        dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), LinearLayoutManager.VERTICAL); //分線控制
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(new RecycelerViewAdapter(mDatas));
        txv=view.findViewById(R.id.txv);
        return view;
    }



    public class RecycelerViewAdapter extends RecyclerView.Adapter<RecycelerViewAdapter.MyViewHolder> {
        private List<StoreMember> mDatas;
        Context context;

        public RecycelerViewAdapter(List<StoreMember> dd) {
            this.mDatas = dd;
        }


        @NonNull
        @Override
        public RecycelerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
            View v;
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            v = inflater.inflate(R.layout.store_row_item, parent, false);

            final RecycelerViewAdapter.MyViewHolder viewHolder = new RecycelerViewAdapter.MyViewHolder(v);
            viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(parent.getContext(), StoreDetailActivity.class);
                    //i.putExtra("store_name", mDatas.get(viewHolder.getAdapterPosition()).getSname());
                    //i.putExtra("store_phone", mDatas.get(viewHolder.getAdapterPosition()).getSphone());
                    //i.putExtra("store_class", mDatas.get(viewHolder.getAdapterPosition()).getSclass());
                    //i.putExtra("store_inf", mDatas.get(viewHolder.getAdapterPosition()).getSinf());
                    i.putExtra("store_id",mDatas.get(viewHolder.getAdapterPosition()).getSuserid());
                    i.putExtra("surl","http://140.126.146.26/chuhouse/images/"+mDatas.get(viewHolder.getAdapterPosition()).getSphotoname());

                    parent.getContext().startActivity(i);
                }
            });

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            StoreMember storeMember = mDatas.get(position);
            holder.tv_name.setText(String.valueOf(storeMember.getSname()));
            holder.tv_phone.setText(String.valueOf(storeMember.getSphone()));
            holder.tv_inf.setText(String.valueOf(storeMember.getSinf()));
            holder.tv_class.setText(String.valueOf(storeMember.getSaddress()));

            String Url ="http://140.126.146.26/chuhouse/images/"+storeMember.getSphotoname();
            Context mcontext=holder.img_store.getContext();
            Glide.with(mcontext).load(Url).into(holder.img_store);

        }
        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public List<StoreMember> mData;
            TextView tv_name;
            TextView tv_class;
            TextView tv_phone;
            TextView tv_inf;
            LinearLayout view_container;
            ImageView img_store;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                view_container = itemView.findViewById(R.id.container);
                this.mData = mData;
                tv_name = itemView.findViewById(R.id.store_name);
                //tv_class = itemView.findViewById(R.id.store_class);
                tv_phone = itemView.findViewById(R.id.store_phone);
                tv_inf = itemView.findViewById(R.id.store_inf);
                tv_class=itemView.findViewById(R.id.store_class);


                img_store=itemView.findViewById(R.id.storepic);
                //img_store=itemView.findViewById(R.id.storepic);
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
                        StoreMember storeMember = new StoreMember();
                        //name=jsonObject.getString("Sname");
                        //txv.setText(name+",");
                        if(jsonObject.getString("sstatus").equals("1")){
                            storeMember.setSuserid(jsonObject.getString("suserid"));
                            storeMember.setSname(jsonObject.getString("sname"));
                            storeMember.setSphone(jsonObject.getString("sphone"));
                            storeMember.setSphotoname(jsonObject.getString("sphotoname"));
                            storeMember.setSaddress(jsonObject.getString("saddress"));
                            //storeMember.setSclass(jsonObject.getString("sclass"));
                            storeMember.setSinf(jsonObject.getString("sinf"));
                            //storeMember.setSuserid(jsonObject.getString("suserid"));
                            mDatas.add(storeMember);
                        }

                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                txv.setText("\n"+"精選店家:");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }
}




