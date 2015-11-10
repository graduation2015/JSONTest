package jp.ac.it_college.std.jsontest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
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
    public static final int REQUEST_CATEGORY = 0x01;


    public static CategoryChoiceDialog newInstance(Fragment targetFragment) {
        CategoryChoiceDialog dialog = new CategoryChoiceDialog();
        dialog.setTargetFragment(targetFragment, REQUEST_CATEGORY);
        dialog.setArguments(dialog.makeArgs(targetFragment.getActivity()));
        return dialog;
    }

    private Bundle makeArgs(Context context) {
        Bundle args = new Bundle();
        String[] items = context.getResources().getStringArray(R.array.categories);
        boolean[] checkedItems = new boolean[items.length];
        args.putStringArray(CategoryChoiceDialog.ITEMS, items);
        args.putBooleanArray(CategoryChoiceDialog.CHECKED_ITEMS, checkedItems);

        return args;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] items = getArguments().getStringArray(ITEMS);
        boolean[] checkedItems = getArguments().getBooleanArray(CHECKED_ITEMS);

        setCancelable(false);
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
    private OnMultiChoiceClickListener makeMultiChoiceClickListener(
            final String[] items, final boolean[] checkedItems) {
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
