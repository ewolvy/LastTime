package com.mooo.ewolvy.lasttime.view;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.mooo.ewolvy.lasttime.LastTimeApplication;
import com.mooo.ewolvy.lasttime.R;
import com.mooo.ewolvy.lasttime.data.TaskItem;
import com.mooo.ewolvy.lasttime.viewmodel.TaskEditViewModel;
import com.mooo.ewolvy.lasttime.viewmodel.TaskListViewModel;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class TaskDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private TaskEditViewModel taskEditViewModel;
    private View rootView;
    private int id = -1;
    private TaskItem mItem;
    private boolean mIsNewItem = true;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TaskDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((LastTimeApplication) Objects.requireNonNull(getActivity()).getApplication())
                .getApplicationComponent()
                .inject(this);

        if (getArguments() != null) {
            if (getArguments().containsKey(ARG_ITEM_ID)) {
                // Load the content specified by the fragment
                // arguments.

                id = getArguments().getInt(ARG_ITEM_ID);
                mIsNewItem = false;
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (!mIsNewItem) {
            //Set up and subscribe (observe) to the ViewModel
            taskEditViewModel = ViewModelProviders.of(this, viewModelFactory)
                    .get(TaskEditViewModel.class);

            taskEditViewModel.getTaskById(id).observe(this, new Observer<TaskItem>() {
                @Override
                public void onChanged(@Nullable TaskItem item) {
                    if (mItem == null) {
                        mItem = item;
                    }
                    if (mItem != null) {
                        fillFields(mItem);
                        Activity activity = getActivity();
                        assert activity != null;
                        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
                        if (appBarLayout != null && mItem != null) {
                            appBarLayout.setTitle(mItem.getName());
                        }
                    }
                    prepareButton();
                }
            });
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.task_detail, container, false);

        return rootView;
    }

    private void fillFields (TaskItem item){
        EditText editText = rootView.findViewById(R.id.txtEditName);
        editText.setText(item.getName());
        editText = rootView.findViewById(R.id.txtEditLastDate);
        editText.setText(item.getLastTime().toString());
        editText = rootView.findViewById(R.id.txtEditReminder);
        editText.setText(item.getRemindOn().toString());

        editText = rootView.findViewById(R.id.txtEditHistoric);
        editText.setText(item.getDatesHistory());
    }

    private void prepareButton (){
        Button button = rootView.findViewById(R.id.btnEditColor);
        final int initialColor;

        if (!mIsNewItem) {
            button.setBackgroundColor(mItem.getColor());
            initialColor = mItem.getColor();
        } else {
            initialColor = Color.GRAY;
        }

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ColorPickerDialogBuilder
                        .with(Objects.requireNonNull(getContext()))
                        .setTitle("Choose color")
                        .initialColor(initialColor)
                        .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                        .density(4)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {
                                onColorSelectedCallback(selectedColor);
                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                // changeBackgroundColor(selectedColor);
                                Toast.makeText(getContext(), "onPositiveButton: 0x" + Integer.toHexString(selectedColor), Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .lightnessSliderOnly()
                        .build()
                        .show();
            }
        });
    }

    private void onColorSelectedCallback (int selectedColor){
        mItem.setColor(selectedColor);
        Button button = rootView.findViewById(R.id.btnEditColor);

        button.setBackgroundColor(mItem.getColor());
    }
}
