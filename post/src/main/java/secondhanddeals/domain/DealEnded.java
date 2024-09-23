package secondhanddeals.domain;

import java.util.*;
import lombok.*;
import secondhanddeals.domain.*;
import secondhanddeals.infra.AbstractEvent;

@Data
@ToString
public class DealEnded extends AbstractEvent {

    private Long dealId;
    private Long postId;
    private String userId;
    private String status;
    private Date updateDt;
    private Integer price;
    private Long offerId;
}
