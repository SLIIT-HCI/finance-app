package com.example.financemanagementapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpandableListView_bankDetails extends AppCompatActivity {

    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> AccountCollection;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list_view_bank_details);

        createGroupList();
        createCollection();

        expandableListView = findViewById(R.id.expandableLV_Bank);
        expandableListAdapter = new BankExpandibleListAdapter(this, groupList, AccountCollection);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            //declaring the last expanded position as -1
            int lastExpandiblePosition = -1;
            //collapsing the previously expanded Item.
            @Override
            public void onGroupExpand(int i) {
                if(lastExpandiblePosition != -1 && i != lastExpandiblePosition){
                    expandableListView.collapseGroup(lastExpandiblePosition);
                }
                lastExpandiblePosition = i;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String selected = expandableListAdapter.getChild(i, i1).toString();
                Toast.makeText(getApplicationContext(), "Selected" + selected, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void createCollection() {
        String [] Account1Model = {"Account Holder", "Bank", "Branch", "Account Number"};
        String [] Account2Model = {"Account Holder", "Bank", "Branch", "Account Number"};
        String [] Account3Model = {"Account Holder", "Bank", "Branch", "Account Number"};
        String [] Account4Model = {"Account Holder", "Bank", "Branch", "Account Number"};
        String [] Account5Model = {"Account Holder", "Bank", "Branch", "Account Number"};

        AccountCollection = new HashMap<String, List<String>>();

        for(String group : groupList){
            if(group.equals("Account1")){
                loadChild(Account1Model);
            }else if(group.equals("Account2")){
                loadChild(Account2Model);
            }else if(group.equals("Account3")){
                loadChild(Account3Model);
            }else if(group.equals("Account4")) {
                loadChild(Account4Model);
            }else if(group.equals("Account5")){
                loadChild(Account5Model);
            }
            AccountCollection.put(group, childList);
        }
    }

    private void loadChild(String[] accountModel) {
        childList = new ArrayList<>();
        for(String model : accountModel){
            childList.add(model);
        }
    }

    private void createGroupList() {
        groupList = new ArrayList<>();
        groupList.add("Account1");
        groupList.add("Account2");
        groupList.add("Account3");
        groupList.add("Account4");
        groupList.add("Account5");

    }
}