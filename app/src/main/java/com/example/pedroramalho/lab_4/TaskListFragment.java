package com.example.pedroramalho.lab_4;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



public class TaskListFragment extends ListFragment {


    public TaskListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setListAdapter(new ArrayAdapter<Task>(getActivity(),
                android.R.layout.simple_list_item_1,android.R.id.text1, MainActivity.myTasks));

    }



}
