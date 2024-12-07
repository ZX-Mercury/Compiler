package MIR;

import AST.ASTNode;
import AST.ASTVisitor;
import AST.Definition.*;
import AST.Expression.*;
import AST.RootNode;
import AST.Statement.*;
import MIR.IREntity.*;
import MIR.Instruction.*;
import MIR.Value.*;
import MIR.Type.*;

import Util.Scope.*;
import Util.*;

import java.util.Objects;

public class IRBuilder implements ASTVisitor {
    private block currentBlock;
    public globalScope gScope;
    public Scope scope;
    public boolean endBlock;

    public IRBuilder(globalScope gScope) {
        this.gScope = gScope;
        this.scope = new Scope(gScope);
    }

    public void visit(RootNode it){
        for(ASTNode node : it.parts){
            if(node instanceof varDefStmtNode) {
                for (varDeclareNode varDec : ((varDefStmtNode) node).varDef.varDeclarations) {
                    String name = varDec.name;

                    varGlobal gVar = new varGlobal(name, type.toIRType(varDec.type));
                    // int dim = varDec.type.dim; TODO: array

                    if (varDec.isInitialized) {
                        varDec.expression.accept(this);
                        gVar.init = varDec.expression.val;
                    }
                    else {
                        gVar.init = new constNull();
                    }
                    gScope.entities.put(varDec.name, gVar);
                }
            }
            else if (node instanceof funcDefNode) {
                node.accept(this);
            }
        }
    }

    public void visit(varDefStmtNode it){
        if (it.varDef!=null) {
            it.varDef.accept(this);
        }
    }

    public void visit(varDefNode it){
        for (varDeclareNode node : it.varDeclarations) {
            if(node.isInitialized){
                node.expression.accept(this);
            }
            scope.entities.put(node.name, new varGlobal(node.name, type.toIRType(it.type)));
        }
    }

    public void visit(intLiteralNode it){
        it.val = new constInt(it.value);
    }

    public void visit(funcDefNode it){
        //TODO: member function
        scope = new FuncScope(scope, it.retType);
        boolean isMemberFunction = (scope.parentScope != gScope);
        type retType = type.toIRType(it.retType);
        String funcName = (isMemberFunction ? String.format("struct.%s.%s", scope.className, it.name) : it.name);
        function funcDef = new function(funcName, retType);
        currentBlock = new block(funcName + "entry", funcDef);
        if (it.name.equals("main")) {
            currentBlock.push_back(new callInst(null, "builtin.init"));
        }
        endBlock = false;
        it.suite.accept(this);
        endBlock = false;
        //irProgram.funcDefMap.put(funcName, funcDef);
        scope = scope.parentScope;
    }

