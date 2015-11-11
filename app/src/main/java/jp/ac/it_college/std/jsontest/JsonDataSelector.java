package jp.ac.it_college.std.jsontest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JsonDataSelector {

    public void executeFilter(JSONObject rootObj, String filter, String target, List<String> items) {
        executeFilter(rootObj, rootObj.names(), filter, target, items);
    }

    public void executeFilter(
            JSONObject rootObj, List<String> filters, String target, List<String> items) {
        executeListFilter(rootObj, rootObj.names(), filters, target, items);
    }

    private void executeFilter(JSONObject rootObj, JSONArray names,
                               String filter, String target, List<String> items) {
        if (rootObj == null || names == null) {
            return;
        }

        for (int i = 0; i < names.length(); i++) {
            JSONArray values = getValues(rootObj, names, i, target);
            for (int j = 0; j < values.length(); j++) {
                // フィルターに入力されたタグが付いているKeyをリストに追加
                if (valueExists(values, j, filter)) {
                    addValue(items, names, i);
                }
            }
        }
    }

    private void executeListFilter(JSONObject rootObj, JSONArray names,
                               List<String> filters, String target, List<String> items) {
        if (rootObj == null || names == null) {
            return;
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

    private boolean valueExists(JSONArray values, int position, String filter) {
        try {
            return values.getString(position).equals(filter);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void addValue(List<String> items, JSONArray names, int position) {
        try {
            items.add(names.getString(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
