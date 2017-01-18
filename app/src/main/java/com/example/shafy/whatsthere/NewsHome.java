package com.example.shafy.whatsthere;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.shafy.whatsthere.Utils.News;

import java.net.InetAddress;

import static java.security.AccessController.getContext;

public class NewsHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private boolean mIsTwoPne=false;
    private static boolean fr =true;


    public SharedPreferences s(){
        SharedPreferences save = getSharedPreferences("com.example.shafy.whatsthere", Context.MODE_PRIVATE);
        return save;
    }
    public static void setfr(boolean fr){
        NewsHome.fr =fr;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sidebar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NewsFeedFragment newsFragment=new NewsFeedFragment();
        String size;



        if(fr){
            size = s().getString("source", "techcrunch");
            Log.v("sup",size);
            newsFragment.setS(size);
            s().edit().putBoolean("fr",false).commit();
            fr=false;
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.news_first_look,newsFragment).commit();


        if(findViewById(R.id.news_details_fragment)!=null){
            mIsTwoPne=true;
        }
        else {
            mIsTwoPne=false;
        }

        if(!isNetworkConnected()){
            Intent intent=new Intent(this,Favourite.class);
            intent.putExtra("fragment","News");
            startActivity(intent);
        }


    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        String size = s().getString("source", "cnn");
        NewsFeedFragment nf = new NewsFeedFragment();
        String current = nf.getS();
        if (size.equals(current)) {
            starState = true;
            Log.v("sou",s().getString("source","not here"));
            menu.findItem(R.id.home).setIcon(R.drawable.ic_fav);
        } else {
            starState = false;
            menu.findItem(R.id.home).setIcon(R.drawable.ic_fav_border);
        }
        return false;
    }
    boolean starState;
    @Override
    public boolean onOptionsItemSelected(MenuItem item1) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item1.getItemId();
        String size = s().getString("source", "cnn");
        NewsFeedFragment nf =new NewsFeedFragment();
        String current = nf.getS();
        starState = size.equals(current);
        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            if(!starState){
                item1.setIcon(R.drawable.ic_fav);
                s().edit().putString("source",current).commit();
            }
        }

        return super.onOptionsItemSelected(item1);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id==R.id.favorite){

            Intent intent=new Intent(this,Favourite.class);
            intent.putExtra("fragment","News");
            startActivity(intent);
            finish();

        }
        if(id==R.id.general){

            CategoryFragment CategoryFragment=new CategoryFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.news_first_look,CategoryFragment).commit();

        }
        if(id==R.id.business){

            CategoryFragment CategoryFragment=new CategoryFragment();
            CategoryFragment.setCat("business");
            getSupportFragmentManager().beginTransaction().replace(R.id.news_first_look,CategoryFragment).commit();

        }
        if(id==R.id.music){

            CategoryFragment CategoryFragment=new CategoryFragment();
            CategoryFragment.setCat("music");
            getSupportFragmentManager().beginTransaction().replace(R.id.news_first_look,CategoryFragment).commit();

        }
        if(id==R.id.technology){

            CategoryFragment CategoryFragment=new CategoryFragment();
            CategoryFragment.setCat("technology");
            getSupportFragmentManager().beginTransaction().replace(R.id.news_first_look,CategoryFragment).commit();

        }
        if(id==R.id.entertainment){

            CategoryFragment CategoryFragment=new CategoryFragment();
            CategoryFragment.setCat("entertainment");
            getSupportFragmentManager().beginTransaction().replace(R.id.news_first_look,CategoryFragment).commit();

        }
        if(id==R.id.sport){

            CategoryFragment CategoryFragment=new CategoryFragment();
            CategoryFragment.setCat("sport");
            getSupportFragmentManager().beginTransaction().replace(R.id.news_first_look,CategoryFragment).commit();

        }
        if(id==R.id.science_and_nature){

            CategoryFragment CategoryFragment=new CategoryFragment();
            CategoryFragment.setCat("science-and-nature");
            getSupportFragmentManager().beginTransaction().replace(R.id.news_first_look,CategoryFragment).commit();

        }
        if(id==R.id.gaming){

            CategoryFragment CategoryFragment=new CategoryFragment();
            CategoryFragment.setCat("gaming");
            getSupportFragmentManager().beginTransaction().replace(R.id.news_first_look,CategoryFragment).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
