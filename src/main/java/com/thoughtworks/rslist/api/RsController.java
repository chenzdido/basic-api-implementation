package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.dto.VoteDto;
import com.thoughtworks.rslist.exception.RsEventNotValueException;
import com.thoughtworks.rslist.repository.RsEventRepositpry;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import com.thoughtworks.rslist.service.RsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class RsController {
  @Autowired
  RsEventRepositpry rsEventRepositpry;
  @Autowired
  UserRepository userRepository;
  @Autowired
  VoteRepository voteRepository;
  @Autowired
  RsService rsService;


  @GetMapping("/rs/{index}")
  public ResponseEntity getRsEvent(@PathVariable int index){
    try{
      return ResponseEntity.ok(rsService.getRsEvent(index));
    }catch(Exception e){
      return ResponseEntity.badRequest().build();
    }

  }

  @GetMapping("/rs")
  public ResponseEntity getRsEvent() {
    List<RsEvent> rsEvents = rsEventRepositpry.findAll().stream().map(item -> RsEvent.builder().eventName(item.getEventName()).keyWord(item.getKeyWord()).userId(item.getId()).voteNum(item.getVoteNum()).build()).collect(Collectors.toList());
    return ResponseEntity.ok(rsEvents);
    }

  @GetMapping("/rs/list")
  public ResponseEntity getRsEventBetween(@RequestParam(required=false) Integer start, @RequestParam(required=false) Integer end) {
    try{
      return ResponseEntity.ok(rsService.getRsEvent(start,end));
    }catch(Exception e){
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/rs/event")
  public ResponseEntity addRsEvent(@RequestBody @Valid RsEvent rsEvent){
    try{
      rsService.addRsEvent(rsEvent);
    }catch(Exception e){
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.created(null).build();

  }

  @DeleteMapping("/rs/delete/{index}")
  public ResponseEntity deleteRsEvent(@PathVariable int index){
    rsEventRepositpry.deleteById(index);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/rs/change/{index}")
  public ResponseEntity changeRsEvent(@PathVariable int index, @RequestBody RsEvent rsEvent){
    try{
      rsService.changeRsEvent(index,rsEvent);
    }catch(Exception e){
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok().build();
  }

  @PostMapping("/rs/vote/{index}")
  public ResponseEntity vote(@PathVariable int index, @RequestBody Vote vote) {
    try{
      rsService.vote(index,vote);
    }catch(Exception e){
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok().build();
  }

}
