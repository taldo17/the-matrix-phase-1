package com.matrix.machineworld.controllers;

import com.matrix.machineworld.datamodel.Program;
import com.matrix.machineworld.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProgramController {

    @Autowired
    MachineService machineService;

    @GetMapping("/program/{id}")
    public Program get(@PathVariable int id) {
        return machineService.getProgram(id);
    }

    @PostMapping("/program")
    public int insert(@RequestBody Program program) {
        return machineService.insert(program);
    }
}
