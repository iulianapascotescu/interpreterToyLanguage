package repository;
import exceptions.MyException;
import model.programState.InterfaceList;
import model.programState.MyList;
import model.programState.ProgramState;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements RepositoryInterface{
    private List<ProgramState> programStates;
    private String logFilePath;

    public Repository(String path){
        programStates=new ArrayList<ProgramState>();
        this.logFilePath=path;
    }

    public void addProgramState(ProgramState program)
    {
        programStates.add(program);
    }
    public List<ProgramState> getProgramStates(){return programStates;}
    public void setProgramStates(List<ProgramState> prg){programStates=prg;}
    public void logProgramStateExecution(ProgramState programState) throws MyException{
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.println(programState.toString());
            logFile.close();
        }
        catch (Exception exception) {
            throw new MyException(exception.toString());
        }
    }
}
