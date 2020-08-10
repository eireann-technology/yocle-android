package com.yocle.app;

import java.util.ArrayList;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ReportsAdapter extends ArrayAdapter<Report> {
	 private final Activity context;
     private final ArrayList<Report> reports;

		// the serverListSize is the total number of items on the server side,
		// which should be returned from the web request results
		protected int serverListSize = -1;
		
		// Two view types which will be used to determine whether a row should be displaying 
		// data or a Progressbar
		public static final int VIEW_TYPE_LOADING = 0;
		public static final int VIEW_TYPE_ACTIVITY = 1;
	 
	 
	 
	 
	 public ReportsAdapter(Activity context, ArrayList<Report> reports) {		 
		  super(context, R.layout.listviewrow, reports);
		  this.context = context;
		  this.reports = reports;
	}
	 
	 public void setServerListSize(int serverListSize){
			this.serverListSize = serverListSize;
		}
		
	 @Override
		public boolean isEnabled(int position) {
			 
			return getItemViewType(position) == VIEW_TYPE_ACTIVITY;
		}
	 
		/**
		 * One type is normal data row, the other type is Progressbar
		 */
		@Override
		public int getViewTypeCount() {
			return 2;
		}
		
		
		/**
		 * the size of the List plus one, the one is the last row, which displays a Progressbar
		 */
		@Override
		public int getCount() {
			return reports.size() + 1;
		}
	 
		/**
		 * return the type of the row, 
		 * the last row indicates the user that the ListView is loading more data
		 */
		@Override
		public int getItemViewType(int position) {
			// TODO Auto-generated method stub
			return (position >= reports.size()) ? VIEW_TYPE_LOADING
					: VIEW_TYPE_ACTIVITY;
		}
	 
		@Override
		public Report getItem(int position) {
			// TODO Auto-generated method stub
			return (getItemViewType(position) == VIEW_TYPE_ACTIVITY) ? reports
					.get(position) : null;
		}
	 
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return (getItemViewType(position) == VIEW_TYPE_ACTIVITY) ? position
					: -1;
		}
		
		
/*	 
	 public ArrayList<Report> getDataSource() {
		 return reports;		 
	 }
	 
	 public void appendDataSource(ArrayList<Report> r) {		 
		 reports.addAll(r);
	 }

	 public void clearDataSource() {
		 reports.clear();		 
	 }
*/	 
	 
	 public View getView(int position,View view,ViewGroup parent) {
		 if (getItemViewType(position) == VIEW_TYPE_LOADING) {
				// display the last row
				return getFooterView(position, view, parent);
			}
		 

		 LayoutInflater inflater=context.getLayoutInflater();
		 View rowView=inflater.inflate(R.layout.listviewrow, null,true);
		 
		 Report r = getItem(position);
		 TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
		 ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		 
		 txtTitle.setText(r.desciption);
		 imageView.setImageResource(R.drawable.pic1);
		
		 return rowView; 
	 }
	 
	 
	 public View getFooterView(int position, View convertView,
				ViewGroup parent) {
			if (position >= serverListSize && serverListSize > 0) {
				// the ListView has reached the last row
				TextView tvLastRow = new TextView(context);
				tvLastRow.setHint("Reached the last row.");
				tvLastRow.setGravity(Gravity.CENTER);
				return tvLastRow;
			}
			
			View row = convertView;
			if (row == null) {
				row = context.getLayoutInflater().inflate(
						R.layout.loadingmore, parent, false);
			}
	 
			return row;
		}
	 
	 
	}