package edu.tacoma.uw.momentum;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class TasksFragment extends Fragment {

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

        taskViewModel.addResponseObserver(getViewLifecycleOwner(), this::observeResponse);

        mBinding.buttonAddTask.setOnClickListener(button -> processAddTask());
        mBinding.buttonAddTask.setEnabled(false); // Initially disable the button

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkFieldsForEmptyValues();
            }
        };

        // Add text watcher to each text field
        mBinding.editTextTaskDue.addTextChangedListener(textWatcher);
        mBinding.editTextTaskCategory.addTextChangedListener(textWatcher);
        mBinding.editTextTaskTitle.addTextChangedListener(textWatcher);
        mBinding.editTextTaskDescription.addTextChangedListener(textWatcher);
    }

    private void checkFieldsForEmptyValues() {
        String due = mBinding.editTextTaskDue.getText().toString();
        String category = mBinding.editTextTaskCategory.getText().toString();
        String title = mBinding.editTextTaskTitle.getText().toString();
        String description = mBinding.editTextTaskDescription.getText().toString();

        mBinding.buttonAddTask.setEnabled(!due.isEmpty() && !category.isEmpty() && !title.isEmpty() && !description.isEmpty());
    }

    private void processAddTask() {
        String due = mBinding.editTextTaskDue.getText().toString();
        String category = mBinding.editTextTaskCategory.getText().toString();
        String title = mBinding.editTextTaskTitle.getText().toString();
        String description = mBinding.editTextTaskDescription.getText().toString();

        Log.i(TAG, "Data is " + due + ", " + category + ", " + title + ", " + description);
        taskViewModel.addTask(due, category, title, description);
    }

    private void observeResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("error")) {
                try {
                    Toast.makeText(this.getContext(), "Error Adding Task: " + response.get("error"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            } else {
                Toast.makeText(this.getContext(), "Task added", Toast.LENGTH_LONG).show();
                clearTextFields();
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }

    private void clearTextFields() {
        mBinding.editTextTaskDue.setText("");
        mBinding.editTextTaskCategory.setText("");
        mBinding.editTextTaskTitle.setText("");
        mBinding.editTextTaskDescription.setText("");
    }

}
