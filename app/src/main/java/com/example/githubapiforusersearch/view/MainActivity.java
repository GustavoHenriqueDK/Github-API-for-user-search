package com.example.githubapiforusersearch.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.example.githubapiforusersearch.R;
import com.example.githubapiforusersearch.model.User;
import com.example.githubapiforusersearch.rest.EndPoint;
import com.example.githubapiforusersearch.rest.RetrofitConfiguration;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ImageView imageViewAvatar;
    private TextView textViewUsername;
    private TextView textViewNickname;
    private EditText editTextNickname;
    private TextView textViewFollowers;
    private TextView textViewFollowing;
    private TextView textViewEmail;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        findViews();

        final EndPoint endPoint = RetrofitConfiguration.getClient().create(EndPoint.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<User> call = endPoint.getUser(editTextNickname.getText().toString());
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        //TODO: Get image and make a "wait" dialog;
                        textViewNickname.setText(response.body().getNickname());
                        textViewFollowers.setText(response.body().getFollowers());
                        textViewFollowing.setText(response.body().getFollowing());
                        try {
                            textViewEmail.setText(response.body().getEmail());
                        } catch (Exception e) {
                            Log.e("User error ", "user don't have a e-mail signed");
                        }
                        try {
                            textViewUsername.setText(response.body().getName());
                        } catch (Exception e) {
                            Log.e("User error ", "user don't have a name signed");
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e("Error executing API ", t.toString());
                    }
                });
            }
        });
    }
    private void findViews() {
        textViewNickname = findViewById(R.id.textViewNickname);
        textViewUsername = findViewById(R.id.textViewUsername);
        editTextNickname = findViewById(R.id.editTextNickname);
        textViewFollowing = findViewById(R.id.textViewFollowing);
        textViewFollowers = findViewById(R.id.textViewFollowers);
        textViewEmail = findViewById(R.id.textViewEmail);
        imageViewAvatar = findViewById(R.id.imageViewUserPhotoProfile);
        button = findViewById(R.id.buttonSearch);
    }
}