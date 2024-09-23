package secondhanddeals.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import secondhanddeals.infra.AbstractEvent;

@Data
public class PostDeleted extends AbstractEvent {

    private Long postId;
    private String userId;
}
