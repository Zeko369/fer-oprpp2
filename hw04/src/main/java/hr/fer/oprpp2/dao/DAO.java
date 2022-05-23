package hr.fer.oprpp2.dao;

import hr.fer.oprpp2.model.Poll;
import hr.fer.oprpp2.model.PollOption;

import java.util.List;

/**
 * The interface Dao.
 *
 * @author franzekan
 */
public interface DAO {
    /**
     * Gets polls.
     *
     * @return the polls
     */
    List<Poll> getPolls();

    /**
     * Gets poll options.
     *
     * @param id the id
     * @return the poll options
     */
    List<PollOption> getPollOptions(Integer id);

    /**
     * Vote.
     *
     * @param optionId the option id
     */
    void vote(Integer optionId);
}
