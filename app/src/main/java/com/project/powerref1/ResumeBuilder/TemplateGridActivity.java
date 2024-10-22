package com.project.powerref1.ResumeBuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.project.powerref1.HomeActivity;
import com.project.powerref1.R;

import java.net.InetAddress;
import java.util.ArrayList;

import static android.view.View.GONE;

public class TemplateGridActivity extends AppCompatActivity {

    private String categoryName;
    private Category category;
    private TextView category_name_gv;

    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_grid);



        Intent intent = getIntent();
        categoryName = intent.getStringExtra("CATEGORY");

        category_name_gv = findViewById(R.id.category_name_gv);
        category_name_gv.setText(categoryName);

        category_name_gv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HomeActivity.class);
                view.getContext().startActivity(intent);
                finish();
            }
        });

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setMax(100);
        progressBar.setProgress(0);
        progressBar.setIndeterminate(true);
        progressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, android.graphics.PorterDuff.Mode.MULTIPLY);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
////        switch (item.getItemId()) {
////            case R.id.btn_logout:
////
////                return true;
////            default:
////                return super.onOptionsItemSelected(item);
////        }
//    }




    @Override
    protected void onResume() {
        super.onResume();
        if(!isInternetAvailable()){
            Toast.makeText(this, "Internet Unavailable. Try again later...", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(GONE);
        }
    }

    public boolean isInternetAvailable() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
        }catch (Exception e){
            return false;
        }
    }
}
