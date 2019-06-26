package com.matrix.machineworld.repository;

import com.matrix.machineworld.datamodel.Program;

public interface ProgramsRepository {
    Program insert(Program program);

    Program getProgram(int programId);

}
