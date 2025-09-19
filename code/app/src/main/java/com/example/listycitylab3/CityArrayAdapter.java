package com.example.listycitylab3;

import android.content.Context; // Assuming Context is used
import android.view.LayoutInflater; // Assuming LayoutInflater is used
import android.view.View; // Assuming View is used
import android.view.ViewGroup; // Assuming ViewGroup is used
import android.widget.ArrayAdapter; // This is the missing import
import android.widget.TextView; // Assuming TextView is used
import androidx.annotation.NonNull; // Assuming NonNull is used
import androidx.annotation.Nullable; // Assuming Nullable is used
import java.util.ArrayList; // Assuming ArrayList is used


public class CityArrayAdapter extends ArrayAdapter<City> {
    public CityArrayAdapter(Context context, ArrayList<City> cities) {
        super(context, 0, cities);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.content,parent, false);
        } else {
            view = convertView;
        }
        City city = getItem(position);
        TextView cityName = view.findViewById(R.id.city_text);
        TextView provinceName = view.findViewById(R.id.province_text);
        // Make sure City class has getName() and getProvince() methods
        if (city != null) {
            cityName.setText(city.getName());
            provinceName.setText(city.getProvince());
        }
        return view;
    }
}
