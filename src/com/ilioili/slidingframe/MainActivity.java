package com.ilioili.slidingframe;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	View left, right;
	FrameLayout centre;
	SlidingFrame sv;
	FragmentManager manager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		manager = getSupportFragmentManager();
		sv = (SlidingFrame) findViewById(R.id.slidingView1);
		left = getLayoutInflater().inflate(R.layout.left, sv, false);
		right = getLayoutInflater().inflate(R.layout.right, sv, false);
		centre = (FrameLayout) getLayoutInflater().inflate(R.layout.centre, sv, false);
		centre.setId(1);
//		centre.addView(getLayoutInflater().inflate(R.layout.list_item, centre, false));
		sv.setLeftView(left);
		sv.setRightView(right);
		sv.setCentreView(centre);
		setFragment();
		sv.setLeftShadow(R.drawable.left_shadow, 10);
		sv.setRightShadow(R.drawable.right_shadow, 10);
		sv.setAnimationDuration(500);
		sv.setSlidingStateListener(new SlidingFrame.SlidingStateListener() {
			public void toTheRight() {
				Toast.makeText(MainActivity.this, "停在右侧", Toast.LENGTH_SHORT).show();
			}
			public void toTheLeft() {
				Toast.makeText(MainActivity.this, "停在左侧", Toast.LENGTH_SHORT).show();
			}
			public void toTheCentre() {
				Toast.makeText(MainActivity.this, "停在中间", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public void setFragment(){
		ListFragment fragment = new ListFragment();
		manager.beginTransaction().add(centre.getId(), fragment).commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.action_left:
			sv.slideToLeft();
			break;
		case R.id.action_right:
			sv.slideToRight();
			break;
		case R.id.action_centre:
			sv.slideToCentre();
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
}
