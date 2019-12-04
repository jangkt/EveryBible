package com.every.evebible.SelectFargment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.every.evebible.ChanActivity;
import com.every.evebible.MainActivity;
import com.every.evebible.R;
import com.every.evebible.MainFragment.read.ReadFrag;

import java.util.ArrayList;
import java.util.List;


public class EndFragment extends Fragment {
    ListView listVerse;
    static final List<Integer> list = new ArrayList<>();
    static ArrayAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_verse, container, false);
        listVerse = (ListView) view.findViewById(R.id.listVerse);
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);
        if (ChanActivity.CHAPTER==0){
            setList(getContext(),"paragraph","구","창세기",1);
        }
        listVerse.setAdapter(adapter);
        listVerse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChanActivity.setVERSE(list.get(position));
                Intent intent = new Intent();
                if (ChanActivity.getCHAPTER()==0){
                    intent.putExtra("testament", "구");
                    intent.putExtra("bible", "창세기");
                    intent.putExtra("chapter", 1);
                    intent.putExtra("verse", 1);
                    getActivity().setResult(getActivity().RESULT_OK,intent);
                    MainActivity.navView.setVisibility(View.VISIBLE);
                    ReadFrag.up_toolbar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ReadFrag.up_toolbar.setVisibility(View.INVISIBLE);
                        }
                    },2000);
                    getActivity().finish();
                }else {
                    intent.putExtra("testament", ChanActivity.getTESTMENT());
                    intent.putExtra("bible", ChanActivity.getBIBLE());
                    intent.putExtra("chapter", ChanActivity.getCHAPTER());
                    intent.putExtra("verse", ChanActivity.getVERSE());
                    MainActivity.navView.setVisibility(View.VISIBLE);
                    ReadFrag.up_toolbar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ReadFrag.up_toolbar.setVisibility(View.INVISIBLE);
                        }
                    },2000);
                    getActivity().setResult(getActivity().RESULT_OK,intent);
                    getActivity().finish();
                }

                getActivity().finish();
            }
        });
        return view;
    }
    public static void setList(Context context, String setName, String testament, String long_label, int chapter) {
        try {
            if (!(testament==null)) {
                SQLiteDatabase ReadDB = context.openOrCreateDatabase("Bible", Context.MODE_PRIVATE, null);
                //SELECT문을 사용하여 테이블에 있는 데이터를 가져옵니다..
                Cursor c = ReadDB.rawQuery("SELECT distinct " + setName + " FROM bibles where testament = '" + testament + "' and long_label = '"+long_label+"' and chapter = "+chapter, null);
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
