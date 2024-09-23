package secondhanddeals.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import secondhanddeals.domain.*;
import secondhanddeals.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class OfferRefused extends AbstractEvent {

    private Long offerId;
    private String userId;
    private Integer offeredPrice;
    private Long postId;
    private String offerStatus;

    public OfferRefused(Offer aggregate) {
        super(aggregate);
    }

    public OfferRefused() {
        super();
    }
}
//>>> DDD / Domain Event
