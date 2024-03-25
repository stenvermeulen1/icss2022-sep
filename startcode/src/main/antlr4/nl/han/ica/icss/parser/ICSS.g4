grammar ICSS;

//--- LEXER: ---

// IF support:
IF: 'if';
ELSE: 'else';
BOX_BRACKET_OPEN: '[';
BOX_BRACKET_CLOSE: ']';


//Literals
TRUE: 'TRUE';
FALSE: 'FALSE';
PIXELSIZE: [0-9]+ 'px';
PERCENTAGE: [0-9]+ '%';
SCALAR: [0-9]+;


//Color value takes precedence over id idents
COLOR: '#' [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f];

//Specific identifiers for id's and css classes
ID_IDENT: '#' [a-z0-9\-]+;
CLASS_IDENT: '.' [a-z0-9\-]+;

//General identifiers
LOWER_IDENT: [a-z] [a-z0-9\-]*;
CAPITAL_IDENT: [A-Z] [A-Za-z0-9_]*;

//All whitespace is skipped
WS: [ \t\r\n]+ -> skip;

//
OPEN_BRACE: '{';
CLOSE_BRACE: '}';
OPEN_PAREN: '(';
CLOSE_PAREN: ')';
SEMICOLON: ';';
COLON: ':';
PLUS: '+';
MIN: '-';
MUL: '*';
DIV: '/';
ASSIGNMENT_OPERATOR: ':=';




//--- PARSER: ---
stylesheet: variable* stylerule* EOF;
stylerule: selector OPEN_BRACE styleruleBody* CLOSE_BRACE;
styleruleBody: (styleDeclaration | ifStatement | variable);
styleDeclaration: property COLON (variableID | literal | expression) SEMICOLON;
property: LOWER_IDENT;

//--- SELECTORS ---
selector: classSelector | idSelector | tagSelector;
classSelector: CLASS_IDENT;
idSelector: ID_IDENT;
tagSelector: LOWER_IDENT;

//--- LITERALS ---
literal: PERCENTAGE #percentageLiteral | SCALAR #scalarLiteral | PIXELSIZE #pixelLiteral | COLOR #colorLiteral | (TRUE | FALSE) #boolLiteral;

//--- VARIABLES ---
variable: variableID ASSIGNMENT_OPERATOR literal SEMICOLON;
variableID: CAPITAL_IDENT;

//--- MATHS ---
expression: (literal | variableID) | expression (MUL | DIV) expression | expression (PLUS | MIN) expression;

//--- (ELSE-)IF-STATEMENT ---
ifStatement: IF BOX_BRACKET_OPEN (variableID | (TRUE | FALSE)) BOX_BRACKET_CLOSE OPEN_BRACE styleruleBody* CLOSE_BRACE elseStatement?;
elseStatement: ELSE OPEN_BRACE styleruleBody* CLOSE_BRACE;