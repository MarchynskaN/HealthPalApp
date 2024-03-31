package com.example.healthpal.ui.FindDoctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.healthpal.R;
import com.example.healthpal.databinding.FragmentFindDoctorBinding;

public class FindDoctorFragment extends Fragment {

    private FragmentFindDoctorBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FindDoctorViewModel FIndDoctorViewModel =
                new ViewModelProvider(this).get(FindDoctorViewModel.class);

        binding = FragmentFindDoctorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        CardView familyPhysician = binding.cardFDFamilyPhysician;
        familyPhysician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("doctorType", "Family Physicians");
                NavController navController = NavHostFragment.findNavController(FindDoctorFragment.this);
                navController.navigate(R.id.action_findDoctorFragment_to_findDoctorDetailsFragment, args);
            }
        });
        CardView dietician = binding.cardFDDietician;
        dietician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("doctorType", "Dietician");
                NavController navController = NavHostFragment.findNavController(FindDoctorFragment.this);
                navController.navigate(R.id.action_findDoctorFragment_to_findDoctorDetailsFragment, args);
            }
        });
        CardView dentist = binding.cardFDDentist;
        dentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("doctorType", "Dentist");
                NavController navController = NavHostFragment.findNavController(FindDoctorFragment.this);
                navController.navigate(R.id.action_findDoctorFragment_to_findDoctorDetailsFragment, args);
            }
        });
        CardView surgeon = binding.cardFDSurgeon;
        surgeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("doctorType", "Surgeon");
                NavController navController = NavHostFragment.findNavController(FindDoctorFragment.this);
                navController.navigate(R.id.action_findDoctorFragment_to_findDoctorDetailsFragment, args);
            }
        });
        CardView cardiologist = binding.cardFDCardiologist;
        cardiologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("doctorType", "Cardiologist");
                NavController navController = NavHostFragment.findNavController(FindDoctorFragment.this);
                navController.navigate(R.id.action_findDoctorFragment_to_findDoctorDetailsFragment, args);
            }
        });

        CardView other = binding.cardFDOther;
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("doctorType", "Other");
                NavController navController = NavHostFragment.findNavController(FindDoctorFragment.this);
                navController.navigate(R.id.action_findDoctorFragment_to_findDoctorDetailsFragment, args);
            }
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}