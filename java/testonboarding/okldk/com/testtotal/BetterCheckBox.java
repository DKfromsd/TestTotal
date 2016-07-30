package testonboarding.okldk.com.testtotal;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.content.Context;
import android.widget.CompoundButton;



public class BetterCheckBox extends CheckBox {
    private CompoundButton.OnCheckedChangeListener myListener = null;
    private CheckBox myCheckBox;
    public BetterCheckBox(Context context) {
        super(context);
    }
    public BetterCheckBox(Context context, CheckBox checkBox) {
        this(context);
        this.myCheckBox = checkBox;
    }
    // assorted constructors here...
    @Override
    public void setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener listener){
        if(this.myListener == null)
            this.myListener = listener;
        myCheckBox.setOnCheckedChangeListener(listener);
    }

    public void silentlySetChecked(boolean checked){
        toggleListener(false);
        myCheckBox.setChecked(checked);
        toggleListener(true);
    }

    private void toggleListener(boolean on){
        if(on){
            this.setOnCheckedChangeListener(myListener);
        }
        else
            this.setOnCheckedChangeListener(null);
    }
}
