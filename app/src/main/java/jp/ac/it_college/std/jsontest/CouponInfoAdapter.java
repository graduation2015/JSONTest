package jp.ac.it_college.std.jsontest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.List;

public class CouponInfoAdapter extends ArrayAdapter<CouponInfo> {

    private Context context;
    private int resource;
    private List<CouponInfo> objects;

    public CouponInfoAdapter(Context context, int resource, List<CouponInfo> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(resource, null);
        }

        CouponInfo info = objects.get(position);

        if (info != null) {
            TextView key = (TextView) view.findViewById(R.id.lbl_object_key);
            TextView name = (TextView) view.findViewById(R.id.lbl_object_name);
            TextView address = (TextView) view.findViewById(R.id.lbl_object_address);
            TextView category = (TextView) view.findViewById(R.id.lbl_object_category);

            key.setText("key: " + info.getKey());
            name.setText("name: " + info.getName());
            address.setText("address: " + info.getAddress());
            category.setText("category: " + info.getCategory().toString());
        }

        return view;
    }
}
