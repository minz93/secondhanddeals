package secondhanddeals.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import secondhanddeals.domain.*;
import secondhanddeals.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class DealEnded extends AbstractEvent {

    private Long offerId;
    private Long postId;
    private String userId;
    private String status;
    private Date updateDt;
    private Integer price;

    public DealEnded(Deal aggregate) {
        super(aggregate);
    }

    public DealEnded() {
        super();
    }
}
//>>> DDD / Domain Event
