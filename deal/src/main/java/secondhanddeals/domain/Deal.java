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
    private Long dealId;

    private Long postId;

    private String userId;

    private String status;

    private Date updateDt;

    private Integer price;

    private Long offerId;

    @PostPersist
    public void onPostPersist() {
        System.out.println(this.status);
        if(this.status.equals("dealReserved")) {
            DealReserved dealReserved = new DealReserved(this);
            dealReserved.publishAfterCommit();
        }
    }

    @PostUpdate
    public void onPostUpdate() {
        if(this.status.equals("dealCanceled")) {
            DealCanceled dealCanceled = new DealCanceled(this);
            dealCanceled.publishAfterCommit();
        } else if(this.status.equals("dealEnded")) {
            DealEnded dealEnded = new DealEnded(this);
            dealEnded.publishAfterCommit();
            
            repository().findById(Long.valueOf(dealEnded.getDealId())).ifPresent(deal->{
                NegotiationCanceled negotiationCanceled = new NegotiationCanceled(deal);
                negotiationCanceled.setOfferId(dealEnded.getOfferId());
                negotiationCanceled.publishAfterCommit();
            });

            // repository().findById(Long.valueOf(strPostId)).ifPresent(offer->{
            //     System.out.println("진입!!!!");
            //     NegotiationCanceled negotiationCanceled = new NegotiationCanceled(offer);
            //     negotiationCanceled.setOfferId(dealEnded.getOfferId());
            //     negotiationCanceled.publishAfterCommit();
            // });
        }
    }

    @PreUpdate
    public void onPreUpdate() {}

    public static DealRepository repository() {
        DealRepository dealRepository = DealApplication.applicationContext.getBean(
            DealRepository.class
        );
        return dealRepository;
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

    //<<< Clean Arch / Port Method
    public static void reserveDeal(DealOffered dealOffered) {
        repository().findById(Long.valueOf(dealOffered.getPostId())).ifPresent(deal->{
            deal.setStatus("dealReserved");
            repository().save(deal);
        });
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
