package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.NewUser;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.domain.UserList;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.exception.Error;
import com.thoughtworks.rslist.exception.RsEventNotValueException;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    public static List<User> users=new ArrayList<>();
    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public List<NewUser> getNewUserList(){
        List<NewUser> newUserList = new ArrayList();;
        for(User user:users){
            NewUser newUser=new NewUser(user);
            newUserList.add(newUser);
        }
        return newUserList;
    }

    @GetMapping("/user")
    public List<User> getserList() {
        return users;

    }

    /*@PostMapping("/user/event")
    public ResponseEntity registerUser(@RequestBody @Valid User user){

        users.add(user);
        return ResponseEntity.created(null).build();
    }*/
    @PostMapping("/user/event")
    public void register(@RequestBody @Valid User user){
        UserDto userDto=new UserDto();
        userDto.setPhone(user.getPhone());
        userDto.setVoteNum(user.getVoteNum());
        userDto.setAge(user.getAge());
        userDto.setEmail(user.getEmail());
        userDto.setGender(user.getGender());
        userDto.setUserName(user.getUserName());
        userRepository.save(userDto);
    }

    @ExceptionHandler( MethodArgumentNotValidException.class)
    public ResponseEntity RsEventExceptionHandler(Exception e){
        String errorMessage="invalid user";
        Error error=new Error();
        error.setError(errorMessage);
        return ResponseEntity.badRequest().body(error);
    }
}
