package secondhanddeals.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import secondhanddeals.domain.*;
import secondhanddeals.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class OfferStatusUpdated extends AbstractEvent {

    private Long offerId;
    private Date updateDt;
    private String offerStatus;

    public OfferStatusUpdated(Offer aggregate) {
        super(aggregate);
    }

    public OfferStatusUpdated() {
        super();
    }
}
//>>> DDD / Domain Event
