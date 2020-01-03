package repository;
import exceptions.MyException;
import model.programState.InterfaceList;
import model.programState.MyList;
import model.programState.ProgramState;

import java.util.List;

public interface RepositoryInterface {
    //public ProgramState getCurrentProgramState();
    void addProgramState(ProgramState program);
    void logProgramStateExecution(ProgramState programState) throws MyException;
    List<ProgramState> getProgramStates();
    void setProgramStates(List<ProgramState> prg);
}
