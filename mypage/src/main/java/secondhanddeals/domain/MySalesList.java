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

        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        private String userId;
        private Long postId;
        private String status;
        private Date createDt;
        private Date updateDt;
        private Integer price;
        private List<String> photos;
        private String goods;


}
