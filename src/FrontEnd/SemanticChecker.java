
package FrontEnd;

import AST.*;
import Util.Scope;
import Util.error.semanticError;
import Util.Type;
import jdk.jshell.execution.Util;

public class SemanticChecker implements ASTVisitor{
    //Scope globalScope;
    Scope currentScope;

    @Override
    public void visit(RootNode it) {
        currentScope = new Scope(null);
        for(ASTNode body : it.parts) {
            body.accept(this);
        }

    }

    @Override public void visit(returnStmtNode it){/*
        if(it.retExpr!=null){
            it.retExpr.accept(this);
            if(!it.retExpr.type.equals(currentScope.returnType)){//?
                throw new semanticError("Semantic Error: return type not match", it.retExpr.pos);
            }
        }*/
    }
    @Override public void visit(ifStmtNode it){

        it.expression.accept(this);
        if (!it.expression.type.btype.equals(Type.basicType.Bool))
            throw new semanticError("Semantic Error: type not match. It should be bool",
                    it.expression.pos);

        currentScope = new Scope(currentScope);
        it.trueStatement.accept(this);
        currentScope = currentScope.parentScope();

        currentScope = new Scope(currentScope);
        if (it.falseStatement != null) it.falseStatement.accept(this);
        currentScope = currentScope.parentScope();
    }
    @Override public void visit(whileStmtNode it){
        it.condition.accept(this);
        if (!it.condition.type.btype.equals(Type.basicType.Bool))
            throw new semanticError("Semantic Error: type not match. It should be bool",
                    it.condition.pos);
        currentScope = new Scope(currentScope);
        it.statement.accept(this);
        currentScope = currentScope.parentScope();
    }
    @Override public void visit(forDefStmtNode it){
        currentScope = new Scope(currentScope);
        it.varDef.accept(this);
        it.condition.accept(this);
        if (!it.condition.type.btype.equals(Type.basicType.Bool))
            throw new semanticError("Semantic Error: type not match. It should be bool",
                    it.condition.pos);
        it.step.accept(this);
        it.statement.accept(this);
        currentScope = currentScope.parentScope();
    }
    @Override public void visit(forExpStmtNode it){
        it.init.accept(this);
        it.condition.accept(this);
        if (!it.condition.type.btype.equals(Type.basicType.Bool))
            throw new semanticError("Semantic Error: type not match. It should be bool",
                    it.condition.pos);
        it.step.accept(this);

        currentScope = new Scope(currentScope);
        it.statement.accept(this);
        currentScope = currentScope.parentScope();
    }
    @Override public void visit(binaryExprNode it){
        /*it.lhs.accept(this);
        it.rhs.accept(this);
        if(it.lhs.type!=it.rhs.type){
            throw new semanticError("Semantic Error: type not match", it.pos);
        }
        if(lhs不可被赋值){
            throw new semanticError("Semantic Error: lhs is not assignable", it.pos);
        }*/
    }
    @Override public void visit(suiteNode it){
        if (!it.statementNodes.isEmpty()) {
            currentScope = new Scope(currentScope);
            /*for (ASTNode stmt : it.statementNodes) stmt.accept(this);*/
            currentScope = currentScope.parentScope();
        }
    }
    @Override public void visit(breakStmt it){}
    @Override public void visit(continueStmtNode it){}
    @Override public void visit(pureExprStmtNode it){}
    @Override public void visit(classConstructNode it){}
    @Override public void visit(varDeclareNode it){}
    @Override public void visit(buildin_typenameNode it){}
    @Override public void visit(varTypeNode it){}
    @Override public void visit(varDefNode it){}
    @Override public void visit(functypenameNode it){}
    @Override public void visit(parameterNode it){}
    @Override public void visit(funcDefParameterNode it){}
    @Override public void visit(funcDefNode it){
        currentScope = new Scope(currentScope);
        /*...*/
        currentScope = currentScope.parentScope();
    }
    @Override public void visit(classDefNode it){
        currentScope = new Scope(currentScope);
        /*...*/
        currentScope = currentScope.parentScope();
    }
    @Override public void visit(expressionListNode it){}
    @Override public void visit(newSizeNode it){}
    @Override public void visit(atomExprNode it){}
    @Override public void visit(parenExprNode it){}
    @Override public void visit(newVarExprNode it){}
    @Override public void visit(assignExprNode it){
        it.lhs.accept(this);
        it.rhs.accept(this);
        if(it.lhs.type!=it.rhs.type){
            throw new semanticError("Semantic Error: type not match", it.pos);
        }
        /*if(lhs不可被赋值){
            throw new semanticError("Semantic Error: lhs is not assignable", it.pos);
        }*/
    }
    @Override public void visit(memberExprNode it){}
    @Override public void visit(callExprNode it){}
    @Override public void visit(arrayExprNode it){}
    @Override public void visit(preIncExprNode it){}
    @Override public void visit(postIncExprNode it){}
    @Override public void visit(unaryExprNode it){}
    @Override public void visit(ternaryExprNode it){}
    @Override public void visit(intLiteralNode it){}
    @Override public void visit(boolLiteralNode it){}
    @Override public void visit(nullLiteralNode it){}
    @Override public void visit(stringLiteralNode it){}
    @Override public void visit(arrayLiteralNode it){}
    @Override public void visit(FmtstringNode it){}
    @Override public void visit(varDefStmtNode it){}
}
