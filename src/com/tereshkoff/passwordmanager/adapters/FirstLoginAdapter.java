package com.tereshkoff.passwordmanager.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.tereshkoff.passwordmanager.activities.PageFragment;

public class FirstLoginAdapter extends FragmentPagerAdapter {

    public FirstLoginAdapter(FragmentManager mgr) {
        super(mgr);
    }
    @Override
    public int getCount() {
        return(5);
    }
    @Override
    public Fragment getItem(int position) {
        return(PageFragment.newInstance(position));
    }
}