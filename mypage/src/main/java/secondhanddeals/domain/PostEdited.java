package secondhanddeals.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import secondhanddeals.infra.AbstractEvent;

@Data
public class PostEdited extends AbstractEvent {

    private Long postId;
    private String userId;
    private Integer price;
    private Date updateDt;
    private String goods;
}
