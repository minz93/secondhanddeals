package secondhanddeals.infra;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import secondhanddeals.domain.*;

@RepositoryRestResource(collectionResourceRel = "myPages", path = "myPages")
public interface MyPageRepository
    extends PagingAndSortingRepository<MyPage, Long> {
    List<MyPage> findByPostId(Long postId);

    void deleteByPostId(Long postId);
}
