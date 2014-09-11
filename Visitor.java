package fia;

import java.util.HashMap;
import java.util.Map;

public class Visitor {

	public Object visit(Node node) {
		if(node != null) {
			return node.visit(this);
		}
		return null;
	}

	public Object visitAssign(AssignNode n) {
        Map<String, Number> assigns = new HashMap<String, Number>();
        String left = (String) visit(n.left);
        Number right = (Number) visit(n.right);
        assigns.put(left, right);
        return assigns;
	}

	public Object visitExpression(ExpressionNode n) {
        int left = (Integer) visit(n.left);
        int right = (Integer) visit(n.right);
        if (n.operator.equals("+"))
            return left + right;
        else if (n.operator.equals("-"))
            return left - right;
		return null;
	}

	public Object visitTerm(TermNode n) {
        int left = (Integer) visit(n.left);
        int right = (Integer) visit(n.right);
        if (n.operator.equals("*"))
            return left * right;
        else if (n.operator.equals("/"))
            return left / right;
        return null;
	}

	public Object visitIdentifier(IdentifierNode n) {
		return n.value;
	}

	public Object visitNumber(NumberNode n) {
		return n.value;
	}
}