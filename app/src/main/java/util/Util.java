package util;

import java.text.DecimalFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by Hemant on 27-07-2017.
 */

public class Util {

    public static String formatnumber(int value){
        java.text.DecimalFormat formatter=new DecimalFormat("#,###,###");
        String formatted=formatter.format(value);

        return formatted;
    }
}
