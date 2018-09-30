package ar.com.tacsutn.grupo1.eventapp.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class ControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    public MockMvc getMockMvc() {
        return mockMvc;
    }

    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    protected void test200Response(String route) throws Exception {
        mockMvc.perform(get(route)).andExpect(status().isOk());
    }

    protected void testRoute(String route, String expectedValue) throws Exception {
        mockMvc.perform(get(route)).andExpect(jsonPath("$.message").value(expectedValue));
    }

    @Test
    public void incorrectURIReturnsErrorStatus() throws Exception {
        mockMvc.perform(get("/fail")).andExpect(status().is4xxClientError());
    }
}
