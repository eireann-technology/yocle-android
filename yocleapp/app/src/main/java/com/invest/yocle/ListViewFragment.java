package com.invest.yocle;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class ListViewFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    
    ListView list = null;
    View view1 = null;
    ReportsAdapter adapter = null;
    TextView tv = null;
    ArrayList<Report> reports = new ArrayList<Report>();    
    private int mPage;
    
    private FetchEvents fetchEvents;
    
    private void fetchEvents(int page) {
        fetchEvents = new FetchEvents();
        fetchEvents.execute(page);
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (fetchEvents != null && fetchEvents.getStatus() != AsyncTask.Status.FINISHED) {
            fetchEvents.cancel(true);
        }
    }
    
    
    public class FetchEvents extends AsyncTask<Integer, Void, ArrayList<Report>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            list.addFooterView(LayoutInflater.from(getActivity()).inflate(R.layout.loadingmore, null));            
        }

        @Override
        protected ArrayList<Report> doInBackground(Integer... params) {
        	int page = params[0];
        	ArrayList<Report> r = getDataFromWeb(page, 0);	        	
//         	LayoutInflater.from(getActivity()).inflate(R.layout.loadingmore, null).setVisibility(View.GONE);
        	return r;
        }

        @Override
        protected void onPostExecute(ArrayList<Report> r) {
        	if(r.size()>0) reports.addAll(r);
        	Log.i("ADIAPONG", "onPostExecute reports.size="+reports.size());
 //       	final View v = LayoutInflater.from(getActivity()).inflate(R.layout.loadingmore, null); 
//        	v.setVisibility(View.GONE);
//        	adapter.clear();
//        	adapter.addAll(r);
           	adapter.notifyDataSetChanged();
//            list.setAdapter(adapter);
//            list.removeFooterView(LayoutInflater.from(getActivity()).inflate(R.layout.loadingmore, null));            
//            list.setAdapter(adapter);
        }  
    }
    

    public static ListViewFragment create(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        ListViewFragment fragment = new ListViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    private ArrayList<Report> getDataFromWeb(int page, int totalItemsCount) {
    	
    	int max = 40;
    	int items_each_time = 10;
    	
    	try {
    		Thread.sleep(800);
    	}
    	catch(Exception e) {}
    	
    	ArrayList<Report> r = new ArrayList<Report>();
    	for(int i=(page-1)*items_each_time; i<page*items_each_time; i++) {
    		if(i>= max) {
    			break;
    		}
    		Report report = new Report("Report #"+String.valueOf(i+1), "image", "content");
    		r.add(report);
    	}
    	        	
    	return r;
    }

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.listviewfragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
                
        list = (ListView)getView().findViewById(R.id.list);
//        tv = (TextView)getView().findViewById(R.id.tv);
//        tv.setVisibility(View.GONE);
//        list.addFooterView(LayoutInflater.from(getActivity()).inflate(R.layout.loadingmore, null));
//     	LayoutInflater.from(getActivity()).inflate(R.layout.loadingmore, null).setVisibility(View.GONE);

        reports.clear();
        ArrayList<Report> r = getDataFromWeb(1, 0);      
        if(r.size()>0) reports.addAll(r);
        adapter = new ReportsAdapter(getActivity(), reports);
        adapter.setServerListSize(40);
        list.setAdapter(adapter);      
 //   	list.removeFooterView(LayoutInflater.from(getActivity()).inflate(R.layout.loadingmore, null));
        
        list.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

            	
 //           	list.addFooterView(LayoutInflater.from(getActivity()).inflate(R.layout.loadingmore, null));
            	
//            	View v = LayoutInflater.from(getActivity()).inflate(R.layout.loadingmore, null);
//            	Log.i("ADIAPONG", "onLoadMore to set visible page="+page+" totalcount="+totalItemsCount);
//             	v.setVisibility(View.VISIBLE);
                fetchEvents(page);
            }
            });
        
        
        
        list.setOnItemClickListener(new OnItemClickListener() {
                	
        @Override
        public void onItemClick(AdapterView<?> parent, View view,
        int position, long id) {
        // TODO Auto-generated method stub
        String Slecteditem= adapter.getItem(position).desciption;
        Toast.makeText(getActivity(), Slecteditem, Toast.LENGTH_SHORT).show();
        
        }
        });
        

        
        

    }    
    
    
    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listviewfragment, container, false);
        
        view1 = inflater.inflate(R.layout.loadingmore, container, false);
        
        		        		
        list = (ListView)(view.findViewById(R.id.list));
        tv = (TextView)view1.findViewById(R.id.tv);
        
//        CustomListAdapter adapter=new CustomListAdapter(getActivity(), itemname, imgid);
//        CustomListAdapter adapter=new CustomListAdapter(getActivity());
        
        ArrayList<Report> reports = getDataFromWeb(1, 0);
        adapter = new ReportsAdapter(getActivity(), reports);
        
        
        list.addFooterView(view1);
        list.setAdapter(adapter);
   //     list.removeFooterView(view1);        
        
     
        
        list.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
   //         	list.addFooterView(view1);
            	ArrayList<Report> reports = getDataFromWeb(page, 0);            	
            	list.removeFooterView(view1);
            	adapter.addAll(reports);
            	adapter.notifyDataSetChanged();
   //         	list.addFooterView(view1);
   //         	list.setAdapter(adapter);
            	
           // 	Toast.makeText(getActivity(), "Loading more", Toast.LENGTH_SHORT).show();
            }
            });
        
        
        
        list.setOnItemClickListener(new OnItemClickListener() {
                	
        @Override
        public void onItemClick(AdapterView<?> parent, View view,
        int position, long id) {
        // TODO Auto-generated method stub
        String Slecteditem= adapter.getItem(position).desciption;
        Toast.makeText(getActivity(), Slecteditem, Toast.LENGTH_SHORT).show();
        
        }
        });
        
                   
        return view;
    }
*/    
}
