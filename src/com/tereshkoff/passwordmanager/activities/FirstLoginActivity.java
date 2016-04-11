package com.tereshkoff.passwordmanager.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.tereshkoff.passwordmanager.R;
import com.tereshkoff.passwordmanager.adapters.FirstLoginAdapter;

public class FirstLoginActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstlogin_activity);

        ViewPager pager=(ViewPager)findViewById(R.id.pager);
        pager.setAdapter(new FirstLoginAdapter(getSupportFragmentManager()));

    }
}
