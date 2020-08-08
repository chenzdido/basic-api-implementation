package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.repository.RsEventRepositpry;
import com.thoughtworks.rslist.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RsControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepositpry rsEventRepositpry;
    UserDto userDto;
    RsEventDto rsEventDto;

    @BeforeEach
    void setUp(){
        rsEventRepositpry.deleteAll();
        userRepository.deleteAll();
        userDto=userRepository.save(UserDto.builder().email("c@zz.com").phone("18888888999").gender("female")
                .age(19).userName("cc").build());
        RsEventDto rsEventDto = RsEventDto.builder().keyWord("无标签").eventName("第一条事件").userDto(userDto).build();
        rsEventRepositpry.save(rsEventDto);
        rsEventDto = RsEventDto.builder().keyWord("无标签").eventName("第二条事件").userDto(userDto).build();
        rsEventRepositpry.save(rsEventDto);

    }


    @Test
    public void should_get_rs_event_list() throws Exception {
        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].eventName",is("第一条事件")))
                .andExpect(jsonPath("$[0].keyWord",is("无标签")))
                .andExpect(jsonPath("$[1].eventName",is("第二条事件")))
                .andExpect(jsonPath("$[1].keyWord",is("无标签")))
                .andExpect(status().isOk());
    }

    @Test
    public void should_get_rs_event() throws Exception {
        mockMvc.perform(get("/rs/1")).andExpect(jsonPath("$.eventName", is("第一条事件")))
                .andExpect(jsonPath("$.keyWord",is("无标签")))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/2")).andExpect(jsonPath("$.eventName", is("第二条事件")))
                .andExpect(jsonPath("$.keyWord",is("无标签")))
                .andExpect(status().isOk());
    }

    @Test
    public void get_rs_event_between() throws Exception {
        rsEventDto = RsEventDto.builder().keyWord("无标签").eventName("第三条事件").userDto(userDto).build();
        rsEventRepositpry.save(rsEventDto);
        mockMvc.perform(get("/rs/list?start=1&end=2"))
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].eventName",is("第一条事件")))
                .andExpect(jsonPath("$[0].keyWord",is("无标签")))
                .andExpect(jsonPath("$[1].eventName",is("第二条事件")))
                .andExpect(jsonPath("$[1].keyWord",is("无标签")));
        mockMvc.perform(get("/rs/list?start=2&end=3"))
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].eventName",is("第二条事件")))
                .andExpect(jsonPath("$[0].keyWord",is("无标签")))
                .andExpect(jsonPath("$[1].eventName",is("第三条事件")))
                .andExpect(jsonPath("$[1].keyWord",is("无标签")));
        mockMvc.perform(get("/rs/list?start=1&end=3"))
                .andExpect(jsonPath("$",hasSize(3)))
                .andExpect(jsonPath("$[0].eventName",is("第一条事件")))
                .andExpect(jsonPath("$[0].keyWord",is("无标签")))
                .andExpect(jsonPath("$[1].eventName",is("第二条事件")))
                .andExpect(jsonPath("$[1].keyWord",is("无标签")))
                .andExpect(jsonPath("$[2].eventName",is("第三条事件")))
                .andExpect(jsonPath("$[2].keyWord",is("无标签")));
    }

    @Test
    public void should_add_rs_event_user_exit() throws Exception {
        RsEvent rsEvent=new RsEvent("猪肉涨价了","经济",1);
        String jsonString=new ObjectMapper().writeValueAsString(rsEvent);
        mockMvc.perform(post("/rs/event").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        List<RsEventDto> all= rsEventRepositpry.findAll();
        assertNotNull(all);
        assertEquals(3,all.size());
        assertEquals("猪肉涨价了",all.get(2).getEventName());
        assertEquals("经济",all.get(2).getKeyWord());
        assertEquals(userDto.getId(),all.get(2).getUserDto().getId());
    }

    @Test
    public void should_not_add_rs_event_user_not_exit() throws Exception {
        UserDto newUser=UserDto.builder().email("c@zzl.com").phone("18888887999").gender("female")
                .age(19).userName("zz").build();
        RsEventDto rsEventDto=RsEventDto.builder().eventName("猪肉涨价了").userDto(newUser).keyWord("经济").build();
        String jsonString=new ObjectMapper().writeValueAsString(rsEventDto);
        mockMvc.perform(post("/rs/event").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_delete_rs_event() throws Exception {
        mockMvc.perform(delete("/rs/delete/2"));
        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].eventName",is("第一条事件")))
                .andExpect(jsonPath("$[0].keyWord",is("无标签")))
                .andExpect(status().isOk());

    }

    /*@Test
    public void should_change_rs_event() throws Exception {
        User user = new User("chenz", "female", 18, "c@z.com", "18824326722");
        RsEvent rsEvent=new RsEvent("猪肉涨价了","经济",1);
        String jsonString=new ObjectMapper().writeValueAsString(rsEvent);
        mockMvc.perform(patch("/rs/change/3").content(jsonString).contentType(MediaType.APPLICATION_JSON));
        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$",hasSize(3)))
                .andExpect(jsonPath("$[0].eventName",is("第一条事件")))
                .andExpect(jsonPath("$[0].keyWord",is("无标签")))
                .andExpect(jsonPath("$[1].eventName",is("第二条事件")))
                .andExpect(jsonPath("$[1].keyWord",is("无标签")))
                .andExpect(jsonPath("$[2].eventName",is("猪肉涨价了")))
                .andExpect(jsonPath("$[2].keyWord",is("经济")))
                .andExpect(status().isOk());

    }

    @Test
    public void should_change_one_rs_event() throws Exception {
        RsEvent rsEvent=new RsEvent("猪肉涨价了",null,1);
        String jsonString=new ObjectMapper().writeValueAsString(rsEvent);
        mockMvc.perform(patch("/rs/change/2").content(jsonString).contentType(MediaType.APPLICATION_JSON));
        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].eventName",is("第一条事件")))
                .andExpect(jsonPath("$[0].keyWord",is("无标签")))
                .andExpect(jsonPath("$[1].eventName",is("猪肉涨价了")))
                .andExpect(jsonPath("$[1].keyWord",is("无标签")))
                .andExpect(status().isOk());

    }*/
    /*@Test
    public void should_throw_index_exception() throws Exception {
        mockMvc.perform(get("/rs/0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid index")));
    }
    @Test
    public void should_throw_start_and_end_exception() throws Exception {
        mockMvc.perform(get("/rs/list?start=0&end=2"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid request param")));
    }
    @Test
    public void should_throw_method_not_valid_exception() throws Exception{
        String jsonString="{\"eventName\":\"猪肉涨价了\",\"keyWord\":\"经济\",\"user\": {\"userName\":\"chenzxxxxxx\",\"age\": 19,\"gender\": \"female\",\"email\": \"a@b.com\",\"phone\": \"18888888888\"}}";
        mockMvc.perform(post("/rs/event").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error",is("invalid param")));
    }*/
}