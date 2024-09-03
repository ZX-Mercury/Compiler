package FrontEnd;

import AST.*;
import AST.Expression.*;
import AST.Statement.*;
import AST.Definition.*;
import Util.Scope.*;
import Util.error.semanticError;
import Util.Type;

import java.util.ArrayList;
import java.util.Objects;

public class SemanticChecker implements ASTVisitor{
    globalScope gScope;
    Scope currentScope;

    public SemanticChecker(globalScope gScope){
        this.gScope = gScope;
        currentScope = gScope;
    }

    @Override
    public void visit(RootNode it) {
        if(!gScope.funcMember.containsKey("main"))
            throw new semanticError("No main function", it.pos);
        else {
            if(gScope.funcMember.get("main").retType.btype!=Type.basicType.Int)
                throw new semanticError("Type Mismatch", it.pos);
            if(gScope.funcMember.get("main").parameterList!=null)
                throw new semanticError("main function should not have parameters", it.pos);
        }
        for(ASTNode body : it.parts) {
            body.accept(this);
        }
    }

    @Override public void visit(returnStmtNode it){
        Scope tmp = currentScope;
        while(!(tmp instanceof FuncScope)&&tmp!=null)
            tmp = tmp.parentScope;
        if(tmp==null){
            throw new semanticError("return statement not in function", it.pos);
        }
        else {
            ((FuncScope) tmp).returned = true;
        }
        if(it.retExpr!=null){
            it.retExpr.accept(this);
            if(it.retExpr.type.btype==Type.basicType.Null){
                if(currentScope.fucRetType.btype!=Type.basicType.Class)
                    throw new semanticError("Type Mismatch", it.pos);
            }
            else if(!(it.retExpr.type.btype==currentScope.fucRetType.btype)||it.retExpr.type.dim!=currentScope.fucRetType.dim){
                throw new semanticError("Type Mismatch", it.pos);
            }
        }
        else{
            if(currentScope.fucRetType.btype!=Type.basicType.Void){
                throw new semanticError("Type Mismatch", it.pos);
            }
        }
    }

