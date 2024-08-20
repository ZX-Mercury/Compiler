package FrontEnd;

import AST.*;
import AST.postIncExprNode.postIncOperator;
import AST.preIncExprNode.preIncOperator;
//import AST.unaryExprNode.unaryOperator;
import Parser.MxBaseVisitor;
import Parser.MxParser;
import Util.Type;
import Util.position;

public class ASTBuilder extends MxBaseVisitor<ASTNode> {
    @Override
    public ASTNode visitProgram (MxParser.ProgramContext ctx) {
        RootNode root = new RootNode(new position(ctx)) ;
        ctx.body().forEach(v -> root.statements.add((bodyNode) visit(v)));
        return root ;
    }

    @Override
    public ASTNode visitVarDefStmt (MxParser.VarDefStmtContext ctx) {
        return new varDefNode(new position(ctx), (varTypeNode) visit(ctx.varDef())) ;
    }

    @Override
    public ASTNode visitFuncDef (MxParser.FuncDefContext ctx) {
        return new funcDefNode(new position(ctx), (functypenameNode) visit (ctx.functypename()),
                ctx.Identifier().toString(), (funcDefParameterNode) visit (ctx.parameterList()), (suiteNode) visit (ctx.suite())) ;
    }

    @Override
    public ASTNode visitArrayExpr (MxParser.ArrayExprContext ctx) {
        return new arrayExprNode(new position(ctx), (ExpressionNode) visit (ctx.expression(0)), (ExpressionNode) visit (ctx.expression(1))) ;
    }

    @Override
    public ASTNode visitAtomExpr (MxParser.AtomExprContext ctx) {
        return visit (ctx.primary()) ;
    }

    @Override
    public ASTNode visitBinaryExpr (MxParser.BinaryExprContext ctx) {
        binaryExprNode binaryExpr = new binaryExprNode(new position(ctx), null, (ExpressionNode) visit (ctx.expression(0)), (ExpressionNode) visit (ctx.expression(1))) ;
        if (ctx.Plus() != null) binaryExpr.binaryOp = binaryExprNode.binaryOperator.Plus ;
        else if (ctx.Minus() != null) binaryExpr.binaryOp = binaryExprNode.binaryOperator.Minus ;
        else if (ctx.Mul() != null) binaryExpr.binaryOp = binaryExprNode.binaryOperator.Mul ;
        else if (ctx.Div() != null) binaryExpr.binaryOp = binaryExprNode.binaryOperator.Div ;
        else if (ctx.Mod() != null) binaryExpr.binaryOp = binaryExprNode.binaryOperator.Mod ;
        else if (ctx.Less() != null) binaryExpr.binaryOp = binaryExprNode.binaryOperator.Less ;
        else if (ctx.LessEqual() != null) binaryExpr.binaryOp = binaryExprNode.binaryOperator.LessEqual ;
        else if (ctx.Greater() != null) binaryExpr.binaryOp = binaryExprNode.binaryOperator.Greater ;
        else if (ctx.GreaterEqual() != null) binaryExpr.binaryOp = binaryExprNode.binaryOperator.GreaterEqual ;
        else if (ctx.Equal() != null) binaryExpr.binaryOp = binaryExprNode.binaryOperator.Equal ;
        else if (ctx.NotEqual() != null) binaryExpr.binaryOp = binaryExprNode.binaryOperator.NotEqual ;
        else if (ctx.LeftShift() != null) binaryExpr.binaryOp = binaryExprNode.binaryOperator.LeftShift ;
        else if (ctx.RightShift() != null) binaryExpr.binaryOp = binaryExprNode.binaryOperator.RightShift ;
        else if (ctx.And() != null) binaryExpr.binaryOp = binaryExprNode.binaryOperator.And ;
        else if (ctx.Caret() != null) binaryExpr.binaryOp = binaryExprNode.binaryOperator.Caret ;
        else if (ctx.Or() != null) binaryExpr.binaryOp = binaryExprNode.binaryOperator.Or ;
        else if (ctx.AndAnd() != null) binaryExpr.binaryOp = binaryExprNode.binaryOperator.AndAnd ;
        else if (ctx.OrOr() != null) binaryExpr.binaryOp = binaryExprNode.binaryOperator.OrOr ;
        else if (ctx.Assign() != null) binaryExpr.binaryOp = binaryExprNode.binaryOperator.Assign ;
        else if (ctx.Dot() != null) binaryExpr.binaryOp = binaryExprNode.binaryOperator.Dot ;
        return binaryExpr ;
    }

    @Override
    public ASTNode visitParenExpr (MxParser.ParenExprContext ctx) {
        return new parenExprNode(new position(ctx), (ExpressionNode) visit (ctx.expression())) ;
    }

    @Override
    public ASTNode visitBreakStmt (MxParser.BreakStmtContext ctx) {
        return new breakStmt(new position(ctx)) ;
    }

    @Override
    public ASTNode visitBuildin_typename (MxParser.Buildin_typenameContext ctx) {
        buildin_typenameNode builtinType = new buildin_typenameNode(new position(ctx), null) ;
        if (ctx.Int() != null) builtinType.bType = Type.basicType.Int ;
        else if (ctx.Bool() != null) builtinType.bType = Type.basicType.Bool ;
        else if (ctx.String() != null) builtinType.bType = Type.basicType.String ;//?
        return builtinType ;
    }

    @Override
    public ASTNode visitClassConstruct (MxParser.ClassConstructContext ctx) {
        return new classConstructNode(new position(ctx), ctx.Identifier().toString(), (suiteNode) visit(ctx.suite())) ;
    }

    @Override
    public ASTNode visitClassDef (MxParser.ClassDefContext ctx) {
        classDefNode classDef = new classDefNode(new position(ctx), ctx.Identifier().toString()) ;
        if (ctx.classConstruct() != null) classDef.constructor = (classConstructNode) visit (ctx.classConstruct()) ;
        ctx.varDefStmt().forEach(v -> classDef.varList.add ((varDefNode) visit (v)));
        ctx.funcDef().forEach(v -> classDef.funcList.add((funcDefNode) visit (v)));
        return classDef ;
    }

    @Override
    public ASTNode visitContinueStmt (MxParser.ContinueStmtContext ctx) {
        return new continueStmtNode(new position(ctx)) ;
    }

}
