package com.example.pad.view;

import android.app.Activity;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.util.Log;
import com.example.pad.AppConfig;
import com.example.pad.AppContext;
import com.example.pad.R;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 5/4/13
 * Time: 11:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class Preference extends PreferenceActivity{
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);

        EditTextPreference etp_server = (EditTextPreference)findPreference("server_path");
        etp_server.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(android.preference.Preference preference, Object o) {
                if (preference.getKey() == "server_path"){
                    AppConfig.getAppConfig(Preference.this).set(AppConfig.CONF_SERVER, (String)o);
                    AppContext.showLongToast(Preference.this, getString(R.string.save_success));
                }else if(preference.getKey() == "port"){

                    AppConfig.getAppConfig(Preference.this).set(AppConfig.CONF_PORT, (String)o);
                    AppContext.showLongToast(Preference.this, getString(R.string.save_success));
                }else{
                }
                return true;
            }
        });
    }
}