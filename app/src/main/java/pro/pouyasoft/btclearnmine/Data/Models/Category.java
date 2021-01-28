package pro.pouyasoft.btclearnmine.Data.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by pouyadark on 8/3/18.
 */

public class Category {
    public String title;
    public ArrayList<Article> articles;

    public Category(String title, ArrayList<Article> articles) {
        this.title = title;
        this.articles = articles;
    }

    public static Category FromJson(JSONObject json) {
        try {
            return new Category(json.getString("title"),Article.FromJsonArray(json.getJSONArray("articles")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ArrayList<Category> FromJsonArray(JSONArray jsonArray){
        ArrayList<Category> arr = new ArrayList<>();
        for (int i=0;i<jsonArray.length();i++){
            try {
                arr.add(Category.FromJson(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return arr;
    }
}
