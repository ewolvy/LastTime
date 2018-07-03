package com.mooo.ewolvy.lasttime.viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.mooo.ewolvy.lasttime.data.TaskItem;
import com.mooo.ewolvy.lasttime.data.TaskRepository;

import java.util.Date;
import java.util.List;

public class TaskListViewModel extends ViewModel {

    private TaskRepository repository;

    public TaskListViewModel(TaskRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<TaskItem>> getAllTasks(){
        return repository.getAllTasks();
    }

    public LiveData<List<TaskItem>> getRemindedTasks() {
        return repository.getRemindedTasks(new Date());
    }

    @SuppressLint("StaticFieldLeak")
    private class DeleteTask extends AsyncTask<TaskItem, Void, Void>{
        @Override
        protected Void doInBackground(TaskItem... taskItems) {
            repository.deleteTask(taskItems);
            return null;
        }
    }

    public void deleteTask (TaskItem... items){
        DeleteTask task = new DeleteTask();
        task.execute(items);
    }

/*    public TaskListViewModel(Application application){
        super (application);

        mTaskDao = TasksDatabase.getInstance(application).taskDao();
        mTasks = mTaskDao.getAllTasks();
    }
*/


/*    public List<TaskItem> getRemindedTasks(){
        List<TaskItem> remindedTasks = new ArrayList<>();
        if (mTasks.getValue() != null) {
            for (int i = 0; i < mTasks.getValue().size(); i++) {
                if (mTasks.getValue().get(i).getRemindOn().before(Calendar.getInstance().getTime())){
                    remindedTasks.add(mTasks.getValue().get(i));
                }
            }
        }

        return remindedTasks;
    }
*/

    public void addDummyTask(TaskItem task){
        repository.addTask(task);
    }
}
