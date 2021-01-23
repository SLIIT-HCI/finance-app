package com.example.financemanagementapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class SMSIntegrationFragment extends Fragment {

    public static SMSIntegrationFragment newInstance() {
        return (new SMSIntegrationFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sms_integration, container, false);
    }
}