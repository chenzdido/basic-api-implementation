package com.thoughtworks.rslist.domain;

import java.util.ArrayList;
import java.util.List;

public class UserList {
    private static List<User> userList=initUserList();
    private static  List<User> initUserList(){
        List<User> userList=new ArrayList<>();
        userList.add(new User("cz", "female", 18, "cc@z.com", "18324326722"));
        return userList;
    }

    public  List<User> getUserList() {
        return userList;
    }
    public  void add_user(User user){
        userList.add(user);
    }
}
