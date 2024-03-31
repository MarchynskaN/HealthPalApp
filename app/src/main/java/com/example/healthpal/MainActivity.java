package com.example.healthpal;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthpal.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView welcomeTextView;
    EditText messageEditText;
    ImageButton sendButton;
    List<Message> messageList;
    MessageAdapter messageAdapter;
    public static final MediaType JSON
            = MediaType.get("application/json");

    OkHttpClient client = new OkHttpClient();
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        setSupportActionBar(binding.appBarMain.toolbar);
        if (binding.appBarMain.fab != null) {
            //need to have my ChatGPT chat here:
            binding.appBarMain.fab.setOnClickListener(view -> {
                // Toggle chat interface visibility
                boolean isVisible = recyclerView.getVisibility() == View.VISIBLE;
                recyclerView.setVisibility(isVisible ? View.GONE : View.VISIBLE);
                messageEditText.setVisibility(isVisible ? View.GONE : View.VISIBLE);
                sendButton.setVisibility(isVisible ? View.GONE : View.VISIBLE);
                welcomeTextView.setVisibility(isVisible ? View.GONE : View.VISIBLE);
            });
        }
        //main content container
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        NavigationView navigationView = binding.navView;
        //call content to main container
        if (navigationView != null) {
            AppBarConfiguration.Builder builder = new AppBarConfiguration.Builder(
                    R.id.nav_lab_test, R.id.nav_find_doctor, R.id.nav_buy_medicine, R.id.nav_my_account);
            builder.setOpenableLayout(binding.drawerLayout);
            mAppBarConfiguration = builder
                    .build();
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
        }
        BottomNavigationView bottomNavigationView = binding.appBarMain.contentMain.bottomNavView;
        if (bottomNavigationView != null) {
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_find_doctor, R.id.nav_lab_test,  R.id.nav_buy_medicine)
                    .build();
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
        }

        messageList = new ArrayList<>();
        recyclerView = binding.appBarMain.recyclerViewGPT;
        welcomeTextView = binding.appBarMain.welcomeText;
        messageEditText = binding.appBarMain.messageEditText;
        sendButton = binding.appBarMain.sendBtn;

        //setup recycler view
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        sendButton.setOnClickListener((v)->{
            String question = "I am experiencing " + messageEditText.getText().toString().trim() + " symptoms, what would you suggest me?";
            addToChat(question,Message.SENT_BY_ME);
            messageEditText.setText("");
            callAPI(question);
            welcomeTextView.setVisibility(View.GONE);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        // Using findViewById because NavigationView exists in different layout files
        // between w600dp and w1240dp
        NavigationView navView = findViewById(R.id.nav_view);
        if (navView == null) {
            // The navigation drawer already has the items including the items in the overflow menu
            // We only inflate the overflow menu if the navigation drawer isn't visible
            getMenuInflater().inflate(R.menu.overflow, menu);
        }
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_my_account) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.nav_my_account);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    void addToChat(String message,String sentBy){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(message,sentBy));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }
    void addResponse(String response) {
        //need to add those
        addToChat(response, Message.SENT_BY_BOT);
    }
    void callAPI(String question) {
        //OkHTTP set-up
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model", "text-davince-003");
            jsonBody.put("max-tokens",4000);
            jsonBody.put("temperature",0);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        RequestBody body = RequestBody.create(jsonBody.toString(), JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .header("Authorization", "Bearer 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")
                .post(body)
                .build();
        client.newCall(request).enqueue((new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Failed to load due to " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    JSONObject  jsonObject;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0).getString("text");
                        addResponse(result.trim());
                    } catch (JSONException e) {
                        addResponse("Error processing the response: " + e.getMessage());
                        e.printStackTrace();
                    } finally {
                        response.close(); // Ensure the response is closed after use
                    }
                } else {
                    // Here, we handle the scenario where the response is not successful
                    try {
                        // You can further customize this message based on response.code() or response.body()
                        String errorMessage = "API request failed with status code: " + response.code() + " - " + response.message();
                        addResponse(errorMessage);
                        Log.e("API Error", errorMessage);
                    } finally {
                        response.close(); // Ensure the response is closed after use
                    }
                }
            }
        }));
    }
}