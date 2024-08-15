package edu.tacoma.uw.momentum;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TaskViewModel extends AndroidViewModel {
    private MutableLiveData<JSONObject> mResponse;

    private MutableLiveData<List<Task>> mTaskList;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
        mTaskList = new MutableLiveData<>();
        mTaskList.setValue(new ArrayList<>());

    }
    public void addResponseObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super JSONObject> observer) {
        mResponse.observe(owner, observer);
    }

    private void handleError(final VolleyError error) {
        if (Objects.isNull(error.networkResponse)) {
            try {
                mResponse.setValue(new JSONObject("{" +
                        "error:\"" + error.getMessage() +
                        "\"}"));
            } catch (JSONException e) {
                Log.e("JSON PARSE", "JSON Parse Error in handleError");
            }
        } else {
            String data = new String(error.networkResponse.data, Charset.defaultCharset())
                    .replace('\"', '\'');
            try {
                mResponse.setValue(new JSONObject("{" +
                        "code:" + error.networkResponse.statusCode +
                        ", data:\"" + data +
                        "\"}"));
            } catch (JSONException e) {
                Log.e("JSON PARSE", "JSON Parse Error in handleError");
            }
        }

    }
    public void addTask(String category, String title, String description, String due) {
        String url = "https://students.washington.edu/cdavid22/add_tasks.php";
        JSONObject body = new JSONObject();
        try {
            body.put("due", due);
            body.put("category", category);
            body.put("title", title);
            body.put("description", description);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body, //no body for this get request
                mResponse::setValue,
                this::handleError);

        Log.i("TaskViewModel", request.getUrl().toString());
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }

    public void addTaskListObserver(@NonNull LifecycleOwner owner,
                                      @NonNull Observer<? super List<Task>> observer) {
        mTaskList.observe(owner, observer);
    }
    private void handleResult(final JSONObject result) {
        Log.d("DEBUG", "Received result from server: " + result.toString());
        try {
            JSONArray arr = result.getJSONArray("tasks");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                int taskId = obj.getInt(Task.ID); // Get task ID
                Task task = new Task(taskId,
                        obj.getString(Task.DUE),
                        obj.getString(Task.CATEGORY),
                        obj.getString(Task.TITLE),
                        obj.getString(Task.DESCRIPTION));
                mTaskList.getValue().add(task);
            }
            mTaskList.setValue(mTaskList.getValue());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }
    }
    public void getTasks() {

        String url =

                "https://students.washington.edu/cdavid22/get_tasks.php";

        Request request = new JsonObjectRequest(

                Request.Method.GET,

                url,

                null, //no body for this get request

                this::handleResult,

                this::handleError);



        request.setRetryPolicy(new DefaultRetryPolicy(

                10_000,

                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,

                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Instantiate the RequestQueue and add the request to the queue

        Volley.newRequestQueue(getApplication().getApplicationContext())

                .add(request);

    }
    public void deleteTask(Task task) {
        Log.d("TaskViewModel", "Deleting task with ID: " + task.getId());
        String url = "https://students.washington.edu/cdavid22/delete_task.php";
        JSONObject body = new JSONObject();
        try {
            body.put("id", task.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body,
                mResponse::setValue,
                this::handleError);

        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }


}
