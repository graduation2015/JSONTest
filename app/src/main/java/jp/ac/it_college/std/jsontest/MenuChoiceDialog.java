package jp.ac.it_college.std.jsontest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

public class MenuChoiceDialog extends ChoiceDialog {
    @Override
    protected Bundle makeArgs(Context context) {
        Bundle args = new Bundle();
        String[] items = context.getResources().getStringArray(R.array.action_menu_items);
        args.putStringArray(ITEMS, items);
        return args;
    }

    @Override
    protected Dialog makeDialog() {
        String[] items = getArguments().getStringArray(ITEMS);

        return new AlertDialog.Builder(getActivity())
                .setItems(items, makeItemClickListener())
                .create();

    }

    private DialogInterface.OnClickListener makeItemClickListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                getTargetFragment().onActivityResult(getTargetRequestCode(), which, null);
            }
        };
    }
}
