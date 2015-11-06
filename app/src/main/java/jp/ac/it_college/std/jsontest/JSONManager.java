package jp.ac.it_college.std.jsontest;

import android.content.Context;
import android.os.Environment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class JSONManager {

    private static final String DIRECTORY_PATH =
            Environment.getExternalStorageDirectory().getPath();
    private static final String FILE_NAME = "/categories.json";

    private static String getJSONStr(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("tags.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            createJSON();
            ex.printStackTrace();
            return getJSONStr(context);
        }
        return json;
    }

    public static JSONObject getJSONObject(Context context) {
        try {
            return new JSONObject(getJSONStr(context));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void createJSON() {
        //TODO: 内部ストレージにディレクトリ作成する処理を実装
    }

}
