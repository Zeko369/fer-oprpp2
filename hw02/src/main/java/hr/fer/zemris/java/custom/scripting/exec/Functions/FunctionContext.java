package hr.fer.zemris.java.custom.scripting.exec.Functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

public record FunctionContext(Stack<Object> stack, RequestContext rc) {
}
