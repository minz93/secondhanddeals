package secondhanddeals.domain;

import java.util.*;
import lombok.*;
import secondhanddeals.domain.*;
import secondhanddeals.infra.AbstractEvent;

@Data
@ToString
public class DealOffered extends AbstractEvent {

    private Long offerId;
    private String userId;
    private Integer price;
    private Long postId;
    private String offerStatus;
    private String offerType;
}
