package hr.fer.oprpp2.services.votesDB;

/**
 * The type Whole vote.
 *
 * @author franzekan
 */
public record WholeVote(int id, String name, int votes, String youtubeLink) implements Comparable<WholeVote> {
    @Override
    public int compareTo(WholeVote o) {
        // to have them sorted descending
        return Integer.compare(this.votes, o.votes) * -1;
    }
}
