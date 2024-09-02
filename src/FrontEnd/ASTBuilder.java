package FrontEnd;

import AST.*;
import AST.Definition.*;
import AST.Statement.*;
import AST.Expression.*;
import AST.Expression.binaryExprNode.binaryOperator;
import AST.Expression.postIncExprNode.postIncOperator;
import AST.Expression.unaryExprNode.unaryOperator;
import AST.Expression.preIncExprNode.preIncOperator;
import Parser.MxBaseVisitor;
import Parser.MxParser;
import Util.Type;
import Util.position;

import java.util.ArrayList;

public class ASTBuilder extends MxBaseVisitor<ASTNode> {
    @Override
    public ASTNode visitProgram (MxParser.ProgramContext ctx) {
        RootNode root = new RootNode(new position(ctx)) ;
        ctx.body().forEach(v -> root.parts.add(visit(v)));
        return root ;
    }

    @Override
    public ASTNode visitBody (MxParser.BodyContext ctx) {
        if (ctx.varDefStmt() != null) return visit (ctx.varDefStmt()) ;
        else if (ctx.funcDef() != null) return visit (ctx.funcDef()) ;
        else if (ctx.classDef() != null) return visit (ctx.classDef()) ;
        else throw new RuntimeException() ;
    }

    @Override
    public ASTNode visitNewVarExpr(MxParser.NewVarExprContext ctx) {
        newVarExprNode newVar = new newVarExprNode(new position(ctx)) ;
        if(ctx.Int()!=null) newVar.type = new Type(Type.basicType.Int, 0, false) ;
        else if(ctx.Bool()!=null) newVar.type = new Type(Type.basicType.Bool, 0, false) ;
        else if(ctx.String()!=null) newVar.type = new Type(Type.basicType.String, 0, false) ;
        else newVar.type = new Type(ctx.Identifier().toString(), 0, false) ;
        return newVar;
    }

    @Override
    public ASTNode visitNewArrayExpr(MxParser.NewArrayExprContext ctx){
        newArrayExprNode newArray = new newArrayExprNode(new position(ctx)) ;
        ctx.expression().forEach(v -> newArray.exprList.add((ExpressionNode) visit(v))) ;
        newArray.arrayLiteral = ctx.arrayLiteral() == null ? null : (arrayLiteralNode) visit(ctx.arrayLiteral()) ;
        if(ctx.Identifier()!=null) newArray.type = new Type(ctx.Identifier().toString(), ctx.LeftBracket().size(), false) ;
        else if(ctx.Int()!=null) newArray.type = new Type(Type.basicType.Int, ctx.LeftBracket().size(), false) ;
        else if(ctx.Bool()!=null) newArray.type = new Type(Type.basicType.Bool, ctx.LeftBracket().size(), false) ;
        else if(ctx.String()!=null) newArray.type = new Type(Type.basicType.String, ctx.LeftBracket().size(), false) ;
        return newArray;
    }

    @Override
    public ASTNode visitVarDef(MxParser.VarDefContext ctx) {
        Type type;
        if(ctx.varType().Int()!=null) type = new Type(Type.basicType.Int, ctx.varType().LeftBracket().size(), false) ;
        else if(ctx.varType().Bool()!=null) type = new Type(Type.basicType.Bool, ctx.varType().LeftBracket().size(), false) ;
        else if(ctx.varType().String()!=null) type = new Type(Type.basicType.String, ctx.varType().LeftBracket().size(), false) ;
        else type = new Type(ctx.varType().Identifier().toString(), ctx.varType().LeftBracket().size(), false) ;
        varDefNode varDef = new varDefNode(new position(ctx), type) ;
        ctx.varDeclare().forEach(v -> varDef.varDeclarations.add((varDeclareNode) visit(v)));
        for (var v : varDef.varDeclarations) {
            v.type = type;
        }
        return varDef;
    }

    @Override
    public ASTNode visitVarDefStmt (MxParser.VarDefStmtContext ctx) {
//        return visit(ctx.varDef());
        return new varDefStmtNode(new position(ctx), (varDefNode) visit(ctx.varDef())) ;
    }


