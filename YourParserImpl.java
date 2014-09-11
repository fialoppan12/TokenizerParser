package fia;

/**
* Created with IntelliJ IDEA.
* User: fialoppan
* Date: 2013-11-09
* Time: 04:09
* To change this template use File | Settings | File Templates.
*/


public class YourParserImpl implements Parser {

    private YourTokenizerImpl tokenizer;

    public YourParserImpl(YourTokenizerImpl t) {
        tokenizer = t;
    }

    /**
     * Parse, using a tokenizer (supplied as a contructor parameter),
     * a parse tree
     */
    @Override
    public Node parse() {
        return parseAssign();
    }


    private AssignNode parseAssign() {
        if (tokenizer.current().type() == Token.Type.IDENTIFIER && tokenizer.peek().type() == Token.Type.EQ) {  // peek pga behöver current nedan
            IdentifierNode id = new IdentifierNode();
            id.value = tokenizer.current().text();
            AssignNode node = new AssignNode();
            node.left = id;
            tokenizer.next(); // tokenizer.current() == Token.Type.EQ
            node.right = parseExpression();
            return node;
        } else
            throw new RuntimeException("Invalid Assign");
    }


    private Node parseExpression() {
        Node n = parseTerm();
        if (tokenizer.peek().type() == Token.Type.PLUS || tokenizer.peek().type() == Token.Type.MINUS) {
            ExpressionNode node = new ExpressionNode();
            node.left = n;

            while (tokenizer.peek().type() == Token.Type.PLUS || tokenizer.peek().type() == Token.Type.MINUS) {
                node.operator = tokenizer.next().text();
                node.right = parseTerm();
                return node;
            }
            throw new RuntimeException("Invalid Expression");
        }
        return n;
    }

    private Node parseTerm() {
        Node n = parseFactor(); // ploblem pga multiple calls ???  // solved
        if (tokenizer.peek().type() == Token.Type.MULT || tokenizer.peek().type() == Token.Type.DIV) {
            TermNode node = new TermNode();
            node.left = n;
            while (tokenizer.peek().type() == Token.Type.MULT || tokenizer.peek().type() == Token.Type.DIV) {
                node.operator = tokenizer.next().text();
                node.right = parseFactor();
                return node;
            }
            throw new RuntimeException("Invalid term");
        }
        return n;
    }

    private Node parseFactor() {
        if (tokenizer.next().type() == Token.Type.NUMBER) {
            NumberNode node = new NumberNode();
            node.value = (Number) tokenizer.current().value(); // lös return type i tokenizer // solved
            return node;
        } else if (tokenizer.current().type() == Token.Type.LEFT_PAREN) {
            Node node = parseExpression();
            if (tokenizer.next().type() == Token.Type.RIGHT_PAREN) {
                return node;
            } else
                throw new RuntimeException("Invalid Factor");
        } else
            throw new RuntimeException("Invalid factor");
    }

}