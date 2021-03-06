package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.repository.RsEventRepositpry;
import com.thoughtworks.rslist.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    ObjectMapper objectMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepositpry rsEventRepositpry;

    @BeforeEach
    void setUp(){
        objectMapper=new ObjectMapper();
        rsEventRepositpry.deleteAll();
        userRepository.deleteAll();
        UserDto userDto=UserDto.builder().userName("dido").age(19).email("dido@t.com").gender("female").phone("18079773289").voteNum(10).build();
        userRepository.save(userDto);
    }

    @Test
    public void should_get_user_list() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].userName",is("dido")))
                .andExpect(status().isOk());
    }

    @Test
    public void should_register_user_test() throws Exception {
        User user = new User("chenz", "female", 18, "c@z.com", "18824326722");
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user/event").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        List<UserDto> all=userRepository.findAll();
        assertEquals(2,all.size());
        assertEquals("chenz",all.get(1).getUserName());
        assertEquals("c@z.com",all.get(1).getEmail());

    }

    @Test
    public void should_get_user_by_id() throws Exception {
        User user = new User("cz", "female", 18, "cc@z.com", "18824526722");
        String jsonString = objectMapper.writeValueAsString(user);
        String id=mockMvc.perform(post("/user/event").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                         .andExpect(status().isCreated())
                         .andReturn()
                         .getResponse()
                         .getHeader("userId");
        mockMvc.perform(get("/user/"+id))
                .andExpect(jsonPath("$.userName",is("cz")))
                .andExpect(status().isOk());

    }
    @Test
    public void should_delete_user_by_id() throws Exception {
        User user = new User("cz", "female", 18, "cc@z.com", "18824526722");
        String jsonString = objectMapper.writeValueAsString(user);
        String id=mockMvc.perform(post("/user/event").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("userId");
        mockMvc.perform(delete("/user/delete/"+id))
                .andExpect(status().isOk());
        mockMvc.perform(get("/user"))
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(status().isOk());


    }

    @Test
    public void should_delete_user_by_id_and_delete_rsEvent() throws Exception {
        UserDto save=userRepository.save(UserDto.builder().email("c@zz.com").phone("18888888999").gender("female")
                .age(19).userName("cc").build());
        RsEventDto rsEventDto=RsEventDto.builder().eventName("猪肉涨价了").keyWord("经济").userDto(save).build();
        rsEventRepositpry.save(rsEventDto);
        mockMvc.perform(delete("/user/delete/{id}",save.getId()))
                .andExpect(status().isOk());
        assertEquals(1,userRepository.findAll().size());
        assertEquals(0,rsEventRepositpry.findAll().size());


    }

    @Test
    public void should_user_name_less_than_8() throws Exception {
        User user=new User("chenzddddddd", "female", 18, "c@z.com", "18824326722");
        ObjectMapper objectMapper=new ObjectMapper();
        String jsonString=objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user/event").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void should_user_age_between_18_and_100() throws Exception {
        User user=new User("chenz", "female", 16, "c@z.com", "18824326722");
        ObjectMapper objectMapper=new ObjectMapper();
        String jsonString=objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user/event").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void should_user_email_suit_format() throws Exception {
        User user=new User("chenz", "female", 19, "ca.com", "18824326722");
        ObjectMapper objectMapper=new ObjectMapper();
        String jsonString=objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user/event").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }
    @Test
    public void should_user_phone_suit_format() throws Exception {
        User user = new User("chenz", "female", 18, "c@z.com", "28824326722");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user/event").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_user_suit_format() throws Exception {
        User user = new User("chenz", "female", 18, "c@z.com", "28824326722");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user/event").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid user")));
    }





}