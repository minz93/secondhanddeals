package secondhanddeals.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import secondhanddeals.domain.*;
import secondhanddeals.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class DealOffered extends AbstractEvent {

    private Long offerId;
    private String userId;
    private Integer price;
    private Long postId;
    private String offerStatus;
    private String offerType;

    public DealOffered(Offer aggregate) {
        super(aggregate);
    }

    public DealOffered() {
        super();
    }
}
//>>> DDD / Domain Event
