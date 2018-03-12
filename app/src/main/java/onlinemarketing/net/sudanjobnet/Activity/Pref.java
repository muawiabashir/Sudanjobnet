package onlinemarketing.net.sudanjobnet.Activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import onlinemarketing.net.sudanjobnet.R;


/**
 * Created by muawia.ibrahim on 4/11/2016.
 */
public class Pref extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

    }
}
