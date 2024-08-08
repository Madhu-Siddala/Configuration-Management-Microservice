package com.example.nms;

import com.example.service.RouterService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationTests {

    private RouterService routerService = new RouterService();

    @Test
    public void testChangeHostname() {
        String response = routerService.changeHostname("NewRouter");
        assert(response.contains("Hostname changed to NewRouter"));
    }
}
