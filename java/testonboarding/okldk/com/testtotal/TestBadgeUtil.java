package testonboarding.okldk.com.testtotal;

import java.util.List;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.os.IBinder;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import testonboarding.okldk.com.testtotal.*;

// provides static utility methods to set "badge count" on Launcher (by Samsung, LG).
// Currently, it's working from Android 4.0.
// But some devices, which are released from the manufacturers, are not working.

public class TestBadgeUtil extends Service {
    private static final String TAG = "TestBadge";
    private static final String ACTION_BADGE_COUNT_UPDATE = "android.intent.action.BADGE_COUNT_UPDATE";
    private static final String EXTRA_BADGE_COUNT = "badge_count";  // TO_DO
    private static final String EXTRA_BADGE_COUNT_PACKAGE_NAME = "badge_count_package_name";
    private static final String EXTRA_BADGE_COUNT_CLASS_NAME = "badge_count_class_name";

    // Usage
    private static final int NULL = 0;
    private static int count;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // TODO service
        Log.d(TAG, "Enter - onCreate --SimpleServiceMissedCall ()");
        count = MissedCallCountNumber();
        if (count == NULL) {
            resetBadgeCount(getApplication());
        } else {
            setBadgeCount(getApplication(), count);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /*************************************************************************
     * Set badge count
     *
     * @param context The context of the application package.
     * @param count Badge count to be set
     ************************************************************************/
    public static void setBadgeCount(Context context, int count) {
        Intent badgeIntent = new Intent(ACTION_BADGE_COUNT_UPDATE);
        badgeIntent.putExtra(EXTRA_BADGE_COUNT, count); // TO DO
        badgeIntent.putExtra(EXTRA_BADGE_COUNT_PACKAGE_NAME, context.getPackageName());
        badgeIntent.putExtra(EXTRA_BADGE_COUNT_CLASS_NAME, getLauncherClassName(context));
        context.sendBroadcast(badgeIntent);
        Log.d(TAG, "setBadgeCount - sendBroadcast");

    }

    /*************************************************************************
     * Reset badge count. The badge count is set to "0"
     *
     * @param context The context of the application package.
     *************************************************************************/
    public static void resetBadgeCount(Context context) {
        setBadgeCount(context, 0);
        Log.d(TAG, "resetBadgeCount - sendBroadcast");
    }

    /**
     * Retrieve launcher activity name of the application from the context
     *
     * @param context The context of the application package.
     * @return launcher activity name of this application. From the
     *         "android:name" attribute.
     */
/*
    private static String getLauncherClassName(Context context) {
        PackageManager packageManager = context.getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        // To limit the components this Intent will resolve to, by setting an
        // explicit package name.
        intent.setPackage(context.getPackageName());
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        // All Application must have 1 Activity at least.
        // Launcher activity must be found!
        ResolveInfo info = packageManager
                .resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);

        // get a ResolveInfo containing ACTION_MAIN, CATEGORY_LAUNCHER
        // if there is no Activity which has filtered by CATEGORY_DEFAULT
        if (info == null) {
            info = packageManager.resolveActivity(intent, 0);
        }

        return info.activityInfo.name;
    }
*/
    public static String getLauncherClassName(Context context) {
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
            if (pkgName.equalsIgnoreCase(context.getPackageName())) {
                String className = resolveInfo.activityInfo.name;
                return className;
            }
        }
        return null;
    }


    public int MissedCallCountNumber() {
        int number;
        try {
            String[] projection3 = {CallLog.Calls.CACHED_NAME, CallLog.Calls.CACHED_NUMBER_LABEL, CallLog.Calls.TYPE, CallLog.Calls.NEW};
            String where3 = CallLog.Calls.TYPE + "=" + CallLog.Calls.MISSED_TYPE + " AND " + CallLog.Calls.NEW + "=1";

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
              //  ActivityCompat.requestPermissions(this,
              // new String[]{Manifest.permission.READ_CALL_LOG}, 0);
            return Integer.parseInt(null); // TODO DK
            }
            Cursor c3 = getContentResolver().query(
                    CallLog.Calls.CONTENT_URI,
                    projection3,
                    where3,
                    null,
                    null);
            c3.moveToFirst();
            number = c3.getCount();

            return number;
        }
        catch (Exception e){
            Log.d(TAG," MissedCallCountNumber ()"+e);
            return 0;
        }
    }

}
