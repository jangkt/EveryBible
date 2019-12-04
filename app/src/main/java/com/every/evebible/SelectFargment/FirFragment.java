package com.every.evebible.SelectFargment;

import android.os.Bundle;
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

public class FirFragment extends Fragment {
    ListView listTestment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_testment, container, false);
        listTestment = (ListView)view.findViewById(R.id.listTestment);
        final List<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,list);
        list.add("신약");
        list.add("구약");
        listTestment.setAdapter(adapter);
        listTestment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChanActivity.setTESTMENT(list.get(position).substring(0,1));
                BiFrag.setList(getContext(),"long_label", ChanActivity.getTESTMENT());
                ChanActivity.refresh();
                ChanActivity.viewPager.setCurrentItem(1);
            }
        });

        return view;
    }
}
