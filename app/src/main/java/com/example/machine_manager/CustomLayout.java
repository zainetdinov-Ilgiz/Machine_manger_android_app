package com.example.machine_manager;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomLayout {

    TextView forDetailNumber;
    TextView forOperationNumber;
    TextView forOrderNumber;
    TextView forQuantityDetail;
    TextView forDateOfBeginning;
    TextView forDateOfEnding;
    View inflate;

    @Nullable

    public View onCreateView(@NonNull LayoutInflater inflater,  String[] data) {

        inflate = inflater.inflate(R.layout.custom_layout2,null);
        forDetailNumber = inflate.findViewById(R.id.foDetailNumber);
        forOperationNumber = inflate.findViewById(R.id.forOperationNumber);
        forOrderNumber = inflate.findViewById(R.id.forOrderNumber);
        forQuantityDetail = inflate.findViewById(R.id.forQuantityDetail);
        forDateOfBeginning = inflate.findViewById(R.id.forDateOfBeginning);
        forDateOfEnding = inflate.findViewById(R.id.forDateOfEnding);
        forDetailNumber.setText(data[0]);
        forOperationNumber.setText(data[1]);
        forOrderNumber.setText(data[2]);
        forQuantityDetail.setText(data[3]);
        forDateOfBeginning.setText(data[4]);
        forDateOfEnding.setText(data[5]);
        return inflate;
    }
}
