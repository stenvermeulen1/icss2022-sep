package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;

import java.util.HashMap;
import java.util.LinkedList;

public class Evaluator implements Transform {

    private IHANLinkedList<HashMap<String, Literal>> variableValues;

    public Evaluator() {
        //variableValues = new HANLinkedList<>();
    }

    @Override
    public void apply(AST ast) {
        //applyStylesheet(ast.root);

    }

//    private void applyStylesheet(Stylesheet root) {
//        applyStylerule((Stylerule) sheet.getChildren().get(x));
//
//        applyVariable((VariableAssignment) sheet.getChildren().get(x));
//    }
//
//    private void applyStylerule(Stylerule rule) {
//        for (ASTNode node : rule.getChildren()) {
//            if (node instanceof Declaration) {
//                applyDeclaration((Declaration) node);
//            }
//        }
//    }
//
//    private void applyDeclaration(Declaration declaration) {
//        declaration.expression = evaluateExpression(declaration.expression);
//    }
//
//    private Expression evaluateExpression(Expression expression) {
//        if (expression instanceof Literal) {
//            return (Literal) expression;
//        } else {
//            return evaluateExpression((Operation) expression);
//        }
//    }
//
//    private Expression evaluateAddOperation(AddOperation expression) {
//        Literal left = (Literal) evaluateExpression(expression.lhs);
//        Literal right = (Literal) evaluateExpression(expression.rhs);
//        return new Literal(left.value + right.value);
//    }
}
