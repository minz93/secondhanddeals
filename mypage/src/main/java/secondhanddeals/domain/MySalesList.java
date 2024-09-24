package secondhanddeals.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Date;
import lombok.Data;
import java.time.LocalDate;


//<<< EDA / CQRS
@Entity
@Table(name="MySalesList_table")
@Data
public class MySalesList {

        private String userId;
        private Long postId;
        private String status;
        private Date createDt;
        private Date updateDt;
        private Integer price;
        private List&lt;String&gt; photos;
        private String goods;
        @Id
        //@GeneratedValue(strategy=GenerationType.AUTO)
        private Long id;


}
