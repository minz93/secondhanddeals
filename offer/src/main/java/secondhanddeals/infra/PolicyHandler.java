package secondhanddeals.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import secondhanddeals.config.kafka.KafkaProcessor;
import secondhanddeals.domain.*;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    OfferRepository offerRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='OfferAccepted'"
    )
    public void wheneverOfferAccepted_UpdateOfferStatus(
        @Payload OfferAccepted offerAccepted
    ) {
        OfferAccepted event = offerAccepted;
        System.out.println(
            "\n\n##### listener UpdateOfferStatus : " + offerAccepted + "\n\n"
        );

        // Sample Logic //
        Offer.updateOfferStatus(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='OfferRefused'"
    )
    public void wheneverOfferRefused_UpdateOfferStatus(
        @Payload OfferRefused offerRefused
    ) {
        OfferRefused event = offerRefused;
        System.out.println(
            "\n\n##### listener UpdateOfferStatus : " + offerRefused + "\n\n"
        );

        // Sample Logic //
        Offer.updateOfferStatus(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='NegotiationCanceled'"
    )
    public void wheneverNegotiationCanceled_UpdateOfferStatus(
        @Payload NegotiationCanceled negotiationCanceled
    ) {
        NegotiationCanceled event = negotiationCanceled;
        System.out.println(
            "\n\n##### listener UpdateOfferStatus : " +
            negotiationCanceled +
            "\n\n"
        );

        // Sample Logic //
        Offer.updateOfferStatus(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
