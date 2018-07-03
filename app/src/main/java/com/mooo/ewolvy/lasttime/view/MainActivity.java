package com.mooo.ewolvy.lasttime.view;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mooo.ewolvy.lasttime.R;
import com.mooo.ewolvy.lasttime.data.TaskItem;
import com.mooo.ewolvy.lasttime.viewmodel.TaskListViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private static final String LIST_FRAG = "LIST_FRAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        ListFragment fragment = (ListFragment) manager.findFragmentByTag(LIST_FRAG);

        if (fragment == null) {
            fragment = ListFragment.newInstance();
        }

        addFragmentToActivity(manager,
                fragment,
                R.id.frameLayout,
                LIST_FRAG
        );

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Call detail fragment or activity to create a new item
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
