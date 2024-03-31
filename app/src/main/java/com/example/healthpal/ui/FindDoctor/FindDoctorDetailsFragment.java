package com.example.healthpal.ui.FindDoctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthpal.R;
import com.example.healthpal.databinding.FragmentFindDoctorDetailsBinding;
import com.example.healthpal.databinding.ItemBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FindDoctorDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindDoctorDetailsFragment extends Fragment {
    private FragmentFindDoctorDetailsBinding binding;
    private TextView tv;
    String[][] doctor_details = {};
    SimpleAdapter sa;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FindDoctorViewModel findDoctorViewModel =
                new ViewModelProvider(this).get(FindDoctorViewModel.class);
        binding = FragmentFindDoctorDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        tv = binding.textViewCart;
        String doctorType = getArguments().getString("doctorType");
        tv.setText(doctorType);

        RecyclerView recyclerView = binding.recyclerviewFindDoctor;
        FindDoctorDetailsFragment.Adapter adapter = new FindDoctorDetailsFragment.Adapter(new FindDoctorDetailsFragment.OnItemClickListener() {
            @Override
            public void onItemClick(Doctor doctor) {
                Bundle args = new Bundle();
                args.putString("title", doctorType);
                args.putString("name", doctor.getName());
                args.putString("address", doctor.getAddress());
                args.putString("number", doctor.getNumber());
                args.putString("rate", doctor.getRate());

                // Find NavController and navigate
                NavController navController = NavHostFragment.findNavController(FindDoctorDetailsFragment.this);
                navController.navigate(R.id.action_findDoctorDetailsFragment_to_findDoctorCheckoutFragment, args);
            }
        });
        recyclerView.setAdapter(adapter);
        findDoctorViewModel.setSelectedType(doctorType);
        findDoctorViewModel.getDoctors().observe(getViewLifecycleOwner(), adapter::submitList);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //newly added
    public interface OnItemClickListener {
        void onItemClick(Doctor doctor);
    }

    private static class Adapter extends ListAdapter<Doctor, FindDoctorDetailsFragment.ViewHolder> {
        FindDoctorDetailsFragment.OnItemClickListener listener;
        public Adapter(FindDoctorDetailsFragment.OnItemClickListener listener) {
            super(new DiffUtil.ItemCallback<Doctor>() {
                @Override
                public boolean areItemsTheSame(@NonNull Doctor oldItem, @NonNull Doctor newItem) {
                    return oldItem.getName().equals(newItem.getName());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Doctor oldItem, @NonNull Doctor newItem) {
                    return oldItem.getName().equals(newItem.getName());
                }
            });
            this.listener = listener; //it's been initialized long ago
        }

        @NonNull
        @Override
        public FindDoctorDetailsFragment.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemBinding binding = ItemBinding.inflate(layoutInflater, parent, false);
            return new FindDoctorDetailsFragment.ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull FindDoctorDetailsFragment.ViewHolder holder, int position) {
            Doctor item = getItem(position);
            holder.bind(item, listener); //this line cannot recognize listener and suggesting to create the variable
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final TextView textViewLineB;
        private final TextView textViewLineC;
        private final TextView textViewLineD;
        private final TextView textViewRate;


        public ViewHolder(ItemBinding binding) {
            super(binding.getRoot());
            textViewName = binding.lineA;
            textViewLineB = binding.lineB;
            textViewLineC = binding.lineC;
            textViewLineD = binding.lineD;
            textViewRate = binding.lineE;
        }

        public void bind(Doctor doctor, FindDoctorDetailsFragment.OnItemClickListener listener) {
            textViewName.setText(doctor.getName());
            textViewLineB.setText(doctor.getAddress());
            textViewLineC.setText(doctor.getExperience());
            textViewLineD.setText(doctor.getNumber());
            textViewRate.setText("Cons Fees: " + doctor.getRate()+ "$");
            itemView.setOnClickListener(view -> listener.onItemClick(doctor));
        }
    }

}