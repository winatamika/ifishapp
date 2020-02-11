package com.example.mick.dockandroidlogin.model;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by Mick on 2/7/2018.
 */

public class InputFilterMinMax implements InputFilter {
    //how to use it for editText totalCatch.setFilters(new InputFilter[]{new InputFilterMinMax(0, 59)});
    private int min;
    private int max;

    public InputFilterMinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        //noinspection EmptyCatchBlock
        /*
        //This only for 0 to max
        try {
            int input = Integer.parseInt(dest.subSequence(0, dstart).toString() + source + dest.subSequence(dend, dest.length()));
            if (isInRange(min, max, input))
                return null;
        } catch (NumberFormatException nfe) { }
        return "";
        */

        //This for min for max
        try {
            if(end==1)
                min=Integer.parseInt(source.toString());
            int input = Integer.parseInt(dest.toString() + source.toString());
            if (isInRange(min, max, input))
                return null;
        } catch (NumberFormatException nfe) {
        }
        return "";
    }

    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }

}
