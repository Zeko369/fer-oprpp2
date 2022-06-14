package hr.fer.oprpp2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Poll.
 *
 * @author franzekan
 */
@Getter
@Setter
@AllArgsConstructor
public class Poll {
    private Integer id;
    private String title;
    private String message;

    /**
     * Instantiates a new Poll.
     *
     * @param title   the title
     * @param message the message
     */
    public Poll(String title, String message) {
        this.title = title;
        this.message = message;
    }
}
