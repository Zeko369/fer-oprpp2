package hr.fer.zemris.java.custom.scripting.parser.commands;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.node.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.node.Node;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import hr.fer.zemris.java.custom.scripting.parser.utils.ElementsHelper;

/**
 * HelperParser for ForLoops
 *
 * @author franzekan
 */
public class ForCommandParser extends CommandParser {
    /**
     * Parses a FOR node
     *
     * @param l Lexer
     * @return parsed ForLoopNode
     */
    public static Node parseCommand(Lexer l) {
        Element[] elements = ElementsHelper.getElementsInTag(l);
        if (elements.length < 3) {
            throw new SmartScriptParserException("[FOR]: Too few arguments");
        }

        if (elements.length > 4) {
            throw new SmartScriptParserException("[FOR]: Too many arguments");
        }

        if (!(elements[0] instanceof ElementVariable)) {
            throw new SmartScriptParserException("[FOR]: First argument isn't a variable");
        }

        for (int i = 1; i < elements.length; i++) {
            if (!(elements[i] instanceof ElementVariable || elements[i] instanceof ElementConstantInteger || elements[i] instanceof ElementConstantDouble)) {
                throw new SmartScriptParserException(String.format("[FOR]: argument[%d] is not a valid type", i));
            }
        }

        return new ForLoopNode((ElementVariable) elements[0], elements[1], elements[2], elements.length == 3 ? null : elements[3]);
    }
}
