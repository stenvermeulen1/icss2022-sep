package nl.han.ica.icss.parser;

import java.util.Stack;


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
		//currentContainer = new HANStack<>();
	}
    public AST getAST() {
        return ast;
    }



//	@Override
//	public void enterTagSelector(ICSSParser.TagSelectorContext ctx) {
//		TagSelector selector = new TagSelector(ctx.getText);
//		currentContainer.push(selector);
//	}
//
//	@Override
//	public void exitTagSelector(ICSSParser.TagSelectorContext ctx) {
//		TagSelector selector = (TagSelector) currentContainer.pop();
//		currentContainer.peek().addChild(selector);
//	}
//
//	@Override
//	public void enterDeclaration(ICSSParser.DeclarationContext ctx) {
//		Declaration declaration = new Declaration();
//		currentContainer.push(declaration);
//	}
//
//	@Override
//	public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
//		Declaration declaration = (Declaration) currentContainer.pop();
//		currentContainer.peek().addChild(declaration);
//	}

//	@Override
//	public void enterStylesheet(ICSSParser.StylesheetContext ctx) {
//		Stylesheet stylesheet = new Stylesheet(ctx);
//		currentContainer.push(stylesheet);
//	}
//
//	@Override
//	public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
//		Stylesheet stylesheet = (Stylesheet) currentContainer.pop();
//		currentContainer.peek().addChild(stylesheet);
//	}

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

}