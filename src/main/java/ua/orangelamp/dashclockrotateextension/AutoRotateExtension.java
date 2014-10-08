package ua.orangelamp.dashclockrotateextension;

import android.content.ContentResolver;
import android.provider.Settings;

import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;

public class AutoRotateExtension extends DashClockExtension {

    @Override
    protected void onUpdateData(int reason) {
        ContentResolver resolver = getContentResolver();
        boolean autoOrientationEnabled = getAutoOrientationEnabled(resolver);

        if (reason == UPDATE_REASON_MANUAL) {
            setAutoOrientationEnabled(resolver, !autoOrientationEnabled);
            autoOrientationEnabled = getAutoOrientationEnabled(resolver);
        }

        publishUpdate(new ExtensionData()
                .visible(true)
                .icon(autoOrientationEnabled ? R.drawable.ic_auto_rotate_on : R.drawable.ic_auto_rotate_off)
                .status(autoOrientationEnabled ? "ON" : "OFF")
                .expandedTitle("Auto-rotate screen - " + (autoOrientationEnabled ? "On" : "Off")));
    }

    public boolean getAutoOrientationEnabled(ContentResolver resolver) {
        return android.provider.Settings.System.getInt(resolver,
                Settings.System.ACCELEROMETER_ROTATION, 0) == 1;
    }

    public void setAutoOrientationEnabled(ContentResolver resolver, boolean enabled) {
        Settings.System.putInt(resolver,
                Settings.System.ACCELEROMETER_ROTATION, enabled ? 1 : 0);
    }
}
