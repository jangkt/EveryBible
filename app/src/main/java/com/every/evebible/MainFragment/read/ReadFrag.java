package com.every.evebible.MainFragment.read;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.every.evebible.BiDBHelp;
import com.every.evebible.Itembox.BiItem;
import com.every.evebible.MainActivity;
import com.every.evebible.R;
import com.every.evebible.ChanActivity;

import java.util.ArrayList;

public class ReadFrag extends Fragment {
    public static ListView listBibleCon;
    public static androidx.appcompat.widget.Toolbar up_toolbar;
    public static final ArrayList<BiItem> list = new ArrayList<>();
    public static BibleAdapter adapter;
    public static Handler handler = new Handler();
    public static TextView tvToolLabel;
    public static TextView tvToolChapter;
    ArrayList<Boolean> isChecked = new ArrayList<>();
    public static ArrayList<BiItem> list2 = new ArrayList<>();
    public static ArrayList<BiItem> list3 = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        handler.removeCallbacksAndMessages(null);
        View root = inflater.inflate(R.layout.fragment_read, container, false);
        up_toolbar = (Toolbar) root.findViewById(R.id.up_toolbar);
        listBibleCon = (ListView) root.findViewById(R.id.listBibleCon);
        adapter = new BibleAdapter(getActivity(), list);
        LayoutInflater inflater1 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater1.inflate(R.layout.actionbar, null);
        up_toolbar.addView(view);

