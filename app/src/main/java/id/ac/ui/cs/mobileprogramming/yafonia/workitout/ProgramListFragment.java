package id.ac.ui.cs.mobileprogramming.yafonia.workitout;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.room.Room;

import java.util.List;
import java.util.ArrayList;
import java.util.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import id.ac.ui.cs.mobileprogramming.yafonia.workitout.databinding.FragmentProgramListBinding;
import id.ac.ui.cs.mobileprogramming.yafonia.workitout.databinding.ProgramListItemBinding;


public class ProgramListFragment extends Fragment {
    private FragmentProgramListBinding binding;
    private DetailsFragment detailsFragment = new DetailsFragment();


    public ProgramListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_program_list, container, false);
        return binding.getRoot();
}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        AppDatabase db = Room.databaseBuilder(requireContext(), AppDatabase.class,"programs").allowMainThreadQueries().build();
        List<Item> list = new ArrayList<>();
        viewModel.insertProgram(requireContext());
        viewModel.getAllPrograms(requireContext()).observe(this.getViewLifecycleOwner(), listItem -> {
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(listItem);
                GridLayoutManager grid = new GridLayoutManager(requireContext(),2);
                binding.recyclerView.setLayoutManager(grid);
                binding.recyclerView.setAdapter(adapter);
                adapter.setListener((v, position) -> {
                viewModel.setSelected(adapter.getItemAt(position));
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, detailsFragment)
                        .addToBackStack(null)
                        .commit();
                });
        });

    }

}

