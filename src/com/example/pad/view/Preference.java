package com.example.pad.view;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.MenuItem;
import com.example.pad.AppConfig;
import com.example.pad.R;
import com.example.pad.common.StringUtils;
import com.example.pad.common.UIHelper;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 5/4/13
 * Time: 11:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class Preference extends PreferenceActivity {
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.top));
            getActionBar().setIcon(getResources().getDrawable(R.drawable.icon_zhuye));
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setTitle("PMP");
        }

        EditTextPreference etp_server = (EditTextPreference) findPreference("server_path");
        EditTextPreference port = (EditTextPreference) findPreference("port");

        etp_server.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(android.preference.Preference preference, Object o) {
                String input = (String) o;
                Log.d("eky", preference.getKey());
                if (StringUtils.isEmpty((input))) {
                    UIHelper.showLongToast(getApplicationContext(), getString(R.string.value_should_not_empty));
                    return false;
                }
                if (preference.getKey().equals("server_path")) {
                    if (StringUtils.isIPAddress(input)) {
                        AppConfig.getAppConfig(Preference.this).set(AppConfig.CONF_SERVER, (String) o);
                        UIHelper.showLongToast(Preference.this, getString(R.string.save_success));
                    } else {
                        UIHelper.showLongToast(Preference.this, getString(R.string.ip_address_not_valid));

                    }
                } else if (preference.getKey().equals("port")) {
                    Log.d("port", input);
                    if (StringUtils.isPort(input)) {
                        AppConfig.getAppConfig(Preference.this).set(AppConfig.CONF_PORT, (String) o);
                        UIHelper.showLongToast(Preference.this, getString(R.string.save_success));
                    } else {
                        UIHelper.showLongToast(Preference.this, getString(R.string.port_not_valid));
                    }
                } else {
                }
                return true;
            }
        });

        port.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(android.preference.Preference preference, Object newValue) {
                String input = (String) newValue;
                Log.d("eky", preference.getKey());
                if (StringUtils.isEmpty((input))) {
                    UIHelper.showLongToast(getApplicationContext(), getString(R.string.value_should_not_empty));
                    return false;
                }
                if (preference.getKey().equals("server_path")) {
                    if (StringUtils.isIPAddress(input)) {
                        AppConfig.getAppConfig(Preference.this).set(AppConfig.CONF_SERVER, input);
                        UIHelper.showLongToast(Preference.this, getString(R.string.save_success));
                    } else {
                        UIHelper.showLongToast(Preference.this, getString(R.string.ip_address_not_valid));

                    }
                } else if (preference.getKey().equals("port")) {
                    Log.d("port", input);
                    if (StringUtils.isPort(input)) {
                        AppConfig.getAppConfig(Preference.this).set(AppConfig.CONF_PORT, input);
                        UIHelper.showLongToast(Preference.this, getString(R.string.save_success));
                    } else {
                        UIHelper.showLongToast(Preference.this, getString(R.string.port_not_valid));
                    }
                } else {
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                Preference.this.finish();
                break;

            default:
                break;
        };

        return super.onOptionsItemSelected(item);
    }
}