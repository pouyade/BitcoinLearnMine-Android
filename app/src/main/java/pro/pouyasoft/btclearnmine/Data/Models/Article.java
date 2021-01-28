package pro.pouyasoft.btclearnmine.Data.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by pouyadark on 8/3/18.
 */

public class Article {
    public int id;
    public String title;
    public String icon;
    public String subtitle;
    public String web;

    public Article(int id, String title, String icon, String subtitle, String web) {
        this.id = id;
        this.title = title;
        this.icon = icon;
        this.subtitle = subtitle;
        this.web = web;
    }
    public static Article FromJson(JSONObject json){
        try {
            return new Article(json.getInt("id"),
                    json.getString("title"),
                    json.getString("icon"),
                    json.getString("subtitle"),
                    json.getString("web"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ArrayList<Article> FromJsonArray(JSONArray jsonArray){
        ArrayList<Article> arr = new ArrayList<>();
        for (int i=0;i<jsonArray.length();i++){
            try {
                arr.add(Article.FromJson(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return arr;
    }
}
