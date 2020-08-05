package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.domain.UserList;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    UserList u=new UserList();
    List<User> userList=u.getUserList();

    @GetMapping("/user")
    public List<User> getserList() {
        return userList;

    }

    @PostMapping("/user/event")
    public void registerUser(@RequestBody @Valid User user){
        userList.add(user);
    }
}
