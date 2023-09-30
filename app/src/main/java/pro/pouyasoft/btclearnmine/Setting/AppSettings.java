package pro.pouyasoft.btclearnmine.Setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pro.pouyasoft.btclearnmine.ApplicationLoader;
import pro.pouyasoft.btclearnmine.Helper.JsonFileHelper;
import pro.pouyasoft.btclearnmine.Helper.RandomHelper;

import pro.pouyasoft.btclearnmine.BuildVars;

/**
 * Created by pouyadark on 8/4/18.
 */


public class AppSettings {

    public interface ServerSettingEvent{
        void Done(Map<String,String> sets);
        void Error();

    }

    public interface SettingEvent{
        void SettingInitiated();
    }

    public static void getSetting(final ServerSettingEvent event) {
        String urls=AppSettings.String(AppSettings.Key.SETTING_PATH);
        if(urls.length()==0){
            event.Error();
            return;
        }
        for (String url :urls.split("@@@!!")) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url + "?rnd=" + RandomHelper.getRandomNumber(), response -> {
                try {
                    Map<String,String> res= JsonFileHelper.jsonToMap(new JSONArray(response).getJSONObject(0));
                    if(res!=null) {
                        event.Done(res);
                    }else{
                        event.Error();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    event.Error();
                }

            }, error -> event.Error()) {
                @Override
                protected Map<String, String> getParams() {
                    HashMap<String, String> fields = new HashMap<>();
                    return fields;
                }
            };
            stringRequest.setShouldCache(false);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            ApplicationLoader.getRequestQueue().add(stringRequest);
        }
    }
    private static final String SPILLITOR = "@@@!!";

    public enum Key {
        NIGHT_MODE,


        SETTING_PATH,
        FIRE_BASE_TOKEN,
        APP_INSTALL_TIME,
        //ADMOB
        ADMOB_ENABLED,
        ADMOB_PUBLISHER_ID,
        SHOW_ADMOB_CONSENT_FORM,
        SHOW_ADMOB_CONSENT_FORM_PRIVACY_URL,
        SHOW_ADMOB_CONSENT_FORM_RESULT,
        ADMOB_DELAY,
        ADMOB_APP_ID,
        ADMOB_BANNER_ID,
        ADMOB_INTERSTAL_ID,
        ADMOB_NATIVE_ID,


        //ADS

        SHOW_AD_AFTER_RELOAD,
        SHOW_AD_AFTER_RELOAD_MINIMUM,
        SHOW_AD_IN_ARTICLE,
        SHOW_AD_NATIVES,
        SHOW_AD_NATIVES_SPACE,



        SHOW_PRIVACY_POLICY,
        IS_SHOWED_PRIVACY_POLICY,
        PRIVACY_POLICY_URL,




        //promotion
        SHOW_PROMOTION,
        PROMOTION_TITLE,
        PROMOTION_MESSAGE,
        PROMOTION_BUTTON,
        PROMOTION_ICON_URL,
        PROMOTION_SHOW_ALLTABS,
        PROMOTION_LINK,
        PROMOTION_SHOW_ALL_TABS,
        PROMOTION_GOOGLE_PLAY_PACKAGENAME,
        PROMOTION_MAX_VERSION_CODE,

        //promotion 2
        SHOW_PROMOTION_2,
        PROMOTION_TITLE_2,
        PROMOTION_MESSAGE_2,
        PROMOTION_BUTTON_2,
        PROMOTION_ICON_URL_2,
        PROMOTION_SHOW_ALLTABS_2,
        PROMOTION_LINK_2,
        PROMOTION_SHOW_ALL_TABS_2,
        PROMOTION_GOOGLE_PLAY_PACKAGENAME_2,
        PROMOTION_MAX_VERSION_CODE_2,




        //batch
        BATCH_ENABLED,
        BATCH_CODE,




        SHOW_RATEME_MENU,
        SHOW_RATING_DIALOG,
        RATING_DIALOG_SHOWED,
        OPENED_FOR_RATING,
        MAX_SHOW_RATING_DIALOG,
        RATING_DIALOG_MESSAGE,





        // Update Dialog
        UPDATE_ENABLED,
        UPDATE_MESSAGE,
        UPDATE_VERSION_CODE,
        UPDATE_URL,
        UPDATE_SHOWED_AT,
        UPDATE_SHOW_DELAY,
        UPDATE_SHOW_ICON_MENU,
        UPDATE_FORCE_VERSION,
        UPDATE_CANCELABLE,
        UPDATE_FORCE,

        // Rate App Dialog
        RATE_DIALOG_ENABLED,
        RATE_DIALOG_MESSAGE,
        RATE_DIALOG_CANCELABLE,
        RATE_DIALOG_SHOWED_AT,
        RATE_DIALOG_INSTALLTIME_CHECK,
        RATE_DIALOG_SHOW_DELAY,
        RATE_DIALOG_RATED,
        RATE_DIALOG_ICON_MENU,
        RATE_DIALOG_MINIMUM,

    }

    private static final String PREF_NAME = "livesettings";
    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;
    private static HashMap<String,Object> dictionary = new HashMap<>();
    private static Map<String, Object> defaultmap = new HashMap<String, Object>() {{


        put(Key.NIGHT_MODE.name(),BuildVars.NIGHT_MODE_DEFAULT);
        put(Key.SETTING_PATH.name(),BuildVars.SETTINGS_PATH);
        //firebase
        put(Key.FIRE_BASE_TOKEN.name(), null);

        ///admob
        put(Key.ADMOB_ENABLED.name(), BuildVars.ADMOB_ENABLED);
        put(Key.SHOW_ADMOB_CONSENT_FORM.name(), BuildVars.ADMOB_ENABLED);
        put(Key.SHOW_ADMOB_CONSENT_FORM_PRIVACY_URL.name(),"https://telegram.org/privacy");
        put(Key.SHOW_ADMOB_CONSENT_FORM_RESULT.name(),null);
        put(Key.ADMOB_PUBLISHER_ID.name(),"pub");
        put(Key.ADMOB_APP_ID.name(),  BuildVars.ADMOB_APPID);
        put(Key.ADMOB_DELAY.name(),0);
        put(Key.ADMOB_BANNER_ID.name(), BuildVars.ADMOB_BANNER_ID);
        put(Key.ADMOB_INTERSTAL_ID.name(), BuildVars.ADMOB_INTERSTAL_ID);
        put(Key.ADMOB_NATIVE_ID.name(), BuildVars.ADMOB_NATIVE_ID);


        ////ads
        put(Key.SHOW_AD_IN_ARTICLE.name(), BuildVars.SHOW_AD_IN_ARTICLE);
        put(Key.SHOW_AD_AFTER_RELOAD.name(), BuildVars.SHOW_AD_AFTER_RELOAD);
        put(Key.SHOW_AD_AFTER_RELOAD_MINIMUM.name(), BuildVars.SHOW_AD_AFTER_RELOAD_MINIMUM);
        put(Key.SHOW_AD_NATIVES.name(), BuildVars.SHOW_AD_NATIVES);
        put(Key.SHOW_AD_NATIVES_SPACE.name(),BuildVars.SHOW_AD_NATIVES_SPACE);

        //design
        put(Key.SHOW_PRIVACY_POLICY.name(),true);
        put(Key.PRIVACY_POLICY_URL.name(),"https://telegram.org/privacy");
        put(Key.IS_SHOWED_PRIVACY_POLICY.name(),false);


        //promotion
        put(Key.SHOW_PROMOTION.name(),false);
        put(Key.PROMOTION_BUTTON.name(),null);
        put(Key.PROMOTION_TITLE.name(),null);
        put(Key.PROMOTION_MESSAGE.name(),null);
        put(Key.PROMOTION_LINK.name(),null);
        put(Key.PROMOTION_SHOW_ALL_TABS.name(),true);
        put(Key.PROMOTION_ICON_URL.name(),null);
        put(Key.PROMOTION_GOOGLE_PLAY_PACKAGENAME.name(),null);
        put(Key.PROMOTION_MAX_VERSION_CODE.name(),0);

        //promotion  2
        put(Key.SHOW_PROMOTION_2.name(),false);
        put(Key.PROMOTION_BUTTON_2.name(),null);
        put(Key.PROMOTION_TITLE_2.name(),null);
        put(Key.PROMOTION_MESSAGE_2.name(),null);
        put(Key.PROMOTION_LINK_2.name(),null);
        put(Key.PROMOTION_SHOW_ALL_TABS_2.name(),true);
        put(Key.PROMOTION_ICON_URL_2.name(),null);
        put(Key.PROMOTION_GOOGLE_PLAY_PACKAGENAME_2.name(),null);
        put(Key.PROMOTION_MAX_VERSION_CODE_2.name(),0);



        //batch
        put(Key.BATCH_ENABLED.name(),BuildVars.BATCH_ENABLED);
        put(Key.BATCH_CODE.name(),BuildVars.BATCH_CODE);



        //SHOW_RATEME_MENU
        put(Key.SHOW_RATEME_MENU.name(),BuildVars.SHOW_RATEME_MENU);
        put(Key.SHOW_RATING_DIALOG.name(),BuildVars.SHOW_RATING_DIALOG);
        put(Key.OPENED_FOR_RATING.name(),0);
        put(Key.MAX_SHOW_RATING_DIALOG.name(),5);
        put(Key.RATING_DIALOG_MESSAGE.name(),null);
        put(Key.RATING_DIALOG_SHOWED.name(),false);



        // Update Dialog
        put(Key.UPDATE_ENABLED.name(),false);
        put(Key.UPDATE_MESSAGE.name(),null);
        put(Key.UPDATE_VERSION_CODE.name(),0);
        put(Key.UPDATE_URL.name(),null);
        put(Key.UPDATE_FORCE_VERSION.name(),false);
        put(Key.UPDATE_CANCELABLE.name(), true);
        put(Key.UPDATE_FORCE.name(), false);
        put(Key.UPDATE_SHOW_DELAY.name(), 12);
        put(Key.UPDATE_SHOW_ICON_MENU.name(), true);

        // Rate Dialog
        put(Key.RATE_DIALOG_INSTALLTIME_CHECK.name(),true);
        put(Key.RATE_DIALOG_ENABLED.name(),false);
        put(Key.RATE_DIALOG_MESSAGE.name(),null);
        put(Key.RATE_DIALOG_CANCELABLE.name(),true);
        put(Key.RATE_DIALOG_SHOW_DELAY.name(),12);
        put(Key.RATE_DIALOG_RATED.name(),false);
        put(Key.RATE_DIALOG_ICON_MENU.name(),true);
        put(Key.RATE_DIALOG_MINIMUM.name(),3);


    }};

    protected static void setup() {
        if(pref==null) {
            pref =  ApplicationLoader.getInstance().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            editor = pref.edit();
        }
    }
    //boolean
    protected static Boolean getBoolean(String key){
        if(dictionary.containsKey(key)){
            return (Boolean) dictionary.get(key);
        }
        setup();
        boolean val=pref.getBoolean(key, false);
        dictionary.put(key,val);
        return val;
    }
    protected static Boolean getBoolean(String key, boolean defaultvalue){
        if(dictionary.containsKey(key)){
            return (Boolean) dictionary.get(key);
        }
        setup();
        boolean val= pref.getBoolean(key, defaultvalue);
        dictionary.put(key,val);
        return val;
    }
    protected static void setBoolean(String key, boolean value) {
        dictionary.put(key,value);
        setup();
        editor.putBoolean(key, value);
        editor.commit();
    }

    //string

    protected static String getString(String key){
        if(dictionary.containsKey(key)){
            return (String) dictionary.get(key);
        }
        setup();
        String val = pref.getString(key, null);
        dictionary.put(key,val);
        return val;
    }
    protected static String getString(String key, String defaultvalue){
        if(dictionary.containsKey(key)){
            return (String) dictionary.get(key);
        }
        setup();
        String val = pref.getString(key, defaultvalue);
        dictionary.put(key,val);
        return val;
    }
    protected static void setString(String key, String value) {

        dictionary.put(key,value);
        setup();
        editor.putString(key, value);
        editor.commit();
    }

    //int
    protected static int getInt(String key){
        if(dictionary.containsKey(key)){
            return (int) dictionary.get(key);
        }
        setup();
        int val = pref.getInt(key, 0);
        dictionary.put(key,val);
        return val;
    }
    protected static int getInt(String key, int defaultvalue){
        if(dictionary.containsKey(key)){
            return (int) dictionary.get(key);
        }
        setup();
        int val = pref.getInt(key, defaultvalue);
        dictionary.put(key,val);
        return val;
    }
    public static void ClearAll(){
        setup();
        editor.clear();
        editor.commit();
    }
    protected static void setInt(String key, int i) {
        dictionary.put(key,i);
        setup();
        editor.putInt(key, i);
        editor.commit();
    }
    protected static void setLong(String key, long i) {
        dictionary.put(key,i);
        setup();
        editor.putLong(key, i);
        editor.commit();
    }
    protected static long getLong(String key, long defaultvalue){
        if(dictionary.containsKey(key)){
            return (long) dictionary.get(key);
        }
        setup();
        long val = pref.getLong(key, defaultvalue);
        dictionary.put(key,val);
        return val;
    }


    /*****Bool*****/
    public static boolean Bool(Key setting){
        Object res = defaultmap.get(setting.name());
        return getBoolean(setting.name(),res!=null?(Boolean)res:false);
    }
    public static boolean NotBool(Key setting){
        Object res = defaultmap.get(setting.name());
        return !getBoolean(setting.name(),res!=null?(Boolean)res:false);
    }
    public static void Bool(Key setting,boolean value){
        setBoolean(setting.name(),value);
    }
    public static boolean ToggleBool(Key setting){
        Bool(setting,!Bool(setting));
        return Bool(setting);
    }

    /*****INT*****/
    public static int Int(Key setting){
        Object res = defaultmap.get(setting.name());
        return getInt(setting.name(),res!=null?(int)res:0);
    }
    public static void Int(Key setting,int value){
        setInt(setting.name(),value);
    }
    public static void IntPlus(Key setting,int value){
        setInt(setting.name(),Int(setting)+value);
    }
    public static int IntInc(Key setting){
        setInt(setting.name(),Int(setting)+1);
        return(Int(setting));
    }
    public static void zero(Key setting){
        setInt(setting.name(),0);
    }

    /*****LONG*****/
    public static long Long(Key setting){
        Object res = defaultmap.get(setting.name());
        return getLong(setting.name(),res!=null?Long.parseLong(String.valueOf(res)):0);
    }
    public static void Long(Key setting,long value){
        setLong(setting.name(),value);
    }

    /*****STRING*****/
    public static String String(Key setting){
        Object res = defaultmap.get(setting.name());
        return getString(setting.name(),res!=null?(String)res:null);
    }
    public static void String(Key setting,String value){
        setString(setting.name(),value);
    }
    public static List<String> StrList(Key setting){
        Object res = defaultmap.get(setting.name());
        if(res==null){
            res = "";
        }
        return Arrays.asList(getString(setting.name(), (String) res).split(SPILLITOR));
    }
    public static void StrList(Key setting,List<String> values){
        setString(setting.name(), TextUtils.join(SPILLITOR,values));
    }
    public static void addToStrList(Key setting,String value){
        List<String> list = StrList(setting);
        list.add(value);
        StrList(setting,list);
    }
    public static void removeFromStrList(Key setting,String value){
        List<String> list = StrList(setting);
        list.remove(value);
        StrList(setting,list);
    }
    public static void clearStrList(Key setting){
        StrList(setting,new ArrayList<String>());
    }
    public static boolean BoolOR(Key setting1,Key setting2) {
        return Bool(setting1)||Bool(setting2);
    }

    public static void Update(final SettingEvent event) {
        if (String(Key.SETTING_PATH) != null && String(Key.SETTING_PATH).length() > 0) {
            getSetting(new ServerSettingEvent() {
                @Override
                public void Done(Map<String, String> sets) {
                    scan(sets, event);
                }

                @Override
                public void Error() {
                    event.SettingInitiated();
                }
            });
        } else {
            event.SettingInitiated();
        }
    }
    public static void scan(Map<String, String> sets, SettingEvent event) {
        setup();
        for(Map.Entry<String, String> entry : sets.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if(defaultmap.containsKey(key)){
                Object nwv = defaultmap.get(key);
                if(nwv instanceof Boolean){
                    setBoolean(key,Boolean.parseBoolean(value));
                }else if(nwv==null ||nwv instanceof String){
                    setString(key,String.valueOf(value));
                }else if(nwv instanceof Long){
                    setLong(key,Long.parseLong(value));
                }else if(nwv instanceof Integer){
                    setInt(key,Integer.parseInt(value));
                }
            }
        }
        if(event!=null) {
            event.SettingInitiated();
        }
    }
}
