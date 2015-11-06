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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_category_filter, container, false);
        contentView.findViewById(R.id.btn_load_json).setOnClickListener(this);
        setListAdapter(new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, items));

        filter = (EditText) contentView.findViewById(R.id.edit_filter);

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
        items.clear();
        JSONObject object = JSONManager.getJSONObject(getActivity());
        JSONArray keys = object.names();

        try {
            for (int i = 0; i < keys.length(); i++) {
                JSONArray category = object.getJSONObject(keys.getString(i)).getJSONArray("category");
                for (int j = 0; j < category.length(); j++) {
                    // フィルターに入力されたタグが付いている画像名をリストに追加
                    if (category.getString(j).equals(filter.getText().toString())) {
                        items.add(keys.getString(i));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ((ArrayAdapter) getListAdapter()).notifyDataSetChanged();
    }
}
