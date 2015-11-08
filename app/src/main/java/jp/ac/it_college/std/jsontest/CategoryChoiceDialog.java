package jp.ac.it_college.std.jsontest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class CategoryChoiceDialog extends DialogFragment {

    private ArrayList<String> categories = new ArrayList<>();
    public static final String CHECKED_ITEMS = "checkedItems";
    public static final String ITEMS = "items";
    public static final String TAG = "CategoryChoiceDialog";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] items = getArguments().getStringArray(ITEMS);
        boolean[] checkedItems = getArguments().getBooleanArray(CHECKED_ITEMS);

        return new AlertDialog.Builder(getActivity())
                .setTitle("Select category")
                .setMultiChoiceItems(items, checkedItems, makeMultiChoiceClickListener(items, checkedItems))
                .setPositiveButton("OK", makeConfirmClickListener())
                .setNegativeButton("Cancel", makeCancelClickListener())
                .create();
    }

    /**
     * OK押下時の処理
     * @return
     */
    private DialogInterface.OnClickListener makeConfirmClickListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //チェックしたカテゴリのリストを親フラグメントに渡す
                Intent intent = new Intent();
                intent.putStringArrayListExtra(CHECKED_ITEMS, categories);
                getTargetFragment().onActivityResult(getTargetRequestCode(), i, intent);
            }
        };
    }

    /**
     * キャンセル押下時の処理
     * @return
     */
    private DialogInterface.OnClickListener makeCancelClickListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "makeCancelClickListener");
            }
        };
    }

    /**
     * チェックボックス押下時の処理
     * @param items
     * @param checkedItems
     * @return
     */
    private OnMultiChoiceClickListener makeMultiChoiceClickListener(final String[] items, final boolean[] checkedItems) {
        return new OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                checkedItems[position] = isChecked;
                if (isChecked) {
                    categories.add(items[position]);
                } else {
                    categories.remove(items[position]);
                }
            }
        };
    }

}
