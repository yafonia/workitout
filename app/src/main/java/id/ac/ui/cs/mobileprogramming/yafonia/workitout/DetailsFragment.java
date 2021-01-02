package id.ac.ui.cs.mobileprogramming.yafonia.workitout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
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
                if(!isConnected()) {
                    showCustomDialog();
                }
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
                    String program_name = item_null.getName();
                    CountdownFragment countdownFragment = new CountdownFragment(set_of_exercise, program_name);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, countdownFragment)
                            .addToBackStack("countdown")
                            .commit();
                }
            }
        }));
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiConn != null && wifiConn.isConnected()) || mobileConn != null && mobileConn.isConnected()) {
            return true;
        }
        else {
            return false;
        }
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Please connect to the Internet to proceed further")
                .setCancelable(true)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getFragmentManager().popBackStack();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


}

