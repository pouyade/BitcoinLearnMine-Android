package pro.pouyasoft.btclearnmine;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.nativead.NativeAd;


/**
 * Created by pouyadark on 8/3/18.
 */

public class ApplicationLoader extends Application {

    public static NativeAd nativeAd=null;
    private static Application instance;
    private static Context appicationContext;
    private static RequestQueue queue;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appicationContext = this;
        queue = Volley.newRequestQueue(instance);

    }

    public static Application getInstance() {
        return instance;
    }

    public static RequestQueue getRequestQueue() {
        return queue;
    }
}
