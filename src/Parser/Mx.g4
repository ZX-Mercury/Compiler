grammar Mx;

program: body* EOF;
body : (varDefStmt | funcDef | classDef);

newVar : (buildin_typename | Identifier) ('(' ')' | ('[' expression? ']')*) ;

varDef : typename varDeclare (',' varDeclare)*;
varDefStmt : varDef ';';//done
buildin_typename : Int | Bool | String;//done
typename : (buildin_typename | Identifier) ('[' ']')*;//done
varDeclare : Identifier (Assign expression)?;//done

functypename : typename | Void;//done
funcDef : functypename Identifier '(' parameterList? ')' suite;//done
parameterList : parameter (',' parameter)*;//done
parameter : typename Identifier;//done

classDef : Class Identifier '{' (varDefStmt | funcDef)* classConstruct? (varDefStmt | funcDef)* '}' ';';
classConstruct : Identifier '(' ')' suite;//done

suite : '{' statement* '}';

loopStatement
    : For '(' (varDef | expression)? ';' expression? ';' expression? ')' statement #forStmt
    | While '(' expression ')' statement #whileStmt
    ;

controlStatement
    : Break ';'                 #breakStmt
    | Continue ';'              #continueStmt
    | Return expression? ';'    #returnStmt
    ;

statement
    : suite                                                 #block//done
    | varDefStmt                                            #vardefStmt
    | If '(' expression ')' trueStmt=statement
        (Else falseStmt=statement)?                         #ifStmt//done
    | loopStatement                                         #loopStmt//forStmt, todo_; whileStmt, done
    | controlStatement                                      #controlStmt//breakStmt, continueStmt, returnStmt; all done
    | expression ';'                                        #pureExprStmt//done
    | ';'                                                   #emptyStmt
    ;

expressionList : '(' (expression (',' expression)*)? ')' ;
expression
    : primary                                                   #atomExpr//done
    | '(' expression ')'                                        #parenExpr
    | expression expressionList                                 #callExpr//done
    | expression '[' expression ']'                             #arrayExpr//done

    | <assoc=right> (PlusPlus | MinusMinus) expression          #preIncExpr//done
    | expression (PlusPlus | MinusMinus)                        #postIncExpr//done
    | <assoc=right> (Plus | Minus | Not | Tilde) expression     #prefixExpr//done
    | <assoc=right> New newVar                                  #newExpr//TODO

    | expression op=(Plus | Minus | Mul | Div | Mod) expression #binaryExpr
    | expression op=(Equal | NotEqual) expression               #binaryExpr
    | expression op=(Less | LessEqual
                    | Greater | GreaterEqual) expression        #binaryExpr
    | expression op=(AndAnd | OrOr) expression                  #binaryExpr
    | expression op=(And | Or | Caret
                    | LeftShift | RightShift) expression        #binaryExpr//done
    | <assoc=right> expression Assign expression                #assignExpr//done, included in binaryExpr
    | expression Dot expression                                 #memberExpr//done, included in binaryExpr

    | expression Question expression Colon expression           #ternaryExpr//done
    ;

primary
    : Identifier
    | literal
    | This
    | Null
    ;

literal
    : DecimalInteger
    | BoolLiteral
    | Null
    | StringLiteral
    | arrayLiteral
    ;

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
: '"' ( PrintableChar | EscapeSequence )* '"'
;

fragment PrintableChar
: [\u0020-\u007E] // Printable characters including space
;

fragment EscapeSequence
: '\\' [n\\"] // \n, \\, \"
;

//NullLiteral : Null;

arrayLiteral
    : '{' (literal(','literal)*)? '}'
    ;