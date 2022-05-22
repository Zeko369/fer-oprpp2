package hr.fer.oprpp2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Poll {
    private Integer id;
    private String title;
    private String message;

    public Poll(String title, String message) {
        this.title = title;
        this.message = message;
    }
}
