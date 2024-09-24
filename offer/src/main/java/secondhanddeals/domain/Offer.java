package secondhanddeals.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import secondhanddeals.OfferApplication;
import secondhanddeals.domain.DealOffered;
import secondhanddeals.domain.OfferAccepted;
import secondhanddeals.domain.OfferRefused;
import secondhanddeals.domain.OfferStatusUpdated;
import secondhanddeals.domain.PriceNegotiated;

@Entity
@Table(name = "Offer_table")
@Data
//<<< DDD / Aggregate Root
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long offerId;

    private String userId;

    private Integer price;

    private Integer offeredPrice;

    private Long postId;

    private String offerStatus;

    private String offerType;

    @PostPersist
    public void onPostPersist() {
        DealOffered dealOffered = new DealOffered(this);
        dealOffered.publishAfterCommit();

        PriceNegotiated priceNegotiated = new PriceNegotiated(this);
        priceNegotiated.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate() {
        OfferAccepted offerAccepted = new OfferAccepted(this);
        offerAccepted.publishAfterCommit();

        OfferStatusUpdated offerStatusUpdated = new OfferStatusUpdated(this);
        offerStatusUpdated.publishAfterCommit();

        OfferRefused offerRefused = new OfferRefused(this);
        offerRefused.publishAfterCommit();
    }

    @PreUpdate
    public void onPreUpdate() {}

    public static OfferRepository repository() {
        OfferRepository offerRepository = OfferApplication.applicationContext.getBean(
            OfferRepository.class
        );
        return offerRepository;
    }

    public void offerDeal() {
        //implement business logic here:

        DealOffered dealOffered = new DealOffered(this);
        dealOffered.publishAfterCommit();
    }

    public void nagotiatePrice() {
        //implement business logic here:

        PriceNegotiated priceNegotiated = new PriceNegotiated(this);
        priceNegotiated.publishAfterCommit();
    }

    public void acceptOffer() {
        //implement business logic here:

        OfferAccepted offerAccepted = new OfferAccepted(this);
        offerAccepted.publishAfterCommit();
    }

    public void refuseOffer() {
        //implement business logic here:

        OfferRefused offerRefused = new OfferRefused(this);
        offerRefused.publishAfterCommit();
    }

    //<<< Clean Arch / Port Method
    public static void updateOfferStatus(OfferAccepted offerAccepted) {
        //implement business logic here:

        /** Example 1:  new item 
        Offer offer = new Offer();
        repository().save(offer);

        OfferStatusUpdated offerStatusUpdated = new OfferStatusUpdated(offer);
        offerStatusUpdated.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(offerAccepted.get???()).ifPresent(offer->{
            
            offer // do something
            repository().save(offer);

            OfferStatusUpdated offerStatusUpdated = new OfferStatusUpdated(offer);
            offerStatusUpdated.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void updateOfferStatus(OfferRefused offerRefused) {
        //implement business logic here:

        /** Example 1:  new item 
        Offer offer = new Offer();
        repository().save(offer);

        OfferStatusUpdated offerStatusUpdated = new OfferStatusUpdated(offer);
        offerStatusUpdated.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(offerRefused.get???()).ifPresent(offer->{
            
            offer // do something
            repository().save(offer);

            OfferStatusUpdated offerStatusUpdated = new OfferStatusUpdated(offer);
            offerStatusUpdated.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void updateOfferStatus(
        NegotiationCanceled negotiationCanceled
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Offer offer = new Offer();
        repository().save(offer);

        OfferStatusUpdated offerStatusUpdated = new OfferStatusUpdated(offer);
        offerStatusUpdated.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(negotiationCanceled.get???()).ifPresent(offer->{
            
            offer // do something
            repository().save(offer);

            OfferStatusUpdated offerStatusUpdated = new OfferStatusUpdated(offer);
            offerStatusUpdated.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
