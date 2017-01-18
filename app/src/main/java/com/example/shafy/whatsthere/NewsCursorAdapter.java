package com.example.shafy.whatsthere;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shafy.whatsthere.Data.WhatsThereContract.NewsTable;
import com.squareup.picasso.Picasso;

/**
 * Created by mina essam on 16-Dec-16.
 */
public class NewsCursorAdapter extends CursorAdapter {



    public NewsCursorAdapter(Context context, Cursor cursor){
        super(context,cursor,0);

    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.favourite_news_list_item,parent,false);
    }


    @Override
    public void bindView(final View view, final Context context, final Cursor cursor) {

        TextView author=(TextView)view.findViewById(R.id.favourite_news_author);
        TextView title=(TextView)view.findViewById(R.id.favourite_news_title);
        TextView description=(TextView)view.findViewById(R.id.favourite_news_description);
        TextView date=(TextView)view.findViewById(R.id.favourite_news_date);
        ImageView image=(ImageView)view.findViewById(R.id.favourite_news_image);
        author.setText(cursor.getString(cursor.getColumnIndexOrThrow(NewsTable.AUTHOR_COLUMN)));
        title.setText(cursor.getString(cursor.getColumnIndexOrThrow(NewsTable.TITLE_COLUMN)));
        description.setText(cursor.getString(cursor.getColumnIndexOrThrow(NewsTable.DESCRIPTION_COLUMN)));
        date.setText(cursor.getString(cursor.getColumnIndexOrThrow(NewsTable.DATE_COLUMN)));
        Picasso.with(context).load(cursor.getString(cursor.getColumnIndex(NewsTable.IMAGE_URL))).
                error(R.drawable.no).into(image);
        Log.v("ID",cursor.getString(cursor.getColumnIndex(NewsTable.ID)));



    }


}
