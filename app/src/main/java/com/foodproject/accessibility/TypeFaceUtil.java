package com.foodproject.accessibility;


import android.content.Context;
import android.graphics.Typeface;

public class TypeFaceUtil {
    private static Context context;

    public void TypefaceUtil(Context context) {
        this.context = context;
    }

    public static Typeface getBoldFont() {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Bold.otf");
        return typeface;
    }

    public static Typeface getRegularFont() {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Regular.otf");
        return typeface;
    }
}
