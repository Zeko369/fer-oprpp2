package hr.fer.oprpp2.dao.jpa;

import lombok.SneakyThrows;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

@WebFilter("/servlet/*")
public class JPAFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            chain.doFilter(request, response);
        } finally {
            JPAEMProvider.close();
        }
    }

    @Override
    public void destroy() {
    }

}
