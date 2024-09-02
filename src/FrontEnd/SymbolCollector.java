package FrontEnd;

import AST.*;
import AST.Definition.*;
import AST.Statement.*;
import AST.Expression.*;
import Util.error.*;
import Util.Scope.*;

public class SymbolCollector implements ASTVisitor {
    private globalScope gScope;
    public SymbolCollector(globalScope gScope) {
        this.gScope = gScope;
    }
    @Override
    public void visit(RootNode it){
        for(var def : it.parts){
            def.accept(this);
        }
    }
    @Override
    public void visit(classDefNode it){
        if (gScope.funcMember.containsKey(it.name))
            throw new semanticError("Multiple Definitions", it.pos);
        gScope.addClass(it);
    }
    @Override
    public void visit(funcDefNode it){
        if (gScope.classMember.containsKey(it.name))
            throw new semanticError("Multiple Definitions", it.pos);
        gScope.addFunc(it);
    }

//-------------\\

    @Override
    public void visit(returnStmtNode it){}
    @Override
    public void visit(ifStmtNode it){}
    @Override
    public void visit(binaryExprNode it){}
    @Override
    public void visit(atomExprNode atomExprNode){}
    @Override
    public void visit(callExprNode callExprNode){}
    @Override
    public void visit(arrayExprNode arrayExprNode){}
    @Override
    public void visit(preIncExprNode preIncExprNode){}
    @Override
    public void visit(postIncExprNode postIncExprNode){}
    @Override
    public void visit(unaryExprNode unaryExprNode){}
    @Override
    public void visit(ternaryExprNode ternaryExprNode){}
    @Override
    public void visit(suiteNode suiteNode){}
    @Override
    public void visit(whileStmtNode whileStmtNode){}
    @Override
    public void visit(breakStmt breakStmt){}
    @Override
    public void visit(continueStmtNode continueStmtNode){}
    @Override
    public void visit(pureExprStmtNode pureExprStmtNode){}
    @Override
    public void visit(classConstructNode classConstructNode){}
    @Override
    public void visit(varDeclareNode varDeclareNode){}
    @Override
    public void visit(varDefNode varDefNode){}
    @Override
    public void visit(parameterNode parameterNode){}
    @Override
    public void visit(funcDefParameterNode fucDefParemeterNode){}
    @Override
    public void visit(parenExprNode parenExprNode){}
    @Override
    public void visit(newVarExprNode newVarExprNode){}
    @Override
    public void visit(assignExprNode assignExprNode){}
    @Override
    public void visit(memberExprNode memberExprNode){}
    @Override
    public void visit(forDefStmtNode forDefStmtNode){}
    @Override
    public void visit(forExpStmtNode forExpStmtNode){}
    @Override
    public void visit(intLiteralNode intLiteralNode){}
    @Override
    public void visit(boolLiteralNode boolLiteralNode){}
    @Override
    public void visit(nullLiteralNode nullLiteralNode){}
    @Override
    public void visit(stringLiteralNode stringLiteralNode){}
    @Override
    public void visit(arrayLiteralNode arrayLiteralNode){}
    @Override
    public void visit(FmtstringNode fmtstringNode){}
    @Override
    public void visit(varDefStmtNode varDefStmtNode){}
    @Override
    public void visit(newArrayExprNode newArrayExprNode){}
}
