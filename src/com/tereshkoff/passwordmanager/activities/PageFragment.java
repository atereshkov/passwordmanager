package com.tereshkoff.passwordmanager.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.tereshkoff.passwordmanager.R;
import com.tereshkoff.passwordmanager.login.SignupActivity;

import java.util.ArrayList;
import java.util.List;

public class PageFragment extends Fragment {

    private int pageNumber;
    private ImageView firstLoginImageView;
    private Button skipButton;
    private ProgressBar progressBar;

    SharedPreferences preferences;
    String[] pagesText;
    List<Integer> pagesImg = new ArrayList<>();

    public static PageFragment newInstance(int page) {
        PageFragment fragment = new PageFragment();
        Bundle args=new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);

        return fragment;
    }

    public PageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result=inflater.inflate(R.layout.firstlogin_fragment_page, container, false);

        skipButton = (Button) result.findViewById(R.id.skipButton);
        firstLoginImageView = (ImageView) result.findViewById(R.id.firstLoginImageView);
        progressBar = (ProgressBar) result.findViewById(R.id.progressBar);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*SharedPreferences.Editor e = preferences.edit();
                e.putBoolean("hasVisited", true);
                e.commit();
                getActivity().onBackPressed();*/

                getActivity().finish();
                Intent intent = new Intent(getActivity(), SignupActivity.class);
                startActivity(intent);

            }
        });

        TextView pageHeader = (TextView)result.findViewById(R.id.displayText);

        pagesText = getResources().getStringArray(R.array.firstLogin);
        TypedArray ar = result.getResources().obtainTypedArray(R.array.firstLoginImages);
        for (int i = 0; i < ar.length(); i++)
            pagesImg.add(ar.getResourceId(i, 0));

        firstLoginImageView.setImageResource(pagesImg.get(pageNumber));
        pageHeader.setText(pagesText[pageNumber]); // set text from strings massive

        progressBar.setMax(5);
        progressBar.setProgress(pageNumber+1);

        if (pageNumber+1 == 5)
        {
            skipButton.setText("Начать работу..");
        }

        setVisited();  // if fragment the last one

        return result;
    }

    private void setVisited()
    {
        if (pageNumber+1 == 5) // if the last fragment
        {
            SharedPreferences.Editor e = preferences.edit();
            e.putBoolean("hasVisited", true);
            e.commit();
        }
    }

    // TODO: BACKPRESSED DENY!

}