package edu.tacoma.uw.momentum;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayoutStates;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import edu.tacoma.uw.momentum.databinding.FragmentRegisterBinding;

/**
 * A simple {@link Fragment} subclass.

 */
public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding mBinding;
    private UserViewModel mUserViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mUserViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        mBinding = FragmentRegisterBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }
    @Override
    public void onDestroyView() {

        super.onDestroyView();
        mBinding = null;
    }
    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUserViewModel.addResponseObserver(getViewLifecycleOwner(), response -> {
            observeResponse(response);

        });
        //Use a Lamda expression to add the OnClickListener
        mBinding.registerButton.setOnClickListener(button -> register());
        mBinding.loginTextview.setOnClickListener(button -> navigateToRegister());

    }
    public void navigateToRegister() {
        Navigation.findNavController(getView())
                .navigate(R.id.action_registerFragment_to_loginFragment);
    }
    public void register() {
        String email = String.valueOf(mBinding.emailEdit.getText());
        String pwd = String.valueOf(mBinding.pwdEdit.getText());
        Account account;
        try {
            account = new Account (email, pwd);
        } catch(IllegalArgumentException ie) {
            Log.e(ConstraintLayoutStates.TAG, ie.getMessage());
            Toast.makeText(getContext(), ie.getMessage(), Toast.LENGTH_LONG).show();
            mBinding.textError.setText(ie.getMessage());
            return;
        }
        Log.i(ConstraintLayoutStates.TAG, email);
        mUserViewModel.addUser(account);
    }

    private void observeResponse(final JSONObject response) {
        if (response.length() > 0) {
            Log.i(TAG, response.toString());
            if (response.has("error")) {
                try {
                    String errorMessage = response.getString("error");
                    // Display the error message in a longer-lasting Toast
                    Toast.makeText(getContext(), "Error Adding User: " + errorMessage, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                    // Log the error message to Logcat
                    Log.e("JSON Parse Error", "Error parsing JSON response: " + response.toString());
                }
            } else {
                Toast.makeText(getContext(), "User added", Toast.LENGTH_LONG).show();
                Navigation.findNavController(getView()).popBackStack();
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }


}