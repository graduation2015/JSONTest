package jp.ac.it_college.std.jsontest;


import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;


public class RemoveDataFragment extends ListFragment {

    private JsonManager jsonManager;
    private List<CouponInfo> couponInfoList;
    public static final int MENU_DELETE = 0;
    private ChoiceDialog menuChoiceDialog;
    private CouponInfo couponInfo;
    private int position;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        jsonManager = new JsonManager(getActivity());
        couponInfoList = jsonManager.getCouponInfoList();
        menuChoiceDialog = ChoiceDialog.newInstance(this, new MenuChoiceDialog());

        setListAdapter(new CouponInfoAdapter(
                getActivity(), R.layout.row_objects, couponInfoList));

        return inflater.inflate(R.layout.fragment_remove_data, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        this.position = position;
        couponInfo = (CouponInfo) getListAdapter().getItem(position);

        menuChoiceDialog.show(getFragmentManager(), "menu_choice");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MenuChoiceDialog.REQUEST_ITEMS) {
            switch (resultCode) {
                case MENU_DELETE:
                    removeObject(this.couponInfo);
                    break;
            }
        }
    }

    private void removeObject(CouponInfo info) {
        jsonManager.removeObject(info.getKey());
        couponInfoList.remove(position);

        ((CouponInfoAdapter) getListAdapter()).notifyDataSetChanged();
    }
}
