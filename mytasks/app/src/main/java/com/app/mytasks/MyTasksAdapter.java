package com.app.mytasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mytasks.database.TaskModel;
import com.app.mytasks.view.fragment.TasksFragment;

import java.util.ArrayList;

public class MyTasksAdapter extends RecyclerView.Adapter<MyTasksAdapter.MyTasksHolder> {

    private static TasksFragment mFragment;
    private OnTaskDelete onTaskDeleteListener;
    private static ArrayList<TaskModel> mList = new ArrayList<>();

    public MyTasksAdapter(TasksFragment fragment, OnTaskDelete listener) {
        this.onTaskDeleteListener = listener;
        this.mFragment = fragment;
    }

    @NonNull
    @Override
    public MyTasksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyTasksHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyTasksHolder holder, final int position) {

        holder.taskTitle.setText(mList.get(position).getTitle());
        holder.taskDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onTaskDeleteListener != null) {
                    onTaskDeleteListener.onDeleted(position, mList.get(position).getId());
                }
            }
        });
    }

    public void updateAdapter(ArrayList<TaskModel> list) {
        this.mList.clear();
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class MyTasksHolder extends RecyclerView.ViewHolder {

        TextView taskTitle;
        ImageView taskDelete;

        public MyTasksHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.tv_task_title);
            taskDelete = itemView.findViewById(R.id.img_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    bundle.putString("title", mList.get(getAdapterPosition()).getTitle());
                    bundle.putString("details", mList.get(getAdapterPosition()).getDetails());

                    NavHostFragment.findNavController(mFragment).navigate(R.id.action_TasksFragment_to_TaskDetailsFragment, bundle);
                }
            });
        }
    }

    public interface OnTaskDelete {
        void onDeleted(int position, int id);
    }
}
