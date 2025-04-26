package controller;

import exceptions.ControllerException;
import exceptions.RepoException;
import model.adt.MyDictionary;
import model.adt.MyIDictionary;
import model.state.PrgState;
import model.value.IValue;
import model.value.RefValue;
import repository.GarbageCollector;
import repository.IRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller implements IController {
    public IRepository repo;
    private ExecutorService exec;

    public Controller(IRepository repo) {
        this.repo = repo;
        exec = Executors.newFixedThreadPool(2);
    }

    public void allStep() throws ControllerException {
        try {

            List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());

            while (prgList.size() > 0) {
                GarbageCollector.conservativeGarbageCollector(prgList);

                oneStepForAll(prgList);
                prgList = removeCompletedPrg(repo.getPrgList());
            }

            exec.shutdownNow();
            repo.setPrgList(prgList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> states){
        return states.stream().filter(p->p.isNotCompleted()).collect(Collectors.toList());
    }

    public void oneStepForAll(List<PrgState> prgList) throws InterruptedException {
        prgList.forEach(p -> repo.logPrgState(p));
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (() -> p.oneStep()))
                .collect(Collectors.toList());
        List<PrgState> newPrgList = exec.invokeAll(callList).stream().map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        ).filter(p->p!=null).collect(Collectors.toList());
        prgList.addAll(newPrgList);
        prgList.stream()
                .forEach(p -> repo.logPrgState(p));
        repo.setPrgList(prgList);

    }

    public List<PrgState> getProgramStates() throws RepoException {
        return repo.getPrgList();
    }

    public void setProgramStates(List<PrgState> prgList){
        repo.setPrgList(prgList);
    }
}

