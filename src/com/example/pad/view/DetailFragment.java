package com.example.pad.view;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragment;
import com.example.pad.R;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 7/4/13
 * Time: 9:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class DetailFragment extends Fragment {
    private int mIndex = 0;

    public static DetailFragment newInstance(int index) {
        DetailFragment df = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        df.setArguments(args);
        return df;
    }

    public static DetailFragment newInstance(Bundle bundle) {
        int index = bundle.getInt("index", 0);
        return newInstance(index);
    }

    public void onCreate(Bundle myBundle) {
        super.onCreate(myBundle);
    }

    public int getShownIndex() {
        return mIndex;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main, container, false);
        return v;
    }
}