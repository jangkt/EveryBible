package com.every.evebible;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.every.evebible.MainFragment.read.ReadFrag;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public static int chapter = 1;
    public static int verse = 1;
    public static String testament;
    public static String bible;
    public static BottomNavigationView navView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_read, R.id.navigation_search, R.id.navigation_favorites)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        NavigationUI.setupWithNavController(navView, navController);
        if (bible == null) {
            testament = "구";
            bible = "창세기";
            chapter = 1;
            verse = 1;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK ) {
            testament = data.getExtras().getString("testament");
            bible = data.getExtras().getString("bible");
            chapter = data.getExtras().getInt("chapter");
            verse = data.getExtras().getInt("verse");
            ReadFrag.setList(getApplication(), testament, bible, chapter);
            ReadFrag.listBibleCon.setSelection(verse - 1);
            ReadFrag.setLabel(bible);
            ReadFrag.setChapter(chapter);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        navView.setVisibility(View.VISIBLE);
        super.onBackPressed();
    }
}
