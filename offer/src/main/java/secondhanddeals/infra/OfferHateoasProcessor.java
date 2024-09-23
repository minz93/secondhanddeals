package secondhanddeals.infra;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import secondhanddeals.domain.*;

@Component
public class OfferHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Offer>> {

    @Override
    public EntityModel<Offer> process(EntityModel<Offer> model) {
        return model;
    }
}
