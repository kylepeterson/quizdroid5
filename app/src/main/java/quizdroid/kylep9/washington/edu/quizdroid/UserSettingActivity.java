package quizdroid.kylep9.washington.edu.quizdroid;

import android.content.SharedPreferences;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by iguest on 2/23/15.
 */
public class UserSettingActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.user_settings);
        SharedPreferences.OnSharedPreferenceChangeListener prefListener =
                new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                Preference preference = findPreference(key);
                if (preference instanceof EditTextPreference) {
                    EditTextPreference editTextPreference = (EditTextPreference) preference;
                    Editor settingsEditor = prefs.edit();
                    settingsEditor.putString(key , editTextPreference.getText());
                    settingsEditor.commit();
                }
            }
        };
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(prefListener);
    }


}
