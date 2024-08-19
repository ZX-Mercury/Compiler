grammar Mx;

program: body* EOF;
body : (varDefStmt | funcDef | classDef);

newVar : (buildin_typename | Identifier) ('(' ')' | ('[' expression? ']')*) ;

varDef : varType varDeclare (',' varDeclare)*;
varDefStmt : varDef ';';//done
buildin_typename : Int | Bool | String;//done
varType : (buildin_typename | Identifier) ('[' ']')*;//done
varDeclare : Identifier (Assign expression)?;//done

functypename : varType | Void;//done
funcDef : functypename Identifier '(' parameterList? ')' suite;//done
parameterList : parameter (',' parameter)*;//done
parameter : varType Identifier;//done

classDef : Class Identifier '{' (varDefStmt | funcDef)* classConstruct? (varDefStmt | funcDef)* '}' ';';
classConstruct : Identifier '(' ')' suite;//done

suite : '{' statement* '}';

ifStmt : If '(' expression ')' trueStmt=statement (Else falseStmt=statement)?;

loopStmt
    : For '(' (varDef | expression)? ';' expression? ';' expression? ')' statement #forStmt
    | While '(' expression ')' statement #whileStmt
    ;

controlStmt
    : Break ';'                 #breakStmt
    | Continue ';'              #continueStmt
    | Return expression? ';'    #returnStmt
    ;

statement
    : suite
    | varDefStmt
    | ifStmt
    | loopStmt
    | controlStmt
    | expression ';'
    | ';'
    ;

expressionList : '(' (expression (',' expression)*)? ')' ;
expression
    : primary                                                   #atomExpr//done
    | '(' expression ')'                                        #parenExpr
    | expression expressionList                                 #callExpr//done
    | expression '[' expression ']'                             #arrayExpr//done

    | <assoc=right> (PlusPlus | MinusMinus) expression          #preIncExpr//done
    | expression (PlusPlus | MinusMinus)                        #postIncExpr//done
    | <assoc=right> (Plus | Minus | Not | Tilde) expression     #unaryExpr//done
    | <assoc=right> New newVar                                  #newExpr//TODO

    | expression op=(Plus | Minus | Mul | Div | Mod) expression #binaryExpr
    | expression op=(Equal | NotEqual) expression               #binaryExpr
    | expression op=(Less | LessEqual
                    | Greater | GreaterEqual) expression        #binaryExpr
    | expression op=(AndAnd | OrOr) expression                  #binaryExpr
    | expression op=(And | Or | Caret
                    | LeftShift | RightShift) expression        #binaryExpr//done
    | <assoc=right> expression Assign expression                #binaryExpr//done, included in binaryExpr
    | expression Dot expression                                 #binaryExpr//done, included in binaryExpr

    | expression Question expression Colon expression           #ternaryExpr//done
    ;

primary
    : Identifier
    | literal
    | This
    | Null
    | stringFormat
    ;

literal
    : DecimalInteger
    | BoolLiteral
    | Null
    | StringLiteral
    | arrayLiteral
    ;

arrayLiteral
    : '{' (literal(','literal)*)? '}'
    ;

//Str : Quote ('\\n' | '\\\\' | '\\"' | [ !#-[\]-~])* Quote;
fragment FormatStr : ('\\n' | '\\\\' | '\\"' | [ !#%-[\]-~] | '$$')*;
Format_Plain : 'f"' FormatStr '"';
Head : 'f"' FormatStr '$';
Middle : '$' FormatStr '$';
Tail : '$' FormatStr '"';
stringFormat : Format_Plain | (Head expression? (Middle expression?)* Tail);

Whitespace : [ \t]+ -> skip;

Newline : ( '\r' '\n'? | '\n' ) -> skip;

// 1.Character set
// Standard operators
Plus : '+'; Minus : '-'; Mul : '*'; Div : '/'; Mod : '%';

// Relational operators
Less : '<'; LessEqual : '<='; Greater : '>';
GreaterEqual : '>='; Equal : '=='; NotEqual : '!=';

// Logical operators
AndAnd : '&&'; OrOr : '||'; Not : '!';

// Bitwise operators
LeftShift : '<<'; RightShift : '>>'; And : '&'; Or : '|';
Caret : '^'; Tilde : '~';

// Assignment operators
Assign : '=';

// Increment operators
PlusPlus : '++'; MinusMinus : '--';

// Component operator
Dot : '.';

// Paren, brace, bracket, and separator
LeftParen : '('; RightParen : ')';
LeftBracket : '['; RightBracket : ']';
LeftBrace : '{'; RightBrace : '}';
Semi : ';'; Comma : ',';

// Ternary operators
Question : '?'; Colon : ':';

// 2. keywords
Void : 'void'; Bool : 'bool'; Int : 'int'; String : 'string';
New : 'new'; Class : 'class'; Null : 'null'; fragment True : 'true'; //use 'fragment', otherwise boolLiteral will be covered.
fragment False : 'false'; This : 'this'; If : 'if'; Else : 'else';
For : 'for'; While : 'while'; Break : 'break'; Continue : 'continue';
Return : 'return';


/*// 3. Whitespace characters
Space : ' ';
Tab : '\t';
//Newline : '\n';*/

// 4. Identifier
Identifier
    : [a-zA-Z] [a-zA-Z_0-9]*
    ;

// 5. Comments
BlockComment
    :   '/*' .*? '*/'
        -> skip
    ;

LineComment
    :   '//' ~[\r\n]*
        -> skip
    ;

// 6. Literals

// Logical constants
BoolLiteral : True | False;

DecimalInteger
    : [1-9] [0-9]*
    | '0'
    ;

StringLiteral
: '"' ('\\n' | '\\\\' | '\\"' | [ !#-[\]-~])* '"'
;

//NullLiteral : Null;
