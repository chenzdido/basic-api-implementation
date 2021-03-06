package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.dto.VoteDto;
import com.thoughtworks.rslist.repository.VoteRepository;
import com.thoughtworks.rslist.service.RsService;
import com.thoughtworks.rslist.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class VoteController {
    @Autowired VoteRepository voteRepository;
    @Autowired
    VoteService voteService;

    @GetMapping("/voteRecord")
    public ResponseEntity<List<Vote>> getVoteRecord(
            @RequestParam int userId, @RequestParam int rsEventId, @RequestParam int pageIndex) {

        return ResponseEntity.ok(voteService.getVoteRecord(userId,rsEventId,pageIndex));
    }
    /*@GetMapping("/voteRecordByTime")
    public ResponseEntity<List<Vote>> getVoteRecordByTime(@RequestParam LocalDateTime start,@RequestParam LocalDateTime end) {
        return ResponseEntity.ok(
                voteRepository.findByLocalDateTimeBetween(start,end).stream().map(
                        item -> Vote.builder().voteNum(item.getNum()).userId(item.getUser().getId())
                                .time(item.getLocalDateTime()).rsEventId(item.getRsEvent().getId()).build())
                        .collect(Collectors.toList()));
    }*/
}
