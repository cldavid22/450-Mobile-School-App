package edu.tacoma.uw.momentum;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.tacoma.uw.momentum.databinding.FragmentTaskListBinding;

/**
 * A simple {@link Fragment} subclass.

 */
public class TaskListFragment extends Fragment {


    private TaskViewModel mModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(TaskViewModel.class);
        mModel.getTasks();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        @NonNull FragmentTaskListBinding binding = FragmentTaskListBinding.bind(view);

        mModel.addTaskListObserver(getViewLifecycleOwner(), taskList -> {
            if (!taskList.isEmpty()) {
                binding.layoutRoot.setAdapter(
                        new TaskRecyclerViewAdapter(taskList, mModel) // Pass the TaskViewModel instance
                );
            }
        });
    }
}