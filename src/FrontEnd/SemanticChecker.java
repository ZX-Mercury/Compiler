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
                throw new semanticError("main function should return int", it.pos);
            if(gScope.funcMember.get("main").parameterList!=null)
                throw new semanticError("main function should not have parameters", it.pos);
        }
        for(ASTNode body : it.parts) {
            body.accept(this);
        }
    }

    @Override public void visit(returnStmtNode it){
        Scope tmp = currentScope;
        while(!(tmp instanceof FuncScope)&&tmp!=null) tmp = tmp.parentScope;
        if(tmp==null){
            throw new semanticError("return statement not in function", it.pos);
        }
        else {
            ((FuncScope) tmp).returned = true;
        }
        if(it.retExpr!=null){
            it.retExpr.accept(this);
            if(!(it.retExpr.type.btype==currentScope.fucRetType.btype)||it.retExpr.type.dim!=currentScope.fucRetType.dim){
                throw new semanticError("return type not match", it.pos);
            }
        }
        else{
            if(currentScope.fucRetType.btype!=Type.basicType.Void){
                throw new semanticError("return type not match", it.pos);
            }
        }
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
        currentScope.isLoop = true;
        it.statement.accept(this);
        currentScope = currentScope.parentScope();
    }
    @Override public void visit(forDefStmtNode it){
        currentScope = new Scope(currentScope);
        it.varDef.accept(this);
        if(it.condition!=null) {
            it.condition.accept(this);
            if (!it.condition.type.btype.equals(Type.basicType.Bool))
                throw new semanticError("Semantic Error: type not match. It should be bool",
                        it.condition.pos);
        }
        if(it.step!=null) it.step.accept(this);
        it.statement.accept(this);
        currentScope = currentScope.parentScope();
    }
    @Override public void visit(forExpStmtNode it){
        if(it.init!=null) it.init.accept(this);
        if(it.condition!=null) {
            it.condition.accept(this);
            if (!it.condition.type.btype.equals(Type.basicType.Bool))
                throw new semanticError("Semantic Error: type not match. It should be bool",
                        it.condition.pos);
            if (it.step != null) it.step.accept(this);
        }
        currentScope = new Scope(currentScope);
        it.statement.accept(this);
        currentScope = currentScope.parentScope();
    }
    @Override public void visit(suiteNode it){
        if (!it.statementNodes.isEmpty()) {
            currentScope = new Scope(currentScope);
            for (ASTNode stmt : it.statementNodes) stmt.accept(this);
            currentScope = currentScope.parentScope();
        }
    }
    @Override public void visit(breakStmt it){
        if (!currentScope.isLoop) {
            throw new semanticError("Semantic Error: break statement not in loop", it.pos);
        }
    }
    @Override public void visit(continueStmtNode it){
        if (!currentScope.isLoop) {
            throw new semanticError("Semantic Error: continue statement not in loop", it.pos);
        }
    }
    @Override public void visit(pureExprStmtNode it){
        it.expr.accept(this);
    }
    @Override public void visit(classConstructNode it){}
    @Override public void visit(varDeclareNode it){
        if(it.type.btype== Type.basicType.Class){
            if(!gScope.classMember.containsKey(it.type.Identifier)){
                throw new semanticError("Invalid Type", it.pos);
            }
        }
        if(gScope.funcMember.containsKey(it.name)){
            throw new semanticError("Re-definition", it.pos);
        }
        if(currentScope.containsVariable(it.name, false)){
            throw new semanticError("Re-definition", it.pos);
        }
        if(it.expression!=null){
            it.expression.accept(this);
            if (it.expression.type.btype == Type.basicType.Null) {
            }
            else if (it.expression.type.btype == Type.basicType.Function
                    &&it.type.btype==it.expression.type.functionReturnType.btype){}
            else if(it.expression.type.btype!=it.type.btype){
                throw new semanticError("type not match", it.pos);
            }
        }
        currentScope.defineVariable(it.name, it.type, it.pos);
    }
    @Override public void visit(parameterNode it){}
    @Override public void visit(funcDefParameterNode it){}
    @Override public void visit(funcDefNode it){
        if(it.retType.btype== Type.basicType.Class){
            if(!gScope.classMember.containsKey(it.retType.Identifier)){
                throw new semanticError("Invalid retType", it.pos);
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
                &&!((FuncScope)currentScope).returned
                &&!(Objects.equals(it.name, "main"))){
            throw new semanticError("function not return", it.pos);
        }
        currentScope = currentScope.parentScope();
    }
    @Override public void visit(classDefNode it){
        currentScope = new ClassScope(currentScope);
        currentScope.className = it.name;

        if(it.constructor!=null)it.constructor.accept(this);
        if(currentScope instanceof ClassScope)((ClassScope) currentScope).funcMember = it.funcList;
        for(varDeclareNode var : it.varList.values()){
            var.accept(this);
        }
        for(funcDefNode func : it.funcList.values()){
            func.accept(this);
        }

        currentScope = currentScope.parentScope();

        /*
         for (var varDef: it.varMap.values()) {
         varDef.accept(this);
         }
         if (currentScope instanceof ClassScope) ((ClassScope) currentScope).funcMember = it.funcMap;
         for (var funcDef: it.funcMap.values()) {
         if (funcDef.funcName.equals(it.className)) throw new Error("SemanticError", "Multiple Definitions", it.pos);
         funcDef.accept(this);
         }
         if (it.constructor != null) {
         if (!it.constructor.className.equals(it.className))
         throw new Error("SemanticError", "constructor has different function name with the class", it.pos);
         it.constructor.accept(this);
         }
         currentScope = currentScope.parentScope;*/
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
            if (className == null) throw new semanticError("Invalid Type", it.pos);
            it.type = new Type(className, 0, true);
            it.type.Identifier = className;
        } else if (it.identifier != null) {
            Type target1 = currentScope.getType(it.identifier, true);
            funcDefNode target2 = gScope.getFunc(it.identifier);
            if (target1 == null && target2 == null)  throw new semanticError("Undefined Identifier" , it.pos);
            if (target1 != null) it.type = target1;
            if (target2 != null) {
                ArrayList<Type> paras = new ArrayList<>();
                for (var para : target2.parameterList.parameters) {
                    paras.add(para.type);
                }
                it.type = new Type(target2.retType);
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
                throw new semanticError("invalid variable definition type", it.pos);
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
        if (it.expr.type.btype == Type.basicType.Class || it.expr.type.btype == Type.basicType.This) {
            if (!gScope.classMember.containsKey(it.expr.type.Identifier))
                throw new semanticError("Undefined member", it.pos);
            classDefNode classDef = gScope.getClass(it.expr.type.Identifier);
            if (classDef.varList.containsKey(it.member)) {
                it.type = classDef.varList.get(it.member).type;
                it.type.isLeftValue = true;
            } else if (classDef.funcList.containsKey(it.member)) {
                funcDefNode function = classDef.funcList.get(it.member);
                it.type = new Type(function.retType);
                it.type.isLeftValue = false;
            } else throw new semanticError("Undefined member" , it.pos);
        }
        else {
            it.checkType();
        }
    }
    @Override public void visit(callExprNode it){
        it.functionIdentifier.accept(this);
       //it.functionIdentifier.type=currentScope.getType(it.functionIdentifier., true);
        for(ExpressionNode expr : it.paraList){
            expr.accept(this);
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
        /*for (var index: it.indexList) {
            index.accept(this);
            if (!index.type.typeName.equals("int") || index.type.isArray)
                throw new Error("SemanticError", "invalid type for array initialization", it.pos);
        }
        if (it.iniList != null) {
            if (it.iniList.type == null) it.iniList.type = new DataType();
            it.iniList.accept(this);
            if (!it.iniList.type.equals(it.type)) throw new Error("SemanticError", "initArray has different dataType", it.pos);
        }
        it.isLeftValue = false;*/
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
                throw new semanticError("invalid variable definition type", it.pos);
            tmp = new Type(it.type.Identifier, it.type.dim, true);
        }
        else tmp = new Type(it.type.btype, it.type.dim, true);
        for(varDeclareNode var : it.varDeclarations){
            var.accept(this);
            //currentScope.defineVariable(var.name, tmp, var.pos);
        }
        /*if (it.type.isClass && !globalScope.classMember.containsKey(it.type.typeName))
            throw new Error("SemanticError", "invalid variable definition type", it.pos);
        if (globalScope.funcMember.containsKey(it.varName))
            throw new Error("SemanticError", "variable " + it.varName + " has the same name with function", it.pos);
        if (currentScope.members.containsKey(it.varName)) throw new Error("SemanticError", it.varName + " redefined", it.pos);
        if (it.assignNode != null) {
            if (it.assignNode.type == null)  it.assignNode.type = new DataType();
            it.assignNode.accept(this);
            if (!it.assignNode.type.equals(it.type) && !it.assignNode.type.isNull) throw new Error("SemanticError", "initialized variable's type is mismatched", it.pos);
            if (it.assignNode.type.isNull && it.type.checkBaseType()) throw new Error("SemanticError", "null cannot be assigned to primitive type", it.pos);
        }
        currentScope.addVar(it.varName, it.pos, it.type);*/
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
