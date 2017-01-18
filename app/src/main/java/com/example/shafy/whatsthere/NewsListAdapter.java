package com.example.shafy.whatsthere;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.shafy.whatsthere.Utils.News;
import com.squareup.picasso.Picasso;

/**
 * Created by mina essam on 08-Dec-16.
 */
public class NewsListAdapter extends BaseAdapter {
    private int newsSize =0;
    private Context mContext;
    public NewsListAdapter(Context context){
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
        public final TextView author;
        public final TextView title;
        //public final TextView description;
        public final ImageView newsImage;
        public ViewHolder(View v){
            author=(TextView) v.findViewById(R.id.authorName);
            title=(TextView)v.findViewById(R.id.headLine);
            //description=(TextView)v.findViewById(R.id.description_text_view);
            newsImage=(ImageView)v.findViewById(R.id.img);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           convertView= inflater.inflate(R.layout.news_feed_list_item,null);
            ViewHolder holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        ViewHolder viewHolder =(ViewHolder)convertView.getTag();

        if (News.news.get(position).getAuthor()!= "null")
            viewHolder.author.setText(News.news.get(position).getAuthor());
        else viewHolder.author.setText(R.string.unknown);
        viewHolder.title.setText(News.news.get(position).getTitle());
        //viewHolder.description.setText(News.news.get(position).getDescription());
        Picasso.with(mContext).load(News.news.get(position).getNewsImageURL())
                .error(R.drawable.no).into(viewHolder.newsImage);
        return convertView;

    }


    public void setNewsSize(int newsSize) {

        this.newsSize = newsSize;
        notifyDataSetChanged();

    }
}
