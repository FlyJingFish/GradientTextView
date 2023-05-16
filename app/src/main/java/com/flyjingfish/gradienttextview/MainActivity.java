package com.flyjingfish.gradienttextview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.flyjingfish.gradienttextview.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.gradientTextView1.setOnClickListener(v -> {
            binding.gradientTextView1.setTextColor(Color.BLACK);
        });
        binding.gradientTextView4.setOnClickListener(v -> {});
    }
}