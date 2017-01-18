package com.example.shafy.whatsthere;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.shafy.whatsthere.Data.NewsDbHelper;
import com.example.shafy.whatsthere.Data.WhatsThereContract;
import com.example.shafy.whatsthere.Utils.News;

public class Favourite extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_favourite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();

        if(bundle.getString("fragment").equals("News")){
            FavouriteNewsFragment fragment=new FavouriteNewsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.favourites_frame,fragment).commit();

        }
        else {

        }

    }
    Cursor getDatabase(){
        NewsDbHelper helper=new NewsDbHelper(this);
        SQLiteDatabase database=helper.getReadableDatabase();
        Cursor cursor=database.rawQuery(WhatsThereContract.SELECT_ALL,null);
        database.close();
        return cursor;
    }

    @Override
    public void onBackPressed() {

        Intent ii= new Intent(getApplicationContext(),NewsHome.class);
        startActivity(ii);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.favourit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.ho) {
            Intent ii= new Intent(getApplicationContext(),NewsHome.class);
            NewsHome.setfr(true);
            startActivity(ii);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
