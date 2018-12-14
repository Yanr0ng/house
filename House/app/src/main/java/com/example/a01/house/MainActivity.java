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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.a01.house.adapter.ViewPagerFragmentAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String name,cuserid;
    TextView txv_log,txv_name;
    private ViewPager myViewPager;
    //private RecyclerView recycler_view;
    private ArrayList<String> mData = new ArrayList<>();
    private final String JSON_URL = "140.126.146.26/chu/api/Stores/findall";
    private JsonArrayRequest request;
    private List<StoreMember> lstStore;
    private RequestQueue requestQueue;
    private RecyclerView recyclerView;
    String suserid;
    String[] ssid= new String[10];
    ActionBar ab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        Intent intent= getIntent();
        name=Log.name;
        cuserid=Log.cuserid;


        txv_log=findViewById(R.id.txv_log);
        //txv_log.setText(Log.name);
        txv_name=findViewById(R.id.txv_name);
        //txv_name.setText(Log.name);
        lstStore = new ArrayList<>();


        myViewPager = (ViewPager) findViewById(R.id.myViewPager);
        setViewPager();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.inflateHeaderView(R.layout.nav_header_main);
        TextView tv_log=(TextView)header.findViewById(R.id.txv_log);
        tv_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go= new Intent();
                go.setClass(MainActivity.this,Log.class);
                startActivity(go);
                MainActivity.this.finish();
            }
        });
        tv_log.setText(Log.name);
        TextView tv_name=(TextView)header.findViewById(R.id.txv_name);
        tv_name.setText("你好~"+Log.name);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //menu.a_n_drawer

        if (id == R.id.nav_order) {
            Intent go = new Intent(MainActivity.this,Order.class);
            Bundle bundle=new Bundle();
            bundle.putString("ID",name);
            bundle.putString("cuserid",cuserid);
            go.putExtras(bundle);
            startActivity(go);

        } else if (id == R.id.nav_history) {
            Intent go = new Intent(MainActivity.this,History.class);
            Bundle bundle=new Bundle();
            bundle.putString("ID",name);
            bundle.putString("cuserid",cuserid);
            go.putExtras(bundle);
            startActivity(go);
            //onDestroy();

        } else if (id == R.id.nav_like) {
            Intent go = new Intent(MainActivity.this,Like.class);
            Bundle bundle=new Bundle();
            bundle.putString("ID",name);
            bundle.putStringArray("ssid",ssid);
            go.putExtras(bundle);
            startActivity(go);

        } else if (id == R.id.nav_random) {
            Intent go = new Intent(MainActivity.this,Random.class);
            startActivity(go);

        } else if (id == R.id.nav_profile) {
            Intent go = new Intent(MainActivity.this,Profile.class);
            startActivity(go);

        } else if (id == R.id.nav_share) {
            Intent go=new Intent(MainActivity.this,Join.class);
            startActivity(go);

        } else if (id == R.id.nav_send) {
            Intent go=new Intent(MainActivity.this,Log.class);
            startActivity(go);
            MainActivity.this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setViewPager(){
        //首頁viewpage設定
        FragmentList_One myFragment1 = new FragmentList_One();
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(myFragment1);
        ViewPagerFragmentAdapter myFragmentAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragmentList);
        myViewPager.setAdapter(myFragmentAdapter);
    }
}

