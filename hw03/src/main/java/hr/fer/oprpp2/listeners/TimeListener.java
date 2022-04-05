package hr.fer.oprpp2.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.TimeZone;

@WebListener
public class TimeListener implements ServletRequestListener, ServletContextListener {
    private static final String TIME_SINCE_STARTED_KEY = "TIME_SINCE_STARTED";
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("DD HH mm ss SS");
    private long start_time = -1;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        this.start_time = System.currentTimeMillis();
        dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        String tmp = dateFormatter.format(new Date(System.currentTimeMillis() - this.start_time));
        servletRequestEvent.getServletRequest().setAttribute(TIME_SINCE_STARTED_KEY, tmp);
    }

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        servletRequestEvent.getServletRequest().removeAttribute(TIME_SINCE_STARTED_KEY);
    }
}