    @Override
    public ASTNode visitVarDeclare(MxParser.VarDeclareContext ctx) {
        String name = ctx.Identifier().toString() ;
        if (ctx.expression() != null)
            return new varDeclareNode(new position(ctx), name, (ExpressionNode) visit(ctx.expression())) ;
        else return new varDeclareNode(new position(ctx), name) ;
    }

    @Override
    public ASTNode visitFuncDef (MxParser.FuncDefContext ctx) {
        Type type;
        if(ctx.functypename().Void()!=null) type = new Type(Type.basicType.Void, 0, false) ;
        else if (ctx.functypename().varType().Int()!=null)
                type = new Type(Type.basicType.Int, ctx.functypename().varType().LeftBracket().size(), false) ;
        else if (ctx.functypename().varType().Bool()!=null)
            type = new Type(Type.basicType.Bool, ctx.functypename().varType().LeftBracket().size(), false) ;
        else if (ctx.functypename().varType().String()!=null)
            type = new Type(Type.basicType.String, ctx.functypename().varType().LeftBracket().size(), false) ;
        else type = new Type(ctx.functypename().varType().Identifier().toString(), ctx.functypename().varType().LeftBracket().size(), false) ;

        String name = ctx.Identifier().toString() ;
        funcDefParameterNode parameterList = ctx.parameterList()==null?null:(funcDefParameterNode) visit(ctx.parameterList()) ;
        suiteNode suite = (suiteNode) visit(ctx.suite()) ;
        return new funcDefNode(new position(ctx), type, name, parameterList, suite) ;
    }

    @Override
    public ASTNode visitParameterList(MxParser.ParameterListContext ctx) {
        funcDefParameterNode parameterList = new funcDefParameterNode(new position(ctx)) ;
        for(int i = 0; i < ctx.varType().size(); i++) {
            Type type;
            if(ctx.varType(i).Int()!=null) type = new Type(Type.basicType.Int, 0, false) ;
            else if(ctx.varType(i).Bool()!=null) type = new Type(Type.basicType.Bool, 0, false) ;
            else if(ctx.varType(i).String()!=null) type = new Type(Type.basicType.String, 0, false);
            else /*(ctx.varType(i).Identifier()!=null)*/ type = new Type(ctx.varType(i).Identifier().toString(), 0, false) ;
            String name = ctx.Identifier(i).toString() ;
            parameterList.parameters.add(new parameterNode(new position(ctx), type, name)) ;
        }
        return parameterList ;
    }

    @Override
    public ASTNode visitClassDef (MxParser.ClassDefContext ctx) {
        classDefNode classDef = new classDefNode(new position(ctx), ctx.Identifier().toString()) ;
        if (ctx.classConstruct() != null) classDef.constructor = (classConstructNode) visit (ctx.classConstruct()) ;
        //ctx.varDefStmt().forEach(v -> classDef.varList.add ((varDefStmtNode) visit (v)));
        for(var v : ctx.varDefStmt()) {
            var tmp = (varDefStmtNode) visit(v);
            for(var tmp2 : tmp.varDef.varDeclarations)
                classDef.varList.put(tmp2.name, tmp2);
        }
        //ctx.funcDef().forEach(v -> classDef.funcList.add((funcDefNode) visit (v)));
        for(var v : ctx.funcDef()) {
            var tmp = (funcDefNode) visit(v);
            classDef.funcList.put(tmp.name, tmp);
        }
        return classDef ;
    }

    @Override
    public ASTNode visitClassConstruct (MxParser.ClassConstructContext ctx) {
        String className = ctx.Identifier().toString() ;
        suiteNode suite = (suiteNode) visit(ctx.suite()) ;
        return new classConstructNode(new position(ctx), className, suite) ;
    }

    @Override
    public ASTNode visitSuite(MxParser.SuiteContext ctx) {
        suiteNode suite = new suiteNode(new position(ctx)) ;
        ctx.statement().forEach(v -> suite.statementNodes.add((ASTNode) visit(v)));
        return suite ;
    }

