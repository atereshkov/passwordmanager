package com.tereshkoff.passwordmanager;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.tereshkoff.passwordmanager.AES.AES;
import com.tereshkoff.passwordmanager.AES.AESEncrypter;
import com.tereshkoff.passwordmanager.AES.StaticAES;
import com.tereshkoff.passwordmanager.activities.*;
import com.tereshkoff.passwordmanager.adapters.MyPagerAdapter;
import com.tereshkoff.passwordmanager.json.JsonFilesWorker;
import com.tereshkoff.passwordmanager.login.LoginActivity;
import com.tereshkoff.passwordmanager.utils.Constants;
import com.tereshkoff.passwordmanager.utils.Dialogs;

import android.support.v4.widget.DrawerLayout;

import java.util.List;
import java.util.Vector;

public class MyActivity extends FragmentActivity {

    private FragmentTabHost mTabHost;
    private SharedPreferences sp;

    private ViewPager pager;
    private MyPagerAdapter adapter;

    DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    String mTitle = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        sp = PreferenceManager.getDefaultSharedPreferences(this);

        showLoginActivity();

        pager = (ViewPager) findViewById(R.id.viewpager);
        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, OneTabActivity.class.getName()));
        fragments.add(Fragment.instantiate(this, TwoTabActivity.class.getName()));
        adapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);

        pager.setAdapter(adapter);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost); // https://code.google.com/p/android/issues/detail?id=78772
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Все пароли"),
                OneTabActivity.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("По группам"),
                TwoTabActivity.class, null);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                mTabHost.setCurrentTab(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });

        mTabHost.getTabWidget().getChildAt(0).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                mTabHost.setCurrentTab(0);
                pager.setCurrentItem(0);
            }
        });

        mTabHost.getTabWidget().getChildAt(1).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                mTabHost.setCurrentTab(1);
                pager.setCurrentItem(1);
            }
        });

        mTitle = (String) getTitle();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerList = (ListView) findViewById(R.id.drawer_list);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer,
                R.string.drawer_open, R.string.drawer_close){

            @Override
            public void onDrawerClosed(View drawerView) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle("Select");
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getBaseContext(), R.layout.drawer_list_item, getResources()
                .getStringArray(R.array.navdraw));
        mDrawerList.setAdapter(adapter);

        getActionBar().setHomeButtonEnabled(true);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String[] rivers = getResources().getStringArray(R.array.navdraw);

                mTitle = rivers[position];

                RiverFragment rFragment = new RiverFragment();

                Bundle data = new Bundle();

                data.putInt("position", position);

/*                rFragment.setArguments(data);

                android.app.FragmentManager fragmentManager = getFragmentManager();

                FragmentTransaction ft = fragmentManager.beginTransaction();

                ft.replace(R.id.content_frame, rFragment);

                ft.commit();*/

                mDrawerLayout.closeDrawer(mDrawerList);
            }

        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.add).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    // TODO: MODIFY DATE OF PASSWORD

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    public void openSettingsActivity(MenuItem item){
        Intent intent = new Intent(this, PrefActivity.class);
        startActivity(intent);
    }

    public void onExitClick(MenuItem item) {
        finish();
    }

    public void onAboutClick(MenuItem item) throws PackageManager.NameNotFoundException {
        String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;

        Dialogs.makeAlertDialog(this, "О приложении",
                "Менеджер паролей HPassword\n" +
                "Автор: Александр Терешков\n" +
                        "Версия: " +versionName+"",
                "Закрыть");
    }

    public void openSyncSettingsActivity(MenuItem item) {

        Intent intent = new Intent(this, SyncSettings.class);
        startActivity(intent);

    }

    public void showLoginActivity()
    {
        // SHOW FIRSTLOGIN ACTIVITY IF NOT VISITED BEFORE
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean hasVisited = preferences.getBoolean("hasVisited", false);

        if (!hasVisited) // if first one
        {
            Intent intent = new Intent(this, FirstLoginActivity.class);
            startActivity(intent);
            JsonFilesWorker.createDefaultBase(Constants.DAFAULT_DBFILE_NAME);
        }
        else // if NOT first one visited
        {
            Boolean masterpw = false;
            if (sp != null)
            {
                masterpw = sp.getBoolean("rememberMasterPassword", false);
            }
            else
            {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }

            if (!masterpw)
            {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        }

        //SharedPreferences.Editor e = preferences.edit(); // for test
        //e.putBoolean("hasVisited", false);
        //e.commit();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        /*if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }*/
    }
}
