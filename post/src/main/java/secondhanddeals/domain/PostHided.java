package secondhanddeals.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import secondhanddeals.domain.*;
import secondhanddeals.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PostHided extends AbstractEvent {

    private Long postId;
    private String userId;
    private String status;
    private Integer price;
    private Date updateDt;

    public PostHided(Post aggregate) {
        super(aggregate);
    }

    public PostHided() {
        super();
    }
}
//>>> DDD / Domain Event
