package sha.com.ind.labapp.dump.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import sha.com.ind.labapp.R;

/**
 * Created by sree on 13/06/17.
 */

public class DialogFragment extends android.support.v4.app.DialogFragment {

    String title = "Please select an option";
    String message;

    private static final Integer DISPOSITION_POSITIVE = 1;
    private static final Integer DISPOSITION_NEUTRAL = 2;
    private static final Integer DISPOSITION_NEGATIVE = 3;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);

        if (title != null) {
            builder.setTitle(title);
        }

        if (message != null) {
            builder.setMessage(Html.fromHtml(message));
        }


//        ArrayList<Integer> buttonTitleResIds = args.getIntegerArrayList(ARG_BUTTON_TITLE_RES_IDS);
//        ArrayList<Integer> buttonDispositions = args.getIntegerArrayList(ARG_BUTTON_DISPOSITIONS);
//
//        for (int i = 0; i < buttonTitleResIds.size(); i++) {
//            final int buttonIndex = i;
//            addButton(builder, buttonTitleResIds.get(i), buttonDispositions.get(i), new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    ((AlertDialogListener)getActivity()).onAlertButtonClick(buttonIndex);
//                }
//            });
//        }

        addButton(builder, R.string.ok, DISPOSITION_POSITIVE, null);
        String items[] = {"one", "two"};
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }

    private void addButton(AlertDialog.Builder builder, int titleResId,
            Integer disposition, DialogInterface.OnClickListener listener) {

        if (DISPOSITION_POSITIVE.equals(disposition)) {
            builder.setPositiveButton(titleResId, listener);
        } else if (DISPOSITION_NEGATIVE.equals(disposition)) {
            builder.setNegativeButton(titleResId, listener);
        } else {
            builder.setNeutralButton(titleResId, listener);
        }
    }

    private static Integer integerFromDisposition(Disposition disposition) {
        switch (disposition) {
            case POSITIVE:
                return DISPOSITION_POSITIVE;
            case NEUTRAL:
                return DISPOSITION_NEUTRAL;
            case NEGATIVE:
                return DISPOSITION_NEGATIVE;
        }

        throw new IllegalArgumentException("Unknown disposition value");
    }



    enum Disposition {
        POSITIVE,
        NEUTRAL,
        NEGATIVE
    }

}
