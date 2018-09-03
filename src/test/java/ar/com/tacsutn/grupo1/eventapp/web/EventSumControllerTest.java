package ar.com.tacsutn.grupo1.eventapp.web;

import ar.com.tacsutn.grupo1.eventapp.Controller;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class EventSumControllerTest extends ControllerTest {
    @Autowired private EventSumController controller;

    @Override
    public Controller getController() {
        return this.controller;
    }

    @Test
    public void v1CorrectURIShouldReturnOkStatus() throws Exception {
        test200Response("events/total_events");
    }

    @Test
    public void v1ShouldReturnDefaultValueWhenNoParamIsSet() throws Exception {
        testRoute("events/total_events","Se registraron 100 eventos desde la fecha 01/01/1970 hasta 31/12/9999");
    }

    @Test
    public void v1ShouldReturnSetValueWhenParamsAreSet() throws Exception {
        testRoute("events/total_events?from=14/12/1492&to=02/09/2018","Se registraron 100 eventos desde la fecha 14/12/1492 hasta 02/09/2018");
    }

    @Test
    public void v2ShouldReturnDefaultValueWhenNoParamIsSet() throws Exception {
        testRoute("events","Se registraron 100 eventos desde la fecha 01/01/1970 hasta 31/12/9999");
    }

    @Test
    public void v2ShouldReturnSetValueWhenParamsAreSet() throws Exception {
        testRoute("events?from=14/12/1492&to=02/09/2018","Se registraron 100 eventos desde la fecha 14/12/1492 hasta 02/09/2018");
    }
}
