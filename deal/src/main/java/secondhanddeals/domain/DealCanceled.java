package secondhanddeals.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import secondhanddeals.domain.*;
import secondhanddeals.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class DealCanceled extends AbstractEvent {

    private Long dealId;
    private Long postId;
    private String userId;
    private String status;
    private Date updateDt;
    private Long offerId;

    public DealCanceled(Deal aggregate) {
        super(aggregate);
    }

    public DealCanceled() {
        super();
    }
}
//>>> DDD / Domain Event
