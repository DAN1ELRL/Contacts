package com.example.daniel.innocv.UI.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.daniel.innocv.Model.User;
import com.example.daniel.innocv.R;
import com.example.daniel.innocv.UI.Activities.MainActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class DetailUser extends Fragment {

    @Bind(R.id.idText) TextView idText;
    @Bind(R.id.nameText) TextView nameText;
    @Bind(R.id.birthdateText) TextView birthText;

    private ArrayList<User> userList;

    public DetailUser() {
        // Required empty public constructor
    }


    public static DetailUser newInstance(ArrayList<User> usr) {
        DetailUser fragment = new DetailUser();
        Bundle args = new Bundle();
        args.putParcelableArrayList("list", usr);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).getFab().hide();
        ((MainActivity)getActivity()).getArrowBack().setVisibility(VISIBLE);
        ((MainActivity)getActivity()).getDeleteIcon().setVisibility(VISIBLE);
        ((MainActivity)getActivity()).getEditIcon().setVisibility(VISIBLE);
        ((MainActivity)getActivity()).getUndoIcon().setVisibility(GONE);
        if (getArguments() != null) {
            userList = getArguments().getParcelableArrayList("list");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_user, container, false);
        ButterKnife.bind(this, view);
        if(userList!=null){
            User user = userList.get(0);
            idText.setText(user.getId());
            nameText.setText(user.getName());
            String date = user.getBirthdate().split("T")[0];
            String dateOcc = date.split("-")[2]+"-"+date.split("-")[1]+"-"+date.split("-")[0];

            birthText.setText(dateOcc);
        }

        return view;
    }

}
