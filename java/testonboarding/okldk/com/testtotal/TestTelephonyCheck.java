package testonboarding.okldk.com.testtotal;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.annotation.TargetApi;
import android.app.Activity;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.View; // for wifi switch test
import android.view.View.OnClickListener; // for wifi switch test

import android.provider.CallLog;
import android.provider.CallLog.Calls;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;


import android.telephony.TelephonyManager;
import android.telephony.CellLocation; // added for spec test
import android.telephony.cdma.CdmaCellLocation; // added for android spec check
import android.telephony.gsm.GsmCellLocation;
import android.telephony.ServiceState; // added for android spec check

import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import android.net.wifi.WifiManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.CheckBox;
import android.widget.TextView;
import android.content.Context;
import android.widget.CompoundButton;

public class TestTelephonyCheck extends Activity {
    public static String TAG="TelephonyCheck";
    TextView tv1,tv2; // test
    boolean status;
    private static final int REQUEST_READ_CALL_LOG = 0;
    private static final int REQUEST_WRITE_CALL_LOG = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(testonboarding.okldk.com.testtotal.R.layout.activity_telephony_check);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS}, 0);
        } // 2016.03.31
        tv1 = (TextView) findViewById(testonboarding.okldk.com.testtotal.R.id.tv1); // test

        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE) ;
        StringBuilder sb= new StringBuilder();
        sb.append("\n\n\n");
        sb.append("device SW ver :").append(tm.getDeviceSoftwareVersion()).append("\n");
        sb.append("fingerprint :").append(getFinger()).append("\n").append(getversion()).append("\n");
        sb.append("baseband : ").append(getbaseband()).append(",").append(getrilboardname());
        sb.append("/ chipname:").append(getchipname()).append("\n");
        sb.append("platform : ").append(getplatform()).append(",").append(getproductboard()).append("\n");
        sb.append("cpu abi :").append(getcpuabi()).append("\n");
        sb.append("ram low :").append(getlowram()).append("\n");
        sb.append("--------------Security-----------------------").append("\n");
        sb.append(getSecurity()).append("\n");
        sb.append("ro.secure   :").append(getRoSecure()).append("\n");
        sb.append("ro.debuggable: ").append(getDebuggable()).append("\n");
        sb.append("--------------telephony-----------------------").append("\n");
        sb.append("getNetworkCountryIso()  :").append(tm.getNetworkCountryIso()).append("\n");
        sb.append("getDeviceId(MEID)       :").append(tm.getDeviceId()).append("\n");
        sb.append("getLine1Number(MDN)     :").append(tm.getLine1Number()).append("\n");
        sb.append("ril IMEI :").append(getImei()).append("\n");
        sb.append("getNetworkOperator(MCCMNC) :").append(tm.getNetworkOperator()).append("\n");
        sb.append("getNetworkOperatorName():").append(tm.getNetworkOperatorName()).append("\n");
        sb.append("getSimCountryIso()      :").append(tm.getSimCountryIso()).append("\n");
        sb.append("getSimOperator(MCCMNC)  :").append(tm.getSimOperator()).append("\n");
        sb.append("getSimOperatorName()    :").append(tm.getSimOperatorName()).append("\n");
        sb.append("getSimSerialNumber()    :").append(tm.getSimSerialNumber()).append("\n");
        sb.append("getSubscriberId(310 00 MIN):").append(tm.getSubscriberId()).append("\n");
        sb.append("Voice mail Alpha Tag    :").append(tm.getVoiceMailAlphaTag()).append("\n");
        sb.append("Voice Mail Number       :").append(tm.getVoiceMailNumber()).append("\n");
        sb.append("SimRecords reflection   :").append(SimRecords()).append("\n");
        sb.append("alpha and numeric       : ").append(getPropTest()).append("\n");
        sb.append("ro.ril.svdo :").append(getsvdoflag()).append("\n").append("\n");
        sb.append("CallState :").append(tm.getCallState()).append("\n");
        sb.append("IsRoaming :").append(tm.isNetworkRoaming()).append("\n");

        sb.append("getAllCellInfo:").append(tm.getAllCellInfo()).append("\n");
        sb.append("CdmaCellLocationInfo : ").append(CellLocationInfo()).append("\n");
        sb.append("--------------connectivity----------------------").append("\n");
        sb.append("GetActiveNetworkInformation :").append(getActiveNetworkInformation()).append("\n");
        sb.append(MeteredorNot()).append("\n");
        sb.append("get missed call count all: ").append("\n").append(missedCallCount()).append("\n");

        tv1.setText(sb.toString());

        status=statusWiFi();
        Switch toggle = (Switch) findViewById(testonboarding.okldk.com.testtotal.R.id.wifi_switch);
        toggle.setChecked(status);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleWiFi(true);
                    Toast.makeText(getApplicationContext(), "Wi-Fi Enabled!", Toast.LENGTH_LONG).show();
                } else {
                    toggleWiFi(false);
                    Toast.makeText(getApplicationContext(), "Wi-Fi Disabled!", Toast.LENGTH_LONG).show();
                }
            }
        });

		/*
        Switch toggle = (Switch) findViewById(R.id.wifi_switch);
        toggle.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                // you might keep a reference to the CheckBox to avoid this class cast
                boolean checked =((CheckBox)v).isChecked();
                if (checked)
                	toggleWiFi(statusWiFi());
            }

        });
        */
    }

    public String getFinger() {
        String strfingerprint = "";
        try {
            Process ifc = Runtime.getRuntime().exec("getprop ro.build.fingerprint");
            BufferedReader bis = new BufferedReader(new InputStreamReader(ifc.getInputStream()));
            strfingerprint = bis.readLine();
            return strfingerprint ;
        } catch (java.io.IOException e) {
            return null ;
        }
    }
    public String getversion() {
        String versionbaseband = "";
        try {
            Process ifc = Runtime.getRuntime().exec("getprop gsm.version.baseband");
            BufferedReader bis = new BufferedReader(new InputStreamReader(ifc.getInputStream()));
            versionbaseband = bis.readLine();
            return versionbaseband ;
        } catch (java.io.IOException e) {
            return null ;
        }
    }
    public String getbaseband() {
        String baseband = "";
        try {
            Process ifc = Runtime.getRuntime().exec("getprop ro.boot.baseband");
            BufferedReader bis = new BufferedReader(new InputStreamReader(ifc.getInputStream()));
            baseband = bis.readLine();
            return baseband ;
        } catch (java.io.IOException e) {
            return null ;
        }
    }
    public String getrilboardname() {
        String rilboard = "";
        try {
            Process ifc = Runtime.getRuntime().exec("getprop ril.modem.board");
            BufferedReader bis = new BufferedReader(new InputStreamReader(ifc.getInputStream()));
            rilboard = bis.readLine();
            return rilboard ;
        } catch (java.io.IOException e) {
            return null ;
        }
    }
    public String getchipname() {
        String chipname = "";
        try {
            Process ifc = Runtime.getRuntime().exec("getprop ro.chipname");
            BufferedReader bis = new BufferedReader(new InputStreamReader(ifc.getInputStream()));
            chipname = bis.readLine();
            return chipname ;
        } catch (java.io.IOException e) {
            return null ;
        }
    }
    public String getplatform() {
        String platform = "";
        try {
            Process ifc = Runtime.getRuntime().exec("getprop ro.board.platform");
            BufferedReader bis = new BufferedReader(new InputStreamReader(ifc.getInputStream()));
            platform = bis.readLine();
            return platform ;
        } catch (java.io.IOException e) {
            return null ;
        }
    }
    public String getproductboard() {
        String board = "";
        try {
            Process ifc = Runtime.getRuntime().exec("getprop ro.product.board");
            BufferedReader bis = new BufferedReader(new InputStreamReader(ifc.getInputStream()));
            board = bis.readLine();
            return board ;
        } catch (java.io.IOException e) {
            return null ;
        }
    }
    public String getsvdoflag() {
        String svdo = "";
        try {
            Process ifc = Runtime.getRuntime().exec("getprop ro.ril.svdo");
            BufferedReader bis = new BufferedReader(new InputStreamReader(ifc.getInputStream()));
            svdo = bis.readLine();
            return svdo ;
        } catch (java.io.IOException e) {
            return null ;
        }
    }
    public String getcpuabi() {
        String cpuabi = "";
        try {
            Process ifc = Runtime.getRuntime().exec("getprop ro.product.cpu.abi");
            BufferedReader bis = new BufferedReader(new InputStreamReader(ifc.getInputStream()));
            cpuabi = bis.readLine();
            return cpuabi ;
        } catch (java.io.IOException e) {
            return null ;
        }
    }
    public String getlowram() {
        String lowram = "";
        try {
            Process ifc = Runtime.getRuntime().exec("getprop ro.config.low_ram");
            BufferedReader bis = new BufferedReader(new InputStreamReader(ifc.getInputStream()));
            lowram = bis.readLine();
            return lowram ;
        } catch (java.io.IOException e) {
            return null ;
        }
    }
    public String getImei() {
        String strImei = "";
        try {
            Process ifc = Runtime.getRuntime().exec("getprop ril.IMEI");
            BufferedReader bis = new BufferedReader(new InputStreamReader(ifc.getInputStream()));
            strImei = bis.readLine();
            return strImei ;
        } catch (java.io.IOException e) {
            return null ;
        }
    }
    public String getRoSecure() {
        String strSecure = "";
        try {
            Process ifc = Runtime.getRuntime().exec("getprop ro.secure");
            BufferedReader bis = new BufferedReader(new InputStreamReader(ifc.getInputStream()));
            strSecure = bis.readLine();
            return strSecure ;
        } catch (java.io.IOException e) {
            return null ;
        }
    }

    public String getDebuggable() {
        String debuggable = "";
        try {
            Process ifc = Runtime.getRuntime().exec("getprop getDebuggable");
            BufferedReader bis = new BufferedReader(new InputStreamReader(ifc.getInputStream()));
            debuggable = bis.readLine();
            return debuggable ;
        } catch (java.io.IOException e) {
            return null ;
        }
    }
    public String getSecurity(){
        StringBuilder sb= new StringBuilder();
        String result,result0, result1,result2;
        try{
            Process ifc0 = Runtime.getRuntime().exec("getprop ro.build.selinux.enforce");
            BufferedReader bis0=new BufferedReader(new InputStreamReader(ifc0.getInputStream()));
            result0 = bis0.readLine();
            if(result0.length() < 1) {
                ifc0 = Runtime.getRuntime().exec("getprop selinux.init_rc_finished");
                BufferedReader bis01=new BufferedReader(new InputStreamReader(ifc0.getInputStream()));
                result0 = bis01.readLine();
                sb.append("selinux.init_rc_finished =").append(result0).append("\n");
            }
            else
                sb.append("ro.build.selinux.enforce =").append(result0).append("\n");

            Process ifc1 = Runtime.getRuntime().exec("getprop ro.security.reactive.enable");
            BufferedReader bis1=new BufferedReader(new InputStreamReader(ifc1.getInputStream()));
            result1 = bis1.readLine();
            sb.append("ro.security.reactive.enable=").append(result1).append("\n");

            Process ifc2 = Runtime.getRuntime().exec("getprop ro.security.reactive.triggered");
            BufferedReader bis2=new BufferedReader(new InputStreamReader(ifc2.getInputStream()));
            result2 = bis2.readLine();
            sb.append("ro.security.reactive.triggered=").append(result2);

            result=sb.toString();
            return result;
        }
        catch (Exception e){
            Log.d(TAG,"SortingTest()"+e);
            return null;
        }
    }
    public String SimRecords(){
        String SimRec;
        try{
            StringBuilder sb= new StringBuilder();
            Class simRecordsClass = Class.forName("com.verizon.phone.SimRecords");
            Object simRecordsInstance = simRecordsClass.newInstance();
            Method getDeviceId=simRecordsClass.getMethod("getDeviceId",int.class);
            String meid=(String) getDeviceId.invoke(simRecordsInstance,0x01);
            String imei=(String) getDeviceId.invoke(simRecordsInstance,0x02);
            String euimid=(String) getDeviceId.invoke(simRecordsInstance, 0x03);
            sb.append(meid).append("\n");
            sb.append(imei).append("\n");
            sb.append(euimid).append("\n");
            tv2.setText(sb.toString()); // minSDK=16
            SimRec=sb.toString();
            Log.d(TAG,"MEID:"+meid+",IMEI:"+imei+",EUIMID:"+euimid+"(com.verizon.phone)");
            return SimRec;
        }catch (Exception e){
            Log.d(TAG,"getDeivceId():" +e);
            return null;
        }
    }

    public String getActiveNetworkInformation(){
        String activenet;
        try{

            ConnectivityManager cm = (ConnectivityManager) getSystemService (CONNECTIVITY_SERVICE);
            StringBuilder sb= new StringBuilder();
            sb.append("getActiveNetworkInfo:").append(cm.getActiveNetworkInfo()).append("\n");
            activenet=sb.toString();
            return activenet;
        }
        catch (Exception e){
            Log.d(TAG,"getDeivceId():" +e);
            return null;
        }
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public String MeteredorNot(){
        String meter;
        try{
            ConnectivityManager cm2=(ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            StringBuilder sb=new StringBuilder();
            sb.append("IsActiveNetworkMetered:").append(cm2.isActiveNetworkMetered()).append("\n");
            meter=sb.toString();
            return meter;
        }
        catch (Exception e){
            Log.d(TAG,"getDeviceId():"+e);
            return null;
        }
    }

    //-------------------------------------------------------------------------
    public String CellLocationInfo(){
        String Cell;
        CellLocation location =null; //FIXME
        try{
            StringBuilder sb=new StringBuilder();
            if (location instanceof GsmCellLocation) {
                GsmCellLocation cellLocation = (GsmCellLocation)location;
                sb.append("cid=").append(cellLocation.getCid()).append("\n");
                sb.append("lac=").append(cellLocation.getLac()).append("\n");
            }
            else if (location instanceof CdmaCellLocation) {
                CdmaCellLocation cellLocation = (CdmaCellLocation)location;
                sb.append("BID=").append(cellLocation.getBaseStationId()).append("\n");
                sb.append("SID=").append(cellLocation.getSystemId()).append("\n");
                sb.append("NID=").append(cellLocation.getNetworkId()).append("\n");
                sb.append("lat=").append(cellLocation.getBaseStationLatitude()).append("\n");
                sb.append("long=").append(cellLocation.getBaseStationLongitude()).append("\n");
            }
            Cell=sb.toString();
            return Cell;
        }
        catch (Exception e) {
            Log.e(TAG, "Exception in CellStateHandler.handleMessage:", e);
            return null;
        }
    }
    // getPropTest for Sprint ro.cdam.home.----//
    public String getPropTest(){
        StringBuilder sb= new StringBuilder();
        String result;
        String result1="";
        String result2;
        try{   //   Class<?> myclass = Class.forName( "android.os.SystemProperties" );
            Process ifc = Runtime.getRuntime().exec("getprop ro.cdma.home.operator.alpha");
            BufferedReader bis=new BufferedReader(new InputStreamReader(ifc.getInputStream()));
            result1 = bis.readLine();
            sb.append("ro.cdma.home.operator.alpha=").append(result1).append("\n");
            //	Method[] methods = myclass.getMethods();
            // 	Object[] params = new Object[] {new String("ro.cdma.home.operator.numeric" ), new String("Unknown")};
            // 	result2 = (String)(methods[2].invoke( myclass, params ));
            Process ifc2 = Runtime.getRuntime().exec("getprop ro.cdma.home.operator.numeric");
            BufferedReader bis2=new BufferedReader(new InputStreamReader(ifc2.getInputStream()));
            result2 = bis2.readLine();
            sb.append("ro.cdma.home.operator.numeric=").append(result2);
            //StringBuilder sb= new StringBuilder();
            //sb.append("ro.cdma.home.operator.numeric=").append(SystemProperties.get("ro.cdma.home.operator.alpha").append("\n");
            //sb.append("ro.cdma.home.operator.numeric=").append(SystemProperties.get("ro.cdma.home.operator.numeric").append("\n");
            result=sb.toString();
            return result;
        }
        catch (Exception e){
            Log.d(TAG,"SortingTest()"+e);
            return null;
        }
    }
//------------wifi ----------------------

    public boolean statusWiFi(){
        boolean status=false;
        WifiManager wifiManager = (WifiManager) this
                .getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            status= false;
        } else if (wifiManager.isWifiEnabled()) {
            status= true;
        }
        return status;
    }
    public void toggleWiFi(boolean status) {
        WifiManager wifiManager = (WifiManager) this
                .getSystemService(Context.WIFI_SERVICE);
        if (status == true && !wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        } else if (status == false && wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
        }
    }

//-------------- missed call count test --------------

    public String missedCallCount(){
        StringBuilder sb=new StringBuilder();
        String missingCount;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // only for M and newer versions
            if (checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(TestTelephonyCheck.this,
                        new String[]{Manifest.permission.READ_CALL_LOG},
                        REQUEST_READ_CALL_LOG);
            }
            if (checkSelfPermission(Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(TestTelephonyCheck.this,
                        new String[]{Manifest.permission.WRITE_CALL_LOG},
                        REQUEST_WRITE_CALL_LOG);
            }
            Toast.makeText(this, " M ",
                    Toast.LENGTH_LONG).show();
        }

        try{
            String[] projection = {CallLog.Calls.CACHED_NAME, CallLog.Calls.CACHED_NUMBER_LABEL,CallLog.Calls.TYPE};
            String where = CallLog.Calls.TYPE+"="+CallLog.Calls.MISSED_TYPE;
            Cursor c=getContentResolver().query(CallLog.Calls.CONTENT_URI, projection, where, null,null);
            c.moveToFirst();
            sb.append("Total missed call count   :").append(String.valueOf(c.getCount())).append("\n");
            c.close();

            String[] projection2 = {CallLog.Calls.CACHED_NAME, CallLog.Calls.CACHED_NUMBER_LABEL,CallLog.Calls.TYPE, CallLog.Calls.NEW};
            String where2 = CallLog.Calls.TYPE+"="+CallLog.Calls.MISSED_TYPE+" AND "+ CallLog.Calls.NEW+ "=1";
            Cursor c2=getContentResolver().query(CallLog.Calls.CONTENT_URI, projection2, where2, null,null);
            c2.moveToFirst();
            sb.append("Current missed call count :").append(String.valueOf(c2.getCount())).append("\n");
            c2.close();
            missingCount=sb.toString();
            return missingCount;
        }
        catch (Exception e){
            Log.d(TAG,"missedCall()"+e);
            return null;
        }
    }


    //---------------------------------------------
    public void dismissTelephony(View view) {
        Log.d(TAG, "Finishing activity");
        finish();
    }
}


