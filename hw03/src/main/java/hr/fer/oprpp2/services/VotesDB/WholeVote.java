package hr.fer.oprpp2.services.VotesDB;

public record WholeVote(int id, String name, int votes, String youtubeLink) implements Comparable<WholeVote> {
    @Override
    public int compareTo(WholeVote o) {
        return Integer.compare(this.votes, o.votes);
    }
}
