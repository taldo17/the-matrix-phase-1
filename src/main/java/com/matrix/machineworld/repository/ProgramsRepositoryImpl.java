package com.matrix.machineworld.repository;

import com.matrix.machineworld.datamodel.Program;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProgramsRepositoryImpl implements ProgramsRepository {
    Logger logger = LoggerFactory.getLogger(ProgramsRepositoryImpl.class);

    private static final int MAX_NUMBER_OF_RETRIES = 3;
    @Autowired
    ProgramCrudRepository programCrudRepository;

    @Override
    public Program insert(Program program) {
        try {
            return programCrudRepository.save(program);
        }
        catch (DataAccessException e) {
            logger.error("Error while querying the DB",  e);
            throw e;
        }
    }

    @Override
    public Program getProgram(int programId) {
        Optional<Program> response = null;
        for (int trial = 0; trial < MAX_NUMBER_OF_RETRIES; trial++) {
            response = tryFindingInDB(programId, trial == MAX_NUMBER_OF_RETRIES);
            if(response.isPresent()){
                break;
            }
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                logger.error("Thread was interrupted", e);
            }
        }
        return response.orElseGet(() -> null);
    }


    private Optional<Program> tryFindingInDB(int programId, boolean lastTrial) {
        Optional<Program> response = null;
        try {
            response = programCrudRepository.findById(programId);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("Error while querying the DB",  e);
            if(lastTrial){
                throw e;
            }
        }
        return response;
    }
}
