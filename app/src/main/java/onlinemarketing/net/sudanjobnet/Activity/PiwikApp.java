package onlinemarketing.net.sudanjobnet.Activity;

import android.app.Application;
import android.os.StrictMode;

import org.piwik.sdk.Piwik;
import org.piwik.sdk.Tracker;
import org.piwik.sdk.TrackerConfig;
import org.piwik.sdk.extra.DownloadTracker;
import org.piwik.sdk.extra.PiwikApplication;
import org.piwik.sdk.extra.TrackHelper;

import timber.log.Timber;

/**
 * Created by muawia.ibrahim on 3/29/2016.
 */
public class PiwikApp extends PiwikApplication {
    private Tracker mPiwikTracker;

//    public synchronized Tracker getTracker() {
//        if (mPiwikTracker != null) return mPiwikTracker;
//        mPiwikTracker = Piwik.getInstance(this).newTracker(new TrackerConfig("http://www.sudanjob.net/piwik/piwik.php", 1);
//        return mPiwikTracker;
//    }

    @Override
    public TrackerConfig onCreateTrackerConfig() {
        return TrackerConfig.createDefault("http://www.sudanjob.net/piwik/piwik.php", 5);
    }
    //...
    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());
        initPiwik();
    }


    private void initPiwik() {
        // Print debug output when working on an app.
        Timber.plant(new Timber.DebugTree());

        // When working on an app we don't want to skew tracking results.
//        getPiwik().setDryRun(BuildConfig.DEBUG);

        // If you want to set a specific userID other than the random UUID token, do it NOW to ensure all future actions use that token.
        // Changing it later will track new events as belonging to a different user.
        // String userEmail = ....preferences....getString
        // getTracker().setUserId(userEmail);

        // Track this app install, this will only trigger once per app version.
        // i.e. "http://com.piwik.demo:1/185DECB5CFE28FDB2F45887022D668B4"
        TrackHelper.track().download().identifier(new DownloadTracker.Extra.ApkChecksum(this)).with(getTracker());
        // Alternative:
        // i.e. "http://com.piwik.demo:1/com.android.vending"
        // getTracker().download();
    }
}