package pro.pouyasoft.btclearnmine.Helper;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;

import pro.pouyasoft.btclearnmine.ApplicationLoader;

/**
 * Created by Pouya on 2/9/2017.
 */
public class AndroidUtilities {
    private static float density=0;

    public static int dp(float value) {
        if(density==0) {
            density = ApplicationLoader.getInstance().getResources().getDisplayMetrics().density;
        }
        if (value == 0) {
            return 0;
        }
        return (int) Math.ceil(density * value);
    }
    public static int getWidth(Context context){
        Display display = ((Activity)context). getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);

        float density  = ((Activity)context).getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels / density;
        float dpWidth  = outMetrics.widthPixels / density;
        return dp(dpWidth);
    }
}