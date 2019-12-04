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

public class BiFrag extends Fragment {
    ListView listBible;
    static final List<String> list = new ArrayList<>();
    static ArrayAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bible, container, false);
        listBible = (ListView) view.findViewById(R.id.listBible);
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);
        listBible.setAdapter(adapter);
        if (ChanActivity.TESTMENT==null){
            setList(getContext(),"long_label","구");
        }else {
            setList(getContext(),"long_label", ChanActivity.TESTMENT);
        }
        listBible.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChanActivity.viewPager.setCurrentItem(2);
                ChanActivity.setBIBLE(list.get(position));
                chapterFrag.setList(getContext(),"chapter", ChanActivity.getTESTMENT(), ChanActivity.getBIBLE());
                ChanActivity.refresh();
            }
        });

        return view;

    }

    public static void setList(Context context, String setName, String testament) {
        try {
            if (!(testament==null)) {
                SQLiteDatabase ReadDB = context.openOrCreateDatabase("Bible", Context.MODE_PRIVATE, null);
                //SELECT문을 사용하여 테이블에 있는 데이터를 가져옵니다..
                Cursor c = ReadDB.rawQuery("SELECT distinct " + setName + " FROM bibles where testament = '" + testament + "'", null);
                list.clear();
                while (c.moveToNext()) {
                    list.add(c.getString(c.getColumnIndex(setName)));
                }
                adapter.notifyDataSetChanged();
            } else {


            }
        } catch (SQLiteException se) {
            Log.e("", se.getMessage());
        }
    }
}
