package hr.fer.oprpp2.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
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
        // TODO: use a library for this
        String[] labelsSingle = {"day", "hour", "minute", "second", "millisecond"};
        String[] labelsMultiple = {"days", "hours", "minutes", "seconds", "milliseconds"};
        String tmp = dateFormatter.format(new Date(System.currentTimeMillis() - this.start_time));

        String[] parts = tmp.split(" ");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            int value = Integer.parseInt(parts[i]);
            if (i == 0) {
                // account for the fact that time starts on 1st and not 0th of January
                value -= 1;
            }

            sb.append(value);
            sb.append(" ");
            sb.append(value < 2 ? labelsSingle[i] : labelsMultiple[i]);
            sb.append(" ");
        }

        sb.append("ago");

        servletRequestEvent.getServletRequest().setAttribute(TIME_SINCE_STARTED_KEY, sb.toString());
    }

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        servletRequestEvent.getServletRequest().removeAttribute(TIME_SINCE_STARTED_KEY);
    }
}
