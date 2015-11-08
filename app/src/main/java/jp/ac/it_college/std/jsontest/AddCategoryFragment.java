package jp.ac.it_college.std.jsontest;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class AddCategoryFragment extends Fragment
        implements View.OnClickListener {

    private JSONManager jsonManager;

    private List<String> categories = new ArrayList<>();
    private CategoryChoiceDialogFragment dialog;
    public static final int REQUEST_CATEGORY = 0x01;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_add_category, container, false);
        findViews(contentView);

        jsonManager = new JSONManager(getActivity());
        return contentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dialog = makeCategoryChoiceDialog();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_category:
                addCategory();
                break;
            case R.id.btn_select_category:
                showCategoryChoiceDialog();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CATEGORY) {
            switch (resultCode) {
                case DialogInterface.BUTTON_POSITIVE:
                    setCategories(data);
                    break;
            }
        }
    }

    private void setCategories(Intent data) {
        List<String> checkedCategories = data.getStringArrayListExtra(CategoryChoiceDialogFragment.CHECKED_CATEGORY);
        categories.clear();
        categories.addAll(checkedCategories);
    }

    private void findViews(View contentView) {
        contentView.findViewById(R.id.btn_add_category).setOnClickListener(this);
        contentView.findViewById(R.id.btn_select_category).setOnClickListener(this);
    }

    private void showCategoryChoiceDialog() {
        dialog.show(getFragmentManager(), "dialog");
    }

    private void addCategory() {
    }

    private CategoryChoiceDialogFragment makeCategoryChoiceDialog() {
        CategoryChoiceDialogFragment dialog = new CategoryChoiceDialogFragment();
        dialog.setTargetFragment(this, REQUEST_CATEGORY);

        return dialog;
    }
}
