package secondhanddeals.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import secondhanddeals.infra.AbstractEvent;

@Data
public class PostHided extends AbstractEvent {

    private Long postId;
    private String userId;
    private String status;
    private Integer price;
    private Date updateDt;
}
