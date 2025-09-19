package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    interface AddCityDialogListener {
        void addCity(City city);
        void cityEdited();
    }
    private AddCityDialogListener listener;
    private City selectedCityToedit;

    public AddCityFragment() {
    }
    public static AddCityFragment newInstance(City city) {
        AddCityFragment fragment = new AddCityFragment();
        Bundle args = new Bundle();
        args.putSerializable("city to edit", city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        String dialogTitle = "Add City";
        String positiveButtonText = "Add";

        if (getArguments() != null && getArguments().containsKey("city to edit")) {
            selectedCityToedit = (City) getArguments().getSerializable("city to edit");
            if (selectedCityToedit != null) {
                dialogTitle = "Edit City";
                positiveButtonText = "Save Changes";
                editCityName.setText(selectedCityToedit.getName());
                editProvinceName.setText(selectedCityToedit.getProvince());
            }
        }

        builder.setView(view)
                .setTitle(dialogTitle)
                .setNegativeButton("Cancel", null)
                .setPositiveButton(positiveButtonText, (dialog, which) -> {
                    String cityName = editCityName.getText().toString().trim();
                    String provinceName = editProvinceName.getText().toString().trim();

                    if (cityName.isEmpty() || provinceName.isEmpty()) {
                        if (cityName.isEmpty()) {
                            editCityName.setError("City name cannot be empty");
                        }
                        if (provinceName.isEmpty()) {
                            editProvinceName.setError("Province name cannot be empty");
                        }
                        return; // Prevent dialog from closing or processing further
                    }

                    if (selectedCityToedit != null) {
                        selectedCityToedit.setName(cityName);
                        selectedCityToedit.setProvince(provinceName);
                        listener.cityEdited();
                    } else {
                        listener.addCity(new City(cityName, provinceName));
                    }
                });
        return builder.create();
    }
}