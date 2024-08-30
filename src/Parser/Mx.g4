grammar Mx;

program: body* EOF;
body : (varDefStmt | funcDef | classDef);

varDef : varType varDeclare (',' varDeclare)*;
varDefStmt : varDef ';';//done
varType : btp=(Int | Bool | String | Identifier) ('[' ']')*;//done
varDeclare : Identifier (Assign expression)?;//done

functypename : varType | Void;//done
funcDef : functypename Identifier '(' parameterList? ')' suite;//done
parameterList : varType Identifier (',' varType Identifier)*;//done

classDef : Class Identifier '{' (varDefStmt | funcDef)* classConstruct? (varDefStmt | funcDef)* '}' ';';
classConstruct : Identifier '(' ')' suite;//done

suite : '{' statement* '}';

ifStmt : If '(' expression ')' trueStmt=statement (Else falseStmt=statement)?;

loopStmt
    : For '(' init=varDef ';' cond=expression? ';' step=expression? ')' statement #forDefStmt
    | For '(' init=expression? ';' cond=expression? ';' step=expression? ')' statement #forExpStmt
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

expression
    : primary                                                   #atomExpr
    | '(' expression ')'                                        #parenExpr
    | expression '(' (expression (',' expression)*)? ')'        #callExpr
    | expression '[' expression ']'                             #arrayExpr

    | <assoc=right> (PlusPlus | MinusMinus) expression          #preIncExpr//done
    | expression (PlusPlus | MinusMinus)                        #postIncExpr//done
    | <assoc=right> (Plus | Minus | Not | Tilde) expression     #unaryExpr//done
    | <assoc=right> New tp=(Int | Bool | String | Identifier) '(' ')'   #newVarExpr
    | <assoc=right> New tp=(Int | Bool | String | Identifier)
            ('[' arraySize=expression? ']')* arrayLiteral?      #newArrayExpr

    | expression op=(Plus | Minus | Mul | Div | Mod) expression #binaryExpr
    | expression op=(Equal | NotEqual) expression               #binaryExpr
    | expression op=(Less | LessEqual
                    | Greater | GreaterEqual) expression        #binaryExpr
    | expression op=(AndAnd | OrOr) expression                  #binaryExpr
    | expression op=(And | Or | Caret
                    | LeftShift | RightShift) expression        #binaryExpr//partly done
    | <assoc=right> expression Assign expression                #assignExpr//done
    | expression Dot Identifier                                 #memberExpr
    | <assoc=right> expression Question expression Colon expression           #ternaryExpr//done
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
    | boolLiteral
    | Null
    | StringLiteral
    | arrayLiteral
    ;

arrayLiteral
    : '{' (literal(','literal)*)? '}'
    ;

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
New : 'new'; Class : 'class'; Null : 'null';
True : 'true'; //use 'fragment', otherwise BoolLiteral will be covered.
False : 'false'; This : 'this'; If : 'if'; Else : 'else';
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
boolLiteral : True | False;

DecimalInteger
    : [1-9] [0-9]*
    | '0'
    ;

StringLiteral
: '"' ('\\n' | '\\\\' | '\\"' | [ !#-[\]-~])* '"'
;
