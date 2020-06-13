package com.example.tp_ihc_android.ui.justification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tp_ihc_android.R;

public class JustificationFragment extends Fragment {

    private JustificationViewModel justificationViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        justificationViewModel =
                ViewModelProviders.of(this).get(JustificationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_justification, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        justificationViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}