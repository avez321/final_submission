package com.assignmentmain;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoActivity extends Activity {
	Typeface typeface ;
	TextView t1,txtname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		getActionBar().setTitle("                    INFORMATION");
		
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3F51BF")));
		 typeface = Typeface.createFromAsset(getAssets(), "comic-sans-ms.ttf");
		 
		 t1=(TextView)findViewById(R.id.txt1);
		 txtname=(TextView)findViewById(R.id.txtname);
		t1.setTypeface(typeface);
		txtname.setTypeface(typeface);
	}

	
		  

}
