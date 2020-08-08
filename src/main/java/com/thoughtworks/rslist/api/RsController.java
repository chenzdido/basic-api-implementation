package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.domain.UserList;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.exception.Error;
import com.thoughtworks.rslist.exception.RsEventNotValueException;
import com.thoughtworks.rslist.repository.RsEventRepositpry;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class RsController {
  @Autowired
  RsEventRepositpry rsEventRepositpry;
  @Autowired
  UserRepository userRepository;


  @GetMapping("/rs/{index}")
  public ResponseEntity getRsEvent(@PathVariable int index){
    List<RsEvent> rsEvents = rsEventRepositpry.findAll().stream().map(item -> RsEvent.builder().eventName(item.getEventName()).keyWord(item.getKeyWord()).userId(item.getId()).build()).collect(Collectors.toList());
    if (index < 1 || index > rsEvents.size()) {
      throw new RsEventNotValueException("invalid index");
    }
    return ResponseEntity.ok(rsEvents.get(index - 1));
  }

  @GetMapping("/rs/list")
  public ResponseEntity getRsEventBetween(@RequestParam(required=false) Integer start, @RequestParam(required=false) Integer end) {
    List<RsEvent> rsEvents = rsEventRepositpry.findAll().stream().map(item -> RsEvent.builder().eventName(item.getEventName()).keyWord(item.getKeyWord()).userId(item.getId()).build()).collect(Collectors.toList());
    /*if(start<1||end>rsEvents.size()){
      throw new RsEventNotValueException("invalid request param");
    }*/
    if (start == null || end == null) {
      return ResponseEntity.ok(rsEvents);
    }
    return ResponseEntity.ok(rsEvents.subList(start - 1, end));

  }

  @PostMapping("/rs/event")
  public ResponseEntity addRsEvent(@RequestBody @Valid RsEvent rsEvent){
    Optional<UserDto> userDto=userRepository.findById(rsEvent.getUserId());
    if(!userDto.isPresent()){
      return ResponseEntity.badRequest().build();
    }
    RsEventDto rsEventDto=RsEventDto.builder().keyWord(rsEvent.getKeyWord()).eventName(rsEvent.getEventName())
            .userDto(userDto.get()).build();
    rsEventRepositpry.save(rsEventDto);
    return ResponseEntity.created(null).build();

  }

  @DeleteMapping("/rs/delete/{index}")
  public ResponseEntity deleteRsEvent(@PathVariable int index){
    rsEventRepositpry.deleteById(index);
    return ResponseEntity.ok().build();
  }


}
