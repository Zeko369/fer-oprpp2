package hr.fer.oprpp2.services.votesDB;

import java.util.Objects;

/**
 * The type Vote result.
 *
 * @author franzekan
 */
public final class VoteResult {
    private final int id;
    private int votes;

    /**
     * Instantiates a new Vote result.
     *
     * @param id    the id
     * @param votes the votes
     */
    public VoteResult(int id, int votes) {
        this.id = id;
        this.votes = votes;
    }

    /**
     * Id int.
     *
     * @return the int
     */
    public int id() {
        return this.id;
    }

    /**
     * Votes int.
     *
     * @return the int
     */
    public int votes() {
        return this.votes;
    }

    /**
     * Increment.
     */
    public void increment() {
        this.votes++;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (VoteResult) obj;
        return this.id == that.id &&
                this.votes == that.votes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.votes);
    }

    @Override
    public String toString() {
        return "VoteResult[" +
                "id=" + this.id + ", " +
                "votes=" + this.votes + ']';
    }
}
