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
        if(this.offerType.equals("dealOffered")) {
            DealOffered dealOffered = new DealOffered(this);
            dealOffered.publishAfterCommit();
        } else if(this.offerType.equals("priceNegotiated")) {
            PriceNegotiated priceNegotiated = new PriceNegotiated(this);
            priceNegotiated.publishAfterCommit();
        } 
    }

    @PostUpdate
    public void onPostUpdate() {
    
        if(this.offerStatus.equals("offerAccepted")) {
            OfferAccepted offerAccepted = new OfferAccepted(this);
            offerAccepted.publishAfterCommit();
        } else if(this.offerStatus.equals("offerRefused")) {
            OfferRefused offerRefused = new OfferRefused(this);
            offerRefused.publishAfterCommit();
        }
        OfferStatusUpdated offerStatusUpdated = new OfferStatusUpdated(this);
        offerStatusUpdated.publishAfterCommit();

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
        repository().findById(Long.valueOf(offerAccepted.getOfferId())).ifPresent(offer->{
            offer.setOfferStatus("offerAccepted");
            repository().save(offer);
        });
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void updateOfferStatus(OfferRefused offerRefused) {
        repository().findById(Long.valueOf(offerRefused.getOfferId())).ifPresent(offer->{
            offer.setOfferStatus("offerRefused");
            repository().save(offer);
        });
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void updateOfferStatus(
        NegotiationCanceled negotiationCanceled
    ) {
        List<Offer> offers = repository().findByPostId(negotiationCanceled.getPostId());

        for (Offer offer : offers) {
            offer.setOfferStatus("dealEnded");
        }

        repository().saveAll(offers);

        // repository().findById(Long.valueOf(negotiationCanceled.getOfferId())).ifPresent(offer->{
        //     offer.setOfferStatus("dealEnded");
        //     repository().save(offer);
        // });
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
