package com.example.financemanagementapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainDashboardFragment extends Fragment {

    //FOR FRAGMENTS
    private Fragment fragmentCategories;
    private Fragment fragmentSMSIntegration;
    private Fragment fragmentPayablesAndReceivables;
    private Fragment fragmentMainDashboard;

    //FOR DATAS
    private static final int FRAGMENT_MAINDASHBOARD = 0;
    private static final int FRAGMENT_CATEGORIES = 1;
    private static final int FRAGMENT_SMSINTEGRATION = 2;
    private static final int FRAGMENT_PAYABLESANDRECEIVABLES = 3;

    public static MainDashboardFragment newInstance() {
        return (new MainDashboardFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main_dashboard, container, false);
    }

    /*
    public void changeFragment(View view){
        Fragment fragment;
        if (view == view.findViewById(R.id.dashboard)){
            fragment = new MainDashboardFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment,fragment);
            ft.commit();
        }
        if (view == view.findViewById(R.id.transactions)){
            fragment = new TransactionsFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment,fragment);
            ft.commit();
        }
    }




    @Override
    public void onClick(View view) {
        int id = item.getItemId();

        switch (id){
            case R.id.mainDashboard:
                this.showFragment(FRAGMENT_MAINDASHBOARD);
                break;
            case R.id.categories:
                this.showFragment(FRAGMENT_CATEGORIES);
                break;
            case R.id.SMSIntegration:
                this.showFragment(FRAGMENT_SMSINTEGRATION);
                break;
            case R.id.payablesAndReceivables:
                this.showFragment(FRAGMENT_PAYABLESANDRECEIVABLES);
                break;
            default:
                break;
        }


    }

     */
}