    @Override
    public ASTNode visitIfStmt(MxParser.IfStmtContext ctx) {
        StmtNode thenStmt = (StmtNode)visit(ctx.trueStmt), elseStmt = null;
        ExpressionNode condition = (ExpressionNode)visit(ctx.expression());
        if (ctx.falseStmt != null) elseStmt = (StmtNode)visit(ctx.falseStmt);
        return new ifStmtNode(new position(ctx), condition, thenStmt, elseStmt);
    }

    @Override
    public ASTNode visitForDefStmt(MxParser.ForDefStmtContext ctx) {
        varDefNode varDef = (varDefNode) visit(ctx.varDef()) ;
        ExpressionNode condition = (ExpressionNode) visit(ctx.cond) ,
                step = ctx.step == null ? null : (ExpressionNode) visit(ctx.step) ;

        StmtNode statement = (StmtNode) visit(ctx.statement()) ;
        return new forDefStmtNode(new position(ctx), varDef, condition, step, statement) ;
    }

    @Override
    public ASTNode visitForExpStmt (MxParser.ForExpStmtContext ctx) {
        ExpressionNode init = ctx.init==null? null : (ExpressionNode) visit(ctx.init),
                       condition = ctx.cond==null? null : (ExpressionNode) visit(ctx.cond),
                       step = ctx.step==null? null : (ExpressionNode) visit(ctx.step) ;
        StmtNode statement = (StmtNode) visit(ctx.statement()) ;
        return new forExpStmtNode(new position(ctx), init, condition, step, statement) ;
    }

    @Override
    public ASTNode visitWhileStmt(MxParser.WhileStmtContext ctx) {
        ExpressionNode condition = (ExpressionNode) visit(ctx.expression());
        StmtNode statement = (StmtNode) visit(ctx.statement()) ;
        return new whileStmtNode(new position(ctx), condition , statement) ;
    }

    @Override
    public ASTNode visitBreakStmt (MxParser.BreakStmtContext ctx) {
        return new breakStmt(new position(ctx)) ;
    }

    @Override
    public ASTNode visitContinueStmt (MxParser.ContinueStmtContext ctx) {
        return new continueStmtNode(new position(ctx)) ;
    }

    @Override
    public ASTNode visitReturnStmt(MxParser.ReturnStmtContext ctx) {
        if(ctx.expression()==null) return new returnStmtNode(new position(ctx), null) ;
        ExpressionNode retExpr = (ExpressionNode) visit(ctx.expression()) ;
        //ExpressionNode retExpr = ctx.expression() == null ? null : (ExpressionNode) visit(ctx.expression()) ;
        return new returnStmtNode(new position(ctx), retExpr) ;
    }

    @Override
    public ASTNode visitStatement(MxParser.StatementContext ctx) {
        if (ctx.suite()!=null) return visit(ctx.suite());
        else if (ctx.varDefStmt() != null) return visit (ctx.varDefStmt()) ;
        else if (ctx.ifStmt() != null) return visit (ctx.ifStmt()) ;
        else if (ctx.loopStmt() != null) return visit (ctx.loopStmt()) ;
        else if (ctx.controlStmt() != null) return visit (ctx.controlStmt()) ;
        else if (ctx.expression() != null) return new pureExprStmtNode(new position(ctx),(ExpressionNode) visit (ctx.expression())) ;
        else if (ctx.Semi()!=null) return null ;
        else throw new RuntimeException() ;
    }

    @Override
    public ASTNode visitArrayExpr (MxParser.ArrayExprContext ctx) {
        ExpressionNode arrayIdentifier = (ExpressionNode) visit(ctx.expression(0)),
                       arrayIndex = (ExpressionNode) visit(ctx.expression(1)) ;
        var tmp = new arrayExprNode(new position(ctx), arrayIdentifier, arrayIndex);
        return tmp ;
    }

