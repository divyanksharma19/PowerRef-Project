package com.project.powerref1.ResumeBuilder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import com.project.powerref1.R;

import java.util.ArrayList;
import java.util.UUID;

public class SelectProfileActivity extends AppCompatActivity {

    private String name, templateImgPath, templateFilePath;
    private ProfileRVAdapter profileRVAdapter;
    private RecyclerView rv_profile_list;
    private ArrayList<Profile> profileList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_profile);


        Button btn_add_profile = findViewById(R.id.btn_add_profile);
        btn_add_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditDetailsActivity.class);
                intent.putExtra("ProfileId", UUID.randomUUID().toString());
                intent.putExtra("CategoryName", name);
                intent.putExtra("TemplateImgPath", templateImgPath);
                intent.putExtra("TemplateFilePath", templateFilePath);
                view.getContext().startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();



        Intent intent = getIntent();
        name = intent.getStringExtra("CategoryName");
        templateImgPath = intent.getStringExtra("TemplateImgPath");
        templateFilePath = intent.getStringExtra("TemplateFilePath");

        profileList = new ArrayList<>();

//        Toast.makeText(this,  name + ' ' + templateImgPath + ' ' + templateFilePath, Toast.LENGTH_SHORT).show();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }



}
