package testonboarding.okldk.com.testtotal;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ScrollView;

public class TestRunningAppProcess extends Activity {//implements OnClickListener {

    //private Button BprocessesShow;
    // private TextView processesShow; // b
    private final static String LOG_TAG ="TestFGMonitor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScrollView v = new ScrollView(this); //a
        LinearLayout lll = new LinearLayout(this); //a
        lll.setOrientation(LinearLayout.VERTICAL); //a
        v.addView(lll);     //a
        // setContentView(R.layout.activity_process); // b

        ActivityManager manager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> runningProcesses = manager.getRunningAppProcesses();
        System.out.println();
        Log.d(LOG_TAG, "Get system service , running app process");

//	BprocessesShow=(Button) this.findViewById(R.id.BshowProcesses);
//   processesShow=(TextView) this.findViewById(R.id.showProcesses);

//  BprocessesShow.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {


        // StringBuilder b = new StringBuilder(); // b
        for (ActivityManager.RunningAppProcessInfo process: runningProcesses) {
            // b.append(process.processName);  // b
            // b.append("\n"); // b
            TextView processesShow = new TextView(this); //a
            processesShow.setText(process.processName);
            //processesShow.setClickable(true);
            //processesShow.setOnClickListener(this);
            lll.addView(processesShow); //a
        }
        // processesShow.setText(b.toString()); // b
//    }
//});
        setContentView(v); //a
    }
    public void dismissProcess(View view) {
        Log.d(LOG_TAG, "Finishing activity");
        finish();
    }
}