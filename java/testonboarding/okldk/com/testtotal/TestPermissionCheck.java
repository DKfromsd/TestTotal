package testonboarding.okldk.com.testtotal;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.content.Intent;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

public class TestPermissionCheck extends Activity implements OnClickListener{
    public static final boolean TEST_CONDITION = true;
    private static final String LOG_TAG = "PermissionCheck";
    private EditText pkgName; // TODO future.
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ScrollView v = new ScrollView(this);
        LinearLayout lll = new LinearLayout(this); // ListbyLinearLayout
        lll.setOrientation(LinearLayout.VERTICAL);
        v.addView(lll);
        if (TEST_CONDITION)
        {
            //StringBuilder sb= new StringBuilder();
            ArrayList<String> apps = new ArrayList<String>();
            List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
            System.out.println();
            for(int j = 0 ; j < packs.size(); j++){

                PackageInfo p = packs.get(j);
                TextView tv = new TextView(this);
                tv.setText(p.packageName);
                tv.setClickable(true);
                tv.setOnClickListener(this);
                lll.addView(tv);
            }
        }
        setContentView(v);
    }
    public void getPermissions(View view) {
        String p = ((TextView) view).getText().toString();
        Log.d(LOG_TAG, "Get permissions for " + p);
        Intent intent = new Intent(this, DisplayPermission.class);
        intent.putExtra("packageName", p);
        startActivity(intent);
    }
    //     @Override
    public void onClick(View v) {
        getPermissions(v);
        // TODO Auto-generated method stub
    }
}