package com.example.shafy.whatsthere;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.shafy.whatsthere.Data.NewsDbHelper;
import com.example.shafy.whatsthere.Data.WhatsThereContract;
import com.example.shafy.whatsthere.Data.WhatsThereContract.NewsTable;
import com.example.shafy.whatsthere.Utils.News;
import com.squareup.picasso.Picasso;

/**
 * Created by mina essam on 14-Dec-16.
 */
public class NewsDetailsFragment extends Fragment {
    ImageView image;
    TextView author;
    TextView title;
    TextView description;
    TextView date;
    LinearLayout bloom;
    LinearLayout def;
    Button more;
    boolean starState;
    int position;
    MenuItem item ;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View fragment=inflater.inflate(R.layout.details_fragment,container,false);
        image=(ImageView)fragment.findViewById(R.id.details_image);
        author=(TextView) fragment.findViewById(R.id.details_autor);
        title=(TextView) fragment.findViewById(R.id.details_title);
        description=(TextView) fragment.findViewById(R.id.details_description);
        date=(TextView) fragment.findViewById(R.id.details_date);
        more=(Button) fragment.findViewById(R.id.continue_reading_button);
        bloom=(LinearLayout)fragment.findViewById(R.id.bloomCon);
        def=(LinearLayout)fragment.findViewById(R.id.defaultCon);
        setHasOptionsMenu(true);
        Bundle bundle=getArguments();
        position=bundle.getInt("pos");
        Picasso.with(getContext()).load(News.news.get(position).getNewsImageURL()).error(R.drawable.no).into(image);
        String name=News.news.get(position).getAuthor();
        String s;
        if(!name.equals("null"))
        {
            String ss="more";
            if(name.length()>20){
                date=(TextView) fragment.findViewById(R.id.details_dateb);
                def.setVisibility(View.GONE);
                bloom.setVisibility(View.VISIBLE);
            }
            author.setText(name);
        }
        else {
            s=getString(R.string.unknown);
            author.setText(s);
        }
        title.setText(News.news.get(position).getTitle());
        description.setText(News.news.get(position).getDescription());
        date.setText(News.news.get(position).getDate());

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(News.news.get(position).getNewsURL()));
                startActivity(intent);
            }
        });

        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.details, menu);
        super.onCreateOptionsMenu(menu,inflater);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu){

        if(isInDatabase()){
            starState=true;
            menu.findItem(R.id.fav).setIcon(R.drawable.ic_fav);
        }
        else {
            starState=false;
            menu.findItem(R.id.fav).setIcon(R.drawable.ic_fav_border);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item1) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item1.getItemId();
        if(isInDatabase()){
            starState=true;
        }
        else {
            starState=false;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.fav) {
            // this is a favourite and i should remove it
            if(starState){
                starState=false;
                item1.setIcon(R.drawable.ic_fav_border);
                removeFavouriteNews();
                Toast.makeText(getContext(),"Deleted From Favourites",Toast.LENGTH_SHORT).show();
            }
            //this is not a favourite and i should add it
            else {
                item1.setIcon(R.drawable.ic_fav);
                addFavouriteNews();
                Toast.makeText(getContext(),"Added To Favourites",Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    void addFavouriteNews(){

        NewsDbHelper helper=new NewsDbHelper(getContext());
        SQLiteDatabase database=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(NewsTable.AUTHOR_COLUMN,News.news.get(position).getAuthor());
        values.put(NewsTable.TITLE_COLUMN,News.news.get(position).getTitle());
        values.put(NewsTable.DESCRIPTION_COLUMN,News.news.get(position).getDescription());
        values.put(NewsTable.DATE_COLUMN,News.news.get(position).getDate());
        values.put(NewsTable.IMAGE_URL,News.news.get(position).getNewsImageURL());
        values.put(NewsTable.NEWS_URL,News.news.get(position).getNewsURL());
        database.insert(NewsTable.TABLE_NAME,null,values);
        database.close();
    }

    void removeFavouriteNews(){
        NewsDbHelper helper=new NewsDbHelper(getContext());
        SQLiteDatabase database=helper.getWritableDatabase();
        String[] args=new String[1];
        args[0]=News.news.get(position).getNewsURL();
        database.delete(NewsTable.TABLE_NAME,NewsTable.NEWS_URL+"=?",args);
        database.close();

    }

      boolean isInDatabase(){
        NewsDbHelper helper=new NewsDbHelper(getContext());
        SQLiteDatabase database=helper.getWritableDatabase();
        String[] selection={News.news.get(position).getNewsURL()};
        Cursor cursor=database.rawQuery(WhatsThereContract.SELECT_ROW_BY_NEWS_URL +" =? ",selection);

        if(cursor.getCount()>0) {
            database.close();
            return true;
        }
        else {
            database.close();
            return false;
        }
    }
}
