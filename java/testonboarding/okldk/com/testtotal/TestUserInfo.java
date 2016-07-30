package testonboarding.okldk.com.testtotal;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class TestUserInfo extends Activity {// implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = "UserInfo";
    /** 2016.03.31
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView v = new ScrollView(this);
        LinearLayout lll = new LinearLayout(this);
        lll.setOrientation(LinearLayout.VERTICAL);
        v.addView(lll);

        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
                 ActivityCompat.requestPermissions(this,
                 new String[]{Manifest.permission.GET_ACCOUNTS}, 0);
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCOUNT_MANAGER) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCOUNT_MANAGER}, 0);
            }
            Account[] accounts = AccountManager.get(this).getAccounts();
            StringBuilder sb = new StringBuilder(); // c
            TextView tv;
            tv = new TextView(this);
            sb.append(getUserAllGmailAccount()).append("\n").append("\n");
            for (Account account : accounts) {
                sb.append(account.type).append(":").append(account.name).append("\n"); // c
            }
            tv.setText(sb.toString()); //c from loop
            lll.addView(tv);    //c from loop
        } catch (Exception e) {
            Log.i("Exception", "Exception:" + e);
        }
        setContentView(v);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

// end b


    public String getUserAllGmailAccount() {
        String acc;
        try {


            AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
            Account[] accounts = manager.getAccountsByType("com.google");
            StringBuilder sb = new StringBuilder();
            sb.append("Active Gmail Acccounts:").append("\n");
            Log.d(LOG_TAG, "after getAccountsByType");
            for (Account account : accounts) {
                sb.append(account.type).append(":").append(account.name).append("\n"); // c
                Log.d(LOG_TAG, "Listing all gmail account");
            }
            acc = sb.toString();
        } catch (Exception ex) {
            Log.d(LOG_TAG, "Active account NULL" + ex);
            return null;
        }
        return acc;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "TestUserInfo Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://testonboarding.okldk.com.testtotal/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "TestUserInfo Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://testonboarding.okldk.com.testtotal/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
//a
    //accinfo=(TextView)findViewById(R.id.tv1);
    //Log.d(LOG_TAG,"onCreate success !");
    // getLoaderManager().initLoader(0, null, (LoaderCallbacks<D>) this);

    // FIXME !!!!!!!!!!!!!!!!!!!!!
    //getLoaderManager().initLoader(0, null,(android.app.LoaderManager.LoaderCallbacks<Cursor>) this);
    //getSupportLoaderManager().initLoader(0, null, this);

        /*
        LoaderInfo info = mLoaders.get(id);
        if (info == null) {
            // Loader doesn't already exist -> create new one
            info = createAndInstallLoader(id, args, LoaderManager.LoaderCallbacks<Object>)callback);
        } else {
           // Loader exists -> only replace callbacks
           info.mCallbacks = (LoaderManager.LoaderCallbacks<Object>)callback;
        }
        */
    // Log.d(LOG_TAG,"getLoaderManager success !");


//a  TODO : cursor option for the future.
/*
    public Loader<Cursor> onCreateLoader(int id, Bundle arguments) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(
                        ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY),
                ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE + " = ?",
                new String[]{ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");

    }

    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            // Potentially filter on ProfileQuery.IS_PRIMARY
            cursor.moveToNext();
        }
        //TODO...
    }

    public void onLoaderReset(Loader<Cursor> cursorLoader) {
    	Log.d(LOG_TAG,"onLoaderReset success !");
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
     }
    */
}