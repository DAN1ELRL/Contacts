package com.example.daniel.innocv.UI.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.daniel.innocv.Model.User;
import com.example.daniel.innocv.R;
import com.example.daniel.innocv.UI.Activities.MainActivity;
import com.example.daniel.innocv.UI.Utils.InputFilterMinMax;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class EditUser extends Fragment {

    @Bind(R.id.editName)EditText name;
    @Bind(R.id.editDay)EditText day;
    @Bind(R.id.editMonth)EditText month;
    @Bind(R.id.editYear)EditText year;

    private ArrayList<User> userList;
    private static boolean modeEdit = false;

    public EditUser() {
        // Required empty public constructor
    }


    public static EditUser newInstance(ArrayList<User> u) {
        EditUser fragment = new EditUser();
        Bundle args = new Bundle();
        args.putParcelableArrayList("list", u);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).getFab().hide();
        ((MainActivity)getActivity()).getArrowBack().setVisibility(VISIBLE);
        ((MainActivity)getActivity()).getUndoIcon().setVisibility(VISIBLE);
        ((MainActivity)getActivity()).getDeleteIcon().setVisibility(GONE);
        ((MainActivity)getActivity()).getEditIcon().setVisibility(GONE);

        if (getArguments() != null) {
            userList = getArguments().getParcelableArrayList("list");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_user, container, false);

        ButterKnife.bind(this, view);
        day.setFilters(new InputFilter[]{new InputFilterMinMax("1", "31")});
        month.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "12")});
        year.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "2016")});
        if(userList!=null){
            modeEdit = true;
            name.setText(userList.get(0).getName());
            String date = userList.get(0).getBirthdate().split("T")[0];
            year.setText(date.split("-")[0]);
            month.setText(date.split("-")[1]);
            day.setText(date.split("-")[2]);

        }else{
            modeEdit = false;
            ((MainActivity)getActivity()).getUndoIcon().setVisibility(GONE);
        }

        ((MainActivity)getActivity()).getUndoIcon().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(userList!=null){
                    User usr = userList.get(0);
                    name.setText(usr.getName());
                    String date = usr.getBirthdate().split("T")[0];
                    year.setText(date.split("-")[0]);
                    month.setText(date.split("-")[1]);
                    day.setText(date.split("-")[2]);
                }
            }
        });

        return view;
    }

    @OnClick(R.id.savebtn)
    public void save(View v){
        User usr = new User();
        usr.setName(name.getText().toString());
        String date = year.getText().toString() + "-" + month.getText().toString() + "-" + day.getText().toString();
        usr.setBirthdate(date);
        if(modeEdit){
            usr.setId(userList.get(0).getId());
        }
        ((MainActivity)getActivity()).onClickSaveUser(v, usr);
    }



}
