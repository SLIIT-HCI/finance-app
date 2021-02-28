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

    public static MainDashboardFragment newInstance() {
        return (new MainDashboardFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_dashboard, container, false);

        //FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        //fragmentTransaction.replace(R.id.fragment, new DashboardFragment());
        //fragmentTransaction.commit();

        Button dashboard = (Button)view.findViewById(R.id.dashboard);
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new DashboardFragment());
                fragmentTransaction.commit();
            }
        });

        Button transactions = (Button)view.findViewById(R.id.transactions);
        transactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new TransactionsFragment());
                fragmentTransaction.commit();
            }
        });

//        Button balanceSheet = (Button)view.findViewById(R.id.balanceSheet);
//        balanceSheet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment, new BalanceSheetFragment());
//                fragmentTransaction.commit();
//            }
//        });

//        Button netEarningsSummary = (Button)view.findViewById(R.id.netEarningsSummary);
//        netEarningsSummary.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment, new NetEarningsFragment());
//                fragmentTransaction.commit();
//            }
//        });

//        Button netWorthSummary = (Button)view.findViewById(R.id.netWorthSummary);
//        netWorthSummary.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment, new NetWorthFragment());
//                fragmentTransaction.commit();
//            }
//        });

        return view;

    }

}