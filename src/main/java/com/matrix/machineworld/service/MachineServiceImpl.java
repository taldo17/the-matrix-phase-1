package com.matrix.machineworld.service;

import com.matrix.machineworld.datamodel.Program;
import com.matrix.machineworld.repository.ProgramsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;

@Service
public class MachineServiceImpl implements MachineService {
    private Logger logger = LoggerFactory.getLogger(MachineServiceImpl.class);

    @Autowired
    private ProgramsRepository programsRepository;


    @Override
    public int insert(Program program) {

        try {
            validateProgram(program);
            logger.info("inserting program: " + program.toString());
            Program savedProgram = programsRepository.insert(program);
            logger.info("inserted program: " + program.toString());
            return savedProgram.getId();
        }
        catch (DataAccessException e) {
            logger.error("a data access error occurred", e);
            throw e;
        }
        catch (Throwable t) {
            logger.error("an error occurred while trying to insert a program", t);
            throw t;
        }
    }

    @Override
    public Program getProgram(int programId) {
        try {
            validateId(programId);
            logger.info("fetching program with program id: " + programId);
            Program program = programsRepository.getProgram(programId);
            if (program != null) {
                logger.info("fetched program: " + program.toString());
            }
            return program;
        }
        catch (DataAccessException e) {
            logger.error("a data access error occurred", e);
            throw e;
        } catch (Throwable t) {
            logger.error("an error occurred while trying to get a program", t);
            throw t;
        }
    }

    private void validateId(int programId) {
        if(programId <= 0){
            throw new IllegalArgumentException("Program id must be positive");
        }
    }


    private void validateProgram(Program program) {
        if (program.getName() == null || program.getName().isEmpty()) {
            throw new ValidationException("The Name cannot be empty");
        }
        if (program.getCreator() == null || program.getCreator().isEmpty()) {
            throw new ValidationException("The creator cannot be empty");
        }
        if (program.getPurpose() == null || program.getPurpose().isEmpty()) {
            throw new ValidationException("The purpose cannot be empty");
        }
        if (program.getCpuUsage() > 30) {
            throw new ValidationException("cpu is over 30");
        }

        if (program.getMemoryConsumption() > 30) {
            throw new ValidationException("memory is over 30");
        }
    }
}
