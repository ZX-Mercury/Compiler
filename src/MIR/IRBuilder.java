package MIR;

import AST.ASTNode;
import AST.ASTVisitor;
import AST.Definition.*;
import AST.Expression.*;
import AST.RootNode;
import AST.Statement.*;
import MIR.IREntity.*;
import MIR.Instruction.*;
import Util.Scope.globalScope;

public class IRBuilder implements ASTVisitor {
    private block currentBlock;
    public globalScope gScope;
    public IRBuilder(globalScope gScope) {
        this.gScope = gScope;
        currentBlock = new block();
    }

    public void visit(RootNode it){
        for(ASTNode node : it.parts){
            node.accept(this);
        }
    }
    public void visit(funcDefNode funcDefNode){
        funcDefNode.suite.accept(this);

    }
    public void visit(suiteNode suiteNode){
        for(ASTNode node : suiteNode.statementNodes){
            node.accept(this);
        }
    }
    public void visit(returnStmtNode it){
        entity value;
        if (it.retExpr != null) {
            it.retExpr.accept(this);
            value = it.retExpr.val;
        } else value = null;
        currentBlock.push_back(new retInst(value));
    }
    public void visit(ifStmtNode it){}
    public void visit(binaryExprNode it){}
    public void visit(atomExprNode atomExprNode){}
    public void visit(callExprNode callExprNode){}
    public void visit(arrayExprNode arrayExprNode){}
    public void visit(preIncExprNode preIncExprNode){}
    public void visit(postIncExprNode postIncExprNode){}
    public void visit(unaryExprNode unaryExprNode){}
    public void visit(ternaryExprNode ternaryExprNode){}
    public void visit(whileStmtNode whileStmtNode){}
    public void visit(breakStmt breakStmt){}
    public void visit(continueStmtNode continueStmtNode){}
    public void visit(pureExprStmtNode pureExprStmtNode){}
    public void visit(classConstructNode classConstructNode){}
    public void visit(varDeclareNode varDeclareNode){}
    public void visit(varDefNode varDefNode){}
    public void visit(parameterNode parameterNode){}
    public void visit(funcDefParameterNode fucDefParemeterNode){}
    public void visit(classDefNode classDefNode){}
    public void visit(parenExprNode parenExprNode){}
    public void visit(newVarExprNode newVarExprNode){}
    public void visit(assignExprNode assignExprNode){}
    public void visit(memberExprNode memberExprNode){}
    public void visit(forDefStmtNode forDefStmtNode){}
    public void visit(forExpStmtNode forExpStmtNode){}
    public void visit(intLiteralNode intLiteralNode){}
    public void visit(boolLiteralNode boolLiteralNode){}
    public void visit(nullLiteralNode nullLiteralNode){}
    public void visit(stringLiteralNode stringLiteralNode){}
    public void visit(arrayLiteralNode arrayLiteralNode){}
    public void visit(FmtstringNode fmtstringNode){}
    public void visit(varDefStmtNode varDefStmtNode){}
    public void visit(newArrayExprNode newArrayExprNode){}
}
