package pro.pouyasoft.btclearnmine.Helper;

import java.io.IOException;
import java.io.InputStream;

import pro.pouyasoft.btclearnmine.ApplicationLoader;

/**
 * Created by pouyadark on 8/3/18.
 */

public class JsonHelper {
    public static String loadDatabase() {
        String json = null;
        try {
            InputStream is = ApplicationLoader.getInstance().getAssets().open("database.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
