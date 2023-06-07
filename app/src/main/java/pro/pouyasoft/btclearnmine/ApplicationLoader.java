package pro.pouyasoft.btclearnmine;

import android.app.Application;
import android.content.Context;


/**
 * Created by pouyadark on 8/3/18.
 */

public class ApplicationLoader extends Application {

    private static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }

    public static Context getInstance() {
        return instance;
    }
}
