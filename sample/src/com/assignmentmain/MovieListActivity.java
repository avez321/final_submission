package com.assignmentmain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.assignment.model.MovieBeanList;
import com.assignment.utils.ConnectionDetector;
import com.assignmentmain.MovieDeatilActivity;
import com.assingment.adapters.ListAdapter;


public class MovieListActivity extends Activity {
	
	ListView lv;
	ArrayList<MovieBeanList> bean;
	ProgressDialog pDialog;
	  
	    String adult;
	    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_movie_list);
       customactionbar();
        cd=new ConnectionDetector(getApplicationContext());
        Boolean isInternetPresent = cd.isConnectingToInternet();
        bean=new ArrayList<MovieBeanList>();
        
        // check for Internet status
        if (isInternetPresent) {
            // Internet Connection is Present
            
        	 new GetMovieData().execute();
        } else {
           
            showAlertDialog( MovieListActivity.this, "No Internet Connection !!!",
                    "PLease enable internet and try again");
            
            
        }
        
        
        
        
        
        lv=(ListView) findViewById(R.id.listmovie);
        lv.setOnItemClickListener(new OnItemClickListener()
        {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				Log.e("movieid", bean.get(pos).getId());
				
				startActivity(new Intent(MovieListActivity.this,MovieDeatilActivity.class).putExtra("movieid", bean.get(pos).getId()));
				
				
				
			}
        	
        	
		});
       
        
      
        
       
        
        
    }

    private class GetMovieData extends AsyncTask<Void, Void, Void> {
    	 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MovieListActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
           
			
			try {
				 String str=GET("https://api.themoviedb.org/3/movie/upcoming?api_key=b7cd3340a794e5a2f35e3abb820b497f");
				 JSONObject jarr = new JSONObject(str);
				 JSONArray data = jarr.getJSONArray("results");
				
					Log.e("data", data.toString());
			
				for(int i=0;i<data.length();i++)
	            {
					
					JSONObject jobj=data.getJSONObject(i);
					if(jobj.getString("adult").equalsIgnoreCase("true"))
					{
						adult="(A)";
					}
						else
						{
							adult="(A/U)";
						}
					bean.add(new MovieBeanList(jobj.getString("original_title"),adult , jobj.getString("release_date"),jobj.getString("poster_path"),jobj.getString("id")));
					
	            }
				
				
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("exp", e.toString());
				
			}
			//Log.e("data",str);
            
			
			
 
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            
            
            ListAdapter adp=new ListAdapter(MovieListActivity.this, bean);
            lv.setAdapter(adp);
        }
 
    }

    
    public String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {
 
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
 
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
 
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
 
            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
 
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
 
        return result;
    }
  
  private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        return result;
 
    }
  
  void customactionbar()
  {
	  
	  
	  ActionBar mActionBar = getActionBar();
		mActionBar.setDisplayShowHomeEnabled(false);
		mActionBar.setDisplayShowTitleEnabled(false);
		mActionBar.setDisplayShowCustomEnabled(true);
		LayoutInflater mInflater = LayoutInflater.from(this);

		View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
		TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
		mTitleTextView.setText("Upcoming Movies");
 
		ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
		ImageView imageButton = (ImageView) mCustomView
				.findViewById(R.id.imginfo);
		imageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				startActivity(new Intent(MovieListActivity.this,InfoActivity.class));
			}
		});
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3F51BF")));
		mActionBar.setCustomView(mCustomView,params);
		
	}

		
	  
	  
	  
   @SuppressWarnings("deprecation")
public void showAlertDialog(Context context, String title, String message) {
       AlertDialog alertDialog = new AlertDialog.Builder(context).create();

       // Setting Dialog Title
       alertDialog.setTitle(title);

       // Setting Dialog Message
       alertDialog.setMessage(message);
        
       // Setting alert dialog icon
       alertDialog.setIcon(R.drawable.fail);

       // Setting OK Button
       alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int which) {
           }
       });

       // Showing Alert Message
       alertDialog.show();
   }
}
	  
	  
	  
	  
	  
	  
  
  
    

   
    

