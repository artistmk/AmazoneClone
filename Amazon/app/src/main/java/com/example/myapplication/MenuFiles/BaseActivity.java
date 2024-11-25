package com.example.myapplication.MenuFiles;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.HomeActivity;


public class BaseActivity extends AppCompatActivity {

    RadioGroup radioGroup1;

    @SuppressLint({"NonConstantResourceId", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        radioGroup1 = findViewById(R.id.radioGroup1);


        radioGroup1.setOnCheckedChangeListener((group, checkedId) -> {
            Intent intent;

            if (checkedId == R.id.bottom_home) {
                intent = new Intent(getBaseContext(), HomeActivity.class);
            } else if (checkedId == R.id.bottom_addprod) {
                intent = new Intent(getBaseContext(), AddProduct.class);
            } else if (checkedId == R.id.bottom_search) {
                intent = new Intent(getBaseContext(), SearchActivity.class);
            } else if (checkedId == R.id.bottom_cart) {
                intent = new Intent(getBaseContext(), CartActivity.class);
            } else if (checkedId == R.id.bottom_profile) {
                intent = new Intent(getBaseContext(), ProfileActivity.class);
            } else {
                return;
            }

            startActivity(intent);
            overridePendingTransition(0, 0); // Removes transition animation
        });
    }
}