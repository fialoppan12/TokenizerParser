package fia;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: fialoppan
 * Date: 2013-11-07
 * Time: 17:26
 * To change this template use File | Settings | File Templates.
 */

public class YourTokenizerImpl implements Tokenizer {

    private ArrayList<Token> tokens;
    private YourScannerImpl sc;
    private  ArrayList<Character> ign;
    private int count = 0;

    public YourTokenizerImpl(YourScannerImpl scanner) {
        sc = scanner;
        ign = new ArrayList<Character>();
        ign.add(' '); // add skipping comma, dot etc here
        this.extractTokens();
    }

    private void extractTokens() {
        tokens = new ArrayList<Token>();

        while (sc.current() != Scanner.EOF) {
            // handle CHAR Token
            if (Character.isLetter(sc.current()))
                this.idToken();
            // handle NUMBER Token
            else if (Character.isDigit(sc.current()))
                this.numberToken();
            // handle OPERATOR Token
            if (Token.Type.OPERATORS.containsKey(String.valueOf(sc.current()))) {
                tokens.add(new Token(String.valueOf(sc.current()), String.valueOf(sc.current()), Token.Type.OPERATORS.get(String.valueOf(sc.current()))));
            }
            // handle skipping whitespace et cetera
            if (ign.contains(sc.current()))
                sc.next();
            // handle EXCEPTION
            // omg ful kod jag skäms D:
            else if (!(Character.isLetter(sc.current())) && !(Character.isDigit(sc.current())) && sc.current() == Scanner.EOF && !(Token.Type.OPERATORS.containsKey(String.valueOf(sc.current())))) {
                throw new RuntimeException("Unexpected character '"+ sc.current()+"'");
            } else  {
                sc.next();
            }
        }
    }

    private void idToken() {
        if (Character.isLetter(sc.peek())) {
            // handle char > z
            StringBuilder b = new StringBuilder();
            while (Character.isLetter(sc.peek())) {
                b.append(sc.current());
                sc.next();
            }
            tokens.add(new Token(b.toString(), "ID", Token.Type.IDENTIFIER));
        } else {
            tokens.add(new Token(String.valueOf(sc.current()), "ID", Token.Type.IDENTIFIER));
        }
    }

    private void numberToken() {
        if (Character.isDigit(sc.peek())) {
            // handle number > 9
            StringBuilder b = new StringBuilder();
            while (Character.isDigit(sc.current())) {
                b.append(sc.current());
                sc.next();
            }
            // add NUMBER Token > 9
            Number n = Integer.parseInt(b.toString());
            tokens.add(new Token("NUMBER", n, Token.Type.NUMBER));
        }   else {
            Number n = Integer.parseInt(String.valueOf(sc.current()));
            tokens.add(new Token("NUMBER", n, Token.Type.NUMBER));
        }
    }


    /**
     * Return the current token in the stream
     */
    @Override
    public Token current() {
        if (count>=tokens.size())
            return new Token("END_OF_FILE", Scanner.EOF, Token.Type.EOF);
        return tokens.get(count);
    }

    /**
     * Return the current token in the stream
     */
    @Override
    public Token next() {
        ++count;
        if (count>=tokens.size())
            return new Token("END_OF_FILE", Scanner.EOF, Token.Type.EOF);
        return tokens.get(count);
    }

    /**
     * Return the next token in the stream, but don't move current()
     * to next()
     */
    @Override
    public Token peek() {
        if (count+1>=tokens.size())
            return new Token("END_OF_FILE", Scanner.EOF, Token.Type.EOF);
        return tokens.get(count+1);
    }
}
