package com.example.tp_ihc_android.ui.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tp_ihc_android.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    Button marcarPresenca;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        marcarPresenca = root.findViewById(R.id.btnMarcarPresenca);

        marcarPresenca.setOnClickListener(myOnclickListener);
        return root;
    }

    private View.OnClickListener myOnclickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnMarcarPresenca:
                    showAlertDialogPresencaMarcada(v);
                    break;
            }
        }
    };


    public void showAlertDialogPresencaMarcada(View view) {
        final Dialog dialog = new Dialog(this.getContext());
        dialog.setContentView(R.layout.dialog_marcar_presenca);

        Button button = (Button) dialog.findViewById(R.id.btnCloseDialog);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
