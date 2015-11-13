package jp.ac.it_college.std.jsontest;


import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;


public class RemoveDataFragment extends ListFragment {

    private JsonManager jsonManager;
    private List<CouponInfo> couponInfoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        jsonManager = new JsonManager(getActivity());
        couponInfoList = jsonManager.getCouponInfoList();

        setListAdapter(new CouponInfoAdapter(
                getActivity(), R.layout.row_objects, couponInfoList));

        return inflater.inflate(R.layout.fragment_remove_data, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        CouponInfo info = (CouponInfo) getListAdapter().getItem(position);

        jsonManager.removeObject(info.getKey());
        couponInfoList.remove(position);

        ((CouponInfoAdapter) getListAdapter()).notifyDataSetChanged();
    }
}
