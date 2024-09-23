package secondhanddeals.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import secondhanddeals.domain.*;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "deals", path = "deals")
public interface DealRepository
    extends PagingAndSortingRepository<Deal, Long> {}
