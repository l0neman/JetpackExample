package io.l0neman.androidplatformexample.initializer;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ApAppInitializer {

    private static final String TAG = "ApAppInitializer";

    public static class GooglePlayInitializer implements Initializer<String> {

        @NonNull
        @Override
        public String create(@NonNull Context context) {
            Log.d(TAG, "init google play");
            return "play.service";
        }

        @NonNull
        @Override
        public List<Class<? extends Initializer<?>>> dependencies() {
            return Collections.emptyList();
        }
    }

    public static class GoogleAdInitializer implements Initializer<String> {

        @NonNull
        @Override
        public String create(@NonNull Context context) {
            Log.d(TAG, "init google ad");
            return "googleAD";
        }

        @NonNull
        @Override
        public List<Class<? extends Initializer<?>>> dependencies() {
            return Arrays.asList(GooglePlayInitializer.class);
        }
    }

}
