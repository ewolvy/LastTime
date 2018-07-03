package com.mooo.ewolvy.lasttime.view;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mooo.ewolvy.lasttime.R;
import com.mooo.ewolvy.lasttime.data.TaskItem;
import com.mooo.ewolvy.lasttime.viewmodel.TaskEditViewModel;

import java.util.Objects;

public class TaskDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    private LiveData<TaskItem> mItem;
    private boolean mIsNewItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TaskDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the content specified by the fragment
            // arguments.

            TaskEditViewModel teViewModel = ViewModelProviders.of(this).get(TaskEditViewModel.class);
            mItem = teViewModel.getTaskById(getArguments().getInt(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            assert activity != null;
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(Objects.requireNonNull(mItem.getValue()).getName());
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.task_detail, container, false);
        EditText editText = rootView.findViewById(R.id.txtEditName);
        editText.setText(Objects.requireNonNull(mItem.getValue()).getName());
        editText = rootView.findViewById(R.id.txtEditLastDate);
        editText.setText(mItem.getValue().getLastTime().toString());
        Button button = rootView.findViewById(R.id.btnEditColor);
        button.setBackgroundColor(mItem.getValue().getColor());
        editText = rootView.findViewById(R.id.txtEditReminder);
        editText.setText(mItem.getValue().getRemindOn().toString());

        editText = rootView.findViewById(R.id.txtEditHistoric);
        editText.setText(mItem.getValue().getDatesHistory());
        return rootView;
    }
}
