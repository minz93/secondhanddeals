package secondhanddeals.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import secondhanddeals.infra.AbstractEvent;

@Data
public class DealReserved extends AbstractEvent {

    private Long dealId;
    private Long postId;
    private String userId;
    private String status;
    private Date updateDt;
    private Integer price;
    private Long offerId;
}
