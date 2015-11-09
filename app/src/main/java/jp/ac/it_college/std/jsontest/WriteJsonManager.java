package jp.ac.it_college.std.jsontest;

import android.util.JsonWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class WriteJsonManager {

    public static final String DEFAULT_INDENT = "  ";

    public void writeJsonStream(OutputStream out, CouponInfo info) throws IOException {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, JsonManager.DEFAULT_ENCODING));
        writer.setIndent(DEFAULT_INDENT);
        writeKey(writer, info);
        writer.close();
    }

    private void writeKey(JsonWriter writer, CouponInfo info) throws IOException {
        writer.beginObject();
        writer.name(info.getKey());
        writeInfo(writer, info);
        writer.endObject();
    }

    private void writeInfo(JsonWriter writer, CouponInfo info) throws IOException {
        writer.beginObject();
        writer.name("company_name").value(info.getCompanyName());
        writer.name("company_address").value(info.getAddress());
        writeCategory(writer, info.getCategory());
        writer.endObject();
    }

    private void writeCategory(JsonWriter writer, List<String> items) throws IOException {
        writer.name("category");

        writer.beginArray();
        for (String value : items) {
            writer.value(value);
        }
        writer.endArray();
    }

}
