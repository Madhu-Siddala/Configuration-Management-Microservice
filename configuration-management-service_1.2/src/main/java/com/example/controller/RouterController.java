package com.example.controller;

import com.example.service.RouterService;
import com.example.service.ChangeLogService;
import com.example.model.ChangeLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/router")
public class RouterController {

    private final RouterService routerService;

    public RouterController(RouterService routerService) {
        this.routerService = routerService;
    }

    // If using GET method
//    @GetMapping("/change-hostname")
//    public String changeHostname(@RequestParam String newHostname) {
//        return routerService.changeHostname(newHostname);
//    }

    // If using POST method
    @PostMapping("/change-hostname")
    public String changeHostname(@RequestParam String newHostname) {
        return routerService.changeHostname(newHostname);
    }
}