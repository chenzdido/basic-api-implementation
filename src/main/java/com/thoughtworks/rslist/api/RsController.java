package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.domain.UserList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class RsController {
  UserList u=new UserList();
  List<User> userList=u.getUserList();
  private List<RsEvent> rsList = initRsEventList();
  private List<RsEvent> initRsEventList(){
    List<RsEvent> rsEventList = new ArrayList<>();
    User user=new User("chenz", "female", 18, "c@z.com", "18824326722");
    userList.add(user);
    rsEventList.add(new RsEvent("第一条事件","无标签",user));
    rsEventList.add(new RsEvent("第二条事件","无标签",user));
    rsEventList.add(new RsEvent("第三条事件","无标签",user));
    return rsEventList;
  }



  @GetMapping("/rs/{index}")
  public ResponseEntity getRsEvent(@PathVariable int index){
    return ResponseEntity.ok(rsList.get(index-1));
  }

  @GetMapping("/rs/list")
  public ResponseEntity getRsEventBetween(@RequestParam(required=false) Integer start, @RequestParam(required=false) Integer end) {
    if (start!=null&&end!=null) {
      return ResponseEntity.ok(rsList.subList(start - 1, end));
    }
    return ResponseEntity.ok(rsList);

  }

  @PostMapping("/rs/event")
  public ResponseEntity addRsEvent(@RequestBody RsEvent rsEvent){
    String username=rsEvent.getUser().getUserName();
    Boolean not_exit=true;
    for(User u: userList){
      if(u.getUserName()==username){
        not_exit=false;
        break;
      }
    }
    if(not_exit){
      userList.add(rsEvent.getUser());
    }
    rsList.add(rsEvent);
    return ResponseEntity.created(null).header("添加的热搜事件在列表中的位置",rsList.indexOf(rsEvent)+"").build();
  }

  @DeleteMapping("/rs/delete/{index}")
  public ResponseEntity deletRsEvent(@PathVariable int index){
    rsList.remove(index-1);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("rs/change/{index}")
  public void ResponseEntity(@PathVariable int index, @RequestBody RsEvent rsEvent){
    if(rsEvent.getEventName()!=null){
      rsList.get(index-1).setEventName(rsEvent.getEventName());
    }
    if(rsEvent.getKeyWord()!=null){
      rsList.get(index-1).setKeyWord(rsEvent.getKeyWord());
    }
    return ResponseEntity.ok().build();
  }
}
