/*
 * This is the source code of Telegram for Android v. 1.3.x.
 * It is licensed under GNU GPL v. 2 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 *
 * Copyright Nikolai Kudashov, 2013-2018.
 */

package pro.pouyasoft.btclearnmine.Ui.cells;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;


import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

import pro.pouyasoft.btclearnmine.BuildVars;
import pro.pouyasoft.btclearnmine.Helper.AndroidUtilities;
import pro.pouyasoft.btclearnmine.Helper.LayoutHelper;
import pro.pouyasoft.btclearnmine.R;
import pro.pouyasoft.btclearnmine.Setting.AppSettings;


public class NativeDialogCell extends FrameLayout {
    NativeAd unifiedNativeAd;
    NativeAdView unifiedNativeAdView;
    ImageView imgIcon;
    TextView txttitle;
    TextView txtMessage;
    TextView txtSponser;
    View devider;
    private CardView cardView;

    public NativeDialogCell(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
        cardView = new CardView(getContext());
        cardView.setCardBackgroundColor(AppSettings.Bool(AppSettings.Key.NIGHT_MODE)?getResources().getColor(R.color.cardbackgrounddark):0xffffffff);
        cardView.setUseCompatPadding(true);
        cardView.setPadding(0,0,0,0);
        
        unifiedNativeAdView = new NativeAdView(getContext());

        txttitle =new TextView(getContext());
        txttitle.setGravity(BuildVars.isRTL? Gravity.RIGHT: Gravity.LEFT);
        txttitle.setTextColor(AppSettings.Bool(AppSettings.Key.NIGHT_MODE)?0xffffffff:0xff000000);
        txttitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
//        txttitle.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        unifiedNativeAdView.addView(txttitle, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.TOP,(BuildVars.isRTL?50:70),8,(BuildVars.isRTL?70:50),0));

        txtMessage =new TextView(getContext());
        txtMessage.setGravity(BuildVars.isRTL? Gravity.RIGHT: Gravity.LEFT);
        txtMessage.setTextColor(AppSettings.Bool(AppSettings.Key.NIGHT_MODE)?0x88ffffff:0xff000000);
        txtMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP,11);
//        txtMessage.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        unifiedNativeAdView.addView(txtMessage, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.TOP,(BuildVars.isRTL?10:70),30,(BuildVars.isRTL?70:10),0));


        imgIcon = new ImageView(getContext());
        unifiedNativeAdView.addView(imgIcon, LayoutHelper.createFrame(56,56, Gravity.TOP| (BuildVars.isRTL? Gravity.RIGHT: Gravity.LEFT),7,11,7,0));

        txtSponser =new TextView(getContext());
        txtSponser.setTextColor(AppSettings.NotBool(AppSettings.Key.NIGHT_MODE)?0x88ffffff:0xff000000);
        GradientDrawable bg=new GradientDrawable();
        bg.setColor(0xff3949AA);
        bg.setCornerRadius(AndroidUtilities.dp(10));
        txtSponser.setBackground(bg);
        txtSponser.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
        txtSponser.setGravity(Gravity.CENTER);
//        txtSponser.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        unifiedNativeAdView.addView(txtSponser, LayoutHelper.createFrame(30, LayoutHelper.WRAP_CONTENT, Gravity.TOP|(BuildVars.isRTL? Gravity.LEFT: Gravity.RIGHT),4,4,10,10));



        cardView.addView(unifiedNativeAdView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,75));
        addView(cardView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,80));
        
    }

    public void setUnifiedNativeAd(NativeAd unifiedNativeAd) {
        if(unifiedNativeAd==null){
            setVisibility(GONE);
            return;
        }else{
            setVisibility(VISIBLE);
        }
        this.unifiedNativeAd = unifiedNativeAd;
        unifiedNativeAdView.setNativeAd(unifiedNativeAd);
        if(unifiedNativeAd.getIcon()!=null){
            imgIcon.setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
        }
        unifiedNativeAdView.setIconView(imgIcon);
        unifiedNativeAdView.setBodyView(txtMessage);
        unifiedNativeAdView.setHeadlineView(txttitle);
        unifiedNativeAdView.setAdvertiserView(txtSponser);
        unifiedNativeAdView.setClickConfirmingView(this);
        if(unifiedNativeAd.getHeadline()!=null) {
            if (unifiedNativeAd.getHeadline().length() > 34) {
                txttitle.setText(unifiedNativeAd.getHeadline().substring(0, 32) + "...");
            } else {
                txttitle.setText(unifiedNativeAd.getHeadline());
            }
        }
        if(unifiedNativeAd.getBody()!=null) {
            if (unifiedNativeAd.getBody().length() > 100) {
                txtMessage.setText(unifiedNativeAd.getBody().substring(0, 80) + "...");
            } else {
                txtMessage.setText(unifiedNativeAd.getBody());
            }
        }
        if(unifiedNativeAd.getIcon()!=null) {
            imgIcon.setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
        }
        txtSponser.setText("Ad");
        cardView.setCardBackgroundColor(AppSettings.Bool(AppSettings.Key.NIGHT_MODE)?getResources().getColor(R.color.cardbackgrounddark):0xffffffff);
        txttitle.setTextColor(AppSettings.Bool(AppSettings.Key.NIGHT_MODE)?0xffffffff:0xff000000);
        txtMessage.setTextColor(AppSettings.Bool(AppSettings.Key.NIGHT_MODE)?0x88ffffff:0xff000000);
        txtSponser.setTextColor(AppSettings.NotBool(AppSettings.Key.NIGHT_MODE)?0x88ffffff:0xff000000);

    }
}
