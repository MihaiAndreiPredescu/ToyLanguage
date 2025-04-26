package controller;

import exceptions.*;
import model.adt.MyDictionary;
import model.state.PrgState;
import model.value.IValue;
import repository.IRepository;
import repository.Repository;

import java.util.List;
import java.util.Map;

public interface IController {
    void allStep () throws ControllerException;
    List<PrgState> removeCompletedPrg(List<PrgState> states);
    void oneStepForAll(List<PrgState> states) throws InterruptedException;
    List<PrgState> getProgramStates() throws RepoException;
    void setProgramStates(List<PrgState> prgList);
}
