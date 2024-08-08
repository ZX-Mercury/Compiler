grammar Mx;

program: (varDef | funcDef | classDef)* EOF;

newVar : (buildin_typename | Identifier) ('(' ')' | ('[' expression? ']')*) ;

varDef : typename varDeclare (',' varDeclare)* ';';
buildin_typename : Int | Bool | String;
typename : (buildin_typename | Identifier) ('[' ']')*;
varDeclare : Identifier (Assign expression)?;

functypename : typename | Void;
funcDef : functypename Identifier '(' (typename Identifier (',' typename Identifier)*)? ')' suite;

classDef : Class Identifier '{' (varDef | funcDef)* classConstruct? (varDef | funcDef)* '}' ';';
classConstruct : Identifier '(' ')' suite;

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
    : suite                                                 #block
    | varDef                                                #vardefStmt
    | If '(' expression ')' trueStmt=statement
        (Else falseStmt=statement)?                         #ifStmt
    | loopStatement                                         #loopStmt
    | controlStatement                                      #controlStmt
    | expression ';'                                        #pureExprStmt
    | ';'                                                   #emptyStmt
    ;

expressionList : '(' (expression (',' expression)*)? ')' ;
expression
    : primary                                                   #atomExpr
    | '(' expression ')'                                        #parenExpr
    | expression expressionList                                 #callExpr
    | expression '[' expression ']'                             #arrayExpr

    | <assoc=right> (PlusPlus | MinusMinus) expression          #preIncExpr
    | expression (PlusPlus | MinusMinus)                        #postIncExpr
    | <assoc=right> (Plus | Minus | Not | Tilde) expression     #prefixExpr
    | <assoc=right> New newVar                                  #newExpr

    | expression op=(Plus | Minus | Mul | Div | Mod) expression #binaryExpr
    | expression op=(Equal | NotEqual) expression               #binaryExpr
    | expression op=(Less | LessEqual
                    | Greater | GreaterEqual) expression        #binaryExpr
    | expression op=(AndAnd | OrOr) expression                  #binaryExpr
    | expression op=(And | Or | Caret
                    | LeftShift | RightShift) expression        #binaryExpr
    | <assoc=right> expression Assign expression                #assignExpr
    | expression Dot expression                                 #memberExpr

    | expression Question expression Colon expression           #ternaryExpr
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