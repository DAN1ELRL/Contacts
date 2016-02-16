package com.example.daniel.innocv.Rest;

import com.example.daniel.innocv.Model.User;

import java.util.ArrayList;

/**
 * Created by daniel on 13/02/16.
 */
public class ApiResponse {

    private static ArrayList<User> usersList;

    private static User user;

    public ApiResponse(){
        usersList = null;
        user = null;
    }

    public ArrayList<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(ArrayList<User> usersList) {
        this.usersList = usersList;
    }

    public User getUserFromArrayList(int index) {
        return usersList.get(index);
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {

        ApiResponse.user = user;
    }
}
