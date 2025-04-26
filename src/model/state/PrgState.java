package model.state;
import exceptions.ControllerException;
import model.adt.*;
import model.expressions.IExpression;
import model.statements.IStatement;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;

public class PrgState {
    private MyIStack<IStatement> execStack;
    private MyIDictionary<String, IValue>  symTable;
    private MyIList<IValue> outputList;
    private IStatement originalStatement;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    ILatchTable latchTable;
    private IHeap heap;
    static int nextId=0;
    private int id;


    public PrgState(IStatement initState, MyIStack<IStatement> execStack, MyIDictionary<String, IValue> symTable,MyIList<IValue> outputList,MyIDictionary<StringValue, BufferedReader> fileTable, IHeap heap, ILatchTable latchTable ) {
        this.execStack = execStack;
        this.symTable = symTable;
        this.outputList = outputList;
        originalStatement = initState.deepCopy();
        execStack.push(originalStatement);
        this.fileTable = fileTable;
        this.heap = heap;
        id = this.getNextId();
        this.latchTable = latchTable;
    }

    public ILatchTable getLatchTable() {
        return latchTable;
    }
    public void setLatchTable(ILatchTable latchTable) {
        this.latchTable = latchTable;
    }

    public MyIStack<IStatement> getExecStack() {
        return execStack;
    }
    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }
    public MyIList<IValue> getOutputList() {
        return outputList;
    }
    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }
    public IHeap getHeap() {return heap;}

    @Override
    public String toString() {
        return "Current prg state with id: " + id + "\n" + execStack.toString() + "\n" + symTable.toString() + "\n"
                + outputList.toString() + "\n" + toStringFile() + "\n" + this.heap.toString() + "\n" + this.latchTable.toString();
    }
    public String toStringFile(){
        String s="File table:\n" ;
        for(StringValue v : fileTable.keys()){
            s+=v.getValue()+ "\n";
        }
        return s;
    }

    public void setHeap(IHeap heap) {
        this.heap = heap;
    }

    public synchronized int getNextId() {
        return ++nextId;
    }

    public PrgState oneStep() throws ControllerException {
        if (execStack.isEmpty()) {
            throw new ControllerException("Stack is empty!");
        }
        try {
            IStatement statement = execStack.pop();
            return statement.execute(this);
        } catch (Exception e) {
            throw new ControllerException(e.getMessage());
        }
    }

    public boolean isNotCompleted(){
        return !execStack.isEmpty();
    }

}
