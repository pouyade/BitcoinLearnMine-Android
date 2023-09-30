package pro.pouyasoft.btclearnmine.AdHelper;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.ads.consent.ConsentForm;
import com.google.ads.consent.ConsentFormListener;
import com.google.ads.consent.ConsentInfoUpdateListener;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;

import java.net.MalformedURLException;
import java.net.URL;

import pro.pouyasoft.btclearnmine.Setting.AppSettings;

public class AdmobHelper {
    private static final String TAG = AdmobHelper.class.getName();
    private static ConsentForm form = null;
    public static void checkForConsent(final Activity activity) {
        String publisherId = AppSettings.String(AppSettings.Key.ADMOB_PUBLISHER_ID);
        if(publisherId!=null&&!publisherId.isEmpty()) {
            ConsentInformation consentInformation = ConsentInformation.getInstance(activity);
            String[] publisherIds = {AppSettings.String(AppSettings.Key.ADMOB_PUBLISHER_ID)};
            consentInformation.requestConsentInfoUpdate(publisherIds, new ConsentInfoUpdateListener() {
                @Override
                public void onConsentInfoUpdated(ConsentStatus consentStatus) {
                    // User's consent status successfully updated.
                    Log.e(TAG, "consentStatus: "+consentStatus);

                    switch (consentStatus) {
                        case PERSONALIZED:
                            AppSettings.String(AppSettings.Key.SHOW_ADMOB_CONSENT_FORM_RESULT,"PERSONALIZED");
                            ConsentInformation.getInstance(activity).setConsentStatus(ConsentStatus.PERSONALIZED);
                            break;
                        case NON_PERSONALIZED:
                            AppSettings.String(AppSettings.Key.SHOW_ADMOB_CONSENT_FORM_RESULT,"NON_PERSONALIZED");
                            ConsentInformation.getInstance(activity).setConsentStatus(ConsentStatus.NON_PERSONALIZED);
                            break;
                        case UNKNOWN:
                            if (ConsentInformation.getInstance(activity).isRequestLocationInEeaOrUnknown()) {
                                requestConsent(activity);
                            } else {
                                AppSettings.String(AppSettings.Key.SHOW_ADMOB_CONSENT_FORM_RESULT,"PERSONALIZED");
                                ConsentInformation.getInstance(activity).setConsentStatus(ConsentStatus.PERSONALIZED);
                            }
                            break;
                        default:
                            break;
                    }
                }

                @Override
                public void onFailedToUpdateConsentInfo(String errorDescription) {
                    // User's consent status failed to update.
                    Log.e(TAG, "Requesting error: "+errorDescription);

                }
            });
        }
    }
    private static void requestConsent(final Activity activity) {
        URL privacyUrl = null;
        try {
            privacyUrl = new URL(AppSettings.String(AppSettings.Key.SHOW_ADMOB_CONSENT_FORM_PRIVACY_URL));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ConsentForm.Builder builder = new ConsentForm.Builder(activity, privacyUrl);
        builder = builder.withListener(new ConsentFormListener() {
            @Override
            public void onConsentFormLoaded() {
                Log.e(TAG, "Requesting Consent: onConsentFormLoaded");
                if (form == null) {
                    Log.e(TAG, "Consent form is null");
                }
                if (form != null) {
                    Log.e(TAG, "Showing consent form");
                    form.show();
                } else {
                    Log.e(TAG, "Not Showing consent form");
                }
            }

            @Override
            public void onConsentFormOpened() {
                Log.e(TAG, "Requesting Consent: onConsentFormOpened");
            }

            @Override
            public void onConsentFormClosed(ConsentStatus consentStatus, Boolean userPrefersAdFree) {
                if (userPrefersAdFree) {
                    Log.e(TAG, "Requesting Consent: User prefers AdFree");
                } else {
                    Log.e(TAG, "Requesting Consent: Requesting consent again");
                    switch (consentStatus) {
                        case PERSONALIZED:
                            AppSettings.String(AppSettings.Key.SHOW_ADMOB_CONSENT_FORM_RESULT,"PERSONALIZED");
                            ConsentInformation.getInstance(activity).setConsentStatus(ConsentStatus.PERSONALIZED);
                            break;
                        case NON_PERSONALIZED:
                        case UNKNOWN:
                            AppSettings.String(AppSettings.Key.SHOW_ADMOB_CONSENT_FORM_RESULT,"NON_PERSONALIZED");
                            ConsentInformation.getInstance(activity).setConsentStatus(ConsentStatus.NON_PERSONALIZED);
                            break;
                    }

                }
            }

            @Override
            public void onConsentFormError(String errorDescription) {
                Log.e(TAG, "Requesting Consent: onConsentFormError. Error - " + errorDescription);
                // Consent form error.
            }
        });
        builder = builder.withPersonalizedAdsOption();
        builder = builder.withNonPersonalizedAdsOption();
//        if(cannoads)
//        builder = builder.withAdFreeOption();
        form = builder.build();
        form.load();
    }

    public static AdRequest getNewRequest() {
        if(AppSettings.String(AppSettings.Key.SHOW_ADMOB_CONSENT_FORM_RESULT)!=null
                &&AppSettings.String(AppSettings.Key.SHOW_ADMOB_CONSENT_FORM_RESULT).equalsIgnoreCase("NON_PERSONALIZED".toLowerCase()))
        {
            AdRequest.Builder builder = new AdRequest.Builder();
            Bundle extras = new Bundle();
            extras.putString("npa", "1");
            builder.addNetworkExtrasBundle(AdMobAdapter.class, extras);
            AdRequest request = builder.build();
            return request;
        }else{
            return new AdRequest.Builder().build();
        }
    }
}
