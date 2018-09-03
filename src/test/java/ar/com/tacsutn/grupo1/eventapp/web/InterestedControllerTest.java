package ar.com.tacsutn.grupo1.eventapp.web;

import ar.com.tacsutn.grupo1.eventapp.Controller;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class InterestedControllerTest extends ControllerTest {
    @Autowired private InterestedController controller;

    @Override
    public Controller getController() {
        return this.controller;
    }

    @Test
    public void v1CorrectURIShouldReturnOkStatus() throws Exception {
        test200Response("events/event");
    }

    @Test
    public void v1ShouldReturnDefaultValueWhenNoParamIsSet() throws Exception {
        testRoute("events/event","10 usuarios estan interesados en el evento 0");
    }

    @Test
    public void v1ShouldReturnSetValueWhenParamIsSet() throws Exception {
        testRoute("events/event?id=1", "10 usuarios estan interesados en el evento 1");
    }

    @Test
    public void v2ACorrectURIShouldReturnOkStatus() throws Exception {
        test200Response("events/0/users");
    }

    @Test
    public void v2AShouldReturnSetValueWhenParamIsSet() throws Exception {
        testRoute("events/0/users","Los usuarios 0, 1, y 2 estan interesados en el evento 0");
    }

    @Test
    public void v2BCorrectURIShouldReturnOkStatus() throws Exception {
        test200Response("events/0/total_users");
    }

    @Test
    public void v2BShouldReturnSetValueWhenParamIsSet() throws Exception {
        testRoute("events/100/total_users","10 usuarios estan interesados en el evento 100");
    }
}