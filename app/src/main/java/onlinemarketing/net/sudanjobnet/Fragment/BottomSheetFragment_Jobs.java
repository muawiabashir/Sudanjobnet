package onlinemarketing.net.sudanjobnet.Fragment;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import onlinemarketing.net.sudanjobnet.R;

/**
 * Created by muawia on 3/26/18.
 */

public class BottomSheetFragment_Jobs extends BottomSheetDialogFragment {
    public BottomSheetFragment_Jobs() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.bottom_sheet_jobs, container, false);

    }
}
