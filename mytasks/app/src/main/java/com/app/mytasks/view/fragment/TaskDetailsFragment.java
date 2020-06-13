package com.app.mytasks.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.app.mytasks.R;

public class TaskDetailsFragment extends Fragment {


    private TextView tvTitle;
    private TextView tvDetails;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_details, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTitle = view.findViewById(R.id.tv_title);
        tvDetails = view.findViewById(R.id.tv_details);


        String title = getArguments().getString("title");
        String details = getArguments().getString("details");

        tvTitle.setText(title);
        tvDetails.setText(details);
    }
}
