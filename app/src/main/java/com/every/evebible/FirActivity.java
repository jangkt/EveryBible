package com.every.evebible;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.every.evebible.DBbox.DB;
import com.every.evebible.DBbox.DB0;
import com.every.evebible.DBbox.DB2;
import com.every.evebible.DBbox.DB3;
import com.every.evebible.DBbox.DB5;
import com.every.evebible.DBbox.DB1;
import com.every.evebible.DBbox.DB4;
import com.every.evebible.DBbox.DB6;


public class FirActivity extends AppCompatActivity {
    BiDBHelp helper;
    SQLiteDatabase db;
    TextView splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splash = (TextView) findViewById(R.id.splash);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                helper = new BiDBHelp(FirActivity.this, "Bible", null, 1);
                db = helper.getWritableDatabase();
                Cursor c = db.rawQuery("SELECT * FROM bibles where idx = 1", null);
                if (c.getCount() == 0) {
                    helper.onUpgrade(db, 1, 2);
                    DB dbs = new DB(db);
                    DB0 db0 = new DB0(db);
                    DB1 db1 = new DB1(db);
                    DB2 db2 = new DB2(db);
                    DB3 db3 = new DB3(db);
                    DB4 db4 = new DB4(db);
                    DB5 db5 = new DB5(db);
                    DB6 db6 = new DB6(db);
                    Handler hd = new Handler();
                    hd.postDelayed(new splashhandler(), 500); // 1초 후에 hd handler 실행  3000ms = 3초
                    Log.i("test1", "생성");
                } else {
                    splash.setText("로딩중");
                    Log.i("test1", "생성안함");
                    Handler hd = new Handler();
                    hd.postDelayed(new splashhandler(), 1000); // 1초 후에 hd handler 실행  3000ms = 3초

                }


            }
        }, 500);

    }

    private class splashhandler implements Runnable {
        public void run() {
            Intent intent = new Intent(getApplication(), MainActivity.class);
            intent.putExtra("testament", "구");
            intent.putExtra("bible", "창세기");
            intent.putExtra("chapter", 1);
            intent.putExtra("verse", 1);
            intent.setAction(ChanActivity.SELECT);
            startActivity(intent); //로딩이 끝난 후, ChoiceFunction 이동
            FirActivity.this.finish(); // 로딩페이지 Activity stack에서 제거
        }
    }


}