    @Override
    public ASTNode visitMemberExpr(MxParser.MemberExprContext ctx) {
        ExpressionNode expression = (ExpressionNode) visit(ctx.expression()) ;
        return new memberExprNode(new position(ctx), expression, ctx.Identifier().toString()) ;
    }

    @Override
    public ASTNode visitAtomExpr (MxParser.AtomExprContext ctx) {
        return visit (ctx.primary()) ;
    }

    @Override
    public ASTNode visitBinaryExpr (MxParser.BinaryExprContext ctx) {
        binaryOperator binaryOp = null ;
        if (ctx.Plus() != null) binaryOp = binaryExprNode.binaryOperator.Plus ;
        else if (ctx.Minus() != null) binaryOp = binaryExprNode.binaryOperator.Minus ;
        else if (ctx.Mul() != null) binaryOp = binaryExprNode.binaryOperator.Mul ;
        else if (ctx.Div() != null) binaryOp = binaryExprNode.binaryOperator.Div ;
        else if (ctx.Mod() != null) binaryOp = binaryExprNode.binaryOperator.Mod ;
        else if (ctx.LeftShift() != null) binaryOp = binaryExprNode.binaryOperator.LeftShift ;
        else if (ctx.RightShift() != null) binaryOp = binaryExprNode.binaryOperator.RightShift ;
        else if (ctx.And() != null) binaryOp = binaryExprNode.binaryOperator.And ;
        else if (ctx.Caret() != null) binaryOp = binaryExprNode.binaryOperator.Caret ;
        else if (ctx.Or() != null) binaryOp = binaryExprNode.binaryOperator.Or ;

        else if (ctx.Less() != null) binaryOp = binaryExprNode.binaryOperator.Less ;
        else if (ctx.LessEqual() != null) binaryOp = binaryExprNode.binaryOperator.LessEqual ;
        else if (ctx.Greater() != null) binaryOp = binaryExprNode.binaryOperator.Greater ;
        else if (ctx.GreaterEqual() != null) binaryOp = binaryExprNode.binaryOperator.GreaterEqual ;
        else if (ctx.Equal() != null) binaryOp = binaryExprNode.binaryOperator.Equal ;
        else if (ctx.NotEqual() != null) binaryOp = binaryExprNode.binaryOperator.NotEqual ;
        else if (ctx.AndAnd() != null) binaryOp = binaryExprNode.binaryOperator.AndAnd ;
        else if (ctx.OrOr() != null) binaryOp = binaryExprNode.binaryOperator.OrOr ;

        ExpressionNode lhs = (ExpressionNode) visit(ctx.expression(0)),
                       rhs = (ExpressionNode) visit(ctx.expression(1)) ;
        return new binaryExprNode(new position(ctx), binaryOp, lhs, rhs) ;
    }

    @Override
    public ASTNode visitParenExpr (MxParser.ParenExprContext ctx) {
        ExpressionNode expression = (ExpressionNode) visit (ctx.expression()) ;
        return new parenExprNode(new position(ctx), expression) ;
    }

    @Override
    public ASTNode visitPostIncExpr(MxParser.PostIncExprContext ctx) {
        postIncOperator postOp = ctx.PlusPlus() != null ? postIncOperator.PlusPlus :
                                (ctx.MinusMinus() != null ? postIncOperator.MinusMinus : null) ;

        ExpressionNode expression = (ExpressionNode) visit(ctx.expression()) ;
        return new postIncExprNode(new position(ctx), postOp, expression) ;
    }

    @Override
    public ASTNode visitPreIncExpr(MxParser.PreIncExprContext ctx) {
        preIncOperator preOp = ctx.PlusPlus() != null ? preIncOperator.PlusPlus :
                               (ctx.MinusMinus() != null ? preIncOperator.MinusMinus : null) ;

        ExpressionNode expression = (ExpressionNode) visit(ctx.expression()) ;
        return new preIncExprNode(new position(ctx), preOp, expression) ;
    }

