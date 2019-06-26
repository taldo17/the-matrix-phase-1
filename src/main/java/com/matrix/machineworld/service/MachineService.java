package com.matrix.machineworld.service;

import com.matrix.machineworld.datamodel.Program;

public interface MachineService {
    int insert(Program program);
    Program getProgram(int programId);
}
