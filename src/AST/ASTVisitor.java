package AST;

import AST.Definition.*;
import AST.Statement.*;
import AST.Expression.*;

public interface ASTVisitor {
    void visit(RootNode it);
    void visit(returnStmtNode it);
    void visit(ifStmtNode it);
    void visit(binaryExprNode it);
    void visit(atomExprNode it);
    void visit(callExprNode it);
    void visit(arrayExprNode it);
    void visit(preIncExprNode it);
    void visit(postIncExprNode it);
    void visit(unaryExprNode it);
    void visit(ternaryExprNode it);
    void visit(suiteNode it);
    void visit(whileStmtNode it);
    void visit(breakStmt it);
    void visit(continueStmtNode it);
    void visit(pureExprStmtNode it);
    void visit(classConstructNode it);
    void visit(varDeclareNode it);
    void visit(varDefNode it);
    void visit(parameterNode it);
    void visit(funcDefParameterNode it);
    void visit(funcDefNode it);
    void visit(classDefNode it);
    void visit(parenExprNode it);
    void visit(newVarExprNode it);
    void visit(assignExprNode it);
    void visit(memberExprNode it);
    void visit(forDefStmtNode it);
    void visit(forExpStmtNode it);
    void visit(intLiteralNode it);
    void visit(boolLiteralNode it);
    void visit(nullLiteralNode it);
    void visit(stringLiteralNode it);
    void visit(arrayLiteralNode it);
    void visit(FmtstringNode it);
    void visit(varDefStmtNode it);
    void visit(newArrayExprNode it);
}