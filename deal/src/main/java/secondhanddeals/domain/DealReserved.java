package secondhanddeals.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import secondhanddeals.domain.*;
import secondhanddeals.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class DealReserved extends AbstractEvent {

    private Long dealId;
    private Long postId;
    private String userId;
    private String status;
    private Date updateDt;
    private Integer price;
    private Long offerId;

    public DealReserved(Deal aggregate) {
        super(aggregate);
    }

    public DealReserved() {
        super();
    }
}
//>>> DDD / Domain Event
