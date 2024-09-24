package secondhanddeals.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import secondhanddeals.infra.AbstractEvent;

@Data
public class PostWrote extends AbstractEvent {

    private Long postId;
    private String userId;
    private Date createDt;
    private Integer price;
    private String address;
    private String status;
    private String goods;
}
