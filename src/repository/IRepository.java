package repository;

import exceptions.RepoException;
import model.state.PrgState;

import java.util.List;

public interface IRepository {
    void logPrgState(PrgState prg);
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> prgList);
}
