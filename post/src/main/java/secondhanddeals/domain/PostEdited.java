package secondhanddeals.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import secondhanddeals.domain.*;
import secondhanddeals.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PostEdited extends AbstractEvent {

    private Long postId;
    private String userId;
    private Integer price;
    private Date updateDt;
    private String goods;

    public PostEdited(Post aggregate) {
        super(aggregate);
    }

    public PostEdited() {
        super();
    }
}
//>>> DDD / Domain Event
