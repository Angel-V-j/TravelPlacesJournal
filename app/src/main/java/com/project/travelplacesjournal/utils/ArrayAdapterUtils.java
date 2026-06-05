package com.project.travelplacesjournal.utils;

import android.app.Activity;
import android.widget.ArrayAdapter;

import com.project.travelplacesjournal.data.entities.Adaptable;

import java.util.ArrayList;
import java.util.List;

public class ArrayAdapterUtils {
    public static <T extends Adaptable> ArrayAdapter<String> createStrArrAdapter(List<T> list, Activity activity) {
        List<String> strings = new ArrayList<>();
        for(T t : list)
            strings.add(t.getName());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout
                .simple_spinner_item, strings);
        adapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);

        return adapter;
    }
}
