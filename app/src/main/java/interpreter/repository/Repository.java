package interpreter.repository;

import interpreter.model.PrgState;

import java.util.ArrayList;
import java.util.List;
public class Repository implements IRepository {
    private final List<PrgState> programStates = new ArrayList<>();

    public Repository(PrgState programState) {
        if (programState == null) {
            throw new NullPointerException("Program state cannot be null");
        }
        programStates.add(programState);
    }

    @Override
    public PrgState getCrtPrg() {
        return programStates.get(0);
    }
}
