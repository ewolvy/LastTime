package com.mooo.ewolvy.lasttime.view;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mooo.ewolvy.lasttime.LastTimeApplication;
import com.mooo.ewolvy.lasttime.R;
import com.mooo.ewolvy.lasttime.data.TaskItem;
import com.mooo.ewolvy.lasttime.viewmodel.TaskListViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class ListFragment extends Fragment {

    private List<TaskItem> listOfData;
    private TaskListViewModel taskListViewModel;
    RecyclerView recyclerView;
    SimpleItemRecyclerViewAdapter adapter;

    @Inject
    ViewModelProvider.Factory viewModelFactory;


    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    public ListFragment(){}

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((LastTimeApplication) Objects.requireNonNull(getActivity()).getApplication())
                .getApplicationComponent()
                .inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Set up and subscribe (observe) to the ViewModel
        taskListViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(TaskListViewModel.class);

        taskListViewModel.getAllTasks().observe(this, new Observer<List<TaskItem>>() {
            @Override
            public void onChanged(@Nullable List<TaskItem> listItems) {
                if (listOfData == null) {
                    setListData(listItems);
                }
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_list, container, false);

        // The detail container view will be present only in the
        // large-screen layouts (res/values-w900dp).
        // If this view is present, then the
        // activity should be in two-pane mode.
        mTwoPane = view.findViewById(R.id.task_detail_container) != null;

        // POR SI SE QUIERE AÑADIR ALGÚN DATO A LA BASE DE DATOS DE FORMA DIRECTA
        // TAMBIÉN HAY QUE DESOMENTAR LA CLASE ASYNCDATABASEADDDUMMY Y CAMBIAR LOS DATOS A AÑADIR
        // new AsyncDatabaseAddDummy().execute();

        recyclerView = view.findViewById(R.id.task_list);

        return view;
    }

    public void setListData(List<TaskItem> listOfData) {
        this.listOfData = listOfData;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SimpleItemRecyclerViewAdapter(getActivity(), mTwoPane);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);
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


    private class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final FragmentActivity mParentActivity;
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

        SimpleItemRecyclerViewAdapter(FragmentActivity parent,
                                      boolean twoPane) {
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

            holder.mNameView.setText(listOfData.get(position).getName());
            holder.mDateReminder.setText(df.format(listOfData.get(position).getRemindOn()));
            holder.mLastTime.setText(df.format(listOfData.get(position).getLastTime()));
            holder.mView.setBackgroundColor(listOfData.get(position).getColor());

            holder.itemView.setTag(listOfData.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return listOfData.size();
        }

        public void setTaskList (List<TaskItem> newTaskList){
            if (newTaskList != null){
                listOfData = newTaskList;
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

    private ItemTouchHelper.Callback createHelperCallback() {
        return new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            //not used, as the first parameter above is 0
            @Override
            public boolean onMove(RecyclerView recyclerView1, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                taskListViewModel.deleteTask(
                        listOfData.get(position)
                );

                //ensure View is consistent with underlying data
                listOfData.remove(position);
                adapter.notifyItemRemoved(position);
            }
        };
    }
}