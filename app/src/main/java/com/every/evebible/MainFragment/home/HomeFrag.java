package com.every.evebible.MainFragment.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.every.evebible.MainActivity;
import com.every.evebible.R;
import com.every.evebible.ChanActivity;
import com.every.evebible.MainFragment.read.ReadFrag;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class HomeFrag extends Fragment {
    FrameLayout flHomeBible;
    LinearLayout outLine, bibleOut;
    TextView tvHomeBibleInfo, tvHomeBibleCon;
    public int idx;
    public static int idx2;
    ImageView btnReadbible;
    Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ReadFrag.handler.removeCallbacksAndMessages(null);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        flHomeBible = (FrameLayout) root.findViewById(R.id.flHomeBible);
        tvHomeBibleInfo = (TextView) root.findViewById(R.id.tvHomeBibleInfo);
        tvHomeBibleCon = (TextView) root.findViewById(R.id.tvHomeBibleCon);
        btnReadbible = (ImageView) root.findViewById(R.id.btnReadBible);
        outLine = (LinearLayout)root.findViewById(R.id.readbibleOutLine);
        bibleOut = (LinearLayout)root.findViewById(R.id.outlineBible);
        toolbar.setTitle("매일성경");
        toolbar.setNavigationIcon(R.drawable.everysmallico);
        toolbar.setBackgroundColor(Color.WHITE);
        toolbar.setTitleTextColor(Color.BLACK);
        getActivity().setActionBar(toolbar);
        GradientDrawable drawable = (GradientDrawable) getActivity().getDrawable(R.drawable.bible_conner);
        flHomeBible.setBackground(drawable);
        flHomeBible.setClipToOutline(true);
        GradientDrawable drawable2 = (GradientDrawable) getActivity().getDrawable(R.drawable.bible_conner);
        outLine.setBackground(drawable2);
        outLine.setClipToOutline(true);
        GradientDrawable drawable3 = (GradientDrawable) getActivity().getDrawable(R.drawable.bible_outline);
        bibleOut.setBackground(drawable3);
        btnReadbible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), ChanActivity.class);
                intent1.setAction(ChanActivity.SELECT);
                startActivityForResult(intent1,1);
                MainActivity.navView.setSelectedItemId(R.id.navigation_read);
            }
        });

        SQLiteDatabase ReadDB = getContext().openOrCreateDatabase("Bible", MODE_PRIVATE, null);
        //SELECT문을 사용하여 테이블에 있는 데이터를 가져옵니다..

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String date1 = getPreferences("date");
        if (date1.equals("")) {
            Log.i("text11", "처음");
            savePreferences("date", format.format(date));
            Random random = new Random();
            idx = random.nextInt(31138) + 1;
            idx2 = idx;
            Cursor c = ReadDB.rawQuery("SELECT * FROM bibles where idx = " + idx, null);
            if (c.getCount() != 0) {
                while (c.moveToNext()) {
                    tvHomeBibleInfo.setText(c.getString(7) + " " + c.getInt(3) + "장 " + c.getInt(4) + "절");
                    tvHomeBibleCon.setText(c.getString(5));
                    savePreferences("info", c.getString(7) + " " + c.getInt(3) + "장 " + c.getInt(4) + "절");
                    savePreferences("con", c.getString(5));
                }

            }
        } else {
            if (!(date1.equals(format.format(date)))) {
                Log.i("text11", format.format(date) + "  " + date1);
                removeAllPreferences();
                savePreferences("date", format.format(date));
                Random random = new Random();
                idx = random.nextInt(31138) + 1;
                Cursor c = ReadDB.rawQuery("SELECT * FROM bible where idx = " + idx, null);
                if (c.getCount() != 0) {
                    while (c.moveToNext()) {
                        tvHomeBibleInfo.setText(c.getString(7) + " " + c.getInt(3) + "장 " + c.getInt(4) + "절");
                        tvHomeBibleCon.setText(c.getString(5));
                        savePreferences("info", c.getString(7) + " " + c.getInt(3) + "장 " + c.getInt(4) + "절");
                        savePreferences("con", c.getString(5));
                    }
                }
            } else {
                Log.i("text11", format.format(date) + "  " + date1);
                tvHomeBibleInfo.setText(getPreferences("info"));
                tvHomeBibleCon.setText(getPreferences("con"));
            }
        }


        return root;
    }

    private String getPreferences(String key) {
        SharedPreferences pref = getContext().getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getString(key, ""); //key, value(defaults)
    }

    //데이터 저장하기
    private void savePreferences(String key, String con) {
        SharedPreferences pref = getContext().getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, con);
        editor.commit();
    }

    //데이터 삭제
    private void removePreferences(String key) {
        SharedPreferences pref = getContext().getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.commit();
    }

    private void removeAllPreferences() {
        SharedPreferences pref = getContext().getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_home,menu);
        return;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.bigFont:
                break;
            case R.id.middleFont:
                break;
            case R.id.smallFont:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}