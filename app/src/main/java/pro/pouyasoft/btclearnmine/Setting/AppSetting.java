package pro.pouyasoft.btclearnmine.Setting;

/**
 * Created by pouyadark on 8/4/18.
 */

public class AppSetting extends Setting {
    private static AppSetting instance;
    private static boolean displayedWellComeMessage;


    public static AppSetting getInstance() {
        if (instance == null) {
            instance = new AppSetting();
        }
        return instance;
    }

    public AppSetting() {
        setupSetting("appsetting");
        instance = this;
    }

    public static boolean getNightMode() {
        return getInstance().getBoolean("nightmode", false);
    }

    public static void setNightMode(boolean f) {
        getInstance().setBoolean("nightmode", f);
    }
}