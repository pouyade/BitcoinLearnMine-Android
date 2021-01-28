package pro.pouyasoft.btclearnmine.Data.DBManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pro.pouyasoft.btclearnmine.Data.Models.Category;
import pro.pouyasoft.btclearnmine.Helper.JsonHelper;

/**
 * Created by pouyadark on 8/3/18.
 */

public class Database {
    static ArrayList<Category> categories = new ArrayList<>();
    public static ArrayList<Category> getCategorys(){
        if(categories.size()==0){
            try {
                categories.addAll(Category.FromJsonArray(new JSONObject(JsonHelper.loadDatabase()).getJSONArray("tabs")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return categories;
    }
}
