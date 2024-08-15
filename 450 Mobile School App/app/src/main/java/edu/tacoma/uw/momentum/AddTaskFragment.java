package edu.tacoma.uw.momentum;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONException;
import org.json.JSONObject;

import edu.tacoma.uw.momentum.databinding.FragmentAddTaskBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTaskFragment extends Fragment {
    private FragmentAddTaskBinding mBinding;

    private TaskViewModel taskViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        taskViewModel = new ViewModelProvider(getActivity()).get(TaskViewModel.class);

        mBinding = FragmentAddTaskBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        taskViewModel.addResponseObserver(getViewLifecycleOwner(), response -> {

            observeResponse(response);

        });


        mBinding.buttonAddTask.setOnClickListener(button -> processAddTask());


    }


    private void processAddTask() {
        final String dueDate = mBinding.editTextTaskDue.getText().toString();
        final String category = mBinding.editTextTaskCategory.getText().toString();
        final String title = mBinding.editTextTaskTitle.getText().toString();
        final String description = mBinding.editTextTaskDescription.getText().toString();

        Log.i(TAG, "Data is " + dueDate + ", " + category + ", " + title + ", " + description);

        taskViewModel.addTask(dueDate, category, title, description);
    }


    private void observeResponse(final JSONObject response) {

        if (response.length() > 0) {

            if (response.has("error")) {

                try {

                    Toast.makeText(this.getContext(),

                            "Error Adding Tasks: " +

                                    response.get("error"), Toast.LENGTH_LONG).show();

                } catch (JSONException e) {

                    Log.e("JSON Parse Error", e.getMessage());

                }

            } else {

               // Toast.makeText(this.getContext(), "Task added", Toast.LENGTH_LONG).show();

            }

        } else {

            Log.d("JSON Response", "No Response");

        }

    }
}