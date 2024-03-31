package com.example.healthpal.ui.LabTest;

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
import com.example.healthpal.databinding.FragmentLabTestBinding;
import com.example.healthpal.databinding.ItemBinding;

/**
 * Fragment that demonstrates a responsive layout pattern where the format of the content
 * transforms depending on the size of the screen. Specifically this Fragment shows items in
 * the [RecyclerView] using LinearLayoutManager in a small screen
 * and shows items using GridLayoutManager in a large screen.
 */
public class LabTestFragment extends Fragment {

    private FragmentLabTestBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LabTestViewModel labTestViewModel =
                new ViewModelProvider(this).get(LabTestViewModel.class);

        binding = FragmentLabTestBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerviewLabTest;
        Adapter adapter = new Adapter(new OnItemClickListener() {
            @Override
            public void onItemClick(LabTest labTest) {
                Bundle args = new Bundle();
                args.putString("title", labTest.getTitle());
                args.putString("details", labTest.getDetails());
                args.putString("price", labTest.getPrice());

                // Find NavController and navigate
                NavController navController = NavHostFragment.findNavController(LabTestFragment.this);
                navController.navigate(R.id.action_labTestFragment_to_labTestDetails, args);
            }
        });
        recyclerView.setAdapter(adapter);
        labTestViewModel.getTests().observe(getViewLifecycleOwner(), adapter::submitList);

        Button buttonCartCheckout = binding.buttonCartCheckout;
        buttonCartCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Find NavController and navigate
                NavController navController = NavHostFragment.findNavController(LabTestFragment.this);
                navController.navigate(R.id.action_labTestFragment_to_labTestCart);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //newly added
    public interface OnItemClickListener {
        void onItemClick(LabTest labTest);
    }


    private static class Adapter extends ListAdapter<LabTest, ViewHolder> {
        OnItemClickListener listener;
        public Adapter(OnItemClickListener listener) {
            super(new DiffUtil.ItemCallback<LabTest>() {
                @Override
                public boolean areItemsTheSame(@NonNull LabTest oldItem, @NonNull LabTest newItem) {
                    return oldItem.getTitle().equals(newItem.getTitle());
                }

                @Override
                public boolean areContentsTheSame(@NonNull LabTest oldItem, @NonNull LabTest newItem) {
                    return oldItem.getTitle().equals(newItem.getTitle());
                }
            });
            this.listener = listener; //it's been initialized long ago
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemBinding binding = ItemBinding.inflate(layoutInflater, parent, false);
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            LabTest item = getItem(position);
            holder.bind(item, listener); //this line cannot recognize listener and suggesting to create the variable
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

        public void bind(LabTest labTest, OnItemClickListener listener) {
            textViewTitle.setText(labTest.getTitle());
            textViewLineB.setText("");
            textViewLineC.setText("");
            textViewLineD.setText("");
            textViewPrice.setText("Total Cost: " + labTest.getPrice());
            itemView.setOnClickListener(view -> listener.onItemClick(labTest));
        }
    }
}