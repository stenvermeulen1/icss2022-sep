package nl.han.ica.icss.parser;

import java.util.Stack;


import nl.han.ica.datastructures.HANStack;
import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {
	
	//Accumulator attributes:
	private AST ast;

	//Use this to keep track of the parent nodes when recursively traversing the ast
	private IHANStack<ASTNode> currentContainer;

	public ASTListener() {
		ast = new AST();
		currentContainer = new HANStack<>();
	}
    public AST getAST() {
        return ast;
    }

	@Override
	public void enterStylesheet(ICSSParser.StylesheetContext ctx) {
		ASTNode stylesheet = new Stylesheet();
		currentContainer.push(stylesheet);
	}

	@Override
	public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
		Stylesheet stylesheet = (Stylesheet) currentContainer.pop();
		ast.setRoot(stylesheet);
	}

	@Override
	public void enterStylerule(ICSSParser.StyleruleContext ctx) {
		ASTNode stylerule = new Stylerule();
		currentContainer.push(stylerule);
	}

	@Override
	public void exitStylerule(ICSSParser.StyleruleContext ctx) {
		Stylerule stylerule = (Stylerule) currentContainer.pop();
		currentContainer.peek().addChild(stylerule);
	}

	@Override
	public void enterClassSelector(ICSSParser.ClassSelectorContext ctx) {
		ClassSelector classSelector = new ClassSelector(ctx.getText());
		currentContainer.push(classSelector);
	}

	@Override
	public void exitClassSelector(ICSSParser.ClassSelectorContext ctx) {
		ClassSelector classSelector = (ClassSelector) currentContainer.pop();
		currentContainer.peek().addChild(classSelector);
	}

	@Override
	public void enterIdSelector(ICSSParser.IdSelectorContext ctx) {
		IdSelector idSelector = new IdSelector(ctx.getText());
		currentContainer.push(idSelector);
	}

	@Override
	public void exitIdSelector(ICSSParser.IdSelectorContext ctx) {
		IdSelector idSelector = (IdSelector) currentContainer.pop();
		currentContainer.peek().addChild(idSelector);
	}

	@Override
	public void enterTagSelector(ICSSParser.TagSelectorContext ctx) {
		TagSelector tagSelector = new TagSelector(ctx.getText());
		currentContainer.push(tagSelector);
	}

	@Override
	public void exitTagSelector(ICSSParser.TagSelectorContext ctx) {
		TagSelector tagSelector = (TagSelector) currentContainer.pop();
		currentContainer.peek().addChild(tagSelector);
	}

	@Override
	public void enterStyleDeclaration(ICSSParser.StyleDeclarationContext ctx) {
		Declaration styleDeclaration = new Declaration(ctx.getText());
		currentContainer.push(styleDeclaration);
	}

	@Override
	public void exitStyleDeclaration(ICSSParser.StyleDeclarationContext ctx) {
		Declaration styleDeclaration = (Declaration) currentContainer.pop();
		currentContainer.peek().addChild(styleDeclaration);
	}

	@Override
	public void enterProperty(ICSSParser.PropertyContext ctx) {
		PropertyName property = new PropertyName(ctx.getText());
		currentContainer.push(property);
	}

	@Override
	public void exitProperty(ICSSParser.PropertyContext ctx) {
		PropertyName property = (PropertyName) currentContainer.pop();
		currentContainer.peek().addChild(property);
	}

	@Override
	public void enterColorLiteral(ICSSParser.ColorLiteralContext ctx) {
		ColorLiteral colorLiteral = new ColorLiteral(ctx.getText());
		currentContainer.push(colorLiteral);
	}

	@Override
	public void exitColorLiteral(ICSSParser.ColorLiteralContext ctx) {
		ColorLiteral colorLiteral = (ColorLiteral) currentContainer.pop();
		currentContainer.peek().addChild(colorLiteral);
	}

	@Override
	public void enterPixelLiteral(ICSSParser.PixelLiteralContext ctx) {
		PixelLiteral pixelLiteral = new PixelLiteral(ctx.getText());
		currentContainer.push(pixelLiteral);
	}

	@Override
	public void exitPixelLiteral(ICSSParser.PixelLiteralContext ctx) {
		PixelLiteral pixelLiteral = (PixelLiteral) currentContainer.pop();
		currentContainer.peek().addChild(pixelLiteral);
	}

	@Override
	public void enterPercentageLiteral(ICSSParser.PercentageLiteralContext ctx) {
		PercentageLiteral percentageLiteral = new PercentageLiteral(ctx.getText());
		currentContainer.push(percentageLiteral);
	}

	@Override
	public void exitPercentageLiteral(ICSSParser.PercentageLiteralContext ctx) {
		PercentageLiteral percentageLiteral = (PercentageLiteral) currentContainer.pop();
		currentContainer.peek().addChild(percentageLiteral);
	}

	@Override
	public void enterScalarLiteral(ICSSParser.ScalarLiteralContext ctx) {
		ScalarLiteral scalarLiteral = new ScalarLiteral(ctx.getText());
		currentContainer.push(scalarLiteral);
	}

	@Override
	public void exitScalarLiteral(ICSSParser.ScalarLiteralContext ctx) {
		ScalarLiteral scalarLiteral = (ScalarLiteral) currentContainer.pop();
		currentContainer.peek().addChild(scalarLiteral);
	}

	@Override
	public void enterBoolLiteral(ICSSParser.BoolLiteralContext ctx) {
		BoolLiteral boolLiteral = new BoolLiteral(ctx.getText());
		currentContainer.push(boolLiteral);
	}

	@Override
	public void exitBoolLiteral(ICSSParser.BoolLiteralContext ctx) {
		BoolLiteral boolLiteral = (BoolLiteral) currentContainer.pop();
		currentContainer.peek().addChild(boolLiteral);
	}

	@Override
	public void enterVariableID(ICSSParser.VariableIDContext ctx) {
		VariableReference variableID = new VariableReference(ctx.getText());
		currentContainer.push(variableID);
	}

	@Override
	public void exitVariableID(ICSSParser.VariableIDContext ctx) {
		VariableReference variableID = (VariableReference) currentContainer.pop();
		currentContainer.peek().addChild(variableID);
	}

	@Override
	public void enterVariable(ICSSParser.VariableContext ctx) {
		VariableAssignment variable = new VariableAssignment();
		currentContainer.push(variable);
	}

	@Override
	public void exitVariable(ICSSParser.VariableContext ctx) {
		VariableAssignment variable = (VariableAssignment) currentContainer.pop();
		currentContainer.peek().addChild(variable);
	}

	@Override
	public void enterExpression(ICSSParser.ExpressionContext ctx) {
		if(ctx.getChildCount() == 3) {
			ASTNode operation = null;
			if (ctx.MIN() != null) {
				operation = new SubtractOperation();
			} else if (ctx.PLUS() != null) {
				operation = new AddOperation();
			} else if (ctx.MUL() != null) {
				operation = new MultiplyOperation();
			}
			currentContainer.push(operation);
		}
	}

	@Override
	public void exitExpression(ICSSParser.ExpressionContext ctx) {
		if(ctx.PLUS() != null || ctx.MIN() != null || ctx.MUL() != null) {
			ASTNode operation = currentContainer.pop();
			currentContainer.peek().addChild(operation);
		}
	}

	@Override
	public void enterIfStatement(ICSSParser.IfStatementContext ctx) {
		ASTNode ifStatement = new IfClause();
		currentContainer.push(ifStatement);
	}

	@Override
	public void exitIfStatement(ICSSParser.IfStatementContext ctx) {
		ASTNode ifStatement = currentContainer.pop();
		currentContainer.peek().addChild(ifStatement);
	}

	@Override
	public void enterElseStatement(ICSSParser.ElseStatementContext ctx) {
		ASTNode elseStatement = new ElseClause();
		currentContainer.push(elseStatement);
	}

	@Override
	public void exitElseStatement(ICSSParser.ElseStatementContext ctx) {
		ASTNode elseStatement = currentContainer.pop();
		currentContainer.peek().addChild(elseStatement);
	}

}