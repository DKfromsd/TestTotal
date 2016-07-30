package testonboarding.okldk.com.testtotal;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class TestBatteryStatus extends Activity {

    private static final String LOG_TAG = "BatteryCheck";
    private TextView batteryInfo;
    // private ImageView imageBatteryState;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(testonboarding.okldk.com.testtotal.R.layout.activity_battery_information);
        batteryInfo=(TextView)findViewById(testonboarding.okldk.com.testtotal.R.id.textViewBatteryInfo);
        // imageBatteryState=(ImageView)findViewById(R.id.imageViewBatteryState);

        this.registerReceiver(this.batteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int  health= intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0);
            //int  icon_small= intent.getIntExtra(BatteryManager.EXTRA_ICON_SMALL,0);
            int  level= intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            int  extra = intent.getIntExtra(BatteryManager.EXTRA_SCALE,0);
            int  plugged= intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,0);
            boolean  present= intent.getExtras().getBoolean(BatteryManager.EXTRA_PRESENT);
            //int  scale= intent.getIntExtra(BatteryManager.EXTRA_SCALE,0);
            int  status= intent.getIntExtra(BatteryManager.EXTRA_STATUS,0);
            String  technology= intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
            int  temperature= intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0);
            int  voltage= intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);


            batteryInfo.setText(
                    "Health: "+health+"\n"+
                            "   :2Good,4dead,7Cool,3overHeat,5oV,1unknown,6F"+"\n"+
                            //       "Icon Small:"+icon_small+"\n"+
                            "Level-Now: "+level+"\n"+
                            "Level-Max: "+extra+"\n"+
                            "Plugged: "+plugged+"\n"+
                            ":1=AC,2=USB,4=Wireless"+"\n"+
                            "Present: "+present+"\n"+
                            ":true=batt, false=no-battpack"+"\n"+
                            //       "Scale: "+scale+"\n"+
                            "Status: "+status+"\n"+
                            "   :2charge,3discharge,5full, 4 NOTcharge,1unkn "+"\n"+

                            "Technology: "+technology+"\n"+
                            "Temperature: "+temperature+"\n"+
                            "Voltage: "+voltage+"\n");
            //  imageBatteryState.setImageResource(icon_small);
        }
    };

    public void dismissBattery(View view) {
        Log.d(LOG_TAG, "Finishing activity");
        finish();
    }
}
/*
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class BatteryInformation extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_battery_information);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.battery_information, menu);
		return true;
	}

}
*/