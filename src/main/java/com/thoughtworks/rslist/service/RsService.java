package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.dto.VoteDto;
import com.thoughtworks.rslist.exception.RsEventNotValueException;
import com.thoughtworks.rslist.repository.RsEventRepositpry;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RsService {
    final RsEventRepositpry rsEventRepositpry;
    final UserRepository userRepository;
    final VoteRepository voteRepository;

    public RsService(RsEventRepositpry rsEventRepository, UserRepository userRepository, VoteRepository voteRepository) {
        this.rsEventRepositpry = rsEventRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    }
    public RsEvent getRsEvent(int index){
        List<RsEvent> rsEvents = rsEventRepositpry.findAll().stream().map(item -> RsEvent.builder().eventName(item.getEventName()).keyWord(item.getKeyWord()).userId(item.getId()).voteNum(item.getVoteNum()).build()).collect(Collectors.toList());
        if (index < 1 || index > rsEvents.size()) {
            throw new RuntimeException();
        }
        return rsEvents.get(index - 1);
    }

    public List<RsEvent> getRsEvent(Integer ... index){
        List<RsEvent> rsEvents = rsEventRepositpry.findAll().stream().map(item -> RsEvent.builder().eventName(item.getEventName()).keyWord(item.getKeyWord()).userId(item.getId()).voteNum(item.getVoteNum()).build()).collect(Collectors.toList());
        if (index==null) {
            return rsEvents;
        }
        if(index[0]<1||index[1]>rsEvents.size()){
            throw new RuntimeException();
        }
        return rsEvents.subList(index[0] - 1, index[1]);
    }

    public void addRsEvent(RsEvent rsEvent){
        Optional<UserDto> userDto=userRepository.findById(rsEvent.getUserId());
        if(!userDto.isPresent()){
            throw new RuntimeException();
        }
        RsEventDto rsEventDto=RsEventDto.builder().keyWord(rsEvent.getKeyWord()).eventName(rsEvent.getEventName())
                .userDto(userDto.get()).build();
        rsEventRepositpry.save(rsEventDto);
    }

    public void changeRsEvent(int index, RsEvent rsEvent){
        RsEventDto newrsEventDto=rsEventRepositpry.findById(index).get();
        if(rsEvent.getUserId()!=newrsEventDto.getUserDto().getId()){
            throw new RuntimeException();
        }
        if(rsEvent.getEventName()!=null){
            newrsEventDto.setEventName(rsEvent.getEventName());
        }
        if(rsEvent.getKeyWord()!=null){
            newrsEventDto.setKeyWord(rsEvent.getKeyWord());
        }
        rsEventRepositpry.save(newrsEventDto);
    }

    public void vote(int index, Vote vote){
        RsEventDto newrsEventDto=rsEventRepositpry.findById(index).get();
        UserDto userDto=userRepository.findById(vote.getUserId()).get();
        if(userDto.getVoteNum()<vote.getVoteNum()){
            throw new RuntimeException();
        }
        VoteDto voteDto= VoteDto.builder().localDateTime(vote.getTime()).num(vote.getVoteNum()).rsEvent(newrsEventDto).user(userDto).build();
        voteRepository.save(voteDto);
        userDto.setVoteNum(userDto.getVoteNum()-vote.getVoteNum());
        userRepository.save(userDto);
        newrsEventDto.setVoteNum(newrsEventDto.getVoteNum()+vote.getVoteNum());
        rsEventRepositpry.save(newrsEventDto);
    }
}
