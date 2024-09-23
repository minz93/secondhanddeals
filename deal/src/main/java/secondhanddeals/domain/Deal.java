package secondhanddeals.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import secondhanddeals.DealApplication;
import secondhanddeals.domain.DealCanceled;
import secondhanddeals.domain.DealEnded;
import secondhanddeals.domain.DealReserved;
import secondhanddeals.domain.NegotiationCanceled;

@Entity
@Table(name = "Deal_table")
@Data
//<<< DDD / Aggregate Root
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long offerId;

    private Long postId;

    private String userId;

    private String status;

    private Date updateDt;

    private Integer price;

    @PostUpdate
    public void onPostUpdate() {
        DealReserved dealReserved = new DealReserved(this);
        dealReserved.publishAfterCommit();

        DealCanceled dealCanceled = new DealCanceled(this);
        dealCanceled.publishAfterCommit();

        DealEnded dealEnded = new DealEnded(this);
        dealEnded.publishAfterCommit();

        NegotiationCanceled negotiationCanceled = new NegotiationCanceled(this);
        negotiationCanceled.publishAfterCommit();
    }

    @PreUpdate
    public void onPreUpdate() {}

    public static DealRepository repository() {
        DealRepository dealRepository = DealApplication.applicationContext.getBean(
            DealRepository.class
        );
        return dealRepository;
    }

    public void reserveDeal() {
        //implement business logic here:

        DealReserved dealReserved = new DealReserved(this);
        dealReserved.publishAfterCommit();
    }

    public void cancelDeal() {
        //implement business logic here:

        DealCanceled dealCanceled = new DealCanceled(this);
        dealCanceled.publishAfterCommit();
    }

    public void endDeal() {
        //implement business logic here:

        DealEnded dealEnded = new DealEnded(this);
        dealEnded.publishAfterCommit();
    }
}
//>>> DDD / Aggregate Root
