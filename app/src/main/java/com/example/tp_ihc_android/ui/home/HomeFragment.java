package com.example.tp_ihc_android.ui.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tp_ihc_android.PresencasSingleton;
import com.example.tp_ihc_android.R;
import com.example.tp_ihc_android.ui.history.HistoryViewModel;

import org.w3c.dom.Text;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    TextView numeroPresencas;
    Button marcarPresenca;

    private PresencasSingleton presencas;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        presencas = PresencasSingleton.getInstance();

        numeroPresencas = root.findViewById(R.id.numero_presencas);
        numeroPresencas.setText(presencas.getPresencasSemana());

        marcarPresenca = root.findViewById(R.id.btnMarcarPresenca);
        marcarPresenca.setOnClickListener(myOnclickListener);

        return root;
    }

    private View.OnClickListener myOnclickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnMarcarPresenca:
                    if(presencas.presencaMarcadaHoje())
                        showAlertDialogPresencaMarcada(v, R.string.presenca_ja_marcada);
                    else{
                        if(presencas.marcarPresenca())
                            showAlertDialogPresencaMarcada(v, R.string.presenca_marcada);
                        else
                            showAlertDialogNaoPossivelMarcarPresenca(v, R.string.presenca_final_de_semana);
                    }


                    numeroPresencas.setText(presencas.getPresencasSemana());
                    break;
            }
        }
    };

    public void showAlertDialogPresencaMarcada(View view, int stringToShow) {
        final Dialog dialog = new Dialog(this.getContext());
        dialog.setContentView(R.layout.dialog_marcar_presenca);

        Button button = (Button) dialog.findViewById(R.id.btnCloseDialog);
        TextView tv = (TextView) dialog.findViewById(R.id.TextView01);
        tv.setTextColor(getResources().getColor(R.color.green));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tv.setText(stringToShow);
        dialog.show();
    }

    public void showAlertDialogNaoPossivelMarcarPresenca(View view, int stringToShow) {
        final Dialog dialog = new Dialog(this.getContext());
        dialog.setContentView(R.layout.dialog_marcar_presenca);

        Button button = (Button) dialog.findViewById(R.id.btnCloseDialog);
        ImageView iv = (ImageView) dialog.findViewById(R.id.ImageView01);
        iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_close_24));
        TextView tv = (TextView) dialog.findViewById(R.id.TextView01);
        tv.setTextColor(getResources().getColor(R.color.colorPrimary));

        tv.setText(stringToShow);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
