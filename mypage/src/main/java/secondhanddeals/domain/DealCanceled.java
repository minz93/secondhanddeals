package secondhanddeals.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import secondhanddeals.infra.AbstractEvent;

@Data
public class DealCanceled extends AbstractEvent {

    private Long dealId;
    private Long postId;
    private String userId;
    private String status;
    private Date updateDt;
    private Long offerId;
}
