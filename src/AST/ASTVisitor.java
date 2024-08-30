package AST;

import AST.Definition.*;
import AST.Statement.*;
import AST.Expression.*;

public interface ASTVisitor {
    void visit(RootNode it);
    void visit(returnStmtNode it);
    void visit(ifStmtNode it);
    void visit(binaryExprNode it);
    void visit(atomExprNode atomExprNode);
    void visit(callExprNode callExprNode);
    void visit(arrayExprNode arrayExprNode);
    void visit(preIncExprNode preIncExprNode);
    void visit(postIncExprNode postIncExprNode);
    void visit(unaryExprNode unaryExprNode);
    void visit(ternaryExprNode ternaryExprNode);
    void visit(suiteNode suiteNode);
    //void visit(forStmtNode forStmtNode);
    void visit(whileStmtNode whileStmtNode);
    void visit(breakStmt breakStmt);
    void visit(continueStmtNode continueStmtNode);
    void visit(pureExprStmtNode pureExprStmtNode);
    void visit(classConstructNode classConstructNode);
    void visit(varDeclareNode varDeclareNode);
    //void visit(buildin_typenameNode buildinTypenameNode);
    //void visit(varTypeNode varTypeNode);
    void visit(varDefNode varDefNode);
    //void visit(functypenameNode functypenameNode);
    void visit(parameterNode parameterNode);
    void visit(funcDefParameterNode fucDefParemeterNode);
    void visit(funcDefNode funcDefNode);
    void visit(classDefNode classDefNode);
    void visit(parenExprNode parenExprNode);
    //void visit(expressionListNode expressionListNode);
    //void visit(newSizeNode newSizeNode);
    void visit(newVarExprNode newVarExprNode);
    //void visit(forInitNode forInitNode);
    void visit(assignExprNode assignExprNode);
    void visit(memberExprNode memberExprNode);
    void visit(forDefStmtNode forDefStmtNode);
    void visit(forExpStmtNode forExpStmtNode);
    void visit(intLiteralNode intLiteralNode);
    void visit(boolLiteralNode boolLiteralNode);
    void visit(nullLiteralNode nullLiteralNode);
    void visit(stringLiteralNode stringLiteralNode);
    void visit(arrayLiteralNode arrayLiteralNode);
    void visit(FmtstringNode fmtstringNode);
    void visit(varDefStmtNode varDefStmtNode);

    void visit(newArrayExprNode newArrayExprNode);
}