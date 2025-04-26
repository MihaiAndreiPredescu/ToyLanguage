/*
import controller.Controller;
import model.adt.MyDictionary;
import model.adt.MyHeap;
import model.adt.MyList;
import model.adt.MyStack;
import model.expressions.*;
import model.adt.MyHeap;
import model.state.PrgState;
import model.statements.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.RefType;
import model.type.StringType;
import model.value.*;
import repository.IRepository;
import repository.Repository;
import view.TextMenu;
import view.commands.ExitCommand;
import view.commands.RunExampleCommand;
import view.commands.RunExampleCommand;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        IStatement ex1 = new CompStatement(new VarDeclStatement("v", new IntType()), new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),new PrintStatement(new VariableExpression("v"))));

        try{ex1.typecheck(new MyDictionary<>());}catch (Exception e){e.getMessage();}

        PrgState crtPrgState1 = new PrgState(ex1, new MyStack<>(), new MyDictionary<String, IValue>(),new MyList<>(), new MyDictionary<StringValue, BufferedReader>(),new MyHeap());
        List<PrgState> list1 = new ArrayList<>();
        list1.add(crtPrgState1);
        IRepository repo1 = new Repository(list1,"log1.txt");
        Controller ctrl1 = new Controller(repo1);

        IStatement ex2 = new CompStatement(
                new VarDeclStatement("a", new IntType()),
                new CompStatement(
                        new VarDeclStatement("b",new IntType()),
                        new CompStatement(
                                new AssignStatement("a", new ArithmeticalExpression(
                                        new ValueExpression(new IntValue(2)),
                                        new ArithmeticalExpression(
                                                new ValueExpression(new IntValue(3)),
                                                new ValueExpression(new IntValue(5)),
                                                ArithmeticalOperation.MULTIPLY
                                        ),
                                        ArithmeticalOperation.PLUS
                                )),
                                new CompStatement(
                                        new AssignStatement("b", new ArithmeticalExpression(
                                                new VariableExpression("a"),
                                                new ValueExpression(new IntValue(1)),
                                                ArithmeticalOperation.PLUS
                                        )),
                                        new PrintStatement(new VariableExpression("b"))
                                )
                        )
                )
        );

        try{ex2.typecheck(new MyDictionary<>());}catch (Exception e){e.getMessage();}

        PrgState crtPrgState2 = new PrgState(ex2, new MyStack<>(), new MyDictionary<String, IValue>(),new MyList<>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap());
        List<PrgState> list2 = new ArrayList<>();
        list2.add(crtPrgState2);
        IRepository repo2 = new Repository(list2,"log2.txt");
        Controller ctrl2 = new Controller(repo2);

        IStatement ex3 = new CompStatement(
                new VarDeclStatement("a", new BoolType()),
                new CompStatement(new VarDeclStatement("v",new IntType()),
                        new CompStatement(new AssignStatement("a", new ValueExpression( new BoolValue(true))),
                                new CompStatement(
                                        new IfStatement(
                                                new VariableExpression("a"), new AssignStatement(
                                                "v", new ValueExpression(new IntValue(2))), new AssignStatement(
                                                "v", new ValueExpression(new IntValue(3)
                                        ))), new PrintStatement(new VariableExpression("v"))
                                ))));

        try{ex3.typecheck(new MyDictionary<>());}catch (Exception e){e.getMessage();}

        PrgState crtPrgState3 = new PrgState(ex3, new MyStack<>(), new MyDictionary<String, IValue>(),new MyList<>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap());
        List<PrgState> list3 = new ArrayList<>();
        list3.add(crtPrgState3);
        IRepository repo3 = new Repository(list3,"log3.txt");
        Controller ctrl3 = new Controller(repo3);

        IStatement ex4 = new CompStatement(
                new VarDeclStatement("varf", new StringType()), new CompStatement(
                new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))), new CompStatement(
                new OpenRFile(new VariableExpression("varf")), new CompStatement(
                new VarDeclStatement("varc", new IntType()), new CompStatement(
                new ReadFileStatement(new VariableExpression("varf"), "varc"), new CompStatement(
                new PrintStatement(new VariableExpression("varc")), new CompStatement(
                new ReadFileStatement(new VariableExpression("varf"), "varc"), new CompStatement(
                new PrintStatement(new VariableExpression("varc")), new CloseRFile(new VariableExpression("varf"))))))))));

        try{ex4.typecheck(new MyDictionary<>());}catch (Exception e){e.getMessage();}

        PrgState crtPrgState4 = new PrgState(ex4, new MyStack<>(), new MyDictionary<String, IValue>(),new MyList<>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap());
        List<PrgState> list4 = new ArrayList<>();
        list4.add(crtPrgState4);
        IRepository repo4 = new Repository(list4,"log4.txt");
        Controller ctrl4 = new Controller(repo4);

        IStatement ex5 = new CompStatement(
                new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(
                                new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(
                                        new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new VariableExpression("a"))
                                        )
                                )
                        )
                )
        );

        try{ex5.typecheck(new MyDictionary<>());}catch (Exception e){e.getMessage();}

        PrgState crtPrgState5 = new PrgState(ex5, new MyStack<>(), new MyDictionary<String, IValue>(),new MyList<>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap());
        List<PrgState> list5 = new ArrayList<>();
        list5.add(crtPrgState5);
        IRepository repo5 = new Repository(list5, "log5.txt");
        Controller ctrl5 = new Controller(repo5);

        IStatement ex6 = new CompStatement(
                new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(
                                new PrintStatement(new HeapReadExpression(new VariableExpression("v"))),
                                new CompStatement(
                                        new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(
                                                new ArithmeticalExpression(
                                                        new HeapReadExpression(new VariableExpression("v")),
                                                        new ValueExpression(new IntValue(5)),
                                                        ArithmeticalOperation.PLUS
                                                )
                                        )
                                )
                        )
                )
        );

        try{ex6.typecheck(new MyDictionary<>());}catch (Exception e){e.getMessage();}

        PrgState crtPrgState6 = new PrgState(ex6, new MyStack<>(), new MyDictionary<>(),new MyList<>(), new MyDictionary<>(), new MyHeap());
        List<PrgState> list6 = new ArrayList<>();
        list6.add(crtPrgState6);
        IRepository repo6 = new Repository(list6, "log6.txt");
        Controller ctrl6 = new Controller(repo6);

        IStatement ex7 = new CompStatement(
                new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(
                                new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(
                                        new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompStatement(
                                                new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(
                                                        new HeapReadExpression(
                                                                new HeapReadExpression(new VariableExpression("a"))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        try{ex7.typecheck(new MyDictionary<>());}catch (Exception e){e.getMessage();}

        PrgState crtPrgState7 = new PrgState(ex7, new MyStack<>(), new MyDictionary<>(),new MyList<>(), new MyDictionary<>(), new MyHeap());
        List<PrgState> list7 = new ArrayList<>();
        list7.add(crtPrgState7);
        IRepository repo7 = new Repository(list7, "log7.txt");
        Controller ctrl7 = new Controller(repo7);

        IStatement ex8 = new CompStatement(
                new VarDeclStatement("v", new IntType()),
                new CompStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(4))),
                        new CompStatement(
                                new WhileStatement(
                                        new RelationalExpression(
                                                new VariableExpression("v"),
                                                new ValueExpression(new IntValue(0)),
                                                RelationalOperation.GREATER
                                        ),
                                        new CompStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new AssignStatement("v",
                                                        new ArithmeticalExpression(
                                                                new VariableExpression("v"),
                                                                new ValueExpression(new IntValue(1)),
                                                                ArithmeticalOperation.MINUS
                                                        )
                                                )
                                        )
                                ),
                                new PrintStatement(new VariableExpression("v"))
                        )
                )
        );

        try{ex8.typecheck(new MyDictionary<>());}catch (Exception e){e.getMessage();}

        PrgState crtPrgState8 = new PrgState(ex8, new MyStack<>(), new MyDictionary<>(),new MyList<>(), new MyDictionary<>(), new MyHeap());
        List<PrgState> list8 = new ArrayList<>();
        list8.add(crtPrgState8);
        IRepository repo8 = new Repository(list8, "log8.txt");
        Controller ctrl8 = new Controller(repo8);

        IStatement ex9 = new CompStatement(
                new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(
                                new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(
                                        new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompStatement(
                                                new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new HeapReadExpression(
                                                        new HeapReadExpression(new VariableExpression("a")))
                                                )
                                        )
                                )
                        )
                )
        );

        try{ex9.typecheck(new MyDictionary<>());}catch (Exception e){e.getMessage();}

        PrgState crtPrgState9 = new PrgState(ex9, new MyStack<>(), new MyDictionary<>(),new MyList<>(), new MyDictionary<>(), new MyHeap());
        List<PrgState> list9 = new ArrayList<>();
        list9.add(crtPrgState9);
        IRepository repo9 = new Repository(list9, "log9.txt");
        Controller ctrl9 = new Controller(repo9);

        IStatement ex10 = new CompStatement(new VarDeclStatement("a", new RefType(new IntType())),
                new CompStatement(new VarDeclStatement("b", new RefType(new IntType())),
                        new CompStatement(new VarDeclStatement("v", new IntType()),
                                new CompStatement(new HeapAllocationStatement("a", new ValueExpression(new IntValue(0))),
                                        new CompStatement(new HeapAllocationStatement("b", new ValueExpression(new IntValue(0))),
                                                new CompStatement(new WriteHeapStatement("a", new ValueExpression(new IntValue(1))),
                                                        new CompStatement(new WriteHeapStatement("b", new ValueExpression(new IntValue(2))),
                                                                new CompStatement(new ConditionalAssigment("v", new RelationalExpression( new HeapReadExpression(new VariableExpression("a")), new HeapReadExpression(new VariableExpression("b")), RelationalOperation.SMALLER), new ValueExpression(new IntValue(100)), new ValueExpression(new IntValue(200))),
                                                                        new CompStatement(new PrintStatement(new VariableExpression("v")),
                                                                                new CompStatement(new ConditionalAssigment("v", new RelationalExpression( new ArithmeticalExpression( new HeapReadExpression(new VariableExpression("b")), new ValueExpression(new IntValue(2)), ArithmeticalOperation.MINUS), new HeapReadExpression(new VariableExpression("a")), RelationalOperation.GREATER), new ValueExpression(new IntValue(100)), new ValueExpression(new IntValue(200))),
                                                                                        new PrintStatement(new VariableExpression("v"))
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        try{ex10.typecheck(new MyDictionary<>());}catch (Exception e){e.getMessage();}

        PrgState crtPrgState10 = new PrgState(ex10, new MyStack<>(), new MyDictionary<>(),new MyList<>(), new MyDictionary<>(), new MyHeap());
        List<PrgState> list10 = new ArrayList<>();
        list10.add(crtPrgState10);
        IRepository repo10 = new Repository(list10, "log10.txt");
        Controller ctrl10 = new Controller(repo10);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0","exit"));
        menu.addCommand(new RunExampleCommand("1",ex1.toString(),ctrl1));
        menu.addCommand(new RunExampleCommand("2",ex2.toString(),ctrl2));
        menu.addCommand(new RunExampleCommand("3",ex3.toString(),ctrl3));
        menu.addCommand(new RunExampleCommand("4",ex4.toString(),ctrl4));
        menu.addCommand(new RunExampleCommand("5",ex5.toString(),ctrl5));
        menu.addCommand(new RunExampleCommand("6",ex6.toString(),ctrl6));
        menu.addCommand(new RunExampleCommand("7",ex7.toString(),ctrl7));
        menu.addCommand(new RunExampleCommand("7",ex7.toString(),ctrl7));
        menu.addCommand(new RunExampleCommand("8",ex8.toString(),ctrl8));
        menu.addCommand(new RunExampleCommand("9",ex9.toString(),ctrl9));
        menu.addCommand(new RunExampleCommand("10",ex10.toString(),ctrl10));
        menu.show();

    }
}
*/