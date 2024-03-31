package com.example.healthpal.ui.BuyMedicine;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.healthpal.Database;
import com.example.healthpal.R;
import com.example.healthpal.databinding.FragmentBuyMedicineDetailsBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuyMedicineDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuyMedicineDetailsFragment extends Fragment {


    private FragmentBuyMedicineDetailsBinding binding;
    TextView tvPackageName,tvTotalCost;
    EditText edDetails;
    Button btnAddToCart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentBuyMedicineDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tvPackageName = binding.textViewODTitle;
        tvTotalCost = binding.textViewLDTotalCost;
        edDetails = binding.listViewOD;
        btnAddToCart = binding.buttonCartCheckout;
        edDetails.setKeyListener(null);

        // Check if arguments are available
        if (getArguments() != null) {
            tvPackageName.setText(getArguments().getString("title"));
            edDetails.setText(getArguments().getString("details"));
            tvTotalCost.setText("Total Cost: " + getArguments().getString("price") + "$");
        }

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedpreferences = getActivity().getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedpreferences.getString("username", "").toString();
                String product = tvPackageName.getText().toString();
                float price = Float.parseFloat(getArguments().getString("price", "0"));

                Database db = new Database(getActivity().getApplicationContext(),"healthpal", null, 1);

                if(db.checkCart(username,product)==1){
                    Toast.makeText(getActivity().getApplicationContext(), "Product Already Added", Toast.LENGTH_SHORT).show();
                }else{
                    db.addCart(username, product, price, "medicine");
                    Toast.makeText(getActivity().getApplicationContext(), "Record Inserted to Cart", Toast.LENGTH_SHORT).show();
                    //back to the list:
                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(R.id.action_BuyMedicineDetails_to_BuyMedicineFragment);
                }
            }
        });
        return root;
    }
}