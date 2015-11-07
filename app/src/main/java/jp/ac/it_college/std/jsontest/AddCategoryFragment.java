package jp.ac.it_college.std.jsontest;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;


public class AddCategoryFragment extends Fragment
        implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private List<String> categories = new ArrayList<>();
    private JSONManager jsonManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_add_category, container, false);
        findViews(contentView);

        jsonManager = new JSONManager(getActivity());
        return contentView;
    }

    private void findViews(View contentView) {
        ((CheckBox) contentView.findViewById(R.id.checkbox_book)).setOnCheckedChangeListener(this);
        ((CheckBox) contentView.findViewById(R.id.checkbox_food)).setOnCheckedChangeListener(this);
        ((CheckBox) contentView.findViewById(R.id.checkbox_game)).setOnCheckedChangeListener(this);
        ((CheckBox) contentView.findViewById(R.id.checkbox_video)).setOnCheckedChangeListener(this);
        contentView.findViewById(R.id.btn_add_category).setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            categories.add(compoundButton.getText().toString());
        } else {
            categories.remove(compoundButton.getText().toString());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_category:
                addCategory();
                break;
        }
    }

    private void addCategory() {
//        jsonManager.createJSONObject();
    }
}
