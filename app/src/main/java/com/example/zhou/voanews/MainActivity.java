package com.example.zhou.voanews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.zhou.voanews.activities.TestActivity;
import com.example.zhou.voanews.activities.VOAPageActivity;
import com.example.zhou.voanews.adpters.VOAItemAdpter;
import com.example.zhou.voanews.data.BaseHelper;

import butterknife.BindView;
import butterknife.ButterKnife;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_recycler)
    RecyclerView mainRecycler;
    @BindView(R.id.nav_view)
    NavigationView navView;

    SharedPreferences sp;
    int type;
    VOAItemAdpter voaItemAdpter;
    VOAItemAdpter.OnMyClickListener onMyClickListener=new VOAItemAdpter.OnMyClickListener() {
        @Override
        public void onClick(int num) {

        }

        @Override
        public void onStartActivity(int pos) {

            Intent intent=new Intent(MainActivity.this, VOAPageActivity.class);
            intent.putExtra("pos",pos);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        sp=getPreferences(MODE_PRIVATE);
        type=sp.getInt("TYPE", BaseHelper.CSVOA);
        if(type==BaseHelper.CSVOA){
            setTitle("常速 VOA");
        } else {
            setTitle("慢速 VOA");
        }
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnMyClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initRecycer();
    }
private void initRecycer(){
    mainRecycler.setLayoutManager(new LinearLayoutManager(this));
    String u;
    if(type==BaseHelper.CSVOA)
        u=VOAItemAdpter.CSVOA;
    else
        u=VOAItemAdpter.MSVOA;
    voaItemAdpter=new VOAItemAdpter(u,onMyClickListener);
    mainRecycler.setAdapter(voaItemAdpter);

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

        switch (id){
            case R.id.menu_main_shift:
//                Log.d(TAG, "onOptionsItemSelected: ..");
                String title;
                if(voaItemAdpter==null)
                    break;
                if(type==BaseHelper.CSVOA){
                    voaItemAdpter.url=VOAItemAdpter.MSVOA;
                    title="慢速 VOA";
                    type=BaseHelper.MSVOA;
                } else {
                    voaItemAdpter.url=VOAItemAdpter.CSVOA;
                    title=("常速 VOA");
                    type=BaseHelper.CSVOA;
                }
                setTitle(title);
                Toast.makeText(this, String.format("切换到%s",title), Toast.LENGTH_SHORT).show();
                voaItemAdpter.update();
                break;
            case R.id.action_settings:
                Intent intent=new Intent(this, TestActivity.class);
                startActivity(intent);
        }

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sp.edit().putInt("TYPE",type).commit();
    }
}
