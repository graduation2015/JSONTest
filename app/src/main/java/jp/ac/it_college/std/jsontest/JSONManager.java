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

public class JSONManager {

    private static final String FILE_NAME = "info.json";
    public static final String TAG = "JSONManager";
    public static final String TEMPLATE_FILE = "template.json";
    public static final String DEFAULT_ENCODING = "UTF-8";

    private Context context;

    public JSONManager(Context context) {
        this.context = context;
    }

    /**
     * JSONファイルを読み込み文字列で返す
     * @param is
     * @return
     */
    private String getJSONStr(InputStream is) {
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
     * @return
     */
    public JSONObject getJSONObject() {
        File dir = createExternalStorageDir(context, Environment.DIRECTORY_DOCUMENTS);
        File file = new File(dir, FILE_NAME);

        return getJSONObject(file);
    }

    /**
     * 外部ストレージ上にJSONファイルが既に存在するかチェックし、JSONオブジェクトを返す
     * @param file
     * @return
     */
    private JSONObject getJSONObject(File file) {
        JSONObject jsonObject = null;

        // JSONファイルが既に存在するかチェック
        if (file.exists()) {
            //存在する場合
            try {
                jsonObject = new JSONObject(getJSONStr(new FileInputStream(file)));
            } catch (JSONException | FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            //存在しない場合
            try {
                jsonObject = new JSONObject(createJSONObject(file));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return jsonObject;
    }

    /**
     * 外部ストレージにJSONファイルを新しく作成する
     * @param file
     * @return
     */
    private String createJSONObject(File file) {
        String json = null;
        if (isExternalStorageWritable()) {
            try {
                InputStream template = context.getAssets().open(TEMPLATE_FILE);
                json = getJSONStr(template);

                FileOutputStream outputStream = new FileOutputStream(file, true);
                outputStream.write(json.getBytes());
                outputStream.close();
                Toast.makeText(context, "File was created", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(context, "File was not created", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        return json;
    }

    /**
     * 外部ストレージにディレクトリを作成する
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
     * @return
     */
    private boolean isExternalStorageWritable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 少なくとも読み取りは可能な外部ストレージか、チェック
     * @return
     */
    private boolean isExternalStorageReadable() {
        return isExternalStorageWritable()
                || Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState());
    }

}
