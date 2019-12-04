package com.every.evebible.MainFragment.favorites;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.every.evebible.BiDBHelp;
import com.every.evebible.Itembox.FavItem;
import com.every.evebible.R;
import com.every.evebible.MainFragment.read.ReadFrag;

import java.util.ArrayList;

public class FavFrag extends Fragment {
    public ListView listFavorite;
    public ArrayList<FavItem> listFavoriteData = new ArrayList<>();
    public FavoriteAdapter adapter;
    ArrayList<Boolean> isChecked = new ArrayList<>();
    Button btnTrash;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ReadFrag.handler.removeCallbacksAndMessages(null);
        View root = inflater.inflate(R.layout.favories_fragment, container, false);
        listFavorite = (ListView) root.findViewById(R.id.listFavorite);
        btnTrash = (Button)root.findViewById(R.id.btnTrash);
        adapter = new FavoriteAdapter(getContext(), listFavoriteData);
        final BiDBHelp helper = new BiDBHelp(getActivity(), "favorite", null, 1);
        final SQLiteDatabase ReadDB = getActivity().openOrCreateDatabase("favorite", Context.MODE_PRIVATE, null);
        helper.getWritableDatabase();
        Cursor c = ReadDB.rawQuery("SELECT * FROM favorites", null);
        listFavoriteData.clear();
        while (c.moveToNext()) {
            listFavoriteData.add(new FavItem(c.getString(0), c.getString(3), c.getInt(1), c.getInt(2)));
            isChecked.add(false);
        }
        c.close();
        listFavorite.setAdapter(adapter);

        btnTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("test2","클릭");
                for (int i = 0; i<listFavoriteData.size(); i++){
                    if (isChecked.get(i)==true){
                        Log.i("test2","조건실행");
                        isChecked.set(i,false);
                        listFavorite.setItemChecked(i,false);
                        ReadDB.execSQL("delete from favorites where sentence ='"+listFavoriteData.get(i).getSentence()+"'");
                    }
                }
                Cursor c = ReadDB.rawQuery("SELECT * FROM favorites", null);
                listFavoriteData.clear();
                while (c.moveToNext()) {
                    listFavoriteData.add(new FavItem(c.getString(0), c.getString(3), c.getInt(1), c.getInt(2)));
                    isChecked.add(false);
                }
                c.close();
                adapter.notifyDataSetChanged();
            }
        });
        listFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isChecked.get(position)==false){
                    isChecked.set(position,true);
                }else {
                    isChecked.set(position,false);
                }

            }
        });




        return root;
    }
}

class FavoriteAdapter extends BaseAdapter {
    Context acontext;
    private ArrayList<FavItem> mItems;
    LayoutInflater inflacter;

    public FavoriteAdapter(Context context, ArrayList<FavItem> bibleItems) {
        acontext = context;
        mItems = bibleItems;

        inflacter = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public FavItem getItem(int position) {
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
            convertView = inflater.inflate(R.layout.list_favorite_item, parent, false);
        }

        TextView favoriteInfo = (TextView) convertView.findViewById(R.id.favoriteInfo);
        TextView favoriteCon = (TextView) convertView.findViewById(R.id.favoriteCon);
        favoriteInfo.setText(mItems.get(position).getBible() + " " + mItems.get(position).getChapter() + "장  " + mItems.get(position).getVerse() + "절");
        favoriteCon.setText(mItems.get(position).getSentence());


        return convertView;
    }


}

