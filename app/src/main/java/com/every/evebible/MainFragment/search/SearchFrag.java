package com.every.evebible.MainFragment.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.every.evebible.EditTextBox;
import com.every.evebible.R;
import com.every.evebible.Itembox.Searitem;
import com.every.evebible.MainFragment.read.ReadFrag;

import java.util.ArrayList;

public class SearchFrag extends Fragment implements AbsListView.OnScrollListener {

    EditTextBox eidtBibleSearch;
    ImageButton ibBibleSearch;
    ListView listBibleSearch;
    TextView tvSearchResult;
    public ArrayList<Searitem> listSearchData = new ArrayList<>();
    public SearchAdapter adapter;
    boolean mLockListView;
    SQLiteDatabase ReadDB;
    Cursor c;
    FrameLayout footerButton;
    ImageView ivNext;
    ProgressBar pbarLoading, pbarSearch;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ReadFrag.handler.removeCallbacksAndMessages(null);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        eidtBibleSearch = (EditTextBox) root.findViewById(R.id.eidtBibleSearch);
        ibBibleSearch = (ImageButton) root.findViewById(R.id.ibBibleSearch);
        listBibleSearch = (ListView) root.findViewById(R.id.listBibleSearch);
        tvSearchResult = (TextView)root.findViewById(R.id.tvSearchResult);
        pbarSearch = (ProgressBar)root.findViewById(R.id.pbarSearch);

        mLockListView = true;
        listBibleSearch.setOnScrollListener(this);
        View listView_footer = inflater.inflate(R.layout.listview_footer,null);
        listBibleSearch.addFooterView(listView_footer);
        adapter = new SearchAdapter(getContext(), listSearchData);
        listBibleSearch.setAdapter(adapter);
        footerButton = (FrameLayout)listView_footer.findViewById(R.id.btnFooter);
        ivNext = (ImageView)listView_footer.findViewById(R.id.ivNext);
        pbarLoading = (ProgressBar)listView_footer.findViewById(R.id.pbarLoading);
        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivNext.setVisibility(View.INVISIBLE);
                pbarLoading.setVisibility(View.VISIBLE);
                addItems(10,c);

            }
        });
        ReadDB = getActivity().openOrCreateDatabase("Bible", Context.MODE_PRIVATE, null);

//
        ibBibleSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbarSearch.setVisibility(View.VISIBLE);
                if (footerButton.getVisibility()==View.VISIBLE){
                    footerButton.setVisibility(View.INVISIBLE);
                }
                String search = eidtBibleSearch.getText().toString();
                listSearchData.clear();
                c = ReadDB.rawQuery("SELECT * FROM bibles where sentence like '%"+search+"%'", null);
                addItems(10,c);
                listBibleSearch.setSelection(0);


            }
        });


        return root;
    }
    private void addItems(final int size , final Cursor c){
        mLockListView = true;
        Runnable run  = new Runnable() {
            @Override
            public void run() {
                if (c.getCount()!=0){
                    listBibleSearch.setVisibility(View.VISIBLE);
                    tvSearchResult.setVisibility(View.INVISIBLE);
                    int i=0;
                    while (c.moveToNext()) {
                        listSearchData.add(new Searitem(c.getString(7), c.getString(5), c.getInt(3), c.getInt(4)));
                        if (i>=size){
                            i=0;
                            break;
                        }else {
                            i++;
                        }
                    }
                    adapter.notifyDataSetChanged();
                    mLockListView=false;
                    ivNext.setVisibility(View.VISIBLE);
                    pbarLoading.setVisibility(View.INVISIBLE);
                    if (pbarSearch.getVisibility()==View.VISIBLE){
                        pbarSearch.setVisibility(View.INVISIBLE);
                    }
                }else {
                    tvSearchResult.setVisibility(View.VISIBLE);
                    pbarSearch.setVisibility(View.INVISIBLE);
                    listBibleSearch.setVisibility(View.INVISIBLE);
                    adapter.notifyDataSetChanged();
                    mLockListView=false;
                }
                Log.i("text", "" + c.getCount());
            }

        };
        Handler handler = new Handler();
        handler.postDelayed(run,1000);
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int count = totalItemCount-visibleItemCount;
        if (firstVisibleItem>=count && totalItemCount !=0 && mLockListView==false){
            ivNext.setVisibility(View.VISIBLE);
            pbarLoading.setVisibility(View.INVISIBLE);
            if (c.getCount()!=listSearchData.size()){
                footerButton.setVisibility(View.VISIBLE);
            }else {
                footerButton.setVisibility(View.GONE);
            }

        }
    }
}

class SearchAdapter extends BaseAdapter {
    Context acontext;
    private ArrayList<Searitem> mItems;
    LayoutInflater inflacter;

    public SearchAdapter(Context context, ArrayList<Searitem> bibleItems) {
        acontext = context;
        mItems = bibleItems;

        inflacter = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Searitem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_search_item, parent, false);
        }

        TextView favoriteInfo = (TextView) convertView.findViewById(R.id.searchInfo);
        TextView favoriteCon = (TextView) convertView.findViewById(R.id.searchCon);
        favoriteInfo.setText(mItems.get(position).getBible() + " " + mItems.get(position).getChapter() + "장 " + mItems.get(position).getVerse() + "절");
        favoriteCon.setText(mItems.get(position).getSentence());


        return convertView;
    }



}
