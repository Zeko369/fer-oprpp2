package hr.fer.oprpp2.dao.sql;

import hr.fer.oprpp2.model.Poll;
import hr.fer.oprpp2.model.PollOption;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record Seed(Connection connection) {
    private static final List<String> tables = List.of("polls", "poll_options");

    public boolean hasAllTables() throws SQLException {
        ResultSet meta = this.connection.getMetaData().getTables(null, "public", null, null);
        Set<String> tableNames = new HashSet<>();
        while (meta != null && meta.next()) {
            tableNames.add(meta.getString(3));
        }

        return tableNames.containsAll(tables);
    }

    public void createTables() throws SQLException {
        this.connection.createStatement().execute("DROP TABLE IF EXISTS poll_options CASCADE");
        this.connection.createStatement().execute("DROP TABLE IF EXISTS polls CASCADE");

        this.connection.createStatement().execute("CREATE TABLE polls (" +
                "id SERIAL PRIMARY KEY," +
                "title VARCHAR(255) NOT NULL," +
                "message TEXT NOT NULL)"
        );

        this.connection.createStatement().execute("CREATE TABLE poll_options (" +
                "id SERIAL PRIMARY KEY," +
                "poll_id INTEGER NOT NULL," +
                "title VARCHAR(255) NOT NULL," +
                "link VARCHAR(255) NOT NULL," +
                "votes BIGINT NOT NULL," +
                "FOREIGN KEY (poll_id) REFERENCES polls(id))"
        );
    }

    public boolean hasData() throws SQLException {
        ResultSet meta = this.connection.createStatement().executeQuery("SELECT * FROM polls");
        return meta.next();
    }

    public void generatePolls() throws SQLException {
        List<PollOption> polls = List.of(
                new PollOption(1, "The Beatles", "https://www.youtube.com/watch?v=z9ypq6_5bsg", 1, 0L),
                new PollOption(2, "The Platters", "https://www.youtube.com/watch?v=H2di83WAOhU", 1, 0L),
                new PollOption(3, "The Beach Boys", "https://www.youtube.com/watch?v=2s4slliAtQU", 1, 0L),
                new PollOption(4, "The Four Seasons", "https://www.youtube.com/watch?v=y8yvnqHmFds", 1, 0L),
                new PollOption(5, "The Marcels", "https://www.youtube.com/watch?v=qoi3TH59ZEs", 1, 0L),
                new PollOption(6, "The Everly Brothers", "https://www.youtube.com/watch?v=tbU3zdAgiX8", 1, 0L),
                new PollOption(7, "The Mamas And The Papas", "https://www.youtube.com/watch?v=N-aK6JnyFmk", 1, 0L)
        );

        PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO polls (title, message) VALUES (?, ?)");
        preparedStatement.setString(1, "Music");
        preparedStatement.setString(2, "Which of these songs is your favorite?");
        preparedStatement.execute();

        this.connection.setAutoCommit(false);
        PreparedStatement preparedStatementOption = this.connection.prepareStatement("INSERT INTO poll_options (poll_id, title, link, votes) VALUES (?, ?, ?, ?)");
        for (PollOption poll : polls) {
            preparedStatementOption.setInt(1, poll.getPollId());
            preparedStatementOption.setString(2, poll.getTitle());
            preparedStatementOption.setString(3, poll.getLink());
            preparedStatementOption.setLong(4, poll.getVotesCount());
            preparedStatementOption.addBatch();
        }

        preparedStatementOption.executeBatch();
        this.connection.commit();
    }
}
