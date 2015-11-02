package jp.ac.it_college.std.jsontest;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity implements View.OnClickListener {

    private List<String> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_load_json).setOnClickListener(this);
        setListAdapter(new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, items
        ));
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
        JSONObject object = getJSONObject();
        JSONArray array = object.names();

        for (int i = 0; i < array.length(); i++) {
            try {
                items.add(array.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private String getJSONStr() {
        String json = null;
        try {
            InputStream is = getAssets().open("tags.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private JSONObject getJSONObject() {
        try {
            return new JSONObject(getJSONStr());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
