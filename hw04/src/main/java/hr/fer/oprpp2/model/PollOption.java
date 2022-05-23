package hr.fer.oprpp2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PollOption {
    private Integer id;
    private String title;
    private String link;
    private Integer pollId;
    private Long votesCount;
}