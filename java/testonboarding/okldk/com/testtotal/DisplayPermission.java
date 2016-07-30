package testonboarding.okldk.com.testtotal;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PermissionInfo;

public class DisplayPermission extends Activity {
    private static final String LOG_TAG = "DisplayPermissions";
    /** Called when the activity is first created. **/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String packageName = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            packageName = extras.getString("packageName");
        }
        if (packageName == null) {
            finish();
            return;
        }
        setContentView(R.layout.activity_display_permission);
        TextView permissionsField = (TextView) findViewById(R.id.permissions);
        TextView requestedPermissionsField = (TextView) findViewById(R.id.requestedPermissions);
        Log.d(LOG_TAG, "Getting permissions for " + packageName);
        try {
            PackageInfo pkgInfo = getPackageManager().getPackageInfo(
                    packageName,
                    PackageManager.GET_PERMISSIONS
            );
            PermissionInfo[] permissions = pkgInfo.permissions;
            if (permissions == null) {
                permissionsField.setText("No declared permissions");
            }
            else {
                String permissionsText = "";
                for (int i = 0; i < permissions.length; i++) {
                    permissionsText += permissions[i].name + "\n";
                }
                permissionsField.setText(permissionsText+"\n"+pkgInfo.versionName+"\n"+pkgInfo.versionCode + "\n"+pkgInfo.applicationInfo.loadLabel(getPackageManager()).toString());
            }

            String[] requestedPermissions = pkgInfo.requestedPermissions;
            if (requestedPermissions == null) {
                requestedPermissionsField.setText("No requested permissions");
            }
            else {
                String reqPermText = "";
                for (int i = 0; i < requestedPermissions.length; i++) {
                    reqPermText += requestedPermissions[i] + "\n";
                }
                requestedPermissionsField.setText(reqPermText);
                requestedPermissionsField.setText(reqPermText+"\n"+pkgInfo.versionName+"\n"+pkgInfo.versionCode + "\n"+pkgInfo.applicationInfo.loadLabel(getPackageManager()).toString());
            }
        }
        catch (PackageManager.NameNotFoundException e) {
            permissionsField.setText("Package Not Found");
            requestedPermissionsField.setText("Package Not Found");
        }
    }
    public void dismissPermissionsList(View view) {
        Log.d(LOG_TAG, "Finishing activity");
        finish();
    }
}