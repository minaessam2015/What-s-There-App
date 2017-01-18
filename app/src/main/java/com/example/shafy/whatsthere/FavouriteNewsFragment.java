package com.example.shafy.whatsthere;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shafy.whatsthere.Data.NewsDbHelper;
import com.example.shafy.whatsthere.Data.WhatsThereContract;
import com.example.shafy.whatsthere.Data.WhatsThereContract.NewsTable;

/**
 * Created by mina essam on 16-Dec-16.
 */
public class FavouriteNewsFragment extends Fragment {
    Cursor cursor;
    static int position;
    NewsCursorAdapter adapter;
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        cursor=getDatabase();
        if(cursor.getCount()==0){
            if(!isNetworkConnected())
                return inflater.inflate(R.layout.no_connection,container,false);
            return inflater.inflate(R.layout.no_database,container,false);
        }
        View fragment=inflater.inflate(R.layout.favourite_list_view,container,false);
        adapter =new NewsCursorAdapter(getContext(),cursor);
        ListView listView=(ListView)fragment.findViewById(R.id.favourites_list);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);

        return fragment;
    }

    Cursor getDatabase(){
        NewsDbHelper helper=new NewsDbHelper(getContext());
        SQLiteDatabase database=helper.getReadableDatabase();
        Cursor c=database.rawQuery(WhatsThereContract.SELECT_ALL,null);

        return c;
    }
    void removeRow(int position){
        NewsDbHelper helper=new NewsDbHelper(getContext());
        SQLiteDatabase database=helper.getWritableDatabase();
        String[]args={cursor.getString(cursor.getColumnIndexOrThrow(NewsTable.ID))};
        database.delete(NewsTable.TABLE_NAME, NewsTable.ID+"=?",args);
        database.close();
        Intent intent=new Intent(getContext(),Favourite.class);
        intent.putExtra("fragment","News");
        startActivity(intent);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        Log.v("Context Menu","Menu Created");
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) menuInfo;
        position=info.position;
        MenuInflater inflater=getActivity().getMenuInflater();
        inflater.inflate(R.menu.favourite_context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                removeRow(position);
                adapter.changeCursor(getDatabase());
                Toast.makeText(getContext(),"Deleted",Toast.LENGTH_LONG).show();
                return true;
            case R.id.openNews:
                Cursor cursor=this.cursor;
                Intent intent=new Intent(Intent.ACTION_VIEW);
                if (cursor.moveToPosition(position)) {
                    intent.setData(Uri.parse(cursor.getString(cursor.getColumnIndexOrThrow(NewsTable.NEWS_URL))));
                    Log.v("intent url",cursor.getString(cursor.getColumnIndexOrThrow(NewsTable.NEWS_URL)));
                    startActivity(intent);
                    return true;
                }
                else {
                    Toast.makeText(getContext(),"No Url Found",Toast.LENGTH_SHORT).show();
                }

           default:   return super.onContextItemSelected(item);
        }

    }
}
