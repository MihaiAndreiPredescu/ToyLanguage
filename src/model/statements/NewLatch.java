package model.statements;


import model.adt.IHeap;
import model.adt.ILatchTable;
import model.adt.MyIDictionary;
import exceptions.*;
import model.state.PrgState;
import model.expressions.*;
import model.type.IntType;
import model.type.IType;
import model.value.IntValue;
import model.value.IValue;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLatch implements IStatement{
    private String var;
    private IExpression expression;
    private static Lock lock = new ReentrantLock();

    public NewLatch(String var, IExpression expression) {
        this.var = var;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpressionException {
        lock.lock();
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        IHeap heap = state.getHeap();
        ILatchTable latchTable = state.getLatchTable();
        IntValue nb = (IntValue) expression.evaluate(symTable, heap);
        int number = nb.getValue();
        int freeAddress = latchTable.getFree();
        latchTable.put(freeAddress, number);
        if (symTable.isDefined(var)) {
            symTable.update(var, new IntValue(freeAddress));
        }
        else {
            throw new ExpressionException("Var is not defined!");
        }
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        if (typeEnv.get(var).equals(new IntType())) {
            if (expression.typecheck(typeEnv).equals(new IntType())) {
                return typeEnv;
            }
            else {
                throw new ExpressionException("Expression must have type int!");
            }
        }
        else {
            throw new ExpressionException("Var must be of type int!");
        }
    }

    @Override
    public String toString() {
        return "NewLatch(" + var + ", " + expression + ")";
    }

    @Override
    public IStatement deepCopy(){
        return new NewLatch(var, expression);
    }
}