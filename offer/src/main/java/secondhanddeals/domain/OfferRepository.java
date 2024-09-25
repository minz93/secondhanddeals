package secondhanddeals.domain;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import secondhanddeals.domain.*;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "offers", path = "offers")
public interface OfferRepository
    extends PagingAndSortingRepository<Offer, Long> {
        List<Offer> findByPostId(Long postId);
    }
