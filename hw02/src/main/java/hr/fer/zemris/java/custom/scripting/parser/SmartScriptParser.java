package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.node.*;
import hr.fer.zemris.java.custom.scripting.parser.commands.ForCommandParser;
import hr.fer.zemris.java.custom.scripting.parser.utils.ElementsHelper;

import java.util.Stack;

/**
 * SmartScript parser class that returns a parsed tree from a string
 *
 * @author franzekan
 */
public class SmartScriptParser {
    private final String body;
    private final DocumentNode root;

    /**
     * Instantiates a new Smart script parser.
     *
     * @param body the body
     */
    public SmartScriptParser(String body) {
        if (body == null) {
            // TODO: Maybe make this a NullPointerException
            throw new SmartScriptParserException("[FATAL]: Body can't be null");
        }

        this.body = body;
        this.root = new DocumentNode();

        try {
            this.parse();
        } catch (SmartScriptParserException ex) {
            throw ex;
        } catch (LexerException ex) {
            throw new SmartScriptParserException(String.format("[Lexer]: %s", ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SmartScriptParserException("Internal error");
        }
    }

    /**
     * Gets the root of the parsed tree
     *
     * @return the document node
     */
    public DocumentNode getDocumentNode() {
        return this.root;
    }

    private void parse() {
        Stack<Node> s = new Stack<>();
        s.push(this.root);

        Lexer l = new Lexer(this.body);
        Token t;

        do {
            t = l.getNextToken();

            switch (t.getType()) {
                case TEXT -> s.peek().addChildNode(new TextNode((String) t.getValue()));
                case TAG_OPEN -> {
                    t = l.getNextToken();

                    switch (t.getType()) {
                        case SYMBOL -> s.peek().addChildNode(new EchoNode(ElementsHelper.getElementsInTag(l)));
                        case COMMAND -> {
                            switch ((String) t.getValue()) {
                                case "FOR" -> {
                                    Node forNode = ForCommandParser.parseCommand(l);

                                    s.peek().addChildNode(forNode);
                                    s.push(forNode);
                                }
                                case "END" -> {
                                    t = l.getNextToken();
                                    s.pop();
                                }
                                default -> throw new SmartScriptParserException("[Tag]: Command not implemented");
                            }
                        }
                        default -> throw new SmartScriptParserException("[General]: Tag type not supported");
                    }
                }
                case TAG_CLOSE -> throw new SmartScriptParserException("[General]: Expected a TagOpen before this close");
                case EOF -> {
                }
                default -> throw new SmartScriptParserException(String.format("[General]: Unexpected token: %s", t));
            }
        } while (t.getType() != TokenType.EOF);

        if (s.size() != 1) {
            throw new SmartScriptParserException("[General]: Some tag not closed");
        }
    }
}
