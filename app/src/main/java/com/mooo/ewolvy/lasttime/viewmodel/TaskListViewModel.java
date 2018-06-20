package com.mooo.ewolvy.lasttime.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.mooo.ewolvy.lasttime.data.TaskItem;

import java.util.List;

public class TaskListViewModel extends ViewModel {
    private MutableLiveData<List<TaskItem>> tasksList;


}
