package FrontEnd;

import AST.*;
import Util.*;
import Util.error.semanticError;
import Util.Type;
import Util.typeCmp;
import jdk.jshell.execution.Util;

public class SemanticChecker implements ASTVisitor{
    globalScope gScope;
    Scope currentScope;

    public SemanticChecker(globalScope gScope){
        this.gScope = gScope;
        currentScope = gScope;
    }

    @Override
    public void visit(RootNode it) {

        for(ASTNode body : it.parts) {
            body.accept(this);
        }
    }

    @Override public void visit(returnStmtNode it){
        if(!currentScope.isFunction){
            throw new semanticError("Semantic Error: return statement not in function", it.pos);
        }
        if(it.retExpr!=null){
            it.retExpr.accept(this);
            if(!(it.retExpr.type.btype==currentScope.fucRetType.retType.buildin_typename.bType
                && it.retExpr.type.dim == currentScope.fucRetType.retType.dim)){//?
                throw new semanticError("Semantic Error: return type not match", it.pos);
            }
        }
        else{
            if(!currentScope.fucRetType.isVoid){
                throw new semanticError("Semantic Error: return type not match", it.pos);
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
    @Override public void visit(varDeclareNode it){}
    @Override public void visit(buildin_typenameNode it){}
    @Override public void visit(varTypeNode it){}
    @Override public void visit(functypenameNode it){}
    @Override public void visit(parameterNode it){}
    @Override public void visit(funcDefParameterNode it){}
    @Override public void visit(funcDefNode it){
        currentScope = new Scope(currentScope);
        currentScope.isFunction = true;
        currentScope.fucRetType = it.type;

        if(it.parameterList!=null)it.parameterList.accept(this);
        it.suite.accept(this);

        currentScope = currentScope.parentScope();
    }
    @Override public void visit(classDefNode it){
        currentScope = new Scope(currentScope);
        currentScope.isClass = true;

        it.constructor.accept(this);
        for(varDefStmtNode var : it.varList){
            var.accept(this);
        }
        for(funcDefNode func : it.funcList){
            func.accept(this);
        }

        currentScope = currentScope.parentScope();
    }
    @Override public void visit(expressionListNode it){}
    @Override public void visit(newSizeNode it){}//maybe not needed
    @Override public void visit(atomExprNode it){
        if (!currentScope.containsVariable(it.identifier, true))
            throw new semanticError("Semantic Error: variable not defined. ", it.pos);
        it.type = currentScope.getType(it.identifier, true);
    }
    @Override public void visit(parenExprNode it){
        it.expr.accept(this);
        it.checkType();
    }
    @Override public void visit(newVarExprNode it){
        it.builtinType.accept(this);
        for(ExpressionNode expr : it.newSize){
            expr.accept(this);
        }
        it.arrayLiteral.accept(this);
        it.checkType();
    }
    @Override public void visit(assignExprNode it){
        it.lhs.accept(this);
        it.rhs.accept(this);
        /*if(it.lhs.type!=it.rhs.type){
            throw new semanticError("Semantic Error: type not match", it.pos);
        }*/
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
        it.checkType();
        /*if (node.class_ instanceof ArrayLiteralNode) {
            if (!node.member_.equals("size")) {
                System.out.println("Undefined Identifier");
                throw new SemanticError("Undefined Symbol Error", node.pos_);
            }
            node.funcType_ = new FuncType(new Type("int"));
            node.isLeftValue_ = false;
            return;
        }
        if (node.class_.type_.isArray_) {
            if (!node.member_.equals("size")) {
                System.out.println("Undefined Identifier");
                throw new SemanticError("Undefined Symbol Error", node.pos_);
            }
            node.funcType_ = new FuncType(new Type("int"));
            node.isLeftValue_ = false;
        }
        else {
            ClassType classType = gScope_.getClassType(node.class_.type_.name_);
            FuncType funcType = classType.funcMap_.get(node.member_);
            Type varType = classType.varMap_.get(node.member_);
            if (funcType != null) {
                node.funcType_ = funcType;
                node.isLeftValue_ = false;
                return;
            }
            if (varType != null) {
                node.type_ = varType;
                return;
            }
            System.out.println("Undefined Identifier");
            throw new SemanticError("Undefined Symbol Error", node.pos_);
        }*/
    }
    @Override public void visit(callExprNode it){
        it.functionIdentifier.accept(this);
        it.expressionList.accept(this);
        it.checkType();
    }
    @Override public void visit(arrayExprNode it){}
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
        it.typeNode.accept(this);
        Type tmp;
        if(it.typeNode.classIdentifier!=null) tmp = new Type(it.typeNode.classIdentifier, it.typeNode.dim, true);
        else tmp = new Type(it.typeNode.buildin_typename.bType, it.typeNode.dim, true);
        for(varDeclareNode var : it.varDeclarations){
            var.accept(this);
            currentScope.defineVariable(var.name, tmp, var.pos);
        }
    }
}
