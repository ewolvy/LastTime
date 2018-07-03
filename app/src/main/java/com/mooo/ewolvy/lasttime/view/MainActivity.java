package com.mooo.ewolvy.lasttime.view;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mooo.ewolvy.lasttime.R;

public class MainActivity extends AppCompatActivity {

    private static final String LIST_FRAG = "LIST_FRAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        TaskListFragment fragment = (TaskListFragment) manager.findFragmentByTag(LIST_FRAG);

        if (fragment == null) {
            fragment = TaskListFragment.newInstance();
        }

        addFragmentToActivity(manager,
                fragment,
                R.id.frameLayout,
                LIST_FRAG
        );
    }

    public void addFragmentToActivity (FragmentManager fragmentManager,
                                       Fragment fragment,
                                       int frameId,
                                       String tag) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment, tag);
        transaction.commit();
    }
}
