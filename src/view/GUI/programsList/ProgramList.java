package view.GUI.programsList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import view.GUI.programController.*;
import controller.Controller;
import model.adt.*;
import exceptions.*;
import model.state.PrgState;
import model.expressions.*;
import model.statements.*;
import model.type.*;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import model.value.IValue;
import repository.IRepository;
import repository.Repository;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class ProgramList {
    private programController programExecutorController;

    public void setProgramExecutorController(programController programExecutorController) {
        this.programExecutorController = programExecutorController;
    }
    @FXML
    private ListView<IStatement> programsListView;

    @FXML
    private Button displayButton;

    @FXML
    public void initialize() {
        programsListView.setItems(getAllStatements());
        programsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void displayProgram(ActionEvent actionEvent) {
        IStatement selectedStatement = programsListView.getSelectionModel().getSelectedItem();
        if (selectedStatement == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error encountered!");
            alert.setContentText("No statement selected!");
            alert.showAndWait();
        } else {
            int id = programsListView.getSelectionModel().getSelectedIndex();
            try {
                selectedStatement.typecheck(new MyDictionary<String, IType>());
                PrgState prg1 = new PrgState(selectedStatement, new MyStack<IStatement>(), new MyDictionary<String, IValue>(), new MyList<IValue>(),
                        new MyDictionary<>(), new MyHeap(), new LatchTable());
                ArrayList<PrgState> l1 = new ArrayList<PrgState>();
                l1.add(prg1);
                IRepository repo1 = new Repository(l1, "log" + (id + 1) + ".txt");
                Controller controller = new Controller(repo1);
                programExecutorController.setController(controller);
            } catch (ExpressionException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error encountered!");
                alert.setContentText(exception.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    private ObservableList<IStatement> getAllStatements() {
        List<IStatement> allStatements = new ArrayList<>();
/*
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExpression(new IntValue(2))), new PrintStmt(new VarExp("v"))));
        allStatements.add(ex1);

        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp("+", new ValueExp(new IntValue(2)),
                                new ArithExp("*", new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithExp("+", new VarExp("a"),
                                        new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
        allStatements.add(ex2);

        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))),
                                        new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));
        allStatements.add(ex3);

        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new OpenRFile(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")), new CloseRFile(new VarExp("varf"))))))))));
        allStatements.add(ex4);

        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalExp(">", new VarExp("v"), new ValueExp(new IntValue(0))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp("-", new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));
        allStatements.add(ex5);

        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))))))));
        allStatements.add(ex6);

        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt(new CompStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExp(new VarExp("a")))
                                                        )))),
                                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExp(new VarExp("a")))))))));
        allStatements.add(ex7);

        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new BoolType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));

        allStatements.add(ex8);
*/
        IStatement ex9 = new CompStatement(new VarDeclStatement("v1", new RefType(new IntType())),
                new CompStatement(new VarDeclStatement("v2", new RefType(new IntType())),
                        new CompStatement(new VarDeclStatement("v3", new RefType(new IntType())),
                                new CompStatement(new VarDeclStatement("cnt", new IntType()),
                                        new CompStatement(new HeapAllocationStatement("v1", new ValueExpression(new IntValue(2))),
                                                new CompStatement(new HeapAllocationStatement("v2", new ValueExpression(new IntValue(3))),
                                                        new CompStatement(new HeapAllocationStatement("v3", new ValueExpression(new IntValue(4))),
                                                                new CompStatement(new NewLatch("cnt", new HeapReadExpression(new VariableExpression("v2"))),
                                                                        new CompStatement(new ForkStatement(new CompStatement(new WriteHeapStatement("v1", new ArithmeticalExpression( new HeapReadExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)), ArithmeticalOperation.MULTIPLY)),
                                                                                new CompStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v1"))),
                                                                                        new CompStatement(new CountDown("cnt"),
                                                                                                new ForkStatement(new CompStatement(new WriteHeapStatement("v2", new ArithmeticalExpression( new HeapReadExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(10)), ArithmeticalOperation.MULTIPLY)),
                                                                                                        new CompStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v2"))),
                                                                                                                new CompStatement(new CountDown("cnt"),
                                                                                                                        new ForkStatement(new CompStatement(new WriteHeapStatement("v3", new ArithmeticalExpression(new HeapReadExpression(new VariableExpression("v3")), new ValueExpression(new IntValue(10)), ArithmeticalOperation.MULTIPLY)),
                                                                                                                                new CompStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v3"))),
                                                                                                                                        new CountDown("cnt")
                                                                                                                                )
                                                                                                                        )
                                                                                                                        )
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                        ),
                                                                                new CompStatement(new Await("cnt"),
                                                                                        new CompStatement(new PrintStatement(new ValueExpression(new IntValue(100))),
                                                                                                new CompStatement(new CountDown("cnt"),
                                                                                                        new PrintStatement(new ValueExpression(new IntValue(100)))
                                                                                                )
                                                                                        )
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

        allStatements.add(ex9);


        return FXCollections.observableArrayList(allStatements);
    }
}