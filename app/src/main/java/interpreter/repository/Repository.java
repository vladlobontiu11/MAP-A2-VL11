package interpreter.repository;

import interpreter.model.PrgState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Repository implements IRepository {
    private final List<PrgState> programStates = new ArrayList<>();

    public Repository(PrgState programState) {
        programStates.add(Objects.requireNonNull(programState));
    }

    @Override
    public PrgState getCrtPrg() {
        return programStates.getFirst();
    }
}
