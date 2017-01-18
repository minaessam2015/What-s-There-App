package com.example.shafy.whatsthere;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shafy.whatsthere.Utils.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mina essam on 08-Dec-16.
 */

public class NewsFeedFragment extends Fragment {
  //  @BindView(R.id.news_list_view)
    ListView newsList;
    NewsListAdapter adapter;
    private String source="";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment=inflater.inflate(R.layout.news_feed_fragment,container,false);
        //ButterKnife.bind(this,fragment);
        newsList=(ListView) fragment.findViewById(R.id.news_list_view);
        adapter=new NewsListAdapter(getContext());
        newsList.setAdapter(adapter);

        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent ii= new Intent(getContext(),NewsDetails.class);
                ii.putExtra("pos",i);
                startActivity(ii);

            }
        });
        setHasOptionsMenu(true);

        if(connected()){
        GetNews getNews=new GetNews();
        getNews.execute();}

        return fragment;
    }

    public void setS(String s){this.source=s;}
    public String getS(){return News.getSource();}

    public boolean connected(){
        ConnectivityManager manager=(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=manager.getActiveNetworkInfo();
        if(info!=null&&info.isConnected()){
            return true;
        }
        else return false;
    }

    class GetNews extends AsyncTask<Void,Void,Void>{
        JSONObject data;
        @Override
        protected Void doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String newsJsonStr = null;
            if(source!="")
            News.setSource(source);
            Uri builturi=Uri.parse(News.getArticlesUrl()).buildUpon()
                    .build();
            Log.v("News Path",(News.getArticlesUrl()));

            try {

                URL url=new URL(builturi.toString());
                Log.v("URI ","Built Url : "+builturi.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                // if was error in response code
                int response = urlConnection.getResponseCode();
                // Log.v("Response Code : ",String.valueOf(response) );
                if(response!=200){
                    Log.e("Response Code : ",String.valueOf(response) );
                    return null;
                }

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {

                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {

                    return null;
                }
                newsJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e("Cannot Read ", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            }
            finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Closing Stream", "Error closing stream", e);
                    }
                }
            }

            parseJSON(newsJsonStr);

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.setNewsSize(News.news.size());

        }

        void parseJSON(String json){
            try{
                News.news.clear();
                 String jsonSring=json;
                data=new JSONObject(jsonSring);
                JSONArray articles=data.getJSONArray("articles");
                for(int i=0;i<articles.length();i++){
                    JSONObject newsJSON=articles.getJSONObject(i);
                    // adding new News object to the list
                    News.news.add(new News(newsJSON.getString("author"),
                            newsJSON.getString("title"),
                            newsJSON.getString("description"),
                            newsJSON.getString("url"),
                            newsJSON.getString("urlToImage"),
                            newsJSON.getString("publishedAt")
                    ));

                }
            }catch (JSONException e){

            }
        }
    }

}
