package edu.tacoma.uw.momentum;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.tacoma.uw.momentum.databinding.FragmentTaskBinding;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {

    private final List<Task> mValues;
    private final TaskViewModel mViewModel;

    public TaskRecyclerViewAdapter(List<Task> taskList, TaskViewModel viewModel) {
        mValues = taskList;
        mViewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_task, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.setItem(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentTaskBinding binding;
        public Task mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentTaskBinding.bind(view);

            // Add a click listener to the delete button
            binding.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Task task = mValues.get(position);
                        // Call a method in TaskViewModel to delete the task
                        mViewModel.deleteTask(task);
                    }
                }
            });

        }

        public void setItem(final Task item) {
            mItem = item;

            binding.taskDate.setText(item.getDue());
            binding.taskCategory.setText(item.getCategory());
            binding.getRoot().setOnClickListener(view -> {
                TaskListFragmentDirections.ActionNavigationHomeToTaskDetailFragment directions =
                        TaskListFragmentDirections.actionNavigationHomeToTaskDetailFragment(item);

                Navigation.findNavController(mView).navigate(directions);
            });
        }
    }
}

