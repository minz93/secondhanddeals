package secondhanddeals.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import secondhanddeals.domain.*;
import secondhanddeals.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PostWrote extends AbstractEvent {

    private Long postId;
    private String userId;
    private Date createDt;
    private Integer price;
    private String address;
    private String status;
    private String goods;

    public PostWrote(Post aggregate) {
        super(aggregate);
    }

    public PostWrote() {
        super();
    }
}
//>>> DDD / Domain Event
