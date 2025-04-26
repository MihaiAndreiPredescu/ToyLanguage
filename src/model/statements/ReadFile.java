/*
package model.statements;

import model.adt.MyIDictionary;
import exceptions.DictionaryException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.expressions.IExpression;
import model.state.PrgState;
import model.type.IntType;
import model.type.StringType;
import model.value.IValue;
import model.value.IntValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStatement {
    private IExpression exp;
    String varName;

    public ReadFile(IExpression exp, String varName) {
        this.exp = exp;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();

        if (!symTable.contains(this.varName)) {
            throw new StatementException("Variable doesn't exist");
        }

        try {
            if (!symTable.get(this.varName).getType().equals(new IntType())) {
                throw new StatementException("Variable isn't type int");
            }

            IValue eval = this.exp.evaluate(symTable);

            if (!eval.getType().equals(new StringType())) {
                throw new StatementException("Expression result isn't type string");
            }

            try {
                BufferedReader br = state.getFileTable().get((StringValue) eval);
                String readVal = br.readLine();
                IntValue newValue;
                if (readVal == null) {
                    newValue = new IntValue(0);
                } else {
                    newValue = new IntValue(Integer.parseInt(readVal));
                }
                symTable.insert(this.varName, newValue);
                return state;
            } catch (IOException e) {
                throw new StatementException(e.getMessage());
            }
        } catch (DictionaryException | ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFile(this.exp.deepCopy(), this.varName);
    }

    @Override
    public String toString() {
        return "Read from file: " + this.exp;
    }
}
*/
