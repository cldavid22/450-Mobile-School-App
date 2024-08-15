package edu.tacoma.uw.momentum;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.Nullable;

import edu.tacoma.uw.momentum.databinding.FragmentTaskDetailBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends Fragment {

    private FragmentTaskDetailBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentTaskDetailBinding.inflate(inflater, container, false);
        return mBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Get a reference to the SafeArgs object
        TaskDetailFragmentArgs args = TaskDetailFragmentArgs.fromBundle(getArguments());
        Task task = (Task) args.getTask();
        mBinding.categoryTextView.setText(task.getCategory());
        mBinding.titleTextView.setText(task.getTitle());
        mBinding.descriptionTextView.setText(task.getDescription());
    }
}