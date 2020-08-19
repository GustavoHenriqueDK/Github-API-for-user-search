package com.example.githubapiforusersearch.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.githubapiforusersearch.R;
import com.example.githubapiforusersearch.model.User;
import com.example.githubapiforusersearch.rest.EndPoint;
import com.example.githubapiforusersearch.retrofit.RetrofitConfiguration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUserName;
    private ImageView imageViewAvatar;
    private TextView textviewUsername;
    private TextView textViewUserRealName;
    private TextView textViewFollowers;
    private TextView textViewFollowing;
    private TextView textViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

        EndPoint endPoint = RetrofitConfiguration.getClient().create(EndPoint.class);
        Call<User> call = endPoint.getUser(editTextUserName.getText().toString());

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Error executing API ", t.toString());
            }
        });
    }

    private void findViews() {
        textviewUsername = findViewById(R.id.textViewUsername);
        textViewFollowers = findViewById(R.id.textViewFollowers);
        textViewFollowing = findViewById(R.id.textViewFollowing);
        textViewEmail = findViewById(R.id.textViewEmail);
        editTextUserName = findViewById(R.id.editTextTextUsername);
        imageViewAvatar = findViewById(R.id.imageViewUser);
        textViewUserRealName = findViewById(R.id.textViewUserRealName);
    }
}