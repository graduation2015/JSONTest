package jp.ac.it_college.std.jsontest;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class JsonManager {

    private static final String FILE_NAME = "info.json";
    public static final String TEMPLATE_FILE = "template.json";
    public static final String TAG = "JsonManager";

    private Context context;
    private File file;
    private JsonDataWriter jsonDataWriter;
    private JsonDataSelector jsonDataSelector;
    private JsonDataReader jsonDataReader;

    public JsonManager(Context context) {
        this.context = context;

        File dir = createExternalStorageDir(context, Environment.DIRECTORY_DOCUMENTS);
        file = new File(dir, FILE_NAME);

        jsonDataWriter = new JsonDataWriter();
        jsonDataSelector = new JsonDataSelector();
        jsonDataReader = new JsonDataReader();

        // jsonファイルがない場合作る
        if (!file.exists()) {
            initJsonFile();
        }
    }

    public File getFile() {
        return file;
    }

    /**
     * JSONオブジェクトを返す
     *
     * @return
     */
    public JSONObject getJsonRootObject() {
        JSONObject jsonObject = null;

        try {
            InputStream is = new FileInputStream(getFile());
            jsonObject = new JSONObject(jsonDataReader.getJsonStr(is));
        } catch (JSONException | FileNotFoundException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    /**
     * 外部ストレージに空のJSONファイルを新しく作成する
     *
     * @return
     */
    private void initJsonFile() {
        if (isExternalStorageWritable()) {
            InputStream template = null;

            try {
                template = context.getAssets().open(TEMPLATE_FILE);
            } catch (IOException e) {
                e.printStackTrace();
            }

            putJsonStr(jsonDataReader.getJsonStr(template));
            Toast.makeText(context, "File was created", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 外部ストレージにディレクトリを作成する
     *
     * @param context
     * @param dirType Environmentのディレクトリータイプ
     * @return
     */
    private File createExternalStorageDir(Context context, String dirType) {
        File file = new File(context.getExternalFilesDir(dirType).getPath());

        if (!file.mkdirs()) {
            Log.d(TAG, "Directory not created");
        }

        return file;
    }

    /**
     * 読み書き可能な外部ストレージをチェック
     *
     * @return
     */
    private boolean isExternalStorageWritable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 少なくとも読み取りは可能な外部ストレージか、チェック
     *
     * @return
     */
    private boolean isExternalStorageReadable() {
        return isExternalStorageWritable()
                || Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState());
    }

    /**
     * JSONファイルに書き込む
     * @param json
     */
    private void putJsonStr(String json) {
/*        try {
            FileOutputStream outputStream = new FileOutputStream(file, false);
            outputStream.write(json.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        jsonDataWriter.putJsonStr(getFile(), json);
    }

    public void putJsonStr(JSONObject rootObj, CouponInfo info) {
        try {
            FileOutputStream outputStream = new FileOutputStream(file, false);
            jsonDataWriter.writeJson(outputStream, rootObj, info);
            outputStream.close();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    public void executeListFilter(
            JSONObject rootObject, List<String> filters, String target, List<String> items) {
        jsonDataSelector.executeFilter(rootObject, filters, target, items);
    }

    public void executeFilter(
            JSONObject rootObject, String filter, String target, List<String> items) {
        jsonDataSelector.executeFilter(rootObject, filter, target, items);
    }

}
