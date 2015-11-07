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
    private JSONManager jsonManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_category_filter, container, false);
        contentView.findViewById(R.id.btn_load_json).setOnClickListener(this);
        setListAdapter(new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, items));

        filter = (EditText) contentView.findViewById(R.id.edit_filter);

        jsonManager = new JSONManager(getActivity());

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
        JSONObject object = jsonManager.getJSONObject();
        executeCategoryFilter(object);
    }

    private void executeCategoryFilter(JSONObject object) {
        items.clear();
        JSONArray names = object.names();

        if (names != null) {
            for (int i = 0; i < names.length(); i++) {
                JSONArray category = getValues(object, names, i, "category");
                for (int j = 0; j < category.length(); j++) {
                    // フィルターに入力されたタグが付いている画像名をリストに追加
                    if (categoryExists(category, j, filter.getText().toString())) {
                        addItems(items, names, i);
                    }
                }
            }
        }

        //リスト更新
        ((ArrayAdapter) getListAdapter()).notifyDataSetChanged();
    }

    private JSONArray getValues(JSONObject object, JSONArray names, int position, String key) {
        JSONArray values = null;
        try {
            values = object.getJSONObject(names.getString(position)).getJSONArray(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return values;
    }

    private boolean categoryExists(JSONArray category, int position, String filter) {
        try {
            return category.getString(position).equals(filter);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void addItems(List<String> items, JSONArray names, int position) {
        try {
            items.add(names.getString(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
