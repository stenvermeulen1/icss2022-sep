package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


public class Checker {

    private LinkedList<HashMap<String, ExpressionType>> variableTypes;

    public void check(AST ast) {
        variableTypes = new LinkedList<>();
        checkStylesheet(ast.root);
    }

    private void checkStylesheet(Stylesheet sheet) {
        variableTypes.addFirst(new HashMap<>());

        for (ASTNode child : sheet.getChildren()) {
            if (child instanceof VariableAssignment) {
                checkVariableAssignment((VariableAssignment) child);
            } else if (child instanceof Stylerule) {
                variableTypes.addFirst(new HashMap<>());
                checkStylerule((Stylerule) child);
                variableTypes.removeFirst();
            }
        }
        variableTypes.clear();
    }

    private void checkStylerule(Stylerule rule) {
        for (ASTNode child: rule.getChildren()) {
            if (child instanceof Declaration) {
                checkDeclaration((Declaration) child);
            }
            if (child instanceof VariableAssignment) {
                checkVariableAssignment((VariableAssignment) child);
            }
            if (child instanceof IfClause) {
                checkIfClause((IfClause) child);
            }
        }
    }

    private void checkDeclaration(Declaration declaration) {
        ExpressionType expressionType = checkExpression(declaration.expression);

        switch (declaration.property.name) {
            case "color":
            case "background-color":
                if (expressionType != ExpressionType.COLOR) {
                    declaration.setError(declaration.property.name + " can only be of type ColorLiteral.");
                }
                break;
            case "width":
            case "height":
                if (expressionType != ExpressionType.PIXEL && expressionType != ExpressionType.PERCENTAGE) {
                    declaration.setError(declaration.property.name + " can only be of type Percentage- or PixelLiteral.");
                }
                break;
            default:
                declaration.setError("Wrong property! Only the following properties are allowed: color, background-color, width & height.");
                break;
        }
    }

    private ExpressionType checkExpression(Expression expression) {
        if (expression instanceof Operation) {
            return checkOperation((Operation) expression);
        }
        else {
            return checkExpressionType(expression);
        }
    }

    private void checkVariableAssignment(VariableAssignment variableAssignment) {
        if (variableAssignment.expression instanceof PercentageLiteral) {
            variableTypes.getFirst().put(variableAssignment.name.name, ExpressionType.PERCENTAGE);
        } else if (variableAssignment.expression instanceof ScalarLiteral) {
            variableTypes.getFirst().put(variableAssignment.name.name, ExpressionType.SCALAR);
        } else if (variableAssignment.expression instanceof PixelLiteral) {
            variableTypes.getFirst().put(variableAssignment.name.name, ExpressionType.PIXEL);
        } else if (variableAssignment.expression instanceof ColorLiteral) {
            variableTypes.getFirst().put(variableAssignment.name.name, ExpressionType.COLOR);
        } else if (variableAssignment.expression instanceof BoolLiteral) {
            variableTypes.getFirst().put(variableAssignment.name.name, ExpressionType.BOOL);
        } else if (variableAssignment.expression instanceof Operation) {
            ExpressionType type = checkOperation((Operation) variableAssignment.expression);
            variableTypes.getFirst().put(variableAssignment.name.name, type);
        } else if (variableAssignment.expression instanceof VariableReference) {
            String varName = ((VariableReference) variableAssignment.expression).name;
            if (!variableTypes.getFirst().containsKey(varName)) {
                variableAssignment.setError("The variable '" + varName + "' doesn't exist!");
            } else {
                variableTypes.getFirst().put(variableAssignment.name.name, variableTypes.getFirst().get(varName));
            }
        }
    }

    private ExpressionType checkOperation(Operation operation) {
        ExpressionType left = operation.lhs instanceof Operation ? checkOperation((Operation) operation.lhs) : checkExpressionType(operation.lhs);
        ExpressionType right = operation.rhs instanceof Operation ? checkOperation((Operation) operation.rhs) : checkExpressionType(operation.rhs);

        if (left == ExpressionType.COLOR || right == ExpressionType.COLOR || left == ExpressionType.BOOL || right == ExpressionType.BOOL) {
            operation.setError("Booleans and colors are not allowed in an operation.");
            return ExpressionType.UNDEFINED;
        }
        if (operation instanceof MultiplyOperation) {
            if (left != ExpressionType.SCALAR && right != ExpressionType.SCALAR) {
                operation.setError("Multiplication can only be performed with at least one scalar value.");
                return ExpressionType.UNDEFINED;
            }
            return right != ExpressionType.SCALAR ? right : left;
        } else if ((operation instanceof SubtractOperation || operation instanceof AddOperation) && left != right) {
            operation.setError("Add and subtract operations can only be performed with the same type of literal.");
            return ExpressionType.UNDEFINED;
        }
        return left;
    }

    private ExpressionType checkExpressionType(Expression expression) {
        if (expression instanceof VariableReference) {
            return checkVariableReference((VariableReference) expression);
        } else if (expression instanceof PercentageLiteral) {
            return ExpressionType.PERCENTAGE;
        } else if (expression instanceof PixelLiteral) {
            return ExpressionType.PIXEL;
        } else if (expression instanceof ColorLiteral) {
            return ExpressionType.COLOR;
        } else if (expression instanceof ScalarLiteral) {
            return ExpressionType.SCALAR;
        } else if (expression instanceof BoolLiteral) {
            return ExpressionType.BOOL;
        }
        return ExpressionType.UNDEFINED;
    }

    private void checkIfClause(IfClause ifClause) {
        variableTypes.addFirst(new HashMap<>());

        Expression conditionalExpression = ifClause.getConditionalExpression();
        ExpressionType expressionType = checkExpressionType(conditionalExpression);

        if (expressionType != ExpressionType.BOOL) {
            ifClause.setError("Conditional expression must have a boolean literal type.");
        }

        checkRuleBody(ifClause.body);
        variableTypes.removeFirst();

        if (ifClause.getElseClause() != null) {
            checkRuleBody((ifClause.getElseClause()).body);
        }
    }

    private void checkRuleBody(ArrayList<ASTNode> body) {
        for (ASTNode bodyPart : body) {
            if (bodyPart instanceof Declaration) {
                checkDeclaration((Declaration) bodyPart);
            } else if (bodyPart instanceof IfClause) {
                checkIfClause((IfClause) bodyPart);
            } else if (bodyPart instanceof VariableAssignment) {
                checkVariableAssignment((VariableAssignment) bodyPart);
            }
        }
    }

    private ExpressionType checkVariableReference(VariableReference variableReference) {
        ExpressionType expressionType = getVariableType(variableReference.name);

        if (expressionType == null) {
            variableReference.setError("Variable '" + variableReference.name + "' doesn't exist!");
            return ExpressionType.UNDEFINED;
        }
        return expressionType;
    }

    private ExpressionType getVariableType(String variableType) {
        for (HashMap<String, ExpressionType> currentScope : variableTypes) {
            ExpressionType expressionType = currentScope.get(variableType);
            if (expressionType != null) {
                return expressionType;
            }
        }
        return null;
    }
}