    @Override public void visit(ifStmtNode it){
        it.expression.accept(this);
        if (!it.expression.type.btype.equals(Type.basicType.Bool))
            throw new semanticError("Invalid Type",
                    it.expression.pos);

        currentScope = new Scope(currentScope);
        if (it.trueStatement != null) it.trueStatement.accept(this);
        currentScope = currentScope.parentScope();

        currentScope = new Scope(currentScope);
        if (it.falseStatement != null) it.falseStatement.accept(this);
        currentScope = currentScope.parentScope();
    }
    @Override public void visit(whileStmtNode it){
        it.condition.accept(this);
        if (!it.condition.type.btype.equals(Type.basicType.Bool))
            throw new semanticError("Invalid Type",
                    it.condition.pos);
        currentScope = new Scope(currentScope);
        if(it.statement!=null) {//while(true); is legal
            currentScope.isLoop = true;
            it.statement.accept(this);
            currentScope = currentScope.parentScope();
        }
    }
    @Override public void visit(forDefStmtNode it){
        currentScope = new Scope(currentScope);
        it.varDef.accept(this);
        if(it.condition!=null) {
            it.condition.accept(this);
            if (!it.condition.type.btype.equals(Type.basicType.Bool))
                throw new semanticError("Invalid Type",
                        it.condition.pos);
        }
        if(it.step!=null) it.step.accept(this);
        currentScope.isLoop = true;
        if(it.statement!=null)it.statement.accept(this);
        currentScope = currentScope.parentScope();
    }
    @Override public void visit(forExpStmtNode it){
        if(it.init!=null) it.init.accept(this);
        if(it.condition!=null) {
            it.condition.accept(this);
            if (!it.condition.type.btype.equals(Type.basicType.Bool))
                throw new semanticError("Invalid Type",
                        it.condition.pos);
            if (it.step != null) it.step.accept(this);
        }
        if(it.statement!=null){
        currentScope = new Scope(currentScope);
            currentScope.isLoop = true;
            it.statement.accept(this);
            currentScope = currentScope.parentScope();
        }
    }
    @Override public void visit(suiteNode it){
        if (!it.statementNodes.isEmpty()) {
            currentScope = new Scope(currentScope);
            for (ASTNode stmt : it.statementNodes)
                if(stmt!=null) stmt.accept(this);
            currentScope = currentScope.parentScope();
        }
    }
    @Override public void visit(breakStmt it){
        var tmp = currentScope;
        while (tmp != null && !tmp.isLoop) tmp = tmp.parentScope;
        if (tmp==null) {
            throw new semanticError("Invalid Control Flow", it.pos);
        }
    }
    @Override public void visit(continueStmtNode it){
        var tmp = currentScope;
        while (tmp != null && !tmp.isLoop) tmp = tmp.parentScope;
        if (tmp==null) {
            throw new semanticError("Invalid Control Flow", it.pos);
        }
    }
    @Override public void visit(pureExprStmtNode it){
        it.expr.accept(this);
    }
    @Override public void visit(classConstructNode it){
        Type tmp = new Type(Type.basicType.Void, 0, false);
        currentScope = new FuncScope(currentScope, tmp);
        currentScope.isFunction = true;
        currentScope.fucRetType = tmp;
        it.suite.accept(this);
        currentScope = currentScope.parentScope;
        /*
        if(it.parameterList!=null) {
            it.parameterList.accept(this);
            for(var paras : it.parameterList.parameters){
                currentScope.defineVariable(paras.name, paras.type, it.pos);
            }
        }
        it.suite.accept(this);*/
    }
    @Override public void visit(varDeclareNode it){
        if(it.type.btype== Type.basicType.Class){
            if(!gScope.classMember.containsKey(it.type.Identifier)){
                throw new semanticError("Undefined Identifier", it.pos);
            }
        }
        if(gScope.funcMember.containsKey(it.name)){
            throw new semanticError("Multiple Definitions", it.pos);
        }
        if(currentScope.containsVariable(it.name, false)){
            throw new semanticError("Multiple Definitions", it.pos);
        }
        if(it.expression!=null){
            it.expression.accept(this);
            if (it.expression.type.btype == Type.basicType.Null) {
                //it.type.btype = Type.basicType.Null;
            }
            else {
                if (it.expression.type.btype == Type.basicType.Function
                        && it.type.btype == it.expression.type.functionReturnType.btype) {
                } else if (it.expression.type.btype != it.type.btype) {
                    throw new semanticError("Type mismatch", it.pos);
                }
                if (it.expression.type.dim != it.type.dim) {
                    throw new semanticError("dimension not match", it.pos);
                }
            }
        }
        currentScope.defineVariable(it.name, it.type, it.pos);
    }
    @Override public void visit(parameterNode it){}
    @Override public void visit(funcDefParameterNode it){}
    @Override public void visit(funcDefNode it){
        if(it.retType.btype== Type.basicType.Class){
            if(!gScope.classMember.containsKey(it.retType.Identifier)){
                throw new semanticError("Undefined Identifier", it.pos);
            }
        }
        currentScope = new FuncScope(currentScope, it.retType);
        currentScope.isFunction = true;
        currentScope.fucRetType = it.retType;
        if(it.parameterList!=null) {
            it.parameterList.accept(this);
            for(var paras : it.parameterList.parameters){
                currentScope.defineVariable(paras.name, paras.type, it.pos);
            }
        }
        it.suite.accept(this);
        if(!(currentScope.fucRetType.btype==Type.basicType.Void)
                &&!(Objects.equals(it.name, "main"))
                &&!((FuncScope)currentScope).returned){
            throw new semanticError("Missing Return Statement", it.pos);
        }
        currentScope = currentScope.parentScope();
    }
    @Override public void visit(classDefNode it){
        currentScope = new ClassScope(currentScope);
        currentScope.className = it.name;

        for(varDeclareNode var : it.varList.values()){
            var.accept(this);
        }
        if(currentScope instanceof ClassScope)((ClassScope) currentScope).funcMember = it.funcList;
        for(funcDefNode func : it.funcList.values()){
            func.accept(this);
        }
        if(it.constructor!=null) {
            it.constructor.accept(this);
            if(!Objects.equals(it.constructor.className, it.name)){
                throw new semanticError("constructor name not match", it.constructor.pos);//--
            }
        }

        currentScope = currentScope.parentScope();
    }
    @Override public void visit(atomExprNode it){
        if (it.pritype == atomExprNode.primaryType.Int) {
            it.type = new Type(Type.basicType.Int, 0, false);
        } else if (it.pritype == atomExprNode.primaryType.Bool) {
            it.type = new Type(Type.basicType.Bool, 0, false);
            //it.isLeftValue = false;
        } else if (it.pritype == atomExprNode.primaryType.String) {
            it.type = new Type(Type.basicType.String, 0, false);
        } else if (it.pritype == atomExprNode.primaryType.Null) {
            it.type = new Type(Type.basicType.Null, 0, false);
        } else if (it.pritype == atomExprNode.primaryType.Fmtstring) {
            it.type = new Type(Type.basicType.String, 0, false);
        } else if (it.pritype == atomExprNode.primaryType.This) {
            String className = currentScope.isInClass();
            if (className == null) throw new semanticError("Invalid Type", it.pos);//--
            it.type = new Type(className, 0, true);
            it.type.Identifier = className;
        } else if (it.identifier != null) {
            Type target1 = currentScope.getType(it.identifier, true);

            var tmp=currentScope;
            while(tmp!=null && !(tmp instanceof ClassScope)) tmp=tmp.parentScope;
            funcDefNode target2 = null;
            if(tmp!=null) target2= ((ClassScope)tmp).getFuncType(it.identifier,true);

            funcDefNode target3 = gScope.getFunc(it.identifier);
            if (target1 == null && target2 == null && target3 == null)
                throw new semanticError("Undefined Identifier" , it.pos);
            if (target1 != null) it.type = target1;
            if (target2 != null) {
                ArrayList<Type> paras = new ArrayList<>();
                if(target2.parameterList!=null) {
                    for (var para : target2.parameterList.parameters) {
                        paras.add(para.type);
                    }
                }
                it.type = new Type(target2.retType);
                //it.type.isLeftValue = false;
            }
            if (target3 != null) {
                ArrayList<Type> paras = new ArrayList<>();
                if(target3.parameterList!=null) {
                    for (var para : target3.parameterList.parameters) {
                        paras.add(para.type);
                    }
                }
                it.type = new Type(target3.retType);
                //it.type.isLeftValue = false;
            }

            if (it.type.btype== Type.basicType.Function) it.type.isLeftValue = false;
            else it.type.isLeftValue = true;
        }
    }
    @Override public void visit(parenExprNode it){
        it.expr.accept(this);
        it.checkType();
    }
    @Override public void visit(newVarExprNode it){
        if(it.type.Identifier!=null) {
            if (!gScope.classMember.containsKey(it.type.Identifier))
                throw new semanticError("Undefined Identifier", it.pos);
            it.type = new Type(it.type.Identifier, it.type.dim, false);
        }
        else it.type = new Type(it.type.btype, it.type.dim, false);
    }
    @Override public void visit(assignExprNode it){
        it.lhs.accept(this);
        it.rhs.accept(this);
        //TODO: new 数组的语法糖
        it.checkType();
    }
    @Override public void visit(binaryExprNode it){
        it.lhs.accept(this);
        it.rhs.accept(this);
        it.checkType();
    }
    @Override public void visit(memberExprNode it){
        it.expr.accept(this);
        if(it.expr.type.dim!= 0){
           if(!Objects.equals(it.member, "size"))
               throw new semanticError("Invalid member", it.pos);//--
           it.type = new Type(Type.basicType.Int, 0, false);
        }
        else {
            if (it.expr.type.btype == Type.basicType.Class || it.expr.type.btype == Type.basicType.This) {
                if (!gScope.classMember.containsKey(it.expr.type.Identifier))
                    throw new semanticError("Undefined member", it.pos);//--
                var a = gScope.getClass(it.expr.type.Identifier);
                var b = a.funcList.get(it.member);
                var c = a.varList.get(it.member);
                if(b!=null) {
                    it.type = new Type(b.retType);
                    it.type.isLeftValue = false;
                }
                else if(c!=null) {
                    it.type = c.type;
                    it.type.isLeftValue = true;
                }
                else throw new semanticError("fnmdp", it.pos);
            } else {
                it.checkType();
            }
        }
    }
    @Override public void visit(callExprNode it){
        it.functionIdentifier.accept(this);
       //it.functionIdentifier.type=currentScope.getType(it.functionIdentifier., true);
        for(ExpressionNode expr : it.paraList){
            expr.accept(this);
        }
    if(it.functionIdentifier instanceof atomExprNode){
            var tmp=currentScope;
            while(tmp!=null && !(tmp instanceof ClassScope)) tmp=tmp.parentScope;
            funcDefNode target2 = null;
            if(tmp!=null) target2= ((ClassScope)tmp).getFuncType(((atomExprNode)it.functionIdentifier).identifier,true);

            funcDefNode target3 = gScope.getFunc(((atomExprNode)it.functionIdentifier).identifier);
            var target = target2!=null ? target2 : target3;
            int rightsize = it.paraList==null?0:it.paraList.size();
            int currentsize = target.parameterList==null?0:target.parameterList.parameters.size();
            if(rightsize!=currentsize){
                throw new semanticError("Wrong Number of Parameters", it.pos);//--
            }
            /*for(int i=0;i<it.paraList.size();i++){
                if(!target.parameterList.parameters.get(i).type.equals(it.paraList.get(i).type)){
                    throw new semanticError("Not a parameter type", it.pos);
                }
            }
            At present, there is no need for it.*/
    }
        it.checkType();
    }
    @Override public void visit(arrayExprNode it){
        it.arrayIdentifier.accept(this);
        for(ExpressionNode expr : it.arrayIndex){
            expr.accept(this);
        }
        it.checkType();
        if (it.type.btype == Type.basicType.Class) {
            if (!currentScope.members.containsKey(it.arrayName)){}
                //throw new semanticError("invalid type for array", it.pos);
        }
    }
    @Override public void visit(preIncExprNode it){
        it.expression.accept(this);
        it.checkType();
    }
    @Override public void visit(postIncExprNode it){
        it.expression.accept(this);
        it.checkType();
    }
    @Override public void visit(unaryExprNode it){
        it.expression.accept(this);
        it.checkType();
    }
    @Override public void visit(ternaryExprNode it){
        it.condition.accept(this);
        it.trueExpr.accept(this);
        it.falseExpr.accept(this);
        it.checkType();
    }
    @Override public void visit(intLiteralNode it){
        it.checkType();}
    @Override public void visit(boolLiteralNode it){
        it.checkType();}
    @Override public void visit(nullLiteralNode it){
        it.checkType();}
    @Override public void visit(stringLiteralNode it){
        it.checkType();}
    @Override public void visit(arrayLiteralNode it){
        for(ExpressionNode expr : it.elements){
            expr.accept(this);
        }
        it.checkType();
    }
    @Override public void visit(FmtstringNode it){
        for(ExpressionNode expr : it.expr){
            expr.accept(this);
        }
        it.checkType();
    }
    @Override public void visit(varDefStmtNode it){
        it.varDef.accept(this);
    }

    @Override public void visit(varDefNode it){
        //it.accept(this);
        Type tmp;
        if(it.type.Identifier!=null) {
            if (!gScope.classMember.containsKey(it.type.Identifier))
                throw new semanticError("Undefined Identifier", it.pos);
            tmp = new Type(it.type.Identifier, it.type.dim, true);
        }
        else tmp = new Type(it.type.btype, it.type.dim, true);
        for(varDeclareNode var : it.varDeclarations){
            var.accept(this);
            //currentScope.defineVariable(var.name, tmp, var.pos);
        }
    }

    @Override
    public void visit(newArrayExprNode it){
        if(it.arrayLiteral!=null)it.arrayLiteral.accept(this);
        for(ExpressionNode expr : it.exprList){
            expr.accept(this);
        }
        it.checkType();
    }
}
