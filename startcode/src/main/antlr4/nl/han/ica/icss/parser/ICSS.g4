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
stylesheet: variable* stylerule*;
stylerule: selector + OPEN_BRACE + (styleDeclaration | ifStatement)* CLOSE_BRACE;
selector: ID_IDENT | CLASS_IDENT | LOWER_IDENT;
styleDeclaration: property + COLON + (value | variableID | expression) + SEMICOLON;
property: LOWER_IDENT;
value: PERCENTAGE | SCALAR | PIXELSIZE #pixelLiteral | COLOR #colorLiteral |TRUE | FALSE;

variable: variableID + ASSIGNMENT_OPERATOR + value + SEMICOLON;
variableID: CAPITAL_IDENT;

expression: term ((PLUS | MIN) term)*;
term: factor ((MUL | DIV) factor)*;
factor: (variableID | value) | (OPEN_PAREN + expression + CLOSE_PAREN);

ifStatement: IF + BOX_BRACKET_OPEN + CAPITAL_IDENT + BOX_BRACKET_CLOSE + OPEN_BRACE + styleDeclaration* ifStatement* CLOSE_BRACE (ELSE + OPEN_BRACE + (styleDeclaration | ifStatement)* CLOSE_BRACE)?;
