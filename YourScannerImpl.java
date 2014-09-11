package fia;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

/**
 * Created with IntelliJ IDEA.
 * User: fialoppan
 * Date: 2013-11-07
 * Time: 16:17
 */

public class YourScannerImpl implements Scanner {

    private ArrayList<Character> temp;
    private int count;

    public YourScannerImpl(StringReader reader) throws IOException {
        int i;
        temp = new ArrayList<Character>();
        do {
            i = reader.read();
            char c = (char) i;
            temp.add(c);
        } while (i != -1);
    }

    /**
     * Return the current character in the stream
     */
    @Override
    public char current() {
        if(count >= temp.size())
            return Scanner.EOF;
        return temp.get(count);
    }

    /**
     * Return the next character in the stream. Subsequent calls to
     * current() will return the same character. Scanner.EOF is return
     * if the end of the file is reached.
     */
    @Override
    public char next() {
        ++count;
        if(count >= temp.size())
            return Scanner.EOF;
        return temp.get(count);
    }

    /**
     * Return the next character in the stream. Subsequent calls to
     * next() will return the same character, but calls to current()
     * will be unaffected.
     */
    @Override
    public char peek() {
        if(count +1 >= temp.size())
            return Scanner.EOF;
        return temp.get(count+1);
    }
}
