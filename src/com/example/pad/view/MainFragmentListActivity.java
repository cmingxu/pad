package com.example.pad.view;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import com.example.pad.R;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 7/4/13
 * Time: 9:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class MainFragmentListActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment);
    }
    public void showDetails(int index) {
            DetailFragment details = (DetailFragment) getFragmentManager()
                    .findFragmentById(R.id.detail);

            if (details == null || details.getShownIndex() != index) {
                details = DetailFragment.newInstance(index);

                FragmentTransaction ft = getFragmentManager()
                        .beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.replace(R.id.detail, details);
                ft.addToBackStack("details");
                ft.commit();
            }
    }
}