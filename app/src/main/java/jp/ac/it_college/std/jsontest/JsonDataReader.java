package jp.ac.it_college.std.jsontest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JsonDataReader {

    public void executeFilter(JSONObject rootObj, String filter, String target, List<String> items) {
        executeFilter(rootObj, rootObj.names(), filter, target, items);
    }

    private void executeFilter(JSONObject rootObj, JSONArray names,
                               String filter, String target, List<String> items) {
        if (rootObj == null || names == null) {
            return;
        }

        items.clear();
        for (int i = 0; i < names.length(); i++) {
            JSONArray category = getValues(rootObj, names, i, target);
            for (int j = 0; j < category.length(); j++) {
                // フィルターに入力されたタグが付いている画像名をリストに追加
                if (categoryExists(category, j, filter)) {
                    addItems(items, names, i);
                }
            }
        }
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
