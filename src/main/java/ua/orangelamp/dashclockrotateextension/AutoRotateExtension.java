package ua.orangelamp.dashclockrotateextension;

import android.content.ContentResolver;
import android.provider.Settings;

import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;

public class AutoRotateExtension extends DashClockExtension {

    public static final String ACTION_EVENT = "ua.orangelamp.dashclockrotateextension.ACTION_EVENT";


    @Override
    protected void onInitialize(boolean isReconnect) {
        super.onInitialize(isReconnect);
        if (!isReconnect) {
////Listener for change battery: I prefer listen only for power change.
////You can change it with ACTION_BATTERY_CHANGED
//            IntentFilter filter=new IntentFilter();
//            filter.addAction(Intent.ACTION_POWER_CONNECTED);
//            filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
//            getApplicationContext().registerReceiver(mBatteryReceiver,
//                    filter);
////User can choose for ACTION_BATTERY_CHANGED
//            registerChangeReceiver();
        }
    }

    @Override
    protected void onUpdateData(int reason) {

////Register and Unregister changeReceiver
//        registerChangeReceiver();

        getData();

        publishUpdateExtensionData();
    }

    private void getData() {

    }

    private void publishUpdateExtensionData() {
        ContentResolver resolver = getContentResolver();

        // Publish the extension data update.
        publishUpdate(new ExtensionData()
                .visible(true)
                .icon(getAutoOrientationEnabled(resolver) ? R.drawable.ic_auto_rotate_on : R.drawable.ic_launcher)
                .status(getAutoOrientationEnabled(resolver) ? "ON" : "OFF")
                .expandedTitle("Auto-rotate screen - " + (getAutoOrientationEnabled(resolver) ? "On" : "Off")));

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
