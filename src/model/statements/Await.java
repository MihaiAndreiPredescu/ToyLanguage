package model.statements;

import model.adt.ILatchTable;
import model.adt.MyIDictionary;
import exceptions.*;
import model.state.PrgState;
import model.type.IntType;
import model.type.IType;
import model.value.IntValue;
import model.value.IValue;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Await implements IStatement{
    private String var;
    private static Lock lock = new ReentrantLock();

    public Await(String var) {
        this.var = var;
    }


    @Override
    public PrgState execute(PrgState state) throws ExpressionException {
        lock.lock();
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        ILatchTable latchTable = state.getLatchTable();
        if (symTable.isDefined(var)) {
            IntValue index = (IntValue) symTable.get(var);
            int foundIndex = index.getValue();
            if (latchTable.containsKey(foundIndex)) {
                if (latchTable.get(foundIndex) != 0) {
                    state.getExecStack().push(this);
                }
            }
            else {
                throw new ExpressionException("Index not found in latch table!");
            }
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
            return typeEnv;
        }
        else {
            throw new ExpressionException("Var must be of type int!");
        }
    }

    @Override
    public String toString() {
        return "Await(" + var + ")";
    }

    @Override
    public IStatement deepCopy() {
        return new Await(var);
    }
}