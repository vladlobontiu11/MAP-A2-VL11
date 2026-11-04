package interpreter;
import interpreter.controller.Controller;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.repository.Repository;
import interpreter.view.*;
import java.util.List;
public class Main {
    public static void main(String[] args){
        List<IStmt> programs=List.of(Examples.EX1,Examples.EX2,Examples.EX3,Examples.EX4,Examples.EX5);
        TextMenu menu=new TextMenu(); menu.addCommand(new ExitCommand("0","exit"));
        for(int i=0;i<programs.size();i++){IStmt p=programs.get(i);menu.addCommand(new RunExampleCommand(String.valueOf(i+1),p.toString(),new Controller(new Repository(PrgState.of(p),"log"+(i+1)+".txt"),true)));}
        menu.show();
    }
}
