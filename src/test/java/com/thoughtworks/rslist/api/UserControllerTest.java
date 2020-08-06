package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.dto.UserDto;
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

    @BeforeEach
    void setUp(){
        objectMapper=new ObjectMapper();
    }

    @Test
    public void should_register_user_test() throws Exception {
        User user = new User("chenz", "female", 18, "c@z.com", "18824326722");
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user/event").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        List<UserDto> all=userRepository.findAll();
        assertEquals(1,all.size());
        assertEquals("chenz",all.get(0).getUserName());
        assertEquals("c@z.com",all.get(0).getEmail());

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
                .andExpect(jsonPath("$",hasSize(0)))
                .andExpect(status().isOk());


    }

    @Test
    public void should_get_new_user_list() throws Exception{
        mockMvc.perform(get("/users"))
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].user_Name",is("cz")))
                .andExpect(jsonPath("$[0].user_gender",is("female")))
                .andExpect(jsonPath("$[0].user_age",is(18)))
                .andExpect(jsonPath("$[0].user_email",is("cc@z.com")))
                .andExpect(jsonPath("$[0].user_phone",is("18324326722")))
                .andExpect(jsonPath("$[1].user_Name",is("chenz")))
                .andExpect(jsonPath("$[1].user_gender",is("female")))
                .andExpect(jsonPath("$[1].user_age",is(18)))
                .andExpect(jsonPath("$[1].user_email",is("c@z.com")))
                .andExpect(jsonPath("$[1].user_phone",is("18824326722")))
                .andExpect(status().isOk());
    }

    @Test
    public void should_get_user_list() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].userName",is("cz")))
                .andExpect(jsonPath("$[1].userName",is("chenz")))
                .andExpect(status().isOk());
    }

    @Test
    public void should_register_user() throws Exception {
        User user = new User("chenz", "female", 18, "c@z.com", "18824326722");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user/event").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
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