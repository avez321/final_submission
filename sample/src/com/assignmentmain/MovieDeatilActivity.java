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
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.assignment.model.MovieBean;
import com.assingment.adapters.ViewPagerAdapter;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;


public class MovieDeatilActivity extends Activity {
	ListView lv;
	ArrayList<MovieBean> bean;
	ProgressDialog pDialog;
	 
	    String adult,title,popularity,overview,id;
	    ViewPagerAdapter adp;
	    String arr[];
	    TextView txttitle,txtoverview;
	    RatingBar rt;
	    Typeface typeface ;
		    ViewPager mPager;
		    PageIndicator mIndicator;
		    LinearLayout mainlayout;
	    
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.movie_details);
        getActionBar().setTitle("");
        
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3F51BF")));
        
        id=getIntent().getExtras().getString("movieid");
        txttitle=(TextView) findViewById(R.id.txtTitle);
        txtoverview=(TextView) findViewById(R.id.txtOverview);
        rt=(RatingBar)findViewById(R.id.ratingBar1);
        typeface = Typeface.createFromAsset(getAssets(), "comic-sans-ms.ttf");
       
        mainlayout =(LinearLayout) findViewById(R.id.mainlayout);
        new GetMovieData().execute();
        LayerDrawable stars = (LayerDrawable) rt
				.getProgressDrawable();
		stars.getDrawable(2).setColorFilter(Color.parseColor("#3F51BF"),
				PorterDuff.Mode.SRC_ATOP); // for filled stars
		stars.getDrawable(1).setColorFilter(Color.parseColor("#ffffff"),
				PorterDuff.Mode.SRC_ATOP); // for half filled stars
		stars.getDrawable(0).setColorFilter(Color.WHITE,
				PorterDuff.Mode.SRC_ATOP); 
       
        txttitle.setTypeface(typeface );
        txtoverview.setTypeface(typeface );
        
        
      
    }
    
    
    private class GetMovieData extends AsyncTask<Void, Void, Void> {
   	 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MovieDeatilActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
           
			
			try {
				 String str=GET("https://api.themoviedb.org/3/movie/"+id+"?api_key=b7cd3340a794e5a2f35e3abb820b497f");
				 JSONObject jarr = new JSONObject(str);
				 String data = jarr.getString("backdrop_path");
				String poster = jarr.getString("poster_path");
				  title = jarr.getString("original_title");
				  popularity = jarr.getString("popularity");
				  overview= jarr.getString("overview");
				
				 
				
					
					arr=new String[]{poster,data};
					
					
			

				
				
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("exp", e.toString());
				
			}
		
            
			
			
 
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
            adp=new ViewPagerAdapter(MovieDeatilActivity.this,arr);

            mPager = (ViewPager)findViewById(R.id.pager);
            mPager.setAdapter(adp);

            mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
            mIndicator.setViewPager(mPager);
            txttitle.setText(title);
            customactionbar(title);
            	 txtoverview.setText(overview);
            rt.setRating(Float.parseFloat(popularity.trim()));
            mainlayout.setVisibility(View.VISIBLE);
            
           
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
   
   void customactionbar(String txttitle)
   {
 	  
 	  
 	  ActionBar mActionBar = getActionBar();
 		mActionBar.setDisplayShowHomeEnabled(false);
 		mActionBar.setDisplayShowTitleEnabled(false);
 		mActionBar.setDisplayShowCustomEnabled(true);
 		LayoutInflater mInflater = LayoutInflater.from(this);

 		View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
 		TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
 		mTitleTextView.setText(txttitle);
  
 		ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                 ActionBar.LayoutParams.MATCH_PARENT,
                 ActionBar.LayoutParams.MATCH_PARENT,
                 Gravity.CENTER);
 		ImageView imageButton = (ImageView) mCustomView
 				.findViewById(R.id.imginfo);
 		imageButton.setVisibility(View.INVISIBLE);

 		mActionBar.setCustomView(mCustomView,params);
 	mActionBar.setDisplayShowCustomEnabled(true);
 	}
   
   
    
}