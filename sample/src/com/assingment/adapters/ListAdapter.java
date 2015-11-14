package com.assingment.adapters;

import java.util.ArrayList;

import com.assignment.model.MovieBean;
import com.assignment.model.MovieBeanList;
import com.assignmentmain.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;





public class ListAdapter extends BaseAdapter  {


    private Context mContext;
    ArrayList<MovieBeanList> movie_data=new ArrayList<MovieBeanList>();
   
    Typeface typeface ;

    //constructor
    public ListAdapter (Context c,ArrayList<MovieBeanList> bean){
        mContext = c;
        movie_data=bean;
    }

    @Override
    public int getCount() {
        return movie_data.size();
    }
    
    
    
    private class ViewHolder {
        ImageView imageView;
        TextView txtdate;
        TextView txtDesc;
        TextView txtadult;
    }
 

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
         
        LayoutInflater mInflater = (LayoutInflater) 
            mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.movie_row, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.txt_movie_desc);
            holder.txtdate = (TextView) convertView.findViewById(R.id.txtreldate);
            holder.txtadult = (TextView) convertView.findViewById(R.id.txtadult);
            holder.imageView=(ImageView) convertView.findViewById(R.id.imgmovie);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
         
       MovieBeanList rowItem = movie_data.get(position);
       typeface = Typeface.createFromAsset(mContext.getAssets(), "comic-sans-ms.ttf");
        holder.txtDesc.setText(rowItem.getDesc());
        holder.txtadult.setText(rowItem.getAdult());
        
        holder.txtdate.setText(rowItem.getRelease_date());
        holder.txtDesc.setTypeface(typeface );
        holder.txtadult.setTypeface(typeface );
        holder.txtdate.setTypeface(typeface );
      
      //your image url
        //String url = "http://image.tmdb.org/t/p/w300/"+rowItem.getImage();
        String url ="http://image.tmdb.org/t/p/w300/"+rowItem.getImage();
        ImageLoader imageLoader = ImageLoader.getInstance();
        @SuppressWarnings("deprecation")
		DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisc(true).resetViewBeforeLoading(true)
				.showImageForEmptyUri(R.drawable.error)
				.showImageOnFail(R.drawable.error)
				.showImageOnLoading(R.drawable.loading).build();
        				
        		
        //initialize image view
      	

        //download and display image from url
        imageLoader.displayImage(url, holder.imageView, options);
        
        
        
         
        return convertView;
    }
}