    public void visit(suiteNode it){
        for(ASTNode node : it.statementNodes){
            node.accept(this);
            if (endBlock) {
                break;
            }
        }
    }
    public void visit(returnStmtNode it){
        if (it.retExpr == null) {
            currentBlock.push_back(new retInst(null));
            submitBlock();
            return;
        }
        it.retExpr.accept(this);
        if (currentBlock.result == null) {
            currentBlock.push_back(new retInst(null));
            submitBlock();
            return;
        }
        value value=null;
        //value = cpt(node.expr_.isLeftValue_);???
        currentBlock.push_back(new retInst(value));
    }
    public void visit(preIncExprNode it){
        it.expression.accept(this);
        varLocal var1 =  varLocal.newvarlocal(new intType(32));
    }
    public void visit(postIncExprNode postIncExprNode){}
    public void visit(unaryExprNode unaryExprNode){}
    public void visit(binaryExprNode it){/*
        if (it.binaryOp == binaryExprNode.binaryOperator.AndAnd) {
            int id = andCnt++;
            block rhs = new block("andrhs" + id, currentBlock.belong);
            block end = new block("andend" + id, currentBlock.belong);
            it.lhs.accept(this);
            value lhsValue = cpt(it.lhs.isLeftValue);
            varLocal resultPtr = varLocal.newvarlocal(new ptrType(new intType(1)));
            currentBlock.push_back(new allocaInst(resultPtr));
            currentBlock.push_back(new storeInst(new constBool(false), resultPtr));
            currentBlock.push_back(new branchInst(lhsValue, rhs, end));
            submitBlock();

            currentBlock = rhs;
            it.rhs.accept(this);
            value rhsValue = cpt(it.rhs.isLeftValue);
            currentBlock.push_back(new storeInst(rhsValue, resultPtr));
            currentBlock.push_back(new jumpInst(end));
            submitBlock();

            currentBlock = end;
            varLocal newVar = varLocal.newvarlocal(new intType(1));
            currentBlock.push_back(new loadInst(newVar, resultPtr));
            currentBlock.result = newVar;
            return;
        }
        if (it.binaryOp == binaryExprNode.binaryOperator.OrOr) {
            int id = orCnt_++;
            IRBasicBlock rhs = new IRBasicBlock("or_rhs_" + id, currentBlock_.belong_);
            IRBasicBlock end = new IRBasicBlock("or_end_" + id, currentBlock_.belong_);
            node.lhs_.accept(this);
            IRValue lhsValue = cpt(node.lhs_.isLeftValue_);
            IRLocalVar resultPtr = IRLocalVar.newLocalVar(new IRPtrType(new IRIntType(1)));
            currentBlock_.instList_.add(new IRAllocaInst(resultPtr));
            currentBlock_.instList_.add(new IRStoreInst(new IRBoolConst(true), resultPtr));
            currentBlock_.instList_.add(new IRBrInst(lhsValue, end, rhs));
            submitBlock();
            currentBlock_ = rhs;
            node.rhs_.accept(this);
            IRValue rhsValue = cpt(node.rhs_.isLeftValue_);
            currentBlock_.instList_.add(new IRStoreInst(rhsValue, resultPtr));
            currentBlock_.instList_.add(new IRJumpInst(end));
            submitBlock();
            currentBlock_ = end;
            IRLocalVar newVar = IRLocalVar.newLocalVar(new IRIntType(1));
            currentBlock_.instList_.add(new IRLoadInst(newVar, resultPtr));
            currentBlock_.result_ = newVar;
            return;
        }
        varLocal newVar = null;
        it.lhs.accept(this);
        value lhsValue = cpt(it.lhs.isLeftValue);
        it.rhs.accept(this);
        value rhsValue = cpt(it.rhs.isLeftValue);
        switch (it.binaryOp) {
            case binaryExprNode.binaryOperator.Plus:
                if (lhsValue.valueType instanceof intType) {
                    newVar = varLocal.newvarlocal(new intType(32));
                    currentBlock.push_back(new binaryInst(newVar, lhsValue, rhsValue, binaryInst.binaryOp.add));
                }
                else {
                    //TODO: String
                }
                break;
            case binaryExprNode.binaryOperator.Minus:
                newVar = varLocal.newvarlocal(new intType(32));
                currentBlock.push_back(new binaryInst(newVar, lhsValue, rhsValue, binaryInst.binaryOp.sub));
                break;
            case binaryExprNode.binaryOperator.Mul:
                newVar = varLocal.newvarlocal(new intType(32));
                currentBlock.push_back(new binaryInst(newVar, lhsValue, rhsValue, binaryInst.binaryOp.mul));
                break;
            case binaryExprNode.binaryOperator.Div:
                newVar = varLocal.newvarlocal(new intType(32));
                currentBlock.push_back(new binaryInst(newVar, lhsValue, rhsValue, binaryInst.binaryOp.sdiv));
                break;
            case binaryExprNode.binaryOperator.Mod:
                newVar = varLocal.newvarlocal(new intType(32));
                currentBlock.push_back(new binaryInst(newVar, lhsValue, rhsValue, binaryInst.binaryOp.srem));
                break;
            case binaryExprNode.binaryOperator.And:
                newVar = varLocal.newvarlocal(new intType(32));
                currentBlock.push_back(new binaryInst(newVar, lhsValue, rhsValue, binaryInst.binaryOp.and));
                break;
            case binaryExprNode.binaryOperator.Or:
                newVar = varLocal.newvarlocal(new intType(32));
                currentBlock.push_back(new binaryInst(newVar, lhsValue, rhsValue, binaryInst.binaryOp.or));
                break;
            case binaryExprNode.binaryOperator.Caret:
                newVar = varLocal.newvarlocal(new intType(32));
                currentBlock.push_back(new binaryInst(newVar, lhsValue, rhsValue, binaryInst.binaryOp.xor));
                break;
            case binaryExprNode.binaryOperator.LeftShift:
                newVar = varLocal.newvarlocal(new intType(32));
                currentBlock.push_back(new binaryInst(newVar, lhsValue, rhsValue, binaryInst.binaryOp.shl));
                break;
            case binaryExprNode.binaryOperator.RightShift:
                newVar = varLocal.newvarlocal(new intType(32));
                currentBlock.push_back(new binaryInst(newVar, lhsValue, rhsValue, binaryInst.binaryOp.ashr));
                break;

            case binaryExprNode.binaryOperator.Equal:
                newVar = varLocal.newvarlocal(new intType(1));
                if (Objects.equals(lhsValue.valueType, new ptrType(new intType(8)))) {
                    //TODO: String
                }
                else {
                    currentBlock.push_back(new icmpInst(newVar, lhsValue, rhsValue, icmpInst.icmpOpType.eq));
                }
                break;
            case binaryExprNode.binaryOperator.NotEqual:
                newVar = varLocal.newvarlocal(new intType(1));
                if (Objects.equals(lhsValue.valueType, new ptrType(new intType(8)))) {
                    //TODO: String
                }
                else {
                    currentBlock.push_back(new icmpInst(newVar, lhsValue, rhsValue, icmpInst.icmpOpType.ne));
                }
                break;
            case binaryExprNode.binaryOperator.Less:
                newVar = varLocal.newvarlocal(new intType(1));
                if (lhsValue.valueType instanceof intType) {
                    currentBlock.push_back(new icmpInst(newVar, lhsValue, rhsValue, icmpInst.icmpOpType.slt));
                }
                else {
                }
                break;
            case binaryExprNode.binaryOperator.Greater:
                newVar = varLocal.newvarlocal(new intType(1));
                if (lhsValue.valueType instanceof intType) {
                    currentBlock.push_back(new icmpInst(newVar, lhsValue, rhsValue, icmpInst.icmpOpType.sgt));
                }
                else {
                }
                break;
            case binaryExprNode.binaryOperator.LessEqual:
                newVar = varLocal.newvarlocal(new intType(1));
                if (lhsValue.valueType instanceof intType) {
                    currentBlock.push_back(new icmpInst(newVar, lhsValue, rhsValue, icmpInst.icmpOpType.sle));
                }
                else {
                }
                break;
            case binaryExprNode.binaryOperator.GreaterEqual:
                newVar = varLocal.newvarlocal(new intType(1));
                if (lhsValue.valueType instanceof intType) {
                    currentBlock.push_back(new icmpInst(newVar, lhsValue, rhsValue, icmpInst.icmpOpType.sge));
                }
                else {
                }
        }
        currentBlock.result = newVar;*/
    }
    public void visit(ifStmtNode it){/*
        int id = ifCnt_++;
        block then = new block("if_then_" + id, currentBlock_.belong_);
        block els = new IRBasicBlock("if_else_" + id, currentBlock_.belong_);
        block end = new IRBasicBlock("if_end_" + id, currentBlock_.belong_);
        node.cond_.accept(this);
        IRValue value = getValueResult(node.cond_.isLeftValue_);
        currentBlock_.instList_.add(new IRBrInst(value, then, node.else_.isEmpty() ? end : els));
        submitBlock();
        currentBlock_ = then;
        scope_ = new Scope(scope_);
        endBlock_ = false;
        for (var stmt : node.then_) {
            stmt.accept(this);
            if (endBlock_) {
                break;
            }
        }
        if (!endBlock_) {
            currentBlock_.instList_.add(new IRJumpInst(end));
            submitBlock();
        }
        endBlock_ = false;
        scope_ = scope_.parent_;
        if (!node.else_.isEmpty()) {
            currentBlock_ = els;
            scope_ = new Scope(scope_);
            endBlock_ = false;
            for (var stmt : node.else_) {
                stmt.accept(this);
                if (endBlock_) {
                    break;
                }
            }
            if (!endBlock_) {
                currentBlock_.instList_.add(new IRJumpInst(end));
                submitBlock();
            }
            endBlock_ = false;
            scope_ = scope_.parent_;
        }
        currentBlock_ = end;*/
    }
    public void visit(whileStmtNode whileStmtNode){

    }
    public void visit(breakStmt breakStmt){

    }
    public void visit(atomExprNode atomExprNode){}
    public void visit(callExprNode callExprNode){}
    public void visit(arrayExprNode arrayExprNode){}
    public void visit(ternaryExprNode ternaryExprNode){}
    public void visit(continueStmtNode continueStmtNode){}
    public void visit(pureExprStmtNode pureExprStmtNode){}
    public void visit(classConstructNode classConstructNode){}
    public void visit(varDeclareNode varDeclareNode){}
    public void visit(parameterNode parameterNode){}
    public void visit(funcDefParameterNode fucDefParemeterNode){}
    public void visit(classDefNode classDefNode){}
    public void visit(parenExprNode parenExprNode){}
    public void visit(newVarExprNode newVarExprNode){}
    public void visit(assignExprNode assignExprNode){}
    public void visit(memberExprNode memberExprNode){}
    public void visit(forDefStmtNode forDefStmtNode){}
    public void visit(forExpStmtNode forExpStmtNode){}
    public void visit(boolLiteralNode boolLiteralNode){}
    public void visit(nullLiteralNode nullLiteralNode){}
    public void visit(stringLiteralNode stringLiteralNode){}
    public void visit(arrayLiteralNode arrayLiteralNode){}
    public void visit(FmtstringNode fmtstringNode){}
    public void visit(newArrayExprNode newArrayExprNode){}

    private value cpt(boolean isLeftValue) {
        if (!isLeftValue) {
            return currentBlock.result;
        }
        varLocal newVar = varLocal.newvarlocal(((ptrType) currentBlock.result.valueType).deref());
        currentBlock.push_back(new loadInst(newVar, currentBlock.result));
        return newVar;
    }

    private void submitBlock() {
        currentBlock.fuc.body.add(currentBlock);
        endBlock = true;
    }
}
