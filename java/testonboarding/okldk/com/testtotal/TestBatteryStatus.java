package testonboarding.okldk.com.testtotal;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class TestBatteryStatus extends Activity {

    private static final String LOG_TAG = "BatteryCheck";
    private TextView batteryInfo;
    // private ImageView imageBatteryState;
    /** Called when the activity is first created. */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(testonboarding.okldk.com.testtotal.R.layout.activity_battery_information);
        TextView BatteryInitial = (TextView)findViewById(R.id.textViewBatteryInitial); // ADDED
        batteryInfo=(TextView)findViewById(testonboarding.okldk.com.testtotal.R.id.textViewBatteryInfo);
        // imageBatteryState=(ImageView)findViewById(R.id.imageViewBatteryState);

        StringBuilder sb= new StringBuilder (); // ADDED
        sb.append(BatteryInitialInfo()); // ADDED
        BatteryInitial.setText(sb.toString()); // ADDED
        this.registerReceiver(this.batteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }
    // ADDED
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onReceive(Context context, Intent intent) {

            BatteryManager mBatteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);

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

            int capacity = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            int chargeCounter = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
            long totalCapacity = chargeCounter/capacity*100;
            double getBC=getBatteryCapacity();

            batteryInfo.setText(
                    "Health: "+health+"\n"+
                            "   :2Good,4dead,7Cool,3overHeat,5oV,1unknown,6F"+"\n"+
                            //       "Icon Small:"+icon_small+"\n"+
                            "Level-Now: "+level+"%"+"\n"+
                            "Level-Max: "+extra+"%"+"\n"+
                            "Plugged: "+plugged+"\n"+
                            "   :1=AC,2=USB,4=Wireless"+"\n"+
                            "Present: "+present+"\n"+
                            "   :true=batt, false=no-battpack"+"\n"+
                            //       "Scale: "+scale+"\n"+
                            "Status: "+status+"\n"+
                            "   :2charge,3discharge,5full, 4 NOTcharge,1unkn "+"\n"+

                            "Technology: "+technology+"\n"+
                            "Temperature: "+temperature+"\n"+
                            "Voltage: "+voltage+"\n"+
                            "Capacity:" + capacity+"\n"+
                            "ChargeCounter:"+chargeCounter+"\n"+
                            "Total Capacity:"+totalCapacity+"\n"+
                            "getBatteryCapacity:"+getBC+"mA"+"\n"
            );
            //  imageBatteryState.setImageResource(icon_small);
        }
    };
    public Double getBatteryCapacity() {

        // Power profile class instance
        Object mPowerProfile_ = null;

        // Reset variable for battery capacity
        double batteryCapacity = 0;

        // Power profile class name
        final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";

        try {

            // Get power profile class and create instance. We have to do this
            // dynamically because android.internal package is not part of public API
            mPowerProfile_ = Class.forName(POWER_PROFILE_CLASS)
                    .getConstructor(Context.class).newInstance(this);

        } catch (Exception e) {

            // Class not found?
            e.printStackTrace();
        }

        try {

            // Invoke PowerProfile method "getAveragePower" with param "battery.capacity"
            batteryCapacity = (Double) Class
                    .forName(POWER_PROFILE_CLASS)
                    .getMethod("getAveragePower", java.lang.String.class)
                    .invoke(mPowerProfile_, "battery.capacity");

        } catch (Exception e) {

            // Something went wrong
            e.printStackTrace();
        }

        return batteryCapacity;
    }
    /*
    public long getBatteryCapacity(BroadcastReceiver context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BatteryManager mBatteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
            Integer chargeCounter = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
            Integer capacity = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

            if(chargeCounter == Integer.MIN_VALUE || capacity == Integer.MIN_VALUE)
                return 0;

            return (chargeCounter/capacity) *100;
        }
        return 0;
    }*/
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