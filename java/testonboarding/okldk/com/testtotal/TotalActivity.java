package testonboarding.okldk.com.testtotal;

import java.util.Arrays;
import java.util.List;
import android.content.Context;
import java.lang.annotation.Annotation;
import android.support.annotation.RequiresApi;
//import testonboarding.okldk.com.TestBadgeUtil;
//import testonboarding.okldk.com.TotalActivity.MyContentObserver;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Build;
import android.os.Handler;
import android.provider.CallLog;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.ContentObserver;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

// ad test
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
//
public class TotalActivity extends Activity implements OnClickListener {
    public static String TAG = "Main Check ";
    private TextView selection;
    private ContentObserver MyContentObserver = null; // badge test missed call

//    private String[] permissions = {Manifest.permission.READ_CALL_LOG, Manifest.permission.ACCESS_COARSE_LOCATION};
// The request code used in ActivityCompat.requestPermissions()
// and returned in the Activity's onRequestPermissionsResult()
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.READ_CALL_LOG,
            android.Manifest.permission.ACCESS_COARSE_LOCATION

    };
    private static final String[] testItems = {"TestBatteryStatus", "TestDisplayCheck",
            "TestTelephonyCheck", "TestPermissionCheck", "TestLocationCheck", "TestRunningAppProcess",
            "TestUserInfo" ,"TestAd"
    }; // testAd
    List<String> testList = Arrays.asList(testItems);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //badge test 1-  contentObserver for missed call test
        this.getApplicationContext().getContentResolver().registerContentObserver(android.provider.CallLog.Calls.CONTENT_URI, true, new MyContentObserver(new Handler()));

        super.onCreate(savedInstanceState);
        ScrollView v = new ScrollView(this);
        LinearLayout lll = new LinearLayout(this);
        lll.setOrientation(LinearLayout.VERTICAL);
        v.addView(lll);
        System.out.println();
        for (int j = 0; j < testList.size(); j++) {
            TextView tv = new TextView(this);
            tv.setTextSize(30); // Set the default text size to the given value, interpreted as "scaled pixel" units.
            tv.setText(testList.get(j));
            tv.setClickable(true);
            Log.d(TAG, " setClickable ");
            tv.setOnClickListener(this);
            Log.d(TAG, " after setonClickListener ");
            lll.addView(tv);
        }

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        setContentView(v);
// Ad test
//        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");
//        AdView mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
/*---------------- TODO 1 permission popup issue ------------------
        //background
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CALL_LOG}, 0);
        }
        MissedCallIsRead();
        // badge test 2-modify by service
        // clear missed call by is read
        startService(new Intent(TotalActivity.this, TestBadgeUtil.class));
*/
    }

    //@Override
    public void onClick(View v) {
        StartCheck(v);
        // TODO Auto-generated method stub
    }

    //---------main test item list ----------------------------------
    public void StartCheck(View view) {
        Class<?> c = null;
        Log.d(TAG, " StartCheck");
        StringBuilder TestClassName = new StringBuilder();
        String p = ((TextView) view).getText().toString();

        Log.d(TAG, "to get : " + p);
        TestClassName.append("com.okldk.testtotal").append(p);//.append(".class");
        //String d = toString(TestClassName);
        TestClassName.reverse();  // reverse it
        //-----------------------------------------------//
        if (p != null) {
            Log.d(TAG, " ---- p ! = NULL ----- ");
            try {
                c = Class.forName(p);
                Log.d(TAG, " c = Class.forName () :  " + p);
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //1
            //Intent intent = new Intent(this, c);
            //intent.putExtra("ClassName", c);
            //Log.d(TAG, " Intent setClassName ");
            //startActivity(intent);
            //2
            //Intent intent = new Intent().setClassName(this,p);
            //Intent intent = new Intent(this, Class.forName(p));
            //startActivity(intent);
            //-----------------------------------------------//
        }
        //3
        if (p == "TestBatteryStatus") {
            Intent intent = new Intent(this, TestBatteryStatus.class);
            intent.putExtra("ClassName", p);
            startActivity(intent);
        } else if (p == "TestDisplayCheck") {
            Intent intent = new Intent(this, TestDisplayCheck.class);
            intent.putExtra("ClassName", p);
            startActivity(intent);
        } else if (p == "TestTelephonyCheck") {
            Intent intent = new Intent(this, TestTelephonyCheck.class);
            intent.putExtra("ClassName", p);
            startActivity(intent);
        } else if (p == "TestPermissionCheck") {
            Intent intent = new Intent(this, TestPermissionCheck.class);
            intent.putExtra("ClassName", p);
            startActivity(intent);
        } else if (p == "TestLocationCheck") {
            Intent intent = new Intent(this, TestLocationCheck.class);
            intent.putExtra("ClassName", p);
            startActivity(intent);
        } else if (p == "TestRunningAppProcess") {
            Intent intent = new Intent(this, TestRunningAppProcess.class);
            intent.putExtra("ClassName", p);
            startActivity(intent);
        } else if (p == "TestAd") {
            Intent intent = new Intent(this, TestAd.class);
            intent.putExtra("ClassName", p);
            startActivity(intent);
        } else if (p == "TestUserInfo") {
            Intent intent = new Intent(this, TestUserInfo.class);
            intent.putExtra("ClassName", p);
            startActivity(intent);
        }
        //-------------------------------------------------3 -//
    }

    //-------------- for badge test ----------
    public final class MyContentObserver extends ContentObserver {
        public MyContentObserver(Handler h) {
            super(h);
        }

        @Override
        public boolean deliverSelfNotifications() {
            return true;
        }

        @Override
        public void onChange(boolean selfChange) {
            Log.d(TAG, "MyContentObserver.onChange(" + selfChange + ")");
            // here you call the method to fill the list
            startService(new Intent(TotalActivity.this, TestBadgeUtil.class));
            Log.d(TAG, "MyContentObserver - Start Service in onChange ");
            stopService(new Intent(TotalActivity.this, TestBadgeUtil.class));
            Log.d(TAG, "MyContentObserver - Stop Service in onChange ");
            super.onChange(selfChange);
        }
    }
/* --------- TODO 2 check permission popup issue ---------------------------
    public void MissedCallIsRead() {
        Log.d(TAG, "MISSEDCALLISREAD -- enter -- ");
        ContentValues values = new ContentValues();
        try {
            String where3 = CallLog.Calls.TYPE + "=" + CallLog.Calls.MISSED_TYPE + " AND " + CallLog.Calls.NEW + "=1";
            values.put(CallLog.Calls.NEW, 0);
            values.put(CallLog.Calls.IS_READ, 1);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_CALL_LOG}, 0);
                return;
            }
            getContentResolver().update(CallLog.Calls.CONTENT_URI, values, where3.toString(),
                    new String[]{Integer.toString(CallLog.Calls.MISSED_TYPE)});
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void onDestroy(){
        // badge test -1
        if( MyContentObserver !=null  ){
            this.getContentResolver().unregisterContentObserver(MyContentObserver);
            Log.d(TAG, "MyContentObserver - unregister ");
        }
        // badge test -2
        stopService(new Intent( TotalActivity.this ,TestBadgeUtil.class));
        Log.d(TAG, "MyContentObserver - destroy and stopService ");

        super.onDestroy();
    }
    //--------------------------------------------------------
*/
    @Override
    public void onBackPressed() {
        String message = "";
        message = "Back button is blocked to test missed call badge. Please use menu item";
        // show message via toast
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(testonboarding.okldk.com.testtotal.R.menu.menu_total, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // display a message when a button was pressed
        String message = "";
        if (item.getItemId() == testonboarding.okldk.com.testtotal.R.id.Exit) {
            message = "Exit this app!";
        }
        // show message via toast
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
        finish();        //super.onDestroy();
        return true;

    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /*
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestMultiplePermissions(){
        List<String> remainingPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                remainingPermissions.add(permission);
            }
        }
        ActivityCompat.requestPermissions(remainingPermissions.toArray(new String[remainingPermissions.size()]), 101);
    }
*/

}