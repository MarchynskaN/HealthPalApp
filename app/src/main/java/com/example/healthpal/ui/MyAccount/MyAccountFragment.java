package com.example.healthpal.ui.MyAccount;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.healthpal.R;
import com.example.healthpal.databinding.FragmentMyAccountBinding;

public class MyAccountFragment extends Fragment {

    private FragmentMyAccountBinding binding;
    EditText etEmail, etFullName, etAddress, etPhone;
    Button btnUpdate, btnOrders, btnExit;
    String username;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MyAccountViewModel myAccountViewModel =
                new ViewModelProvider(this).get(MyAccountViewModel.class);

        binding = FragmentMyAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize your views
        etEmail = binding.editTextEmail;
        etFullName = binding.editTextFullName;
        etAddress = binding.editTextAddress;
        etPhone = binding.editTextPhone;
        btnUpdate = binding.buttonUpdate;
        btnOrders = binding.buttonOrders;
        btnExit = binding.buttonExit;

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");

        displayUserData(username);

        btnUpdate.setOnClickListener(view -> {
            String email = etEmail.getText().toString();
            String fullName = etFullName.getText().toString();
            String address = etAddress.getText().toString();
            String phone = etPhone.getText().toString();

            updateUserInDatabase(username, email, fullName, address, phone);
            Toast.makeText(getContext(), "User updated successfully", Toast.LENGTH_SHORT).show();
        });

        btnOrders.setOnClickListener(view -> {
            NavController navController = NavHostFragment.findNavController(MyAccountFragment.this);
            navController.navigate(R.id.action_myAccountFragment_to_orderDetailsFragment);
        });

        btnExit.setOnClickListener(view -> {
            SharedPreferences sharedpreferences = getActivity().getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.apply();
            // Implement navigation to Login Activity or Fragment
        });

        return root;
    }

    private void displayUserData(String username) {
        // Your database interaction here
    }

    private void updateUserInDatabase(String username, String email, String fullName, String address, String phone) {
        // Your database update logic here
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}