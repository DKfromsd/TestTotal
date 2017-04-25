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
    private TextView batteryInfo, BatteryInitial;
    double capacity;
    // private ImageView imageBatteryState;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(testonboarding.okldk.com.testtotal.R.layout.activity_battery_information);
        BatteryInitial = (TextView)findViewById(R.id.textViewBatteryInitial);
        batteryInfo=(TextView)findViewById(testonboarding.okldk.com.testtotal.R.id.textViewBatteryInfo);
        // imageBatteryState=(ImageView)findViewById(R.id.imageViewBatteryState);

        StringBuilder sb= new StringBuilder ();
        sb.append(BatteryInitialInfo());
        BatteryInitial.setText(sb.toString());
        this.registerReceiver(this.batteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }
    public String BatteryInitialInfo(){
        String battinfo;
        double capacity,chargingCount;
        long currentAve,currentNow,energyCount;
        try{
            BatteryManager mBatteryManager = (BatteryManager) getSystemService(BATTERY_SERVICE);
            capacity = (double) mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            // this is not coming when action changed.
            chargingCount = ((double) mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER));
            currentAve = ((long)mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE))/1000;
            currentNow = ((long)mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW))/1000;
            energyCount = ((long)mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER));

            StringBuilder sb= new StringBuilder();
            sb.append("\n\n\n").append("Capaticy :").append(capacity).append("\n");
            sb.append("ChargingCount :").append(chargingCount).append("nAh\n");
            sb.append("Current Ave   :").append(currentAve).append("mA\n");
            sb.append("Current Now   :").append(currentNow).append("mA\n");
            sb.append("Energy Count  :").append(energyCount).append("nWh\n");
            battinfo=sb.toString();
            return battinfo;
        }
        catch (Exception e){
            Log.d(LOG_TAG,"Initial info" +e);
            return null;
        }
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
            float  temperature= ((float)intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0))/10;
            float  voltage= intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);


            batteryInfo.setText(
                            "Health: "+health+   "( 2GD,4dead,7Cool,3overHeat,5oV,1unkn,6F)"+"\n"+
                            "Level-Now :  "+level+ "( Level-Max: "+extra+")\n"+
                            "Plugged :  "+plugged+ "( 1=AC,2=USB,4=Wireless)"+"\n"+
                            "Present :  "+present+ "( true=batt, false=no-battpack)"+"\n"+
                            "Status  :  "+status+  "( 2charge,3disch,5full,4NOTcharge,1unkn)"+"\n"+
                            "Technology :  "+technology+"\n"+
                            "Temperature:  "+temperature+" "+(char)0x00B0+"C"+"\n"+
                            "Voltage    :  "+((voltage)/1000)+"V\n");
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