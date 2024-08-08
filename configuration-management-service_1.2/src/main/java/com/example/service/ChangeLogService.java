package com.example.service;

import com.example.model.ChangeLog;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChangeLogService {
    private List<ChangeLog> changeLogs = new ArrayList<>();

    public void logChange(ChangeLog changeLog) {
        changeLogs.add(changeLog);
    }

    public List<ChangeLog> getChangeLogs() {
        return changeLogs;
    }
}
