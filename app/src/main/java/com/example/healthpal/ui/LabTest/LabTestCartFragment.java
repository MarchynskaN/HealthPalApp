package com.example.healthpal.ui.LabTest;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.healthpal.Database;
import com.example.healthpal.R;
import com.example.healthpal.databinding.FragmentLabTestCartBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class LabTestCartFragment extends Fragment {
    private FragmentLabTestCartBinding binding;
    HashMap<String,String> item;
    ArrayList list;
    SimpleAdapter sa;
    TextView tvTotal;
    ListView lst;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button dateButton, timeButton, btnCheckout;
    private String[][] packages = {};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLabTestCartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dateButton = binding.buttonCartDate;
        timeButton = binding.buttonCartTime;
        btnCheckout = binding.buttonCartCheckout;
        tvTotal = binding.textViewCartTotalCost;
        lst = binding.listViewOD;

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","").toString();

        Database db = new Database(getActivity().getApplicationContext(),"healthpal",null,1);

        float totalAmount = 0;
        ArrayList dbData = db.getCartData(username,"lab");
        //Toast.makeText(getApplicationContext(),"" + dbData, Toast.LENGTH_LONG).show();

        packages = new String[dbData.size()][];
        for(int i = 0; i < packages.length; i++){
            packages[i] = new String[5];
        }

        for(int i = 0;i < dbData.size(); i++){
            String arrData = dbData.get(i).toString();
            String[] strData = arrData.split(java.util.regex.Pattern.quote("$"));
            packages[i][0] = strData[0];
            packages[i][4] = "Cost : " + strData[1] + "$";
            totalAmount = totalAmount + Float.parseFloat(strData[1]);
        }
        tvTotal.setText("Total Cost : " + totalAmount);

        list = new ArrayList();
        for(int i = 0; i < packages.length; i++){
            item = new HashMap<String,String>();
            item.put("line1", packages[i][0]);
            item.put("line2", packages[i][1]);
            item.put("line3", packages[i][2]);
            item.put("line4", packages[i][3]);
            item.put("line5", packages[i][4]);
            list.add(item);
        }
        //below adapter has an issue in it's arguments
        sa = new SimpleAdapter(getActivity(),list,R.layout.multi_lines,
                new String[]{"line1","line2","line3","line4","line5"},
                new int[]{R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e}
        );
        lst.setAdapter(sa);

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("price", tvTotal.getText().toString());
                args.putString("date", dateButton.getText().toString());
                args.putString("time", timeButton.getText().toString());

                // Find NavController and navigate
                NavController navController = NavHostFragment.findNavController(LabTestCartFragment.this);
                navController.navigate(R.id.action_labTestCartFragment_to_labTestCheckout, args);
            }
        });

        initDatePicker();
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        initTimePicker();
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show();
            }
        });

        return root;
    }
    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1 + 1;
                dateButton.setText(i2 + "/" + i1 + "/" + i);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        //this has issue
        datePickerDialog = new DatePickerDialog(getActivity(), 0, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis() + 86400000);
    }

    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                timeButton.setText(i+":"+i1);
            }
        };

        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);
        //this has issue
        timePickerDialog = new TimePickerDialog(getActivity(), 0, timeSetListener, hour, minute, true);
    }
}