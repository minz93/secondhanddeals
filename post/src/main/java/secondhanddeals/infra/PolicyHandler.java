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
    PostRepository postRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='DealEnded'"
    )
    public void wheneverDealEnded_UpdateStatus(@Payload DealEnded dealEnded) {
        DealEnded event = dealEnded;
        System.out.println(
            "\n\n##### listener UpdateStatus : " + dealEnded + "\n\n"
        );

        // Sample Logic //
        Post.updateStatus(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='DealReserved'"
    )
    public void wheneverDealReserved_UpdateStatus(
        @Payload DealReserved dealReserved
    ) {
        DealReserved event = dealReserved;
        System.out.println(
            "\n\n##### listener UpdateStatus : " + dealReserved + "\n\n"
        );

        // Sample Logic //
        Post.updateStatus(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='DealCanceled'"
    )
    public void wheneverDealCanceled_UpdateStatus(
        @Payload DealCanceled dealCanceled
    ) {
        DealCanceled event = dealCanceled;
        System.out.println(
            "\n\n##### listener UpdateStatus : " + dealCanceled + "\n\n"
        );

        // Sample Logic //
        Post.updateStatus(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
