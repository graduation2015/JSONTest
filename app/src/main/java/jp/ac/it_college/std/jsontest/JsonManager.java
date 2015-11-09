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

public class JsonManager {

    private static final String FILE_NAME = "info.json";
    public static final String TAG = "JsonManager";
    public static final String TEMPLATE_FILE = "template.json";
    public static final String DEFAULT_ENCODING = "UTF-8";

    private Context context;
    private File file;
    private WriteJsonManager writeJsonManager;

    public JsonManager(Context context) {
        this.context = context;

        File dir = createExternalStorageDir(context, Environment.DIRECTORY_DOCUMENTS);
        file = new File(dir, FILE_NAME);

        writeJsonManager = new WriteJsonManager();

        // jsonファイルがない場合作る
        if (!file.exists()) {
            initJsonFile();
        }
    }

    /**
     * JSONファイルを読み込み文字列で返す
     *
     * @param is
     * @return
     */
    private String getJsonStr(InputStream is) {
        String json = null;
        try {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, DEFAULT_ENCODING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * JSONオブジェクトを返す
     *
     * @return
     */
    public JSONObject getJsonObject() {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(getJsonStr(new FileInputStream(file)));
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

            putJsonStr(getJsonStr(template));
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
    public void putJsonStr(String json) {
        try {
            FileOutputStream outputStream = new FileOutputStream(file, true);
            outputStream.write(json.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void putJsonStr(CouponInfo info) {
        try {
            FileOutputStream outputStream = new FileOutputStream(file, true);
            writeJsonManager.writeJsonStream(outputStream, info);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
