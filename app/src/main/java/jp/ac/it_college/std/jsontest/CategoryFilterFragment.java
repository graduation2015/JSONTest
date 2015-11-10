package jp.ac.it_college.std.jsontest;


import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryFilterFragment extends ListFragment implements View.OnClickListener {

    private List<String> items = new ArrayList<>();
    private EditText filter;
    private JsonManager jsonManager;
    private JsonDataReader jsonDataReader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_category_filter, container, false);
        contentView.findViewById(R.id.btn_load_json).setOnClickListener(this);
        setListAdapter(new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, items));

        filter = (EditText) contentView.findViewById(R.id.edit_filter);

        jsonManager = new JsonManager(getActivity());
        jsonDataReader = new JsonDataReader();

        return contentView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_load_json:
                loadJSON();
                break;
        }
    }

    private void loadJSON() {
        JSONObject rootObject = jsonManager.getJsonRootObject();
        executeCategoryFilter(rootObject);
    }

    private void executeCategoryFilter(JSONObject rootObject) {
        jsonDataReader.executeFilter(rootObject, getFilter(), CouponInfo.CATEGORY, items);

        //リスト更新
        ((ArrayAdapter) getListAdapter()).notifyDataSetChanged();
    }

    private String getFilter() {
        return filter.getText().toString();
    }

}
