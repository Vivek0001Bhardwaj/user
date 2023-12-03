package com.techolution.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.techolution.user.util.ConstructObjectUtil.constructLoginRequest;
import static com.techolution.user.util.ConstructObjectUtil.constructSignUpRequest;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ActiveProfiles("unittest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void registerUser() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                .with(SecurityMockMvcRequestPostProcessors.testSecurityContext())
                .content(new ObjectMapper().writeValueAsString(constructSignUpRequest()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.*").exists())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.message", is("User registered successfully")))
                .andExpect(jsonPath("$.success", is(Boolean.TRUE)))
                .andReturn();

        Assertions.assertNotNull(response.getResponse().getContentAsString());
    }

    @Test
    public void authenticateUser() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                        .param("username", "leanne")
                        .with(SecurityMockMvcRequestPostProcessors.testSecurityContext())
                        .content(new ObjectMapper().writeValueAsString(constructLoginRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.*").exists())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.message", is("User Singed In successfully")))
                .andExpect(jsonPath("$.success", is(Boolean.TRUE)))
                .andReturn();

        Assertions.assertNotNull(response.getResponse().getContentAsString());
    }

    @Test
    public void getCurrentUser() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/api/user/me")
                        .param("username", "leanne")
                        .with(SecurityMockMvcRequestPostProcessors.testSecurityContext())
                        .content(new ObjectMapper().writeValueAsString(constructLoginRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.*").exists())
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.username", is("leanne")))
                .andExpect(jsonPath("$.firstName", is("leanne")))
                .andExpect(jsonPath("$.lastName", is("graham")))
                .andReturn();

        Assertions.assertNotNull(response.getResponse().getContentAsString());
    }
}
