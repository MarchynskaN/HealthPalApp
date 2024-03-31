package com.example.healthpal.ui.FindDoctor;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.healthpal.Database;
import com.example.healthpal.databinding.FragmentFindDoctorCheckoutBinding;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FindDoctorCheckoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindDoctorCheckoutFragment extends Fragment {
    private FragmentFindDoctorCheckoutBinding binding;

    EditText ed1,ed2,ed3,ed4;
    TextView tv;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button dateButton, timeButton, btnBook;
    String title, name, address, number, rate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFindDoctorCheckoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        tv = binding.textViewAppTitle;
        ed1 = binding.editTextAppFullName;
        ed2 = binding.editTextAppAddress;
        ed3 = binding.editTextAppContactNumber;
        ed4 = binding.editTextAppFees;
        dateButton = binding.buttonAppDate;
        timeButton = binding.buttonAppTime;
        btnBook = binding.buttonBookAppointment;

        ed1.setKeyListener(null);
        ed2.setKeyListener(null);
        ed3.setKeyListener(null);
        ed4.setKeyListener(null);

        // Check if arguments are available
        if (getArguments() != null) {
            title = getArguments().getString("title");
            name = getArguments().getString("name");
            address = getArguments().getString("address");
            number = getArguments().getString("number");
            rate = getArguments().getString("rate");

            tv.setText(title);
            ed1.setText(name);
            ed2.setText(address);
            ed3.setText(number);
            ed4.setText("Cons fees: " + rate + "$");
        }

        //datePicker
        initDatePicker();
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        //timePicker
        initTimePicker();
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show();
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedpreferences = getActivity().getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedpreferences.getString("username","").toString();
                Database db = new Database(getActivity().getApplicationContext(),"healthpal",null,1);
                if (db.checkAppointmentExists(username, title + " => " + name, address, number,
                        dateButton.getText().toString(), timeButton.getText().toString()) == 1) {
                    Toast.makeText(getActivity().getApplicationContext(),"Appointmnet already booked", Toast.LENGTH_LONG).show();
                } else {
                    db.addOrder(username,title + " => " + name, address, address,0,
                            dateButton.getText().toString(), timeButton.getText().toString(), Float.parseFloat(rate),"appointment");
                    Toast.makeText(getActivity().getApplicationContext(),"Your appointmnet is booked successfully", Toast.LENGTH_LONG).show();
                }
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