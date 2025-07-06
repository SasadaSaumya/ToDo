package com.sasadev.todo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_launcher);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.splash_anim);
        ImageView imageView = findViewById(R.id.splashImageView);
        imageView.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.i("Sasa","onAnimationStart");

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                finish();
                Log.i("Sasa","onAnimationEnd");
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.i("Sasa","Animation");
            }
        });
    }
}