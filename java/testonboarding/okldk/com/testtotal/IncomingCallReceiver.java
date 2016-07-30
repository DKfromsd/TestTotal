
/*
package testonboarding.okldk.com.testonboarding;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.provider.CallLog;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.view.View;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.util.Log;

//------------  1   -------------------------------------------------------

public class IncomingCallReceiver extends BroadcastReceiver {

    private static final String TAG = "IncomingMissedCall";
    private static final String ACTION_BADGE_COUNT_UPDATE = "android.intent.action.BADGE_COUNT_UPDATE";
    private static final String EXTRA_BADGE_COUNT ="badge_count";  // TO_DO
    private static final String EXTRA_BADGE_COUNT_PACKAGE_NAME = "badge_count_package_name";
    private static final String EXTRA_BADGE_COUNT_CLASS_NAME = "badge_count_class_name";
 //   TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
 //   TelephonyMgr.listen(new TeleListener(), PhoneStateListener.LISTEN_CALL_STATE);

      static boolean ring=false;
      static boolean callReceived=false;

      @Override
      public void onReceive(Context mContext, Intent intent)
      {

             // Get the current Phone State
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            int count;
            if(state==null)
                return;
            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING))
                    ring =true;
            if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))
                    callReceived=true;
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE))
            {
            	if(state.equals(TelephonyManager.CALL_STATE_IDLE)) {
            		if(ring==true && callReceived== false){
            			// API - missed call count would be changed from device call log
            			// API - set badge and send intent to launcher

            	     //TODO   setBadgeCount(context,count);



            	 	}
            	}

            }
      }


}

// --------- in manifest ----------------------
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <application
        android:allowBackup="true"
        android:icon="@drawable/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/AppTheme" >

        <!-- Register the Broadcast receiver  -->
        <receiver android:name=".IncommingCallReceiver" android:enabled="true">
            <intent-filter>
                    <action android:name="android.intent.action.PHONE_STATE" />
                </intent-filter>
           </receiver>
    </application>
 //----------------------------------------------


//------------ 2   ----------------------------------------------------------


public class ListeningMissedCallActivity extends Activity {

 static boolean ring = false;
 static boolean callReceived = false;

 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);

  TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
  TelephonyMgr.listen(new TeleListener(), PhoneStateListener.LISTEN_CALL_STATE);

 }

//-------------------------------------

public class TeleListener extends PhoneStateListener {

  public void onCallStateChanged(int state, String incomingNumber) {

   super.onCallStateChanged(state, incomingNumber);

   switch (state) {

   case TelephonyManager.CALL_STATE_IDLE:

    if (ring == true && callReceived == false) {
//     Toast.makeText(getApplicationContext(),"Missed call from : " + incomingNumber, Toast.LENGTH_LONG).show();
      // Missed call count 1 added in call log; let's utilize;

    }
    // CALL_STATE_IDLE;
//    Toast.makeText(getApplicationContext(), "CALL_STATE_IDLE", Toast.LENGTH_LONG).show();
    break;

   case TelephonyManager.CALL_STATE_OFFHOOK:
    // CALL_STATE_OFFHOOK;
    callReceived = true;
//    Toast.makeText(getApplicationContext(), "CALL_STATE_OFFHOOK",Toast.LENGTH_LONG).show();
    break;

   case TelephonyManager.CALL_STATE_RINGING:
    ring = true;
    // CALL_STATE_RINGING
//    Toast.makeText(getApplicationContext(), incomingNumber,Toast.LENGTH_LONG).show();
//    Toast.makeText(getApplicationContext(), "CALL_STATE_RINGING",Toast.LENGTH_LONG).show();
    break;

   default:
    break;
   }
  }

 }

----------------------------------------------------------------------*/
