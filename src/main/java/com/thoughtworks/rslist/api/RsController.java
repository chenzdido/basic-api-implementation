package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.domain.UserList;
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
  public RsEvent getRsEvent(@PathVariable int index){
    return rsList.get(index-1);
  }

  @GetMapping("/rs/list")
  public List<RsEvent> getRsEventBetween(@RequestParam(required=false) Integer start, @RequestParam(required=false) Integer end) {
    if (start!=null&&end!=null) {
      return rsList.subList(start - 1, end);
    }
    return rsList;

  }

  @PostMapping("/rs/event")
  public void addRsEvent(@RequestBody RsEvent rsEvent){
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
  }

  @DeleteMapping("/rs/delete/{index}")
  public void deletRsEvent(@PathVariable int index){
    rsList.remove(index-1);
  }

  @PatchMapping("rs/change/{index}")
  public void changeRsEvent(@PathVariable int index, @RequestBody RsEvent rsEvent){
    if(rsEvent.getEventName()!=null){
      rsList.get(index-1).setEventName(rsEvent.getEventName());
    }
    if(rsEvent.getKeyWord()!=null){
      rsList.get(index-1).setKeyWord(rsEvent.getKeyWord());
    }
  }
}
