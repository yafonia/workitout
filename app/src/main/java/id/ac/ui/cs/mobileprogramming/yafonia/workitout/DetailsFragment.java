package id.ac.ui.cs.mobileprogramming.yafonia.workitout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import id.ac.ui.cs.mobileprogramming.yafonia.workitout.databinding.FragmentDetailsBinding;


public class DetailsFragment extends Fragment {

    private FragmentDetailsBinding binding;
    Item item_null = null;
    MainActivity activity;


    public DetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);
        activity =(MainActivity) requireActivity();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        viewModel.getSelected().observe(getViewLifecycleOwner(), item -> {
            binding.takeExercise.setText(getString(R.string.take) + " " + item.getName() + " " + getString(R.string.program_ten_minutes));
            item_null = item;
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        activity.backButtonDisabled = false;
        viewModel.getSelected().observe(getViewLifecycleOwner(), item -> {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(item.getName());

        });
        Button add_to_calendar = (Button) binding.getRoot().findViewById(R.id.add_to_calendar);
        Button start_exercise = (Button) binding.getRoot().findViewById(R.id.start_exercise);
        add_to_calendar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                AddToCalendarFragment addtocalendarFragment = new AddToCalendarFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, addtocalendarFragment)
                        .addToBackStack("add_to_calendar")
                        .commit();

            }
        });

        start_exercise.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item_null != null) {
                    String[] set_of_exercise = item_null.getSet().split(",");
                    CountdownFragment countdownFragment = new CountdownFragment(set_of_exercise);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, countdownFragment)
                            .addToBackStack("countdown")
                            .commit();

                }
            }
        }));
    }



}

