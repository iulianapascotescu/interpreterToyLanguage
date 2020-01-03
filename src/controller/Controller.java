package controller;
import exceptions.MyException;
import model.programState.*;
import model.statement.InterfaceStatement;
import model.value.ReferenceValue;
import model.value.Value;
import repository.Repository;
import repository.RepositoryInterface;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private RepositoryInterface repository;
    private ExecutorService executor;
    public Controller(RepositoryInterface repo){
        executor = Executors.newFixedThreadPool(2);
        repository=repo;
    }

    /*
    public void allStep() throws MyException {
        ProgramState state = getCurrentState();
        this.repository.logProgramStateExecution();
        while (!state.getStack().empty()){
            oneStep();
            state.getHeap().setContent(unsafeGarbageCollector(
                    getAddrFromSymTable(Stream.concat(state.getSymbolTable().getContent().values().stream(),
                    state.getHeap().getContent().values().stream()).collect(Collectors.toList())),state.getHeap().getContent()));
        }
        oneStep();
    }
     */

    public RepositoryInterface getRepository(){return this.repository;}

    public void oneStepForAllPrograms(List<ProgramState> programStateList) throws MyException{
        programStateList.forEach(programState -> {
            try { repository.logProgramStateExecution(programState); }
            catch (MyException e) {e.getMessage();} });
        List<Callable<ProgramState>> callList = programStateList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(p::oneStep))
                .collect(Collectors.toList());
        try {
            List<ProgramState> newPrgList = executor.invokeAll(callList).stream().map(future -> {try {return future.get();}
            catch (Exception e){ return null; } }).filter(p->p!=null).collect(Collectors.toList());
            programStateList.addAll(newPrgList);
            programStateList.forEach(programState -> {
                try { repository.logProgramStateExecution(programState); }
                catch (MyException e) {e.getMessage();} });
        }
        catch (Exception e){
            throw new MyException(e.getMessage());
        }
        repository.setProgramStates(programStateList);
    }

    public void allStep() throws MyException {
        //executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStateList=removeCompletedPrg(repository.getProgramStates());
        while(programStateList.size()>0)
        {
            programStateList.get(0).getHeap().setContent(safeGarbageCollector(
                    getAddrFromSymTable(Stream.concat(programStateList.iterator().next().getSymbolTable().getContent().values().stream(),
                    programStateList.get(0).getHeap().getContent().values().stream()).collect(Collectors.toList())),
                    programStateList.get(0).getHeap().getContent()));

            oneStepForAllPrograms(programStateList);
            programStateList=removeCompletedPrg(repository.getProgramStates());
        }
        executor.shutdownNow();
        repository.setProgramStates(programStateList);
    }

    public List<ProgramState> removeCompletedPrg(List<ProgramState> inPrgList)
    {
        return inPrgList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    private Map<Integer,Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap){
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof ReferenceValue)
                .map(v-> {ReferenceValue v1 = (ReferenceValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }
}
