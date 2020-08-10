/*

package com.invest.yocle;

import java.util.HashMap;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.text.InputType;

public class SearchHKEXActivity extends AppCompatActivity {


    String winTitle;
    static String titles[] = {"披露權益通知","大股東名單"};
    static String urls[] = {"hkex1.html", "hkex2.html"};

    SlidingTabLayout1 mSlidingTabLayout;
    HashMap<String, WVFragment> fragments = new HashMap<String, WVFragment>();

    SampleFragmentPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    String stocknum = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchhkex);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            urls[0] = extras.getString("uri1");
            urls[1] = extras.getString("uri2");
            winTitle = extras.getString("title");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        //       actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        //           getSupportActionBar().setIcon(R.drawable.ic_launcher);
        actionBar.setDisplayHomeAsUpEnabled(true);
    //    actionBar.setTitle(getResources().getString(R.string.app_name)+" - "+winTitle);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
//        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mSectionsPagerAdapter = new SampleFragmentPagerAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(1);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.

        mSlidingTabLayout = (SlidingTabLayout1) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);

        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout1.TabColorizer() {

            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.fff);    //define any color in xml resources and set it here, I have used white
            }

            @Override
            public int getDividerColor(int position) {
                return getResources().getColor(R.color.white);
            }
        });


        LinearLayout areawebview = (LinearLayout)findViewById(R.id.areawebview);
        LinearLayout areatabs = (LinearLayout)findViewById(R.id.areatabs);

        areawebview.setVisibility(View.VISIBLE);
        areatabs.setVisibility(View.GONE);

        Log.i("PONG", "oncreate end");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            //onBackPressed();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
//            Toast.makeText(this, query, Toast.LENGTH_LONG).show();

            if(fragments.size()==2) {
                fragments.get("0").searchhkex(query);
                fragments.get("1").searchhkex(query);
            }

            stocknum = query;

            LinearLayout areawebview = (LinearLayout)findViewById(R.id.areawebview);
            LinearLayout areatabs = (LinearLayout)findViewById(R.id.areatabs);

            areawebview.setVisibility(View.GONE);
            areatabs.setVisibility(View.VISIBLE);

            Log.i("PONG", "onhandleintent end");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.searchhkex_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setQueryHint(getResources().getString(R.string.enterstocknum));
        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
        return true;
    }

    public void setTitle(int position) {
        setTitle("顥森天下 - " + titles[position]);
    }

    public class SampleFragmentPagerAdapter extends FragmentStatePagerAdapter {
        final int PAGE_COUNT = titles.length;

        public SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            Log.i("PONG", "getitem position=" + position);

            WVFragment fragment = new WVFragment();
            fragments.put("" + position, fragment);
            Bundle b = new Bundle();
            b.putString("url", urls[position]+"?no="+stocknum);
            fragment.setArguments(b);
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch(keyCode)
            {
                case KeyEvent.KEYCODE_BACK:
                    finish();
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
*/