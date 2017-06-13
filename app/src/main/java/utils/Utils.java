package utils;

import android.icu.text.DecimalFormat;

/**
 * Created by User on 6/13/2017.
 */

public class Utils {
    public static String formatNumber(int value){
        DecimalFormat format=new DecimalFormat("#,###,####");
        String formatted=format.format(value);
        return formatted;
    }
}
