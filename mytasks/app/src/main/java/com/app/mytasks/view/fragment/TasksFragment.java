package com.app.mytasks.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mytasks.MyTasksAdapter;
import com.app.mytasks.R;
import com.app.mytasks.database.MyDataBase;
import com.app.mytasks.database.TaskModel;

import java.util.ArrayList;

public class TasksFragment extends Fragment {

    private MyDataBase myDataBase;
    private RecyclerView recyclerView;
    private TextView noData;

    private MyTasksAdapter myTasksAdapter;
    private ArrayList<TaskModel> mTasks = new ArrayList<>();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (myDataBase == null)
            myDataBase = MyDataBase.getInstance(getContext());


        mTasks = myDataBase.getAllTasks();
        if(mTasks.size()==0){
            noData.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else{
            noData.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        myTasksAdapter.updateAdapter(mTasks);

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerview_tasks);
        noData = view.findViewById(R.id.no_data);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        myTasksAdapter = new MyTasksAdapter(TasksFragment.this, new MyTasksAdapter.OnTaskDelete() {
            @Override
            public void onDeleted(int position, int id) {
                showConfirmDialog(position, id);
            }
        });

        recyclerView.setAdapter(myTasksAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void showConfirmDialog(final int position, final int taskId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.deleteTask)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mTasks.remove(position);
                        myDataBase.deleteTask(taskId);
                        myTasksAdapter.updateAdapter(mTasks);
                        myTasksAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.dismiss();
                    }
                });

        AlertDialog d = builder.create();
        d.setTitle("Are you sure");
        d.show();
    }
}
