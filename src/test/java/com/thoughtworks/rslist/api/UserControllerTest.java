package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void should_get_newuser_list() throws Exception{
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



}