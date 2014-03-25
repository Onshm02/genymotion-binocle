package com.genymotion.binocle.test;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.genymotion.api.GenymotionManager;
import com.genymotion.binocle.R;
import com.genymotion.binocle.RadioSampleFragment;
import com.genymotion.binocle.SampleActivity;

import junit.framework.Assert;


public class TestRadio extends ActivityInstrumentationTestCase2<SampleActivity> {

    RadioSampleFragment fragmentRadio;

    public TestRadio() {
        super(SampleActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Change IMEI before creating activity
        GenymotionManager genymotion;
        genymotion = GenymotionManager.getGenymotionManager(getInstrumentation().getContext());
        // Faking a Google Nexus 4
        genymotion.getRadio().setDeviceId("353918050000000");

        // Add parameter to allow activity to start and create fragment GpsSampleFragment.
        Intent radioIntent;
        radioIntent = new Intent(getInstrumentation().getTargetContext(), SampleActivity.class);
        radioIntent.putExtra(SampleActivity.ARG_ITEM_ID, RadioSampleFragment.TAG);
        setActivityIntent(radioIntent);

        // Create activity and get fragment back
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentRadio = (RadioSampleFragment) fragmentManager.findFragmentByTag(RadioSampleFragment.TAG);
    }

    public void testDeviceId() {
        try {
            Thread.sleep(2000); //Android needs time to poll sensors and broadcast event.
        } catch (InterruptedException ie) {
        }

        TextView tvDeviceType = (TextView) fragmentRadio.getView().findViewById(R.id.tv_radioDeviceType);

        String text;
        text = tvDeviceType.getText().toString();

        Assert.assertTrue(text.endsWith("Nexus 4"));
    }
}