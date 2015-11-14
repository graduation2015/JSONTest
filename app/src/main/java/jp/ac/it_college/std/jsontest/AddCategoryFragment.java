package jp.ac.it_college.std.jsontest;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AddCategoryFragment extends Fragment
        implements View.OnClickListener {

    private JsonManager jsonManager;

    private List<String> categories = new ArrayList<>();
    private ChoiceDialog multipleChoiceDialog;
    private static final String COMPANY_NAME = "company01";
    private static final String COMPANY_ADDRESS = "okinawa";

    private EditText keyName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_add_category, container, false);
        findViews(contentView);

        jsonManager = new JsonManager(getActivity());
        multipleChoiceDialog = ChoiceDialog.newInstance(this, new CategoryMultipleChoiceDialog());
        return contentView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_category:
                addCategory();
                break;
            case R.id.btn_select_category_multiple:
                showCategoryChoiceDialog();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CategoryMultipleChoiceDialog.REQUEST_ITEMS) {
            switch (resultCode) {
                case DialogInterface.BUTTON_POSITIVE:
                    setCategories(data);
                    break;
            }
        }
    }

    private void setCategories(Intent data) {
        List<String> checkedCategories =
                data.getStringArrayListExtra(CategoryMultipleChoiceDialog.CHECKED_ITEMS);
        categories.clear();
        categories.addAll(checkedCategories);
    }

    private List<String> getCategories() {
        return categories;
    }

    private String getKeyName() {
        return keyName.getText().toString();
    }

    private void findViews(View contentView) {
        contentView.findViewById(R.id.btn_add_category).setOnClickListener(this);
        contentView.findViewById(R.id.btn_select_category_multiple).setOnClickListener(this);
        keyName = (EditText) contentView.findViewById(R.id.edit_key_name);
    }

    private void showCategoryChoiceDialog() {
        multipleChoiceDialog.show(getFragmentManager(), "multipleChoiceDialog");
    }

    /**
     * JSONファイルにJSONオブジェクトを追加/更新
     */
    private void addCategory() {
        JSONObject rootObject = jsonManager.getJsonRootObject();
        CouponInfo info = new CouponInfo(
                getKeyName(), COMPANY_NAME, COMPANY_ADDRESS, getCategories());

        jsonManager.putJsonObj(rootObject, info);
    }

}
