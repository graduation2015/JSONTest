package jp.ac.it_college.std.jsontest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class CategoryChoiceDialogFragment extends DialogFragment {

    private ArrayList<String> items = new ArrayList<>();
    public static final String CHECKED_CATEGORY = "CHECKED_CATEGORY";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] categories = getActivity().getResources().getStringArray(R.array.categories);

        setCancelable(false);
        return new AlertDialog.Builder(getActivity())
                .setTitle("Select category")
                .setMultiChoiceItems(categories, null, makeMultiChoiceClickListener(categories))
                .setPositiveButton("OK", makeConfirmClickListener())
                .setNegativeButton("Cancel", makeCancelClickListener())
                .create();
    }

    private DialogInterface.OnClickListener makeConfirmClickListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //チェックしたカテゴリのリストを親フラグメントに渡す
                Intent intent = new Intent();
                intent.putStringArrayListExtra(CHECKED_CATEGORY, items);
                getTargetFragment().onActivityResult(getTargetRequestCode(), i, intent);
            }
        };
    }

    private DialogInterface.OnClickListener makeCancelClickListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //リストの要素を全て削除する
                items.clear();
            }
        };
    }

    private DialogInterface.OnMultiChoiceClickListener makeMultiChoiceClickListener(final String[] categories) {
        return new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                if (isChecked) {
                    items.add(categories[position]);
                } else {
                    items.remove(categories[position]);
                }
            }
        };
    }

}
