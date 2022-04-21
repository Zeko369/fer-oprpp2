package hr.fer.zemris.java.custom.scripting.parser.utils;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper for getting all elements inside of a tag (till TAG_CLOSE)
 *
 * @author franzekan
 */
public class ElementsHelper {
    private static Element parseElement(Token token) {
        return switch (token.getType()) {
            case INTEGER -> new ElementConstantInteger((Integer) token.getValue());
            case DOUBLE -> new ElementConstantDouble((Double) token.getValue());
            case STRING -> new ElementString((String) token.getValue());
            case OPERATOR -> new ElementOperator((String) token.getValue());
            case VARIABLE -> new ElementVariable((String) token.getValue());
            case FUNCTION -> new ElementFunction((String) token.getValue());
            default -> throw new SmartScriptParserException(String.format("[Element parser]: Token inside of tag is not supported element. Found: %s", token.getType()));
        };
    }

    /**
     * Get all elements in till end of tag
     *
     * @param l Lexer
     * @return elements inside the tag
     */
    public static Element[] getElementsInTag(Lexer l) {
        List<Element> elements = new ArrayList<>();
        Token t = l.getNextToken();

        while (t.getType() != TokenType.EOF && t.getType() != TokenType.TAG_CLOSE) {
            elements.add(ElementsHelper.parseElement(t));
            t = l.getNextToken();
        }

        // FIXME: HERE
        Element[] tmp = new Element[elements.size()];
        for (int i = 0; i < elements.size(); i++) {
            tmp[i] = elements.get(i);
        }

        return tmp;
    }
}