        tvToolLabel = (TextView) view.findViewById(R.id.tvToolLabel);
        tvToolChapter = (TextView) view.findViewById(R.id.tvToolChapter);
        Button btnBack = (Button) view.findViewById(R.id.btnBack);
        Button btnFavorite = (Button) view.findViewById(R.id.btnFavorite);
        up_toolbar.setVisibility(View.INVISIBLE);
        up_toolbar.setVisibility(View.VISIBLE);

        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                up_toolbar.setVisibility(View.INVISIBLE);
            }
        }, 2000);

        tvToolLabel.setText(MainActivity.bible);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), ChanActivity.class);
                intent1.setAction(ChanActivity.RE_SELECT_VERSE);
                intent1.putExtra("testament", MainActivity.testament);
                intent1.putExtra("bible", MainActivity.bible);
                intent1.putExtra("verse", MainActivity.verse);
                startActivityForResult(intent1, 1);

            }
        });
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BiDBHelp helper = new BiDBHelp(getActivity(), "favorite", null, 1);
                SQLiteDatabase sqlDB = getActivity().openOrCreateDatabase("favorite", Context.MODE_PRIVATE, null);
                helper.getWritableDatabase();
                list2.clear();
                list3.clear();
                Cursor c = sqlDB.rawQuery("SELECT * FROM favorites", null);
                if (c.getCount() == 0) {
                    for (int i = 0; i < list.size(); i++) {
                        if (isChecked.get(i) == true) {
                            sqlDB.execSQL("insert into favorites values('" + tvToolLabel.getText() + "'," + list.get(i).getChapter() + "," + list.get(i).getVerse() + ",'" + list.get(i).getSentence() + "')");
                            Log.i("test11", "저장" + list.get(i).getSentence());
                            listBibleCon.setItemChecked(i, false);
                            isChecked.set(i, false);
                        }
                    }
                } else {
                    while (c.moveToNext()) {
                        list3.add(new BiItem(c.getString(3), c.getInt(1), c.getInt(2)));
                        Log.i("test11", "1.  " + c.getString(3));
                    }

                    for (int i = 0; i < list.size(); i++) {
                        if (isChecked.get(i) == true) {
                            listBibleCon.setItemChecked(i, false);
                            isChecked.set(i, false);
                            list2.add(new BiItem(list.get(i).getSentence(), list.get(i).getChapter(), list.get(i).getVerse()));
                            Log.i("test11", "2.  " + list.get(i).getSentence());
                        }
                    }
                    for (int i = 0; i < list2.size(); i++) {
                        for (int j = 0; j < list3.size(); j++) {
                            if (!(list2.size() == 0 && list3.size() == 0)) {
                                if (list2.get(i).getSentence().equals(list3.get(j).getSentence())) {
                                    Log.i("test11", "중복" + list2.get(i).getSentence());
                                    if (list2.size() > 1) {
                                        list2.remove(i);
                                    } else {
                                        list2 = new ArrayList<>();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    for (int i = 0; i < list2.size(); i++) {
                        sqlDB.execSQL("insert into favorites values('" + tvToolLabel.getText() + "'," + list2.get(i).getChapter() + "," + list2.get(i).getVerse() + ",'" + list2.get(i).getSentence() + "')");
                        Log.i("test11", "저장" + list2.get(i).getSentence());

                    }
                    sqlDB.close();
                    c.close();
                }
            }

        });

        tvToolLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), ChanActivity.class);
                if (MainActivity.testament == null) {
                    intent1.setAction(ChanActivity.SELECT);
                } else {
                    intent1.setAction(ChanActivity.RE_SELECT_BIBLE);
                    intent1.putExtra("testament", MainActivity.testament);
                }
                startActivityForResult(intent1, 1);
            }
        });
        tvToolChapter.setText(MainActivity.chapter + "장");
        tvToolChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), ChanActivity.class);
                intent1.setAction(ChanActivity.RE_SELECT_CHAPTER);
                intent1.putExtra("testament", MainActivity.testament);
                intent1.putExtra("bible", MainActivity.bible);
                startActivityForResult(intent1, 1);
            }
        });
        listBibleCon.setAdapter(adapter);


        setList(getActivity(), MainActivity.testament, MainActivity.bible, MainActivity.chapter);


        listBibleCon.setSelection(MainActivity.verse - 1);
        for (int i = 0; i < list.size(); i++) {
            isChecked.add(listBibleCon.getCheckedItemPositions().get(i));
        }

        listBibleCon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox box = (CheckBox) view.findViewById(R.id.cbFavorite);
                isChecked.set(position, box.isChecked());
            }
        });
        listBibleCon.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            up_toolbar.setVisibility(View.INVISIBLE);
                            MainActivity.navView.setVisibility(View.INVISIBLE);
                        }
                    }, 3000);
                } else if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    handler.removeCallbacksAndMessages(null);
                    MainActivity.navView.setVisibility(View.VISIBLE);
                    up_toolbar.setVisibility(View.VISIBLE);
                } else if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    handler.removeCallbacksAndMessages(null);
                    MainActivity.navView.setVisibility(View.VISIBLE);
                    up_toolbar.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        return root;

    }

    public static void setLabel(String label) {
        tvToolLabel.setText(label);
    }

    public static void setChapter(int chapter) {
        tvToolChapter.setText(chapter + "장");
    }


    public static void setList(Context context, String testament, String long_label, int chapter) {
        try {
            if (!(testament == null)) {
                SQLiteDatabase ReadDB = context.openOrCreateDatabase("Bible", Context.MODE_PRIVATE, null);
                //SELECT문을 사용하여 테이블에 있는 데이터를 가져옵니다..
                Cursor c = ReadDB.rawQuery("SELECT * FROM bibles where long_label = '" + long_label + "' and chapter =" + chapter, null);
                list.clear();
                while (c.moveToNext()) {
                    list.add(new BiItem(c.getString(5), c.getInt(3), c.getInt(4)));
                }
                adapter.notifyDataSetChanged();
            }
        } catch (SQLiteException se) {
            Log.e("", se.getMessage());
        }
    }
}

class BibleAdapter extends BaseAdapter {
    Context acontext;
    private ArrayList<BiItem> mItems;
    LayoutInflater inflacter;

    public BibleAdapter(Context context, ArrayList<BiItem> biItems) {
        acontext = context;
        mItems = biItems;

        inflacter = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public BiItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_bible_item, parent, false);
        }

        final LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.listBackground);
        TextView tvBible = (TextView) convertView.findViewById(R.id.tvBible);
        TextView tvContent = (TextView) convertView.findViewById(R.id.tvContent);
        tvBible.setText("" + mItems.get(position).getVerse());
        tvContent.setText(mItems.get(position).getSentence());


        return convertView;
    }


}
