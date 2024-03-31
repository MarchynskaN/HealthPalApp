package com.example.healthpal.ui.BuyMedicine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.healthpal.databinding.FragmentBuyMedicineBinding;
import com.example.healthpal.databinding.ItemBinding;
import com.example.healthpal.ui.LabTest.LabTest;
import com.example.healthpal.ui.LabTest.LabTestFragment;

public class BuyMedicineFragment extends Fragment {

    private FragmentBuyMedicineBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BuyMedicineViewModel buyMedicineViewModel =
                new ViewModelProvider(this).get(BuyMedicineViewModel.class);

        binding = FragmentBuyMedicineBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerviewBuyMedicine;
        Adapter adapter = new Adapter(new BuyMedicineFragment.OnItemClickListener() {
            @Override
            public void onItemClick(Medicine medicine) {
                Bundle args = new Bundle();
                args.putString("title", medicine.getTitle());
                args.putString("details", medicine.getDetails());
                args.putString("price", medicine.getPrice());

                // Find NavController and navigate
                NavController navController = NavHostFragment.findNavController(BuyMedicineFragment.this);
                navController.navigate(R.id.action_buyMedicineFragment_to_buyMedicineDetails, args);
            }
        });
        recyclerView.setAdapter(adapter);
        buyMedicineViewModel.getMeds().observe(getViewLifecycleOwner(), adapter::submitList);

        Button buttonCartCheckout = binding.buttonCartCheckout;
        buttonCartCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Find NavController and navigate
                NavController navController = NavHostFragment.findNavController(BuyMedicineFragment.this);
                navController.navigate(R.id.action_buyMedicineFragment_to_buyMedicineCart);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public interface OnItemClickListener {
        void onItemClick(Medicine medicine);
    }

    private static class Adapter extends ListAdapter<Medicine, BuyMedicineFragment.ViewHolder> {
        BuyMedicineFragment.OnItemClickListener listener;
        public Adapter(BuyMedicineFragment.OnItemClickListener listener) {
            super(new DiffUtil.ItemCallback<Medicine>() {
                @Override
                public boolean areItemsTheSame(@NonNull Medicine oldItem, @NonNull Medicine newItem) {
                    return oldItem.getTitle().equals(newItem.getTitle());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Medicine oldItem, @NonNull Medicine newItem) {
                    return oldItem.getTitle().equals(newItem.getTitle());
                }
            });
            this.listener = listener; //it's been initialized long ago
        }

        @NonNull
        @Override
        public BuyMedicineFragment.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemBinding binding = ItemBinding.inflate(layoutInflater, parent, false);
            return new BuyMedicineFragment.ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull BuyMedicineFragment.ViewHolder holder, int position) {
            Medicine item = getItem(position);
            holder.bind(item, listener);
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;
        private final TextView textViewLineB;
        private final TextView textViewLineC;
        private final TextView textViewLineD;
        private final TextView textViewPrice;


        public ViewHolder(ItemBinding binding) {
            super(binding.getRoot());
            textViewTitle = binding.lineA;
            textViewLineB = binding.lineB;
            textViewLineC = binding.lineC;
            textViewLineD = binding.lineD;
            textViewPrice = binding.lineE;
        }

        public void bind(Medicine medicine, BuyMedicineFragment.OnItemClickListener listener) {
            textViewTitle.setText(medicine.getTitle());
            textViewLineB.setText("");
            textViewLineC.setText("");
            textViewLineD.setText("");
            textViewPrice.setText("Total Cost: " + medicine.getPrice());
            itemView.setOnClickListener(view -> listener.onItemClick(medicine));
        }
    }

}