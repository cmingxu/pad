package com.example.pad.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.pad.R;
import com.example.pad.common.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 7/4/13
 * Time: 9:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class ListFragment extends android.app.ListFragment {

    private MainFragmentListActivity myActivity = null;
    int mCurCheckPosition = 0;

    public void onAttach(Activity myActivity) {
        super.onAttach(myActivity);
        this.myActivity = (MainFragmentListActivity) myActivity;
    }

    @Override
    public void onActivityCreated(Bundle icicle) {
        super.onActivityCreated(icicle);
        if (icicle != null) {
            mCurCheckPosition = icicle.getInt("curChoice", 0);
        }

        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, Config.LINKS));

        ListView lv = getListView();
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv.setSelection(mCurCheckPosition);

        myActivity.showDetails(mCurCheckPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);
        icicle.putInt("curChoice", mCurCheckPosition);
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        myActivity.showDetails(pos);
        mCurCheckPosition = pos;
    }
}