package hr.fer.zemris.java.custom.scripting.exec.Functions;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * The type Function context.
 *
 * @author franzekan
 */
public record FunctionContext(Stack<ValueWrapper> stack, RequestContext rc) {
}
