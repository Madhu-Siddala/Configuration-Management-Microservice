package com.example.controller;

import com.example.service.RouterService;
import com.example.service.ChangeLogService;
import com.example.model.ChangeLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/router")
public class RouterController {
    private final RouterService routerService;
    private final ChangeLogService changeLogService;

    public RouterController(RouterService routerService, ChangeLogService changeLogService) {
        this.routerService = routerService;
        this.changeLogService = changeLogService;
    }

    @GetMapping("/change-hostname")
    public String changeHostname(@RequestParam String newHostname) {
        String result = routerService.changeHostname(newHostname);
        ChangeLog log = new ChangeLog();
        log.setTimestamp(String.valueOf(System.currentTimeMillis()));
        log.setAction("Change Hostname");
        log.setDetails(result);
        changeLogService.logChange(log);
        return result;
    }

    // Add endpoints for backup, restore, and pushing configurations here
}
