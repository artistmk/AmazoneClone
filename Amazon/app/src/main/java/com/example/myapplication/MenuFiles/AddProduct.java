package com.example.myapplication.MenuFiles;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.HomeActivity;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddProduct extends BaseActivity {

    ImageView addProdImg, addProductBack;
    EditText addProdName, addProdPrice, addProdDesc, addProdCategory;
    TextView confirmAdd;
    FirebaseStorage storage;
    StorageReference storageReference;
    Uri setImageUri;
    String finalImageUri;
    Toolbar addtoolbar;

    LinearLayout dynamicContent, bottomNavBar;

    @SuppressLint({"ResourceAsColor", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        dynamicContent = findViewById(R.id.dynamicContent);
        bottomNavBar = findViewById(R.id.bottomNavBar);

        View wizard = getLayoutInflater().inflate(R.layout.activity_add_product, dynamicContent, false);
        dynamicContent.addView(wizard);

        RadioGroup rg = findViewById(R.id.radioGroup1);
        RadioButton rb = findViewById(R.id.bottom_addprod);

        rb.setBackgroundColor(getResources().getColor(R.color.item_selected, null));
        rb.setTextColor(Color.parseColor("#3F5185"));

        addProdImg = findViewById(R.id.addProductImg);
        addProdName = findViewById(R.id.addProductName);
        addProdPrice = findViewById(R.id.addProductPrice);
        addProdDesc = findViewById(R.id.addProductDesc);
        addProdCategory = findViewById(R.id.addProductCategory);
        confirmAdd = findViewById(R.id.confirmAddProd);
        addProductBack = findViewById(R.id.addProductBack);
        addtoolbar = findViewById(R.id.addtoolbar);

        addtoolbar.setBackgroundResource(R.drawable.bg_color);

        storage = FirebaseStorage.getInstance();

        addProdImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });

        confirmAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = addProdName.getEditableText().toString();
                String price = addProdPrice.getEditableText().toString();
                String desc = addProdDesc.getEditableText().toString();
                String category = addProdCategory.getEditableText().toString();

                if (TextUtils.isEmpty(name)) {
                    addProdName.setError("Please enter name of the product");
                } else if (TextUtils.isEmpty(price)) {
                    addProdPrice.setError("Please enter price of the product");
                } else if (TextUtils.isEmpty(desc)) {
                    addProdDesc.setError("Please enter description of the product");
                } else if (TextUtils.isEmpty(category)) {
                    addProdCategory.setError("Please enter category of the product");
                } else if (!isImageSelected()) {
                    Toast.makeText(AddProduct.this, "Please choose a product image", Toast.LENGTH_SHORT).show();
                } else {
                    storageReference = storage.getReference().child("products").child(name);
                    addingToProdList();
                }
            }
        });

        addProductBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProduct.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isImageSelected() {
        Drawable drawable = addProdImg.getDrawable();
        return drawable != null;
    }

    private void addingToProdList() {
        String saveCurrentDate, saveCurrentTime;

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        DatabaseReference prodListRef = FirebaseDatabase.getInstance().getReference().child("View All");

        final HashMap<String, Object> prodMap = new HashMap<>();
        prodMap.put("pid", addProdName.getText().toString());
        prodMap.put("name", addProdName.getText().toString());
        prodMap.put("price", "₹" + addProdPrice.getText().toString());
        prodMap.put("category", addProdCategory.getText().toString());
        prodMap.put("description", addProdDesc.getText().toString());
        prodMap.put("date", saveCurrentDate);
        prodMap.put("time", saveCurrentTime);

        if (setImageUri != null) {
            storageReference.putFile(setImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            finalImageUri = uri.toString();
                            Log.i("Image", "added successfully to storage");
                            prodMap.put("img", finalImageUri);

                            prodListRef.child("User View").child("Products")
                                    .child(addProdName.getText().toString()).updateChildren(prodMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(AddProduct.this, "Product added successfully after verification", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(AddProduct.this, HomeActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                    });
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            setImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), setImageUri);
                addProdImg.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
