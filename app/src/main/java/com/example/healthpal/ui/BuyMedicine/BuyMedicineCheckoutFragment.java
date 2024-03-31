package com.example.healthpal.ui.BuyMedicine;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.healthpal.Database;
import com.example.healthpal.R;
import com.example.healthpal.databinding.FragmentBuyMedicineCheckoutBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuyMedicineCheckoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuyMedicineCheckoutFragment extends Fragment {

    private FragmentBuyMedicineCheckoutBinding binding;
    EditText edname,edaddress,edcontact,edpincode;
    Button btnBooking;
    String[] price;
    String date, time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentBuyMedicineCheckoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        edname = binding.editTextLTBFullName;
        edaddress = binding.editTextLTBAddress;
        edcontact = binding.editTextLTBContact;
        edpincode = binding.editTextLTBPincode;
        btnBooking = binding.buttonLTBBooking;

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","").toString();

        // Check if arguments are available
        if (getArguments() != null) {
            price = getArguments().getString("price").split(java.util.regex.Pattern.quote(":"));
            date = getArguments().getString("date");
            time = getArguments().getString("time");
        }
        displayUserData(username);

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "").toString();

                Database db = new Database(getActivity().getApplicationContext(), "healthpal", null, 1);
                try {
                    db.addOrder(username, edname.getText().toString(), edaddress.getText().toString(),
                            edcontact.getText().toString(), Integer.parseInt(edpincode.getText().toString()),
                            date.toString(), time.toString(), Float.parseFloat(price[1].toString()), "medicine");
                    db.removeCart(username, "medicine");
                    Toast.makeText(getActivity().getApplicationContext(), "Your booking is done successfully", Toast.LENGTH_LONG).show();
                    //back to the list:
                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(R.id.action_buyMedicineCheckoutFragment_to_buyMedicine);
                } catch (Exception e){
                    Log.e("MyHealthPal", "Error during database operation: " + e.getMessage());
                    Toast.makeText(getActivity().getApplicationContext(), "Error during database operation", Toast.LENGTH_LONG).show();
                }
            }
        });
        // Inflate the layout for this fragment
        return root;
    }
    private void displayUserData(String username) {
        Database db = new Database(getActivity().getApplicationContext(), "healthpal", null, 1);
        ArrayList<String> userData = db.getUserData(username);

        if (!userData.isEmpty()) {
            String[] userFields = userData.get(0).split("\\$");
            if(!userFields[1].equals(" ")) {
                edname.setText(userFields[1]);
            }
            if(!userFields[2].equals(" ")) {
                edaddress.setText(userFields[2]);
            }
            if(!userFields[3].equals(" ")) {
                edcontact.setText(userFields[3]);
            }
        }
    }
}