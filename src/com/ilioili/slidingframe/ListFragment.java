package com.ilioili.slidingframe;

import android.os.Bundle;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

public class ListFragment extends Fragment {
	ListView lv;
	
	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
//		lv = new ListView(getActivity());
//		lv.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
//		lv.setBackgroundColor(Color.WHITE);
//		lv.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				Toast.makeText(getActivity(), "你点击了第"+position+"条", Toast.LENGTH_SHORT).show();
//				view.setSelected(true);
//			}
//		});
//		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
//
//			@Override
//			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//				Toast.makeText(getActivity(), "你长按了第"+position+"条", Toast.LENGTH_SHORT).show();
//				view.setSelected(true);
//				return true;
//			}
//		});
//		lv.setAdapter(new BaseAdapter() {
//			@Override
//			public View getView(int position, View item, ViewGroup parent) {
//				if(null==item){
//					item = inflater.inflate(R.layout.list_item_1, parent, false);
//				}
//				return item;
//			}
//			
//			@Override
//			public long getItemId(int position) {
//				// TODO Auto-generated method stub
//				return position;
//			}
//			
//			@Override
//			public Object getItem(int position) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//			@Override
//			public int getCount() {
//				// TODO Auto-generated method stub
//				return 100;
//			}
//		});
		return inflater.inflate(R.layout.list_item, null);
	}
}
