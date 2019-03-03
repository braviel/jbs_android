package com.example.jbs.fragment;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    public void useToolBar(Toolbar toolBar, boolean enableBack) {
        setHasOptionsMenu(true);
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.setSupportActionBar(toolBar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(enableBack);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
