package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MainControllerTest {
    MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private MainController mainController;

    @BeforeEach
    public void initmocks() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
    }

    @Test
    public void test_get() throws Exception {
        UserModel mdl = getUser11();

        this.mockMvc.perform(
                post("/user")
                        .param("id",mdl.getId())
                        .param("username",mdl.getUsername())
                        .param("firstname",mdl.getFirstname())
                        .param("lastname",mdl.getLastname())
                        .param("email",mdl.getEmail())
                        .param("password",mdl.getPassword())
                        .param("phone",mdl.getPhone()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(mdl.getUsername()));

        this.mockMvc.perform(get("/user/{username}",mdl.getUsername()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mdl.getId()))
                .andExpect(jsonPath("$.username").value(mdl.getUsername()))
                .andExpect(jsonPath("$.firstname").value(mdl.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(mdl.getLastname()))
                .andExpect(jsonPath("$.email").value(mdl.getEmail()))
                .andExpect(jsonPath("$.password").value(mdl.getPassword()))
                .andExpect(jsonPath("$.phone").value(mdl.getPhone()));

        mdl.setId("0");
        mdl.setFirstname("pppp");
        mdl.setLastname("ssss");
        mdl.setEmail("usr1@example.com");
        mdl.setPassword("password");
        mdl.setPhone("000-0000-0000");
        this.mockMvc.perform(
                put("/user/{username}",mdl.getUsername())
                        .param("id",mdl.getId())
                        .param("firstname",mdl.getFirstname())
                        .param("lastname",mdl.getLastname())
                        .param("email",mdl.getEmail())
                        .param("password",mdl.getPassword())
                        .param("phone",mdl.getPhone()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(mdl.getUsername()));

        this.mockMvc.perform(get("/user/{username}",mdl.getUsername()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mdl.getId()))
                .andExpect(jsonPath("$.username").value(mdl.getUsername()))
                .andExpect(jsonPath("$.firstname").value(mdl.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(mdl.getLastname()))
                .andExpect(jsonPath("$.email").value(mdl.getEmail()))
                .andExpect(jsonPath("$.password").value(mdl.getPassword()))
                .andExpect(jsonPath("$.phone").value(mdl.getPhone()));

        this.mockMvc.perform(delete("/user/{username}",mdl.getUsername()))
                .andExpect(status().isOk());
    }

    @Test
    public void test_err() throws Exception {
        UserModel mdl = getUser12();

        this.mockMvc.perform(
                put("/user/{username}",mdl.getUsername())
                        .param("id",mdl.getId())
                        .param("firstname",mdl.getFirstname())
                        .param("lastname",mdl.getLastname())
                        .param("email",mdl.getEmail())
                        .param("password",mdl.getPassword())
                        .param("phone",mdl.getPhone())     )
                .andExpect(status().isNotFound());

        this.mockMvc.perform(
                delete("/user/{username}",mdl.getUsername()))
                .andExpect(status().isNotFound());

        this.mockMvc.perform(get("/user/{username}",mdl.getUsername()))
                .andExpect(status().isNotFound());

        this.mockMvc.perform(
                post("/user")
                        .param("id",mdl.getId())
                        .param("username",mdl.getUsername())
                        .param("firstname",mdl.getFirstname())
                        .param("lastname",mdl.getLastname())
                        .param("email",mdl.getEmail())
                        .param("password",mdl.getPassword())
                        .param("phone",mdl.getPhone()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(mdl.getUsername()));

        this.mockMvc.perform(
                post("/user")
                        .param("id",mdl.getId())
                        .param("username",mdl.getUsername())
                        .param("firstname",mdl.getFirstname())
                        .param("lastname",mdl.getLastname())
                        .param("email",mdl.getEmail())
                        .param("password",mdl.getPassword())
                        .param("phone",mdl.getPhone()))
                .andExpect(status().isBadRequest());

    }

    public UserModel getUser11() {
        UserModel mdl = new UserModel();
        mdl.setId("11");
        mdl.setUsername("pppp1ssss1");
        mdl.setFirstname("pppp1");
        mdl.setLastname("ssss1");
        mdl.setEmail("test1@example.com");
        mdl.setPassword("password1");
        mdl.setPhone("001-0000-0000");

        return mdl;
    }

    public UserModel getUser12() {
        UserModel mdl = new UserModel();
        mdl.setId("12");
        mdl.setUsername("pppp2ssss2");
        mdl.setFirstname("pppp2");
        mdl.setLastname("ssss2");
        mdl.setEmail("test2@example.com");
        mdl.setPassword("password2");
        mdl.setPhone("002-0000-0000");

        return mdl;
    }
}
