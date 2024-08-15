package edu.tacoma.uw.momentum;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ShareFragment extends Fragment {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private Button buttonShare;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        setHasOptionsMenu(true);

        editTextTitle = view.findViewById(R.id.edit_text_title);
        editTextDescription = view.findViewById(R.id.edit_text_description);
        buttonShare = view.findViewById(R.id.button_share);

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareTaskDetailsViaEmail();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_task, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            shareTaskDetailsViaEmail();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareTaskDetailsViaEmail() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(getContext(), "Please enter both title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        String subject = "Task Details: " + title;
        String body = "Here are the details of the task:\n\n" +
                "Title: " + title + "\n" +
                "Description: " + description + "\n\n" +
                "Regards,\nYour App";

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);

        if (emailIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(emailIntent);
        } else {
            Toast.makeText(getContext(), "No email client found", Toast.LENGTH_SHORT).show();
        }
    }
}
