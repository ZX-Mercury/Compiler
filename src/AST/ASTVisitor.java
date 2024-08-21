package AST;

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
    void visit(prefixExprNode prefixExprNode);
    void visit(ternaryExprNode ternaryExprNode);

    void visit(suiteNode suiteNode);
    void visit(forStmtNode forStmtNode);
    void visit(whileStmtNode whileStmtNode);
    void visit(breakStmt breakStmt);
    void visit(continueStmtNode continueStmtNode);
    void visit(pureExprStmtNode pureExprStmtNode);

    void visit(classConstructNode classConstructNode);
    void visit(varDeclareNode varDeclareNode);
    void visit(buildin_typenameNode buildinTypenameNode);
    void visit(varTypeNode varTypeNode);
    void visit(varDefNode varDefNode);
    void visit(functypenameNode functypenameNode);
    void visit(parameterNode parameterNode);
    void visit(funcDefParameterNode fucDefParemeterNode);
    void visit(funcDefNode funcDefNode);
    void visit(classDefNode classDefNode);

    void visit(parenExprNode parenExprNode);
    void visit(expressionListNode expressionListNode);
    void visit(newSizeNode newSizeNode);
    void visit(newVarExprNode newVarExprNode);

    void visit(forInitNode forInitNode);
}