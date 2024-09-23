package secondhanddeals.infra;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import secondhanddeals.domain.*;

@Component
public class DealHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Deal>> {

    @Override
    public EntityModel<Deal> process(EntityModel<Deal> model) {
        return model;
    }
}
