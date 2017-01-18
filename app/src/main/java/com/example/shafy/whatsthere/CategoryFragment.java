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
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.shafy.whatsthere.Utils.News;
import com.example.shafy.whatsthere.Utils.Sources;

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
 * Created by shafy on 15/12/2016.
 */

public class CategoryFragment extends Fragment {
    //  @BindView(R.id.news_list_view)
    ListView newsList;
    SourcesListAdapter adapter;
    private  String cat="general";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment=inflater.inflate(R.layout.news_feed_fragment,container,false);
        //ButterKnife.bind(this,fragment);
        newsList=(ListView) fragment.findViewById(R.id.news_list_view);
        adapter=new SourcesListAdapter(getContext());
        newsList.setAdapter(adapter);
        final ListView lv=(ListView)fragment.findViewById(R.id.news_list_view);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int pos= (int) lv.getItemAtPosition(i);
                Log.v("pos", String.valueOf(pos));
                String s =Sources.sources.get(i).getSourceId();
                Log.v("source",Sources.sources.get(i).getSourceId());
                News.setSource(s);
                Intent ii = new Intent(getContext(),NewsHome.class);
                startActivity(ii);

            }
        });

        if(connected()){
            CategoryFragment.GetNews getNews=new CategoryFragment.GetNews();
            getNews.execute();}

        return fragment;
    }

    public void setCat(String c){this.cat=c;}

    public boolean connected(){
        ConnectivityManager manager=(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=manager.getActiveNetworkInfo();
        if(info!=null&&info.isConnected()){
            return true;
        }
        else return false;
    }


    class GetNews extends AsyncTask<Void,Void,Void> {
        JSONObject data;
        @Override
        protected Void doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String newsJsonStr = null;
            Sources.setCategory(cat);
            Uri builturi=Uri.parse(Sources.getSourceUrl()).buildUpon()
                    .build();
            Log.v("Sources Path",(Sources.getSourceUrl()));

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
            adapter.setNewsSize(Sources.sources.size());

        }

        void parseJSON(String json){
            try{
                Sources.sources.clear();
                String jsonSring=json;
                data=new JSONObject(jsonSring);
                JSONArray articles=data.getJSONArray("sources");
                for(int i=0;i<articles.length();i++){
                    JSONObject newsJSON=articles.getJSONObject(i);
                    // adding new News object to the list
                    Sources.sources.add(new Sources(newsJSON.getString("id"),
                            newsJSON.getString("name"),
                            newsJSON.getString("description"),
                            newsJSON.getString("url")
                    ));
                    Log.v("id",newsJSON.getString("id"));
                }
            }catch (JSONException e){

            }
        }
    }
}
