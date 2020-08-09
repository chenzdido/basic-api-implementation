package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.dto.VoteDto;
import com.thoughtworks.rslist.repository.RsEventRepositpry;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class VoteControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepositpry rsEventRepositpry;
    @Autowired
    VoteRepository voteRepository;
    UserDto userDto;
    RsEventDto rsEventDto;

    @BeforeEach
    void setUp() {
        userDto=UserDto.builder().userName("dido").age(19).email("dido@t.com").gender("female").phone("18079773289").voteNum(10).build();
        userDto=userRepository.save(userDto);
        rsEventDto = RsEventDto.builder().keyWord("无标签").eventName("第一条事件").userDto(userDto).voteNum(0).build();
        rsEventDto=rsEventRepositpry.save(rsEventDto);
    }

    @AfterEach
    void tearDown() {
        voteRepository.deleteAll();
        rsEventRepositpry.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void should_get_vote_record() throws Exception {
        for(int i=0 ; i<8 ; i++ ){
            VoteDto voteDto=VoteDto.builder().localDateTime(LocalDateTime.now()).rsEvent(rsEventDto).user(userDto).num(i+1).build();
            voteRepository.save(voteDto);
        }
        mockMvc.perform(get("/voteRecord")
                .param("userId",String.valueOf(userDto.getId()))
                .param("rsEventId",String.valueOf(rsEventDto.getId()))
                .param("pageIndex", "1"))
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].userId",is(userDto.getId())))
                .andExpect(jsonPath("$[0].rsEventId",is(rsEventDto.getId())))
                .andExpect(jsonPath("$[0].voteNum",is(1)))
                .andExpect(jsonPath("$[1].voteNum",is(2)))
                .andExpect(jsonPath("$[2].voteNum",is(3)))
                .andExpect(jsonPath("$[3].voteNum",is(4)))
                .andExpect(jsonPath("$[4].voteNum",is(5)));

        mockMvc.perform(get("/voteRecord")
                .param("userId",String.valueOf(userDto.getId()))
                .param("rsEventId",String.valueOf(rsEventDto.getId()))
                .param("pageIndex", "2"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].userId",is(userDto.getId())))
                .andExpect(jsonPath("$[0].rsEventId",is(rsEventDto.getId())))
                .andExpect(jsonPath("$[0].voteNum",is(6)))
                .andExpect(jsonPath("$[1].voteNum",is(7)))
                .andExpect(jsonPath("$[2].voteNum",is(8)));


    }
   /* @Test
    public void should_get_vote_record_by_time() throws Exception {
        for(int i=0 ; i<8 ; i++ ){
            VoteDto voteDto=VoteDto.builder().localDateTime(LocalDateTime.now().minusMinutes(8-i)).rsEvent(rsEventDto).user(userDto).num(i+1).build();
            voteRepository.save(voteDto);
        }
        mockMvc.perform(get("/voteRecordByTime")
                .param("start",String.valueOf(LocalDateTime.now().minusMinutes(8)))
                .param("end",String.valueOf(LocalDateTime.now())))
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].userId",is(userDto.getId())))
                .andExpect(jsonPath("$[0].rsEventId",is(rsEventDto.getId())))
                .andExpect(jsonPath("$[0].voteNum",is(1)))
                .andExpect(jsonPath("$[1].voteNum",is(2)))
                .andExpect(jsonPath("$[2].voteNum",is(3)))
                .andExpect(jsonPath("$[3].voteNum",is(4)))
                .andExpect(jsonPath("$[4].voteNum",is(5)));


    }*/
}