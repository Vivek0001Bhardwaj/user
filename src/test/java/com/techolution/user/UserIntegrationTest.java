package com.techolution.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techolution.user.model.user.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.techolution.user.util.ConstructObjectUtil.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ActiveProfiles("unittest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
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
    @Order(2)
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
    @Order(3)
    public void getCurrentUser() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/api/user/me")
                        .param("username", "leanne")
                        .with(SecurityMockMvcRequestPostProcessors.testSecurityContext())
                        .content("")
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

    @Test
    @Order(4)
    public void checkUsernameAvailability() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/api/user/checkUsernameAvailability")
                        .param("username", "leanne")
                        .with(SecurityMockMvcRequestPostProcessors.testSecurityContext())
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.*").exists())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andReturn();

        Assertions.assertNotNull(response.getResponse().getContentAsString());
    }

    @Test
    @Order(5)
    public void checkEmailAvailability() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/api/user/checkEmailAvailability")
                        .param("email", "leanne.graham@gmail.com")
                        .with(SecurityMockMvcRequestPostProcessors.testSecurityContext())
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.*").exists())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andReturn();

        Assertions.assertNotNull(response.getResponse().getContentAsString());
    }

    @Test
    @Order(6)
    public void getUserProfile() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/api/user/{name}/profile", "leanne")
                        .with(SecurityMockMvcRequestPostProcessors.testSecurityContext())
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.*").exists())
                .andReturn();

        Assertions.assertNotNull(response.getResponse().getContentAsString());
    }

    @Test
    @Order(7)
    public void addUser() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                        .with(SecurityMockMvcRequestPostProcessors.testSecurityContext())
                        .param("username", "leanne")
                        .content(new ObjectMapper().writeValueAsString(constructUser()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.*").exists())
                .andReturn();

        Assertions.assertNotNull(response.getResponse().getContentAsString());
    }

    @Test
    @Order(8)
    public void updateUser() throws Exception {
        User user = constructUser();
        user.setPassword("new Password");

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.put("/api/user/{name}", "ervin")
                        .with(SecurityMockMvcRequestPostProcessors.testSecurityContext())
                        .param("username", "leanne")
                        .content(new ObjectMapper().writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.*").exists())
                .andReturn();

        Assertions.assertNotNull(response.getResponse().getContentAsString());
    }

    @Test
    @Order(9)
    public void giveAdmin() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.put("/api/user/{name}/giveAdmin", "ervin")
                        .with(SecurityMockMvcRequestPostProcessors.testSecurityContext())
                        .param("username", "leanne")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.*").exists())
                .andReturn();

        Assertions.assertNotNull(response.getResponse().getContentAsString());
    }

    @Test
    @Order(10)
    public void takeAdmin() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.put("/api/user/{name}/takeAdmin", "ervin")
                        .with(SecurityMockMvcRequestPostProcessors.testSecurityContext())
                        .param("username", "leanne")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.*").exists())
                .andReturn();

        Assertions.assertNotNull(response.getResponse().getContentAsString());
    }

    @Test
    @Order(11)
    public void setOrUpdateUser() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.put("/api/user/setOrUpdateInfo")
                        .with(SecurityMockMvcRequestPostProcessors.testSecurityContext())
                        .param("username", "ervin")
                        .content(new ObjectMapper().writeValueAsString(constructInfoRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.*").exists())
                .andReturn();

        Assertions.assertNotNull(response.getResponse().getContentAsString());
    }

    @Test
    @Order(12)
    public void deleteUser() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/{name}", "leanne")
                        .with(SecurityMockMvcRequestPostProcessors.testSecurityContext())
                        .param("username", "leanne")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.*").exists())
                .andReturn();

        Assertions.assertNotNull(response.getResponse().getContentAsString());
    }
}
