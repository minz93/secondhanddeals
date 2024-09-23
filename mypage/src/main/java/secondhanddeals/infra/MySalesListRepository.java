package secondhanddeals.infra;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import secondhanddeals.domain.*;

@RepositoryRestResource(
    collectionResourceRel = "mySalesLists",
    path = "mySalesLists"
)
public interface MySalesListRepository
    extends PagingAndSortingRepository<MySalesList, Long> {
    List<MySalesList> findByPostId(Long postId);

    void deleteByPostId(Long postId);
}
