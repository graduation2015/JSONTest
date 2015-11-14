package jp.ac.it_college.std.jsontest;


import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryFilterFragment extends ListFragment implements View.OnClickListener {

    private List<String> items = new ArrayList<>();
    private List<String> checkedCategories = new ArrayList<>();
    private String checkedCategory;
    private JsonManager jsonManager;
    private ChoiceDialog multipleChoiceDialog;
    private ChoiceDialog singleChoiceDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_category_filter, container, false);
        findViews(contentView);

        setListAdapter(new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, items));

        jsonManager = new JsonManager(getActivity());
        multipleChoiceDialog = ChoiceDialog.newInstance(this, new CategoryMultipleChoiceDialog());
        singleChoiceDialog = ChoiceDialog.newInstance(this, new CategorySingleChoiceDialog());

        return contentView;
    }

    private void findViews(View contentView) {
        contentView.findViewById(R.id.btn_load_json).setOnClickListener(this);
        contentView.findViewById(R.id.btn_select_category_multiple).setOnClickListener(this);
        contentView.findViewById(R.id.btn_select_category_single).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_load_json:
                loadJSON();
                break;
            case R.id.btn_select_category_multiple:
                showCategoryChoiceDialog();
                break;
            case R.id.btn_select_category_single:
                showCategorySingleChoiceDialog();
                break;
        }
    }

    private void showCategorySingleChoiceDialog() {
        singleChoiceDialog.show(getFragmentManager(), "singleChoice");
    }

    /**
     * フィルターを実行してJSONを読み込む
     */
    private void loadJSON() {
        JSONObject rootObject = jsonManager.getJsonRootObject();
        executeCategoryFilter(rootObject);
    }

    /**
     * フィルターを実行してリストを更新
     * @param rootObject
     */
    private void executeCategoryFilter(JSONObject rootObject) {
        items.clear();
//        jsonManager.executeFilter(rootObject, getFilter(), CouponInfo.CATEGORY, items);
        jsonManager.executeFilter(
                rootObject, getCheckedCategory(), CouponInfo.CATEGORY, items);

        //リスト更新
        ((ArrayAdapter) getListAdapter()).notifyDataSetChanged();
    }

    private String getCheckedCategory() {
        return checkedCategory;
    }

    private void showCategoryChoiceDialog() {
        multipleChoiceDialog.show(getFragmentManager(), "multipleChoiceDialog");
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

    /**
     * ダイアログで選択したカテゴリをセット
     * @param data
     */
    private void setCategories(Intent data) {
/*
        //Multiple用
        List<String> checkedCategories = data.getStringArrayListExtra(CategoryMultipleChoiceDialog.CHECKED_ITEMS);
        this.checkedCategories.clear();
        this.checkedCategories.addAll(checkedCategories);
*/

        //Single用
        this.checkedCategory = data.getStringExtra(CategoryMultipleChoiceDialog.CHECKED_ITEMS);
    }

}
