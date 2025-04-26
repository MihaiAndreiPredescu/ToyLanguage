package model.statements;

import model.adt.MyIDictionary;
import model.adt.MyIStack;
import exceptions.*;
import model.state.PrgState;
import model.expressions.IExpression;
import model.expressions.VariableExpression;
import model.statements.AssignStatement;
import model.statements.IStatement;
import model.statements.IfStatement;
import model.type.BoolType;
import model.type.IType;

import java.beans.Expression;

public class ConditionalAssigment implements IStatement {
    private String v;

    private IExpression exp1;
    private IExpression exp2;
    private IExpression exp3;



    public ConditionalAssigment(String v, IExpression exp1, IExpression exp2, IExpression exp3) {
        this.v = v;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
    }



    @Override
    public PrgState execute(PrgState state) throws ExpressionException {
        MyIStack<IStatement> exeStack = state.getExecStack();
        // we create a new if statement which has as condition exp1 and assigns to v exp2 if exp1 is true, and exp3 otherwise
        IStatement converted = new IfStatement(exp1, new AssignStatement(v, exp2), new AssignStatement(v, exp3));
        // and we push the statement on the stack
        exeStack.push(converted);
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        // we evaluate the variable v and the expressions exp1, exp2, exp3
        IType typev = new VariableExpression(v).typecheck(typeEnv);
        IType type1 = exp1.typecheck(typeEnv);
        IType type2 = exp2.typecheck(typeEnv);
        IType type3 = exp3.typecheck(typeEnv);
        // exp1 must be of type bool and exp2 and exp3 must have the same type as v
        if (type1.equals(new BoolType()) && type2.equals(typev) && type3.equals(typev))
            return typeEnv;
        else
            throw new ExpressionException("The conditional assignment is invalid!");    }


    @Override
    public String toString() {
        return "ConditionalAssignment{" +
                "v='" + v + '\'' +
                ", exp1=" + exp1 +
                ", exp2=" + exp2 +
                ", exp3=" + exp3 +
                '}';
    }

    @Override
    public IStatement deepCopy() {
        return new ConditionalAssigment(v,exp1,exp2,exp3);
    }
}