package com.example.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("POST /user - isCreated")
    public void testCreateUser() throws Exception {
        User user = getUser();
        doReturn(true).when(userService).create(any());

        mockMvc.perform(post("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(user)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(user.getId()))
            .andExpect(jsonPath("$.username").value(user.getUsername()))
            .andExpect(jsonPath("$.firstname").value(user.getFirstname()))
            .andExpect(jsonPath("$.lastname").value(user.getLastname()))
            .andExpect(jsonPath("$.email").value(user.getEmail()))
            .andExpect(jsonPath("$.password").value(user.getPassword()))
            .andExpect(jsonPath("$.userstatus").value(user.getUserstatus()));    
    }

    @Test
    @DisplayName("POST /user - badRequest")
    public void testCreateUserBadRequest() throws Exception {
        User user = new User();
        doReturn(false).when(userService).create(user);

        mockMvc.perform(post("/user")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /user/{username} - isOK")
    public void testGetUser() throws Exception {
        User user = getUser();
        doReturn(user).when(userService).get(user.getUsername());

        mockMvc.perform(get("/user/{username}", user.getUsername()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(user.getId()))
            .andExpect(jsonPath("$.username").value(user.getUsername()))
            .andExpect(jsonPath("$.firstname").value(user.getFirstname()))
            .andExpect(jsonPath("$.lastname").value(user.getLastname()))
            .andExpect(jsonPath("$.email").value(user.getEmail()))
            .andExpect(jsonPath("$.password").value(user.getPassword()))
            .andExpect(jsonPath("$.userstatus").value(user.getUserstatus()));
    }

    @Test
    @DisplayName("GET /user/{username} - notFound")
    public void testGetUserNotFound() throws Exception {
        User user = getUser();
        doReturn(null).when(userService).get(user.getUsername());

        mockMvc.perform(get("/user/{username}", user.getUsername()))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /user/{username} - isOK") 
    public void testPutUser() throws Exception {
        User user = getUser();
        user.setEmail("test2@example.com");
        doReturn(true).when(userService).update(any());

        mockMvc.perform(put("/user/{username}", user.getUsername())
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(user)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(user.getId()))
            .andExpect(jsonPath("$.username").value(user.getUsername()))
            .andExpect(jsonPath("$.firstname").value(user.getFirstname()))
            .andExpect(jsonPath("$.lastname").value(user.getLastname()))
            .andExpect(jsonPath("$.email").value("test2@example.com"))
            .andExpect(jsonPath("$.password").value(user.getPassword()))
            .andExpect(jsonPath("$.userstatus").value(user.getUserstatus()));
    }

    @Test
    @DisplayName("PUT /user/{username} - notFound")
    public void testPutUserNotFound() throws Exception {
        User user = getUser();
        doReturn(null).when(userService).get(user.getUsername());

        mockMvc.perform(put("/user/{username}", user.getUsername())
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(user)))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /user/{username} - isOK")
    public void testDeleteUser() throws Exception {
        User user = getUser();
        doReturn(true).when(userService).delete(any());

        mockMvc.perform(delete("/user/{username}", user.getUsername()))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /user/{username} - notFound")
    public void testDeleteUserNotFound() throws Exception {
        User user = getUser();
        doReturn(null).when(userService).get(user.getUsername());

        mockMvc.perform(delete("/user/{username}}", user.getUsername()))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /user/list - isOK")
    public void testGetUserList() throws Exception {
        User user = getUser();
        doReturn(Lists.newArrayList(user)).when(userService).list();

        mockMvc.perform(get("/user/list"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id").value(user.getId()))
            .andExpect(jsonPath("$[0].username").value(user.getUsername()))
            .andExpect(jsonPath("$[0].firstname").value(user.getFirstname()))
            .andExpect(jsonPath("$[0].lastname").value(user.getLastname()))
            .andExpect(jsonPath("$[0].email").value(user.getEmail()))
            .andExpect(jsonPath("$[0].password").value(user.getPassword()))
            .andExpect(jsonPath("$[0].phone").value(user.getPhone()))
            .andExpect(jsonPath("$[0].userstatus").value(user.getUserstatus()));
    }
    

    public User getUser() {
        User mdl = new User();
        mdl.setId("11");
        mdl.setUsername("pppp1ssss1");
        mdl.setFirstname("pppp1");
        mdl.setLastname("ssss1");
        mdl.setEmail("test1@example.com");
        mdl.setPassword("password1");
        mdl.setPhone("001-0000-0000");

        return mdl;
    }


    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
