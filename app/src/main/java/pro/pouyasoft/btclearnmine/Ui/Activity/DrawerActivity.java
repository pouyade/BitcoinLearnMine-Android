package pro.pouyasoft.btclearnmine.Ui.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import pro.pouyasoft.btclearnmine.BuildConfig;
import pro.pouyasoft.btclearnmine.R;
import pro.pouyasoft.btclearnmine.Setting.AppSetting;
import pro.pouyasoft.btclearnmine.Ui.Fragment.LearnFragment;
import pro.pouyasoft.btclearnmine.Ui.Fragment.NightSupportFragment;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Handler mHandler = new Handler();
    LearnFragment learnFragment;
    Toolbar toolbar;
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
                AppSetting.getNightMode()?
                        R.layout.nav_header_dark:
                        R.layout.nav_header_normal
        );
        changeFramgent(learnFragment);
        UpdateColor();
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
                break;

            case R.id.action_nightmodeoff:
                AppSetting.setNightMode(false);
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
        if(AppSetting.getNightMode()){
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
                    AppSetting.getNightMode()?
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
}
