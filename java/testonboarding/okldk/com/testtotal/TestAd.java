package testonboarding.okldk.com.testtotal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/*
// Ad network-specific imports (AdMob).
import com.google.android.gms.ads.AdView;
import com.google.ads.*;
import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
//import com.google.ads.AdView;
import com.google.ads.AdRequest.ErrorCode;
import testonboarding.okldk.com.testtotal.R;

public class TestAd extends FragmentActivity {
    private static final int NUM_ITEMS = 4;
//  ads:adUnitId="pub-6003297803448326"
//    ads:adSize="BANNER"
    //ads:testDevices="7559D4F3FBD04F911CB02B5871575E87"
    // Ad network-specific mechanism to enable test mode.
    private static final String TEST_DEVICE_ID = "7559D4F3FBD04F911CB02B5871575E87";

    PagerAdapter mAdapter;
    ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ad);

        mAdapter = new PagerAdapter(getSupportFragmentManager());

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mPager.setCurrentItem(0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class PagerAdapter extends FragmentStatePagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return AdFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }

    public static class AdFragment extends Fragment {
        private int mNum;
        private AdView mAdView;
        private TextView mAdStatus;

        static AdFragment newInstance(int num) {
            AdFragment af = new AdFragment();

            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", num);
            af.setArguments(args);

            return af;
        }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle args = getArguments();
            mNum = args != null ? args.getInt("num") : 1;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            // Set up the various ad layouts on different flip pages.
            final int[] layouts = {
                    R.layout.ad_top,
                    R.layout.ad_bottom,
                    R.layout.ad_next_to_button,
                    R.layout.ad_covers_content };
            int layoutId = layouts[mNum];
            View v = inflater.inflate(layoutId, container, false);
            mAdStatus = (TextView) v.findViewById(R.id.status);
            mAdView = (AdView) v.findViewById(R.id.ad);
            mAdView.setAdListener(new MyAdListener());

            AdRequest adRequest = new AdRequest();
            // adRequest.addKeyword("ad keywords");

            // Ad network-specific mechanism to enable test mode.  Be sure to disable before
            // publishing your application.
            adRequest.addTestDevice(TEST_DEVICE_ID);
            mAdView.loadAd(adRequest);
            return v;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            mAdView.destroy();
        }

        // Receives callbacks on various events related to fetching ads.  In this sample,
        // the application displays a message on the screen.  A real application may,
        // for example, fill the ad with a banner promoting a feature.
        private class MyAdListener implements AdListener {

            @Override
            public void onDismissScreen(Ad ad) {}

            @Override
            public void onFailedToReceiveAd(Ad ad, ErrorCode errorCode) {
                mAdStatus.setText(R.string.error_receive_ad);
            }

            @Override
            public void onLeaveApplication(Ad ad) {}

            @Override
            public void onPresentScreen(Ad ad) {}

            @Override
            public void onReceiveAd(Ad ad) { mAdStatus.setText(""); }
        }
    }
}

*/

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.ads.*;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
public class TestAd extends Activity {
    private String PublisuerID ="pub-9693957509187788"; //"7559D4F3FBD04F911CB02B5871575E87";
	private AdView adView;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ad);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }
    public void dismissTestAd(View view) {
       // Log.d(TAG, "Finishing activity");
        finish();
    }
}