package hr.fer.oprpp2.dao.sql;

import hr.fer.oprpp2.dao.DAO;
import hr.fer.oprpp2.dao.DAOException;
import hr.fer.oprpp2.dao.SQLConnectionProvider;
import hr.fer.oprpp2.model.Poll;
import hr.fer.oprpp2.model.PollOption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Sqldao.
 *
 * @author franzekan
 */
public class SQLDAO implements DAO {
    @Override
    public List<Poll> getPolls() {
        List<Poll> polls = new ArrayList<>();
        Connection con = SQLConnectionProvider.getConnection();
        PreparedStatement pst;

        try {
            pst = con.prepareStatement("select id, title, message from polls order by id");
            try {
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs != null && rs.next()) {
                        polls.add(new Poll(rs.getInt(1), rs.getString(2), rs.getString(3)));
                    }
                }
            } finally {
                try {
                    pst.close();
                } catch (Exception ignored) {
                }
            }
        } catch (Exception ex) {
            throw new DAOException("Error fetching all polls", ex);
        }

        return polls;
    }

    @Override
    public List<PollOption> getPollOptions(Integer id) {
        List<PollOption> pollOptions = new ArrayList<>();
        Connection con = SQLConnectionProvider.getConnection();
        PreparedStatement pst;

        try {
            pst = con.prepareStatement("select id, title, link, poll_id, votes from poll_options where poll_id = ? order by id ");
            pst.setInt(1, id);

            try {
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs != null && rs.next()) {
                        pollOptions.add(
                                new PollOption(
                                        rs.getInt(1),
                                        rs.getString(2),
                                        rs.getString(3),
                                        rs.getInt(4),
                                        rs.getLong(5)
                                )
                        );
                    }
                }
            } finally {
                try {
                    pst.close();
                } catch (Exception ignored) {
                }
            }
        } catch (Exception ex) {
            throw new DAOException("Error fetching all pollOptions", ex);
        }

        return pollOptions;
    }

    @Override
    public void vote(Integer optionId) {
        Connection con = SQLConnectionProvider.getConnection();
        PreparedStatement pst;

        try {
            pst = con.prepareStatement("update poll_options set votes = votes + 1 where id = ?");
            pst.setInt(1, optionId);

            try {
                pst.executeUpdate();
            } finally {
                try {
                    pst.close();
                } catch (Exception ignored) {
                }
            }
        } catch (Exception ex) {
            throw new DAOException("Error voting", ex);
        }
    }
}
