package nl.han.ica.icss.generator;


import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;

import java.util.HashMap;
import java.util.Map;

public class Generator {

	public String generate(AST ast) {
		Stylesheet stylesheet = ast.root;
		StringBuilder stylesheetStringGenerator = new StringBuilder();

		for(ASTNode child : stylesheet.getChildren()){
			stylesheetStringGenerator.append(selectorToString((Stylerule) child));
			stylesheetStringGenerator.append(declarationToString(child));
			stylesheetStringGenerator.append("}\n\n");
		}
		return stylesheetStringGenerator.toString();
	}

	private String selectorToString(Stylerule stylerule){
		StringBuilder selectorStringGenerator = new StringBuilder();

		for(Selector selector : stylerule.selectors){
			selectorStringGenerator.append(selector.toString());
		}
		selectorStringGenerator.append(" {\n");
		return selectorStringGenerator.toString();
	}

	private String declarationToString(ASTNode declaration) {
		StringBuilder declarationStringGenerator = new StringBuilder();
		Map<String, Declaration> lastDeclarationMap = new HashMap<>();

		for (ASTNode child : declaration.getChildren()) {
			if (child instanceof Declaration) {
				Declaration declarationChild = (Declaration) child;
				lastDeclarationMap.put(declarationChild.property.name, declarationChild);
			}
		}
		for (Declaration declarationChild : lastDeclarationMap.values()) {
			declarationStringGenerator.append("  ")
					.append(declarationChild.property.name)
					.append(": ")
					.append(expressionValueToString(declarationChild.expression))
					.append(";\n");
		}
		return declarationStringGenerator.toString();
	}

	private String expressionValueToString(Expression expression){
		if (expression instanceof ColorLiteral){
			return ((ColorLiteral) expression).value;
		} else if(expression instanceof PixelLiteral){
			return ((PixelLiteral) expression).value + "px";
		} else if(expression instanceof PercentageLiteral){
			return ((PercentageLiteral) expression).value + "%";
		}
		return "";
	}
}
