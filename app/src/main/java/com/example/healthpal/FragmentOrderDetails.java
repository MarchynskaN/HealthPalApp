package com.example.healthpal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentOrderDetails extends Fragment {

    private ListView lst;
    private ArrayList<HashMap<String, String>> list = new ArrayList<>();
    HashMap<String,String>item;
    private SimpleAdapter sa;

    public FragmentOrderDetails() {
        // Required empty public constructor
    }

    public static FragmentOrderDetails newInstance() {
        return new FragmentOrderDetails();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);
        lst = view.findViewById(R.id.listViewOD);
        loadData();
        return view;
    }

    private void loadData() {
        Database db = new Database(getContext(), "healthpal", null, 1);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        ArrayList dbData = db.getOrderData(username);

        String[][] order_details = new String[dbData.size()][];
        for(int i = 0; i < order_details.length; i++){
            order_details[i] = new String[5];
            String arrData = dbData.get(i).toString();
            String[] strData = arrData.split(java.util.regex.Pattern.quote("$"));
            order_details[i][0] = strData[0];
            order_details[i][1] = strData[1];
            if(strData[7].compareTo("medicine") == 0){
                order_details[i][3] = "Del:" + strData[4];
            }else{
                order_details[i][3] = "Del:" + strData[4] + " " + strData[5];
            }
            order_details[i][2] = "Rs." + strData[6];
            order_details[i][4] = strData[7];
        }

        list = new ArrayList();
        for(int i = 0; i < order_details.length; i++){
            item=new HashMap<String,String>();
            item.put("line1",order_details[i][0]);
            item.put("line2",order_details[i][1]);
            item.put("line3",order_details[i][2]);
            item.put("line4",order_details[i][3]);
            item.put("line5",order_details[i][4]);
            list.add(item);
        }

        sa = new SimpleAdapter(getContext(), list,
                R.layout.multi_lines,
                new String[] {"line1", "line2", "line3", "line4", "line5"},
                new int[] {R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        lst.setAdapter(sa);
    }
}