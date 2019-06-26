package com.matrix.machineworld;

import com.matrix.machineworld.datamodel.Program;
import com.matrix.machineworld.repository.ProgramsRepository;
import com.matrix.machineworld.service.MachineService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MachineWorldApplication.class)
public class MachineWorldTest {

    @Autowired
    MachineService machineService;
    @Autowired
    ProgramsRepository programsRepository;


    @Test
    public void insertedProgramExistsInTheDB() {
        Program program = new Program();
        String expectedProgramName = "Very Unique Name";
        program.setName(expectedProgramName);
        program.setCreator("Creator");
        program.setPurpose("Purpose");
        int programId = machineService.insert(program);
        Program insertedProgram = machineService.getProgram(programId);
        Assert.assertEquals(expectedProgramName, insertedProgram.getName());
    }

    @Test
    public void getProgramThatDoesNotExists() {
        Program program = new Program();
        String expectedProgramName = "Very Unique Name";
        program.setName(expectedProgramName);
        program.setCreator("Creator");
        program.setPurpose("Purpose");
        int programId = machineService.insert(program);
        Program fetchedProgram = machineService.getProgram(Integer.MAX_VALUE);
        Assert.assertNull(fetchedProgram);
    }
}
