package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    Button button, btn, b, TeacherBtn, button_notification;
    TextView textView;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logout);
        btn = findViewById(R.id.QRcode);
        b = findViewById(R.id.Database);
        TeacherBtn = findViewById(R.id.TDb);
        textView = findViewById(R.id.user_details);
        user = auth.getCurrentUser();


        if (user == null) {
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        } else {
            textView.setText(user.getEmail());
        }

        // Setup listeners for other buttons
        setupButtonListeners();
    }


    private void setupButtonListeners() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }


        b.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), CreateDB.class));
            finish();
        });

        btn.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), QR_code.class));
            finish();
        });

        button.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        });

        TeacherBtn.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), teacherDB.class));
            finish();
        });
    }


}
