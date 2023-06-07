package pro.pouyasoft.btclearnmine.Ui.Activity;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import pro.pouyasoft.btclearnmine.R;
import pro.pouyasoft.btclearnmine.Setting.AppSetting;

public class ViewArticleActivity extends AppCompatActivity {

    Toolbar toolbar;
    Menu currentmenu;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        setContentView(R.layout.activity_view_article);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(bundle.getString("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webView = findViewById(R.id.webview);
        webView.loadUrl("file:///android_asset/web/" + bundle.getString("web"));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setBackgroundColor(AppSetting.getNightMode()?0xff141d26:0xffffffff);
        UpdateColor();
        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                updatewebviewtheme();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        currentmenu = menu;
        if(AppSetting.getNightMode()){
            getMenuInflater().inflate(R.menu.drawer_activity_dark_menu, menu);
        }else {
            getMenuInflater().inflate(R.menu.drawer_activity_normal_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_nightmode:
                AppSetting.setNightMode(true);
                UpdateColor();
                return true;

            case R.id.action_nightmodeoff:
                AppSetting.setNightMode(false);
                UpdateColor();
                return true;
        }
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }
    public void UpdateColor(){
        if(AppSetting.getNightMode()){
            //nightmood
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimarynight));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarknight));
            }
            if(currentmenu!=null) {
                currentmenu.removeItem(R.id.action_nightmode);
                getMenuInflater().inflate(R.menu.drawer_activity_dark_menu, currentmenu);
            }
        }else{
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            }
            if(currentmenu!=null) {
                currentmenu.removeItem(R.id.action_nightmodeoff);
                getMenuInflater().inflate(R.menu.drawer_activity_normal_menu, currentmenu);
            }
        }

        updatewebviewtheme();
    }
//    public void updatewebviewtheme(){
//        String str="";
//        if(!AppSetting.getNightMode()){
//            str="document.getElementsByTagName(\"body\")[0].style.background ='#ffffff';";
//            str=str + "document.getElementsByTagName(\"body\")[0].style.color ='#000000';";
//            webView.setBackgroundColor(0xffffffff);
//        }else{
//            str="document.getElementsByTagName(\"body\")[0].style.background ='#141d26';";
//            str=str + "document.getElementsByTagName(\"body\")[0].style.color ='#ffffff';";
//            webView.setBackgroundColor(0xff000000);
//        }
////        run(str);
//        webView.loadUrl("javascript:" + str);
//    }
        public void updatewebviewtheme(){

            if(!AppSetting.getNightMode()){
                run("nightoff();");
                webView.setBackgroundColor(0xffffffff);
            }else{
                run("nighton();");
                webView.setBackgroundColor(0xff141d26);
            }

        }
    public void run(final String scriptSrc) {
        webView.post(new Runnable() {
            @Override
            public void run() {
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//                    webView.evaluateJavascript(scriptSrc, null);
//                } else {
                    webView.loadUrl("javascript:" + scriptSrc);
//                }
            }
        });
    }
}
