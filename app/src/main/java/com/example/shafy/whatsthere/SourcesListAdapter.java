package com.example.shafy.whatsthere;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shafy.whatsthere.Utils.Sources;
import com.squareup.picasso.Picasso;

/**
 * Created by mina essam on 15/12/2016.
 */

public class SourcesListAdapter extends BaseAdapter {
    private int newsSize =0;
    private Context mContext;

    public SourcesListAdapter(Context context){
        mContext=context;
    }
    @Override
    public int getCount() {
        return newsSize;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder{
        public final TextView name;
        public final TextView description;
        public final ImageView newsImage;
        public ViewHolder(View v){
            description=(TextView) v.findViewById(R.id.description);
            name=(TextView)v.findViewById(R.id.name);
            newsImage=(ImageView)v.findViewById(R.id.sImg);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= inflater.inflate(R.layout.sources_list_item,null);
            SourcesListAdapter.ViewHolder holder=new SourcesListAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }
        SourcesListAdapter.ViewHolder viewHolder =(SourcesListAdapter.ViewHolder)convertView.getTag();
        viewHolder.description.setText(Sources.sources.get(position).getDescription());
        viewHolder.name.setText(Sources.sources.get(position).getSourceName());
        Picasso.with(mContext).load("http://i.newsapi.org/"+Sources.sources.get(position).getSourceId()+"-l.png")
                .error(R.drawable.no).into(viewHolder.newsImage);
        return convertView;

    }

    public void setNewsSize(int newsSize) {

        this.newsSize = newsSize;
        notifyDataSetChanged();

    }
}


