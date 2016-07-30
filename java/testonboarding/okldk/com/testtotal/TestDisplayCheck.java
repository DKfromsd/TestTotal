package testonboarding.okldk.com.testtotal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.Process;

import android.os.Build;
import android.os.Bundle;
import android.os.UserHandle;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Context;
import android.view.Menu;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.os.UserManager;

import java.util.ArrayList;
import java.util.List;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class TestDisplayCheck extends Activity {

    //private final IUserManager mService;

    private static final String LOG_TAG = "DisplayCheck";
    /** Called when the activity is first created. */
    public float density;
    public int densityDpi;
    public int heightPixels;
    public float scaledDensity;
    public int widthPixels;
    public float xdpi, ydpi;
    View view;
    TextView tv1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(testonboarding.okldk.com.testtotal.R.layout.activity_display_check);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        density=metrics.density;
        densityDpi=metrics.densityDpi;
        heightPixels=metrics.heightPixels;
        scaledDensity=metrics.scaledDensity;
        widthPixels=metrics.widthPixels;
        xdpi=metrics.xdpi;
        ydpi=metrics.ydpi;
//       view.setSystemUiVisibility(2);//View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        tv1 =(TextView) findViewById(testonboarding.okldk.com.testtotal.R.id.tv1);

        StringBuilder sb= new StringBuilder ();
        sb.append("\n\n\n");
        sb.append("Density scale: \n");
        sb.append("ldpi(low)  120dpi : low density   \n");
        sb.append("mdpi(medium)160dpi : mid density   \n");
        sb.append("hdpi(high) 240dpi : high density   \n");
        sb.append("xhdpi(xhigh) 320dpi : xhigh density \n");
        sb.append("xxhdpi(xxhigh)480dpi : xhigh density  \n");
        sb.append("xxxhdpi(xxxhigh)640dpi : xhigh density \n\n");

        sb.append("density= ").append(density).append("\n");
        sb.append("densityDpi= ").append(densityDpi).append("\n");
        sb.append("scaledDensity= ").append(scaledDensity).append("\n\n");
        sb.append("heightPixels= ").append(heightPixels).append("\n");
        sb.append("widthPixels= ").append(widthPixels).append("\n\n");
        sb.append("xdpi= ").append(xdpi).append("\n");
        sb.append("ydpi= ").append(ydpi).append("\n\n");
        sb.append("getTextSize= ").append(tv1.getTextSize()).append("\n");
        sb.append("openGL ES version (dec to hex) =").append(getgles()).append("\n").append("\n");

        sb.append("Is current user owner?").append(isCurrentUserOwner()).append("\n");

        //sb.append("Get User accounts:").append("\n"); sb.append(getUserAccount()).append("\n").append("\n");
        //sb.append("Get User accounts2:").append( getUserAllGmailAccount()).append("\n");
        //sb.append("get All accounts:").append(getAllAccount()).append("\n");
        // sb.append("get first gmail:").append(getEmail(getApplicationContext())).append("\n");

        sb.append("isAdminUser:").append(isAdminUser(getApplicationContext()));

        tv1.setText(sb.toString());
    }/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_display_check, menu);
        return true;
    }*/

    public String getgles() {
        String gles = "";         // String hex = Integer.toHexString(i);
        int i;
        try {
            Process ifc = Runtime.getRuntime().exec("getprop ro.opengles.version");
            BufferedReader bis = new BufferedReader(new InputStreamReader(ifc.getInputStream()));
            gles = bis.readLine();
            i=Integer.parseInt(gles.toString());
            gles=Integer.toHexString(i);
            return gles ;
        } catch (java.io.IOException e) {
            return null ;
        }
    }
    public void dismissDisplay(View view) {
        Log.d(LOG_TAG, "Finishing activity");
        finish();
    }

    //-----------start test account initial --------------//
    public boolean isCurrentUserOwner()
    {
        try
        {
            Method getUserHandle = UserManager.class.getMethod("getUserHandle");
            int userHandle = (Integer) getUserHandle.invoke(getApplicationContext().getSystemService(Context.USER_SERVICE));
            return userHandle == 0;
        }
        catch (Exception ex)
        {
            return false;
        }
    }
/* --------- move to UserInfo
    public String getUserAccount(){
        String acc;
        try{
            AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
            Account[] list = manager.getAccounts();
            StringBuilder sb= new StringBuilder();
            sb.append("Active Acccounts:").append(list).append("\n");
            acc=sb.toString();
            return acc;
        }
        catch (Exception ex){
            Log.d(LOG_TAG,"Active account NULL" +ex);
            return null;
        }
    }

    public String getUserAllGmailAccount(){
        String acc;
        try{
            AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
            Account[] accounts = manager.getAccountsByType("com.google");
            StringBuilder sb= new StringBuilder();
            sb.append("Active Gmail Acccounts:").append("\n");
            Log.d(LOG_TAG, "after getAccountsByType");
            for (Account account : accounts) {
                sb.append(account.type).append(":").append(account.name).append("\n"); // c
                Log.d(LOG_TAG, "Listing all gmail account");
            }
            acc=sb.toString();
        }
        catch (Exception ex){
            Log.d(LOG_TAG,"Active account NULL" +ex);
            return null;
        }
        return acc;
    }

    public static String getEmail(Context context) {
        AccountManager accountManager = AccountManager.get(context);
        //Account account = getAllAccount(accountManager);
        Account account = getGooglePrimaryAccount(accountManager);
        if (account == null) {
            return null;
        }
        else {
            return account.name;
        }
    }

    private String getAllAccount() {
        String acc;
        try {
            Account[] accounts = AccountManager.get(this).getAccounts();
            StringBuilder sb = new StringBuilder(); // c
            sb.append("Active All Acccounts:").append("\n");

            Log.d(LOG_TAG, "active All Accounts");
            TextView tv = new TextView(this); //c from loop
            tv = new TextView(this); //c from loop

            for (Account account : accounts) {
                sb.append(account.type).append(":").append(account.name).append("\n"); // c
                Log.d(LOG_TAG, "Listing all account");
            }
            acc=sb.toString();
        }
        catch (Exception ex){
            Log.d(LOG_TAG,"Active account NULL" +ex);
            return null;
        }
        return acc;
    }

    private static Account getGooglePrimaryAccount(AccountManager accountManager) {
        Account[] accounts = accountManager.getAccountsByType("com.google"); // TODO for M OS
        Account account;
        if (accounts.length > 0) {
            account = accounts[0];
        }
        else {
            account = null;
        }
        return account;
    }

*/
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    static boolean isAdminUser(Context context)   {
        UserHandle uh = android.os.Process.myUserHandle();
        UserManager um = (UserManager) context.getSystemService(Context.USER_SERVICE);

        if(null != um)
        {
            long userSerialNumber = um.getSerialNumberForUser(uh); // This must be included in spec
            Log.d(LOG_TAG, "userSerialNumber = " + userSerialNumber);
            return 0 == userSerialNumber;
        }
        else
            return false;
    }
}