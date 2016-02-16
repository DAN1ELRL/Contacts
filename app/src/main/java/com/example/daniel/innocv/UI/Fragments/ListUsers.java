package com.example.daniel.innocv.UI.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.daniel.innocv.Model.User;
import com.example.daniel.innocv.R;
import com.example.daniel.innocv.UI.Activities.MainActivity;
import com.example.daniel.innocv.UI.Adapters.UsersListAdapter;
import com.example.daniel.innocv.UI.Utils.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListUsers extends Fragment  implements SearchView.OnQueryTextListener{

    @Bind(R.id.swipe) public SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.list) RecyclerView recyclerView;
    private static ArrayList<User> users;
    private UsersListAdapter adapter;
    private Context context;
    private View view;

    public ListUsers() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ListUsers newInstance(ArrayList<User> usr) {
        ListUsers fragment = new ListUsers();
        Bundle args = new Bundle();
        args.putParcelableArrayList("list", usr);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            users = getArguments().getParcelableArrayList("list");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_users, container, false);
        ButterKnife.bind(this, view);
        loadSwipeRefreh();
        context = view.getContext();
        setHasOptionsMenu(true);
        return view;
    }

    public void loadDetailUserFragment(ArrayList<User> list){
        ((MainActivity)getActivity()).expandToolbar();
        DetailUser createUser = DetailUser.newInstance(list);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, createUser).commit();
    }

    private void loadRecyclerView(){

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        registerForContextMenu(recyclerView);

        adapter = new UsersListAdapter(users);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User u = users.get(recyclerView.getChildPosition(v));
                ArrayList<User> list = new ArrayList<User>();
                list.add(u);
                loadDetailUserFragment(list);

            }
        });

        recyclerView.setAdapter(adapter);
    }

    public void refreshRecycler(){
       users = new ArrayList<>();
       for(String key: MainActivity.getUserHashMap().keySet()){
           users.add(MainActivity.getUserHashMap().get(key));
       }
       loadRecyclerView();
    }

    private void loadSwipeRefreh(){
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((MainActivity) getActivity()).cbAllUsers(false);
            }
        });

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        adapter.setFilter(users);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<User> filteredModelList = filter(users, newText);
        adapter.setFilter(filteredModelList);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private ArrayList<User> filter(ArrayList<User> models, String query) {
        query = query.toLowerCase();

        final ArrayList<User> filteredModelList = new ArrayList<>();
        for (User model : models) {
            final String text = model.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    public static void setUsers(ArrayList<User> users) {
        ListUsers.users = users;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).getArrowBack().setVisibility(View.GONE);
        ((MainActivity)getActivity()).getDeleteIcon().setVisibility(View.GONE);
        ((MainActivity)getActivity()).getEditIcon().setVisibility(View.GONE);
        ((MainActivity)getActivity()).getUndoIcon().setVisibility(View.GONE);

        ((MainActivity)getActivity()).getFab().show();
        refreshRecycler();
    }
}
