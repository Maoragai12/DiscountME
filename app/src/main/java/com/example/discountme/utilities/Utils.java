package com.example.discountme.utilities;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.RawRes;

import java.io.InputStream;
import java.util.Scanner;

public class Utils {
    public static String readRawResource(@RawRes int res, Context context) {
        return readStream(context.getResources().openRawResource(res));
    }

    private static String readStream(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static void setItems(Spinner spinner, String[] items, int itemLayout, int dropDownLayout) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                spinner.getContext(),
                itemLayout,
                items
        );

        adapter.setDropDownViewResource(dropDownLayout);

        spinner.setAdapter(adapter);
    }
}