    @Override
    public ASTNode visitUnaryExpr(MxParser.UnaryExprContext ctx) {
        unaryOperator unaryOp = ctx.Plus() != null ? unaryOperator.Plus :
                                (ctx.Minus() != null ? unaryOperator.Minus :
                                (ctx.Not() != null ? unaryOperator.Not :
                                (ctx.Tilde() != null ? unaryOperator.Tilde : null))) ;

        ExpressionNode expression = (ExpressionNode) visit(ctx.expression()) ;
        return new unaryExprNode(new position(ctx), unaryOp, expression) ;
    }

    @Override
    public ASTNode visitTernaryExpr(MxParser.TernaryExprContext ctx) {
        ExpressionNode condition = (ExpressionNode) visit(ctx.expression(0)),
                       trueExpr = (ExpressionNode) visit(ctx.expression(1)),
                       falseExpr = (ExpressionNode) visit(ctx.expression(2)) ;
        return new ternaryExprNode(new position(ctx), condition, trueExpr, falseExpr) ;
    }

    @Override
    public ASTNode visitCallExpr(MxParser.CallExprContext ctx) {//expressionList?
        ExpressionNode functionIdentifier = (ExpressionNode) visit(ctx.expression(0)) ;
        var tmp = new callExprNode(new position(ctx), functionIdentifier);
        for(int i = 1; i < ctx.expression().size(); i++) {
            tmp.paraList.add((ExpressionNode) visit(ctx.expression(i))) ;
        }
        return tmp;
    }

    @Override
    public ASTNode visitAssignExpr(MxParser.AssignExprContext ctx) {
        ExpressionNode lhs = (ExpressionNode) visit(ctx.expression(0)) ,
                       rhs = (ExpressionNode) visit(ctx.expression(1)) ;
        return new assignExprNode(new position(ctx), lhs, rhs) ;
    }

    @Override
    public ASTNode visitPrimary(MxParser.PrimaryContext ctx) {
        if (ctx.Identifier() != null) return new atomExprNode(new position(ctx), atomExprNode.primaryType.Identifier, ctx.Identifier().toString());
        else if (ctx.literal() != null) {
            return visit(ctx.literal());
        }
        else if (ctx.This() != null) return new atomExprNode(new position(ctx), atomExprNode.primaryType.This, null);
        else if (ctx.Null() != null) return new atomExprNode(new position(ctx), atomExprNode.primaryType.Null, null);
        else if (ctx.stringFormat() != null) return visit(ctx.stringFormat());//?
        else throw new RuntimeException();
    }

    @Override
    public ASTNode visitLiteral(MxParser.LiteralContext ctx) {
        if(ctx.DecimalInteger() != null) return new intLiteralNode(new position(ctx), Integer.parseInt(ctx.DecimalInteger().toString()));
        else if(ctx.boolLiteral() != null) return new boolLiteralNode(new position(ctx), Boolean.parseBoolean(ctx.boolLiteral().toString()));
        else if(ctx.Null() != null) return new nullLiteralNode(new position(ctx));
        else if(ctx.StringLiteral() != null) return new stringLiteralNode(new position(ctx), ctx.StringLiteral().toString());
        else if(ctx.arrayLiteral()!=null) return visit(ctx.arrayLiteral());
        else throw new RuntimeException();
    }

    @Override
    public ASTNode visitArrayLiteral(MxParser.ArrayLiteralContext ctx) {
        arrayLiteralNode arrayLiteral = new arrayLiteralNode(new position(ctx));
        ctx.literal().forEach(v -> arrayLiteral.elements.add((LiteralNode) visit(v)));
        return arrayLiteral;
    }

    @Override
    public ASTNode visitStringFormat(MxParser.StringFormatContext ctx) {
        FmtstringNode stringFormat = new FmtstringNode(new position(ctx));
        ctx.expression().forEach(v -> stringFormat.expr.add((ExpressionNode) visit(v)));
        ctx.Middle().forEach(v -> stringFormat.middle.add(v.toString()));
        return stringFormat;
    }

    @Override
    public ASTNode visitBoolLiteral(MxParser.BoolLiteralContext ctx) {
        return new boolLiteralNode(new position(ctx), Boolean.parseBoolean(ctx.getText()));
    }
}
