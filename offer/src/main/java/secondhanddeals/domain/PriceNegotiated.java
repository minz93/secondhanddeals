package secondhanddeals.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import secondhanddeals.domain.*;
import secondhanddeals.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PriceNegotiated extends AbstractEvent {

    private Long offerId;
    private String userId;
    private Integer price;
    private Integer offeredPrice;
    private Long postId;
    private String offerStatus;
    private String offerType;

    public PriceNegotiated(Offer aggregate) {
        super(aggregate);
    }

    public PriceNegotiated() {
        super();
    }
}
//>>> DDD / Domain Event
