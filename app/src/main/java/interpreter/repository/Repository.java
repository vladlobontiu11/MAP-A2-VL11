package interpreter.repository;

import interpreter.model.PrgState;
import interpreter.exceptions.RepositoryException;
import interpreter.exceptions.MyException;
import java.io.*;

import java.util.ArrayList;
import java.util.List;
public class Repository implements IRepository {
    private final List<PrgState> programStates = new ArrayList<>();
    private final String logFilePath;

    public Repository(PrgState programState) {
        this(programState, "log.txt");
    }
    public Repository(PrgState programState, String logFilePath) {
        if (programState == null) {
            throw new NullPointerException("Program state cannot be null");
        }
        programStates.add(programState);
        this.logFilePath = logFilePath;
    }

    @Override
    public PrgState getCrtPrg() {
        return programStates.get(0);
    }
    @Override public void logPrgStateExec() throws MyException {
        try (var writer = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) { writer.println(getCrtPrg().toLogString()); }
        catch(IOException e){throw new RepositoryException("cannot write the log file "+logFilePath+": "+e.getMessage());}
    }
}
