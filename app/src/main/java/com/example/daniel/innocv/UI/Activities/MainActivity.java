package com.example.daniel.innocv.UI.Activities;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.daniel.innocv.Model.User;
import com.example.daniel.innocv.R;
import com.example.daniel.innocv.Rest.ApiInterface;
import com.example.daniel.innocv.Rest.ApiResponse;
import com.example.daniel.innocv.Rest.ApiService;
import com.example.daniel.innocv.UI.Fragments.EditUser;
import com.example.daniel.innocv.UI.Fragments.ListUsers;

import java.util.ArrayList;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.fab)FloatingActionButton fab;
    @Bind(android.R.id.content)View view;
    @Bind(R.id.arrowBack)public LinearLayout arrowBack;
    @Bind(R.id.delete)public ImageView deleteIcon;
    @Bind(R.id.edit)public ImageView editIcon;
    @Bind(R.id.undo)public ImageView undoIcon;
    @Bind(R.id.appBarLayout) AppBarLayout appBarLayout;
    @Bind(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;

    public static TreeMap<String, User> userHashMap;
    public static ApiInterface service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Contacts");
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        userHashMap = new TreeMap<>();

        service = ApiService.getInstance();
        cbAllUsers(true);

    }

    @OnClick(R.id.fab)
    public void addItem(View v) {

        expandToolbar();

        EditUser createUser = EditUser.newInstance(null);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, createUser).commit();
    }

    public void cbAllUsers(final boolean loadFragment) {
        if (service != null) {
            Call<ApiResponse> call = service.getAllUsers();
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    Log.d("MainActivity", "Status Code = " + response.code());
                    if (response.code() == 200) {
                        if(loadFragment){
                            loadListUserFragment(response.body());
                        }else{
                            ListUsers fragment = (ListUsers) getSupportFragmentManager().findFragmentById(R.id.fragment);
                            fragment.mSwipeRefreshLayout.setRefreshing(false);
                            fragment.setUsers(response.body().getUsersList());
                            fragment.refreshRecycler();
                        }
                    } else {
                        showMessage(getString(R.string.failed_call_api));
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    showMessage(t.getMessage());
                }
            });
        }
    }

    public void loadListUserFragment(ApiResponse response) {
        if (response != null) {
            for(User u: response.getUsersList()){
                setUsers(u.getId(), u);
            }
            ListUsers listUsers = ListUsers.newInstance(response.getUsersList());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, listUsers).commit();
        }
    }


    public void showMessage(String s) {
        Snackbar.make(view, s, Snackbar.LENGTH_LONG).setAction("Action", null).show();

    }

    @Override
    @OnClick(R.id.arrowBack)
    public void onBackPressed() {
        if(arrowBack.getVisibility() == View.VISIBLE){
            ListUsers listUsers = ListUsers.newInstance(null);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, listUsers).commit();
        }
    }

    @OnClick(R.id.delete)
    public void onClickDeleteUser(View v){
        Snackbar.make(v, R.string.are_sure, Snackbar.LENGTH_LONG).setAction("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        }).show();

    }

    public void deleteUser(){
        final TextView id = (TextView)findViewById(R.id.idText);
        if(service!=null){
            Call<ApiResponse> call = service.deleteUser(id.getText().toString());
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    Log.d("MainActivity", "Status Code = " + response.code());
                    if(response.code() == 200){
                        getUserHashMap().remove(id.getText().toString());
                        onBackPressed();
                    }else{
                        showMessage(response.message());
                    }
                }
                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    showMessage(t.getMessage());
                }
            });
        }
    }

    @OnClick(R.id.edit)
    public void onClickEditUser(){
        TextView id = (TextView)findViewById(R.id.idText);
        ArrayList<User> usr = new ArrayList<>();
        usr.add(getUserHashMap().get(id.getText().toString()));

        EditUser createUser = EditUser.newInstance(usr);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, createUser).commit();
    }

    public void onClickSaveUser(View v, final User u){
        Snackbar.make(v, R.string.are_sure, Snackbar.LENGTH_LONG).setAction("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser(u);
            }
        }).show();
    }

    public void saveUser(User u){
        if (service != null) {
            Call<ApiResponse> call;
            if(!u.getId().equals("")){
                call = service.updateUser(u);
            }else{
                call = service.createUser(u);
            }
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    Log.d("MainActivity", "Status Code = " + response.code());
                    if(response.code()==200){
                        getUserHashMap().put(ApiResponse.getUser().getId(), ApiResponse.getUser());
                        onBackPressed();
                    }else{
                        showMessage(response.message());
                    }

                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    showMessage(t.getMessage());
                }
            });
        }
    }

    public LinearLayout getArrowBack() {
        return arrowBack;
    }

    public FloatingActionButton getFab() {
        return fab;
    }

    public ImageView getDeleteIcon() {
        return deleteIcon;
    }

    public ImageView getEditIcon() {
        return editIcon;
    }

    public ImageView getUndoIcon() {
        return undoIcon;
    }

    public void expandToolbar(){
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        if(behavior != null) {
            behavior.setTopAndBottomOffset(0);
            behavior.onNestedPreScroll(coordinatorLayout, appBarLayout, null, 0, 1, new int[2]);
        }
    }

    public static TreeMap<String, User> getUserHashMap() {
        return userHashMap;
    }

    public static void setUsers(String id, User user) {
        userHashMap.put(id, user);
    }

}
