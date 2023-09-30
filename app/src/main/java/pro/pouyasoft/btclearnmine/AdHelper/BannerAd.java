package pro.pouyasoft.btclearnmine.AdHelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import pro.pouyasoft.btclearnmine.Helper.LayoutHelper;
import pro.pouyasoft.btclearnmine.Setting.AppSettings;

public class BannerAd extends FrameLayout {

    private AdView adView;
    private AdEvents event;
    private String currentAdId;

    public interface AdEvents{
        void Loaded();
    }
    public void setListener(AdEvents event) {
        this.event=event;
    }

    public BannerAd(@NonNull Context context) {
        super(context);
        init();
    }

    public BannerAd(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        adView = new AdView(getContext());

        adView.setAdSize(AdSize.BANNER);

        addView(adView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,LayoutHelper.WRAP_CONTENT));


        adView.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                setVisibility(View.VISIBLE);
                adView.setVisibility(View.VISIBLE);
                event.Loaded();
            }
        });

        currentAdId = AppSettings.String(AppSettings.Key.ADMOB_BANNER_ID);
        loadbanner();

    }
    public void loadbanner(){
        setVisibility(View.GONE);
        adView.setVisibility(View.GONE);
        AdRequest adRequest = AdmobHelper.getNewRequest();
        adView.setAdUnitId(currentAdId);
        adView.loadAd(adRequest);
    }
}