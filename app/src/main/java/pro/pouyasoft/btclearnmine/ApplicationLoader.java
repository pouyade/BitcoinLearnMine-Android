package pro.pouyasoft.btclearnmine;

import android.app.Application;
import android.content.Context;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import io.fabric.sdk.android.Fabric;

/**
 * Created by pouyadark on 8/3/18.
 */

public class ApplicationLoader extends Application {

    private static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Answers(), new Crashlytics());
        instance = this;

    }

    public static Context getInstance() {
        return instance;
    }
}
