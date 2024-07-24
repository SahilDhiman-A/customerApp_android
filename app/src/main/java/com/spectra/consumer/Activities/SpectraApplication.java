package com.spectra.consumer.Activities;

import android.app.Application;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.HashMap;
import java.util.Map;
import activeandroid.ActiveAndroid;
import static com.spectra.consumer.Utils.Constant.EVENT.EVENT_TYPE;

public class SpectraApplication extends Application {
    FirebaseAnalytics mFirebaseAnalytics;
    private static SpectraApplication mInstance;
    public HashMap<String, String> stringStringHashMap = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        mInstance = this;
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static synchronized SpectraApplication getInstance() {
        return mInstance;
    }

    public void postEvent(String category, String name, String action, String canId) {
        //TODO SP3
        try {
            Bundle bundle = new Bundle();
            bundle.putString("Category", category);
            bundle.putString("Action", action);
            bundle.putString("CanID", canId);
            bundle.putString("EventType", EVENT_TYPE);
            if (stringStringHashMap != null && stringStringHashMap.size() > 0) {
                for (Map.Entry mapElement : stringStringHashMap.entrySet()) {
                    String key = (String) mapElement.getKey();
                    String value = (String) mapElement.getValue();
                    bundle.putString(key, value);
                }
            }
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(getApplicationContext());
            mFirebaseAnalytics.logEvent(name, bundle);
            stringStringHashMap = new HashMap<>();
        } catch (Exception e)
         {
            e.printStackTrace();
        }
    }

    public void addKey(String key, String value) {
        //TODO SP3
        stringStringHashMap.put(key, value);
    }
}
