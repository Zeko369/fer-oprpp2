package hr.fer.oprpp2.services.VotesDB;

import java.util.Objects;

public final class VoteResult {
    private final int id;
    private int votes;

    public VoteResult(int id, int votes) {
        this.id = id;
        this.votes = votes;
    }

    public int id() {
        return this.id;
    }

    public int votes() {
        return this.votes;
    }

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
