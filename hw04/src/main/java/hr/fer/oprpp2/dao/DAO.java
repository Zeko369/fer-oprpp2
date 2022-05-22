package hr.fer.oprpp2.dao;

import hr.fer.oprpp2.model.Poll;
import hr.fer.oprpp2.model.PollOption;

import java.util.List;

public interface DAO {
    List<Poll> getPolls();

    List<PollOption> getPollOptions(Long id);

    void vote(Long id, Long optionId);
}
