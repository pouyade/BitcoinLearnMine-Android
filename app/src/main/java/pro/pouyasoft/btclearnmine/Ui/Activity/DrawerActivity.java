package pro.pouyasoft.btclearnmine.Ui.Activity;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.batch.android.Batch;
import com.batch.android.BatchActivityLifecycleHelper;
import com.batch.android.Config;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import pro.pouyasoft.btclearnmine.AdHelper.AdmobHelper;
import pro.pouyasoft.btclearnmine.ApplicationLoader;
import pro.pouyasoft.btclearnmine.BuildConfig;
import pro.pouyasoft.btclearnmine.Helper.AndroidUtilities;
import pro.pouyasoft.btclearnmine.R;
import pro.pouyasoft.btclearnmine.Ui.Fragment.LearnFragment;
import pro.pouyasoft.btclearnmine.Ui.Fragment.NightSupportFragment;
import pro.pouyasoft.btclearnmine.Setting.AppSettings;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Handler mHandler = new Handler();
    LearnFragment learnFragment;
    public static Toolbar toolbar;
    private int lastmenuid=-1;
    private Menu currentmenu;
    private NightSupportFragment currentFragment;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drawer2);
        learnFragment=new LearnFragment();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.inflateHeaderView(
                AppSettings.Bool(AppSettings.Key.NIGHT_MODE)?
                        R.layout.nav_header_dark:
                        R.layout.nav_header_normal
        );
        changeFramgent(learnFragment);
        UpdateColor();

        AppSettings.Update(() -> {
            if(AppSettings.Bool(AppSettings.Key.BATCH_ENABLED)) {
                Batch.setConfig(new Config(AppSettings.String(AppSettings.Key.BATCH_CODE)));
                ApplicationLoader.getInstance().registerActivityLifecycleCallbacks(new BatchActivityLifecycleHelper());
                Batch.onNewIntent(DrawerActivity.this, DrawerActivity.this.getIntent());
                Batch.onStart(DrawerActivity.this);
            }
        });

        LoadAndshow();
        LoadNatives();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        currentmenu = menu;
        if(AppSettings.Bool(AppSettings.Key.NIGHT_MODE)){
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
                AppSettings.Bool(AppSettings.Key.NIGHT_MODE,true);
                UpdateColor();
                break;

            case R.id.action_nightmodeoff:
                AppSettings.Bool(AppSettings.Key.NIGHT_MODE,false);

                UpdateColor();
                break;
        }
        //noinspection SimplifiableIfStatement
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(lastmenuid==id)return true;
        lastmenuid = id;
        if (id == R.id.learnandmine) {
           changeFramgent(learnFragment);
        }else if (id == R.id.share) {
            String ShareString = "<string name=\"shared_via\"><![CDATA[via <a href=\"https://play.google.com/store/apps/details?id="+BuildConfig.APPLICATION_ID+"\">great App for Bitcoin Learn you about Mining and ...</a>]]></string>";
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
            shareIntent.putExtra(Intent.EXTRA_TEXT, ShareString);
            shareIntent.setType("text/plain");
            startActivity(Intent.createChooser(shareIntent, "Share this Application with..."));
        }else if(id==R.id.RateApp){
            try {
                String packagename = BuildConfig.APPLICATION_ID;
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packagename)));
            }catch (Exception ex){
                Toast.makeText(this,"There is a Problem with connecting to Google PlayStore",Toast.LENGTH_LONG).show();
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void UpdateColor(){
        if(AppSettings.Bool(AppSettings.Key.NIGHT_MODE)){
            //nightmood
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimarynight));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarknight));
            }
            if(currentmenu!=null){
                currentmenu.removeItem(R.id.action_nightmode);
                getMenuInflater().inflate(R.menu.drawer_activity_dark_menu,currentmenu);

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
        if(navigationView!=null){
            navigationView.removeHeaderView(navigationView.getHeaderView(0));
            navigationView.inflateHeaderView(
                    AppSettings.Bool(AppSettings.Key.NIGHT_MODE)?
                            R.layout.nav_header_dark:
                            R.layout.nav_header_normal
            );
        }
        if(currentFragment!=null)currentFragment.UpdateColor();
    }
    public void changeFramgent(final NightSupportFragment fragment){
        final Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commitAllowingStateLoss();
                fragment.UpdateColor();
                currentFragment = fragment;

                // update the main content by replacing fragments
            }
        };

        // If mPendingRunnable is not null, then add to the message queue


        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
    }
    private void LoadAndshow() {
        if (AppSettings.Bool(AppSettings.Key.ADMOB_ENABLED)) {
            if(AppSettings.Bool(AppSettings.Key.ADMOB_ENABLED)){
                String admobbannerid = AppSettings.String(AppSettings.Key.ADMOB_INTERSTAL_ID);
                AdRequest adRequest = new AdRequest.Builder().build();
                InterstitialAd.load(this, admobbannerid, adRequest, new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                    }

                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        interstitialAd.show(DrawerActivity.this);
                    }
                });
            }
        }
    }
    public void LoadNatives() {
        if ((AppSettings.Bool(AppSettings.Key.ADMOB_ENABLED))
                && AppSettings.Bool(AppSettings.Key.SHOW_AD_NATIVES)
                && !AppSettings.String(AppSettings.Key.ADMOB_NATIVE_ID).isEmpty());
                final AdLoader adLoader = new AdLoader.Builder(DrawerActivity.this, AppSettings.String(AppSettings.Key.ADMOB_NATIVE_ID))
                        .forNativeAd(NativeAd -> {
                            ApplicationLoader.nativeAd=NativeAd;
                        })
                        .withAdListener(new AdListener() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                super.onAdFailedToLoad(loadAdError);
                            }

                            @Override
                            public void onAdClicked() {

                            }
                        })
                        .withNativeAdOptions(new NativeAdOptions.Builder()
                                // Methods in the NativeAdOptions.Builder class can be
                                // used here to specify individual options settings.
                                .build())
                        .build();
                adLoader.loadAd(AdmobHelper.getNewRequest());
        }

}
