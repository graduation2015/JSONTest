package jp.ac.it_college.std.jsontest;

import org.json.JSONArray;

public class JsonDataRemover {

    //Jsonデータを削除
    public void remove(JSONArray target, int position) {
        target.remove(position);
    }
}
