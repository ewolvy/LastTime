package com.mooo.ewolvy.lasttime.view;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mooo.ewolvy.lasttime.R;
import com.mooo.ewolvy.lasttime.data.TaskItem;
import com.mooo.ewolvy.lasttime.viewmodel.TaskListViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private List<TaskItem> mValues;
    private TaskListViewModel taskListViewModel;
    SimpleItemRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());*/

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Call detail fragment or activity to create a new item
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        // The detail container view will be present only in the
        // large-screen layouts (res/values-w900dp).
        // If this view is present, then the
        // activity should be in two-pane mode.
        mTwoPane = findViewById(R.id.task_detail_container) != null;

        // Create a TaskListViewModel instance
        taskListViewModel = ViewModelProviders.of(this).get(TaskListViewModel.class);
        // Get the task list and set the task list for the adapter
        taskListViewModel.getAllTasks().observe(this, new Observer<List<TaskItem>>() {
            @Override
            public void onChanged(@Nullable List<TaskItem> tasks) {
                adapter.setTaskList(tasks);
            }
        });


        // POR SI SE QUIERE AÑADIR ALGÚN DATO A LA BASE DE DATOS DE FORMA DIRECTA
        // TAMBIÉN HAY QUE DESOMENTAR LA CLASE ASYNCDATABASEADDDUMMY Y CAMBIAR LOS DATOS A AÑADIR
        // new AsyncDatabaseAddDummy().execute();

        RecyclerView recyclerView = findViewById(R.id.task_list);
        assert recyclerView != null;
        setupRecyclerView(recyclerView, taskListViewModel);
    }



    // POR SI SE QUIERE AÑADIR ALGÚN DATO A LA BASE DE DATOS DE FORMA DIRECTA
    // TAMBIÉN HAY QUE DESCOMENTAR LA LINEA EN EL ONCREATE()
    /*private class AsyncDatabaseAddDummy extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            TaskItem prueba;
            Date date = Calendar.getInstance().getTime();
            prueba = new TaskItem(1, "Limpiar filtros lavavajillas", Color.CYAN, "", date, date, 1);
            taskListViewModel.addDummyTask(prueba);
            prueba = new TaskItem(2, "Comprobar presión ruedas", Color.YELLOW, "", date, date, 2);
            taskListViewModel.addDummyTask(prueba);
            prueba = new TaskItem(3, "Cambiar antimosquitos", Color.LTGRAY, "", date, date, 3);
            taskListViewModel.addDummyTask(prueba);

            return null;
        }
    }*/

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, TaskListViewModel vm) {
        adapter = new SimpleItemRecyclerViewAdapter(this, vm.getRemindedTasks(), mTwoPane);
        recyclerView.setAdapter(adapter);
        //ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        //itemTouchHelper.attachToRecyclerView(recyclerView);
        LinearLayoutManager linearVertical = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearVertical);
    }


    private class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final MainActivity mParentActivity;
        private final boolean mTwoPane;

        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskItem item = (TaskItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putInt(TaskDetailFragment.ARG_ITEM_ID, item.getId());
                    TaskDetailFragment fragment = new TaskDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.task_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, TaskDetailActivity.class);
                    intent.putExtra(TaskDetailFragment.ARG_ITEM_ID, item.getId());

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(MainActivity parent,
                                      List<TaskItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.task_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            @SuppressLint("SimpleDateFormat")
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            holder.mNameView.setText(mValues.get(position).getName());
            holder.mDateReminder.setText(df.format(mValues.get(position).getRemindOn()));
            holder.mLastTime.setText(df.format(mValues.get(position).getLastTime()));
            holder.mView.setBackgroundColor(mValues.get(position).getColor());

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public void setTaskList (List<TaskItem> newTaskList){
            if (newTaskList != null){
                mValues = newTaskList;
                notifyDataSetChanged();
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mNameView;
            final TextView mDateReminder;
            final TextView mLastTime;
            final View mView;

            ViewHolder(View view) {
                super(view);
                mNameView = view.findViewById(R.id.lbl_task_name);
                mDateReminder = view.findViewById(R.id.lbl_date_reminder);
                mLastTime = view.findViewById(R.id.lbl_last_date);
                mView = view.findViewById(R.id.item_root);
            }
        }
    }
}
