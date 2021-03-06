package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.NewUser;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.domain.UserList;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.exception.Error;
import com.thoughtworks.rslist.exception.RsEventNotValueException;
import com.thoughtworks.rslist.repository.RsEventRepositpry;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepositpry rsEventRepositpry;



    @GetMapping("/user")
    public List<UserDto> getUserList() {
        List<UserDto> all=userRepository.findAll();
        return all;
    }


    @GetMapping("/user/{index}")
    public ResponseEntity getUser(@PathVariable int index){
        UserDto user=userRepository.findById(index).get();
        return ResponseEntity.ok(user);
    }

    @PostMapping("/user/event")
    public ResponseEntity register(@RequestBody @Valid User user){
        UserDto userDto=UserDto.builder().voteNum(user.getVoteNum()).phone(user.getPhone()).gender(user.getGender()).email(user.getEmail()).age(user.getAge()).userName(user.getUserName()).build();
        userRepository.save(userDto);
        return ResponseEntity.created(null).header("userId",userDto.getId()+"").build();
    }

    @DeleteMapping("/user/delete/{id}")
    @Transactional
    public ResponseEntity deleteUserEvent(@PathVariable int id){
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler( MethodArgumentNotValidException.class)
    public ResponseEntity RsEventExceptionHandler(Exception e){
        String errorMessage="invalid user";
        Error error=new Error();
        error.setError(errorMessage);
        return ResponseEntity.badRequest().body(error);
    }
}
