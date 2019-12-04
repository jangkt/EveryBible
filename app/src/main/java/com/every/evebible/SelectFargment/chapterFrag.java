package com.every.evebible.SelectFargment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.every.evebible.ChanActivity;
import com.every.evebible.R;

import java.util.ArrayList;
import java.util.List;

public class chapterFrag extends Fragment {

    ListView listChapter;
    static final List<Integer> list = new ArrayList<>();
    static ArrayAdapter adapter;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chapter, container, false);
        listChapter = (ListView) view.findViewById(R.id.listChapter);
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);
        listChapter.setAdapter(adapter);
        if (ChanActivity.BIBLE==null){
            setList(getContext(),"chapter","구","창세기");
        }
        listChapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChanActivity.viewPager.setCurrentItem(3);
                ChanActivity.setCHAPTER(list.get(position));
                EndFragment.setList(getContext(),"paragraph", ChanActivity.getTESTMENT(), ChanActivity.getBIBLE(), ChanActivity.getCHAPTER());
                ChanActivity.refresh();
            }
        });
        return view;
    }

    public static void setList(Context context, String setName, String testament, String long_label) {
        try {
            if (!(testament==null)) {
                SQLiteDatabase ReadDB = context.openOrCreateDatabase("Bible", Context.MODE_PRIVATE, null);
                //SELECT문을 사용하여 테이블에 있는 데이터를 가져옵니다..
                Cursor c = ReadDB.rawQuery("SELECT distinct " + setName + " FROM bibles where testament = '" + testament + "' and long_label = '"+long_label+"'", null);
                list.clear();
                while (c.moveToNext()) {
                    list.add(c.getInt(c.getColumnIndex(setName)));
                }
                adapter.notifyDataSetChanged();
            }
        } catch (SQLiteException se) {
            Log.e("", se.getMessage());
        }
    }
}
