package hr.fer.oprpp2.filter;

import hr.fer.oprpp2.dao.SQLConnectionProvider;
import hr.fer.oprpp2.listener.InitListener;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.sql.DataSource;

@WebFilter(filterName = "f1", urlPatterns = {"/voting/*"})
public class ConnectionSetterFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        DataSource ds = (DataSource) request.getServletContext().getAttribute(InitListener.DB_CONNECTION_ATTRIBUTE);
        Connection con;
        try {
            con = ds.getConnection();
        } catch (SQLException e) {
            throw new IOException("DB not available", e);
        }

        SQLConnectionProvider.setConnection(con);

        try {
            chain.doFilter(request, response);
        } finally {
            SQLConnectionProvider.setConnection(null);

            try {
                con.close();
            } catch (SQLException ignored) {
            }
        }
    }

}
