package view.GUI.programController;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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


class Pair<T1, T2> {
    T1 first;
    T2 second;

    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }
}

public class programController {
    private Controller controller;

    @FXML
    private TextField numberOfProgramStatesTextField;

    @FXML
    private TableView<Pair<Integer, IValue>> heapTableView;

    @FXML
    private TableColumn<Pair<Integer, IValue>, Integer> addressColumn;

    @FXML
    private TableColumn<Pair<Integer, IValue>, String> valueColumn;

    @FXML
    private ListView<String> outputListView;

    @FXML
    private ListView<String> fileTableListView;

    @FXML
    private ListView<Integer> programStateIdentifiersListView;

    @FXML
    private TableView<Pair<String, IValue>> symbolTableView;

    @FXML
    private TableColumn<Pair<String, IValue>, String> variableNameColumn;

    @FXML
    private TableColumn<Pair<String, IValue>, String> variableValueColumn;

    @FXML
    private ListView<String> executionStackListView;
    @FXML
    private TableView<Pair<Integer, Integer>> latchTableView;

    @FXML
    private TableColumn<Pair<Integer, Integer>, Integer> latchLocationColumn;

    @FXML
    private TableColumn<Pair<Integer, Integer>, Integer> latchValueColumn;

    @FXML
    private Button runOneStepButton;

    public void setController(Controller controller) {
        this.controller = controller;
        populate();
    }

    @FXML
    public void initialize() {
        programStateIdentifiersListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        addressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().first).asObject());
        valueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().second.toString()));
        variableNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().first));
        variableValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().second.toString()));
        latchLocationColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().first).asObject());
        latchValueColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().second).asObject());
    }

    private PrgState getCurrentProgramState() {
        if (controller.getProgramStates().size() == 0)
            return null;
        else {
            int currentId = programStateIdentifiersListView.getSelectionModel().getSelectedIndex();
            if (currentId == -1)
                return controller.getProgramStates().get(0);
            else
                return controller.getProgramStates().get(currentId);
        }
    }

    private void populate() {
        if (getCurrentProgramState() == null)
            return;
        populateHeapTableView();
        populateOutputListView();
        populateFileTableListView();
        populateProgramStateIdentifiersListView();
        populateSymbolTableView();
        populateExecutionStackListView();
        populateLatchTableView();

    }

    @FXML
    private void changeProgramState(MouseEvent event) {
        populateExecutionStackListView();
        populateSymbolTableView();
    }

    private void populateNumberOfProgramStatesTextField() {
        List<PrgState> programStates = controller.getProgramStates();
        numberOfProgramStatesTextField.setText(String.valueOf(programStates.size()));
    }

    private void populateHeapTableView() {
        PrgState programState = getCurrentProgramState();
        IHeap heap = programState.getHeap();
        ArrayList<Pair<Integer, IValue>> heapEntries = new ArrayList<>();
        for(Map.Entry<Integer, IValue> entry: heap.getContent().entrySet()) {
            heapEntries.add(new Pair<>(entry.getKey(), entry.getValue()));
        }
        heapTableView.setItems(FXCollections.observableArrayList(heapEntries));
    }

    private void populateOutputListView() {
        PrgState programState = getCurrentProgramState();
        List<String> output = new ArrayList<>();
        List<IValue> outputList = Objects.requireNonNull(programState).getOutputList().getAll();
        int index;
        for (index = 0; index < outputList.size(); index++){
            output.add(outputList.get(index).toString());
        }
        outputListView.setItems(FXCollections.observableArrayList(output));
    }

    private void populateFileTableListView() {
        PrgState programState = getCurrentProgramState();
        List<String> files = new ArrayList();
        for (StringValue Object : programState.getFileTable().getContent().keySet())
            files.add(Object.getValue().toString());
        //List<String> files = new ArrayList<>(Objects.requireNonNull(programState).getFileTable().getContent().keySet());
        fileTableListView.setItems(FXCollections.observableList(files));
    }

    private void populateProgramStateIdentifiersListView() {
        List<PrgState> programStates = controller.getProgramStates();
        List<Integer> idList = programStates.stream().map(PrgState::getNextId).collect(Collectors.toList());
        programStateIdentifiersListView.setItems(FXCollections.observableList(idList));
        populateNumberOfProgramStatesTextField();
    }

    private void populateSymbolTableView() {
        PrgState programState = getCurrentProgramState();
        MyIDictionary<String, IValue> symbolTable = Objects.requireNonNull(programState).getSymTable();
        ArrayList<Pair<String, IValue>> symbolTableEntries = new ArrayList<>();
        for (Map.Entry<String, IValue> entry: symbolTable.getContent().entrySet()) {
            symbolTableEntries.add(new Pair<>(entry.getKey(), entry.getValue()));
        }
        symbolTableView.setItems(FXCollections.observableArrayList(symbolTableEntries));
    }

    private void populateExecutionStackListView() {
        PrgState programState = getCurrentProgramState();
        List<String> executionStackToString = new ArrayList<>();
        if (programState != null)
            for (IStatement statement: programState.getExecStack().getReversed()) {
                executionStackToString.add(statement.toString());
            }
        executionStackListView.setItems(FXCollections.observableList(executionStackToString));
    }

    private void populateLatchTableView() {
        PrgState programState = getCurrentProgramState();
        ILatchTable latchTable = Objects.requireNonNull(programState).getLatchTable();
        ArrayList<Pair<Integer, Integer>> latchTableEntries = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry: latchTable.getContent().entrySet()) {
            latchTableEntries.add(new Pair<>(entry.getKey(), entry.getValue()));
        }
        latchTableView.setItems(FXCollections.observableArrayList(latchTableEntries));
    }

    @FXML
    private void runOneStep(MouseEvent mouseEvent) {
        if (controller != null) {
            try {
                List<PrgState> programStates = Objects.requireNonNull(controller.getProgramStates());
                if (programStates.size() > 0) {
                    controller.oneStepForAll(controller.getProgramStates());
                    populate();
                    programStates = controller.removeCompletedPrg(controller.getProgramStates());
                    controller.setProgramStates(programStates);
                    populateProgramStateIdentifiersListView();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText("An error has occured!");
                    alert.setContentText("There is nothing left to execute!");
                    alert.showAndWait();
                }
            } catch (InterruptedException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Execution error!");
                alert.setHeaderText("An execution error has occured!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("An error has occured!");
            alert.setContentText("No program selected!");
            alert.showAndWait();
        }
    }
}