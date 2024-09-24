package secondhanddeals.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import secondhanddeals.PostApplication;
import secondhanddeals.domain.PostDeleted;
import secondhanddeals.domain.PostEdited;
import secondhanddeals.domain.PostWrote;
import secondhanddeals.domain.StatusUpdated;

@Entity
@Table(name = "Post_table")
@Data
//<<< DDD / Aggregate Root
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;

    private String userId;

    private String status;

    private Date createDt;

    private Integer price;

    private String address;

    private Date updateDt;

    private String goods;

    @PostPersist
    public void onPostPersist() {
        PostWrote postWrote = new PostWrote(this);
        postWrote.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate() {
        PostEdited postEdited = new PostEdited(this);
        postEdited.publishAfterCommit();

        StatusUpdated statusUpdated = new StatusUpdated(this);
        statusUpdated.publishAfterCommit();
    }

    @PreUpdate
    public void onPreUpdate() {}

    @PreRemove
    public void onPreRemove() {
        PostDeleted postDeleted = new PostDeleted(this);
        postDeleted.publishAfterCommit();
    }

    public static PostRepository repository() {
        PostRepository postRepository = PostApplication.applicationContext.getBean(
            PostRepository.class
        );
        return postRepository;
    }

    public void writePost() {
        //implement business logic here:

        PostWrote postWrote = new PostWrote(this);
        postWrote.publishAfterCommit();
    }

    public void editPost() {
        //implement business logic here:

        PostEdited postEdited = new PostEdited(this);
        postEdited.publishAfterCommit();
    }

    public void deletePost() {
        //implement business logic here:

        PostDeleted postDeleted = new PostDeleted(this);
        postDeleted.publishAfterCommit();
    }

    //<<< Clean Arch / Port Method
    public static void updateStatus(DealEnded dealEnded) {
        //implement business logic here:

        /** Example 1:  new item 
        Post post = new Post();
        repository().save(post);

        StatusUpdated statusUpdated = new StatusUpdated(post);
        statusUpdated.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(dealEnded.get???()).ifPresent(post->{
            
            post // do something
            repository().save(post);

            StatusUpdated statusUpdated = new StatusUpdated(post);
            statusUpdated.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void updateStatus(DealReserved dealReserved) {
        //implement business logic here:

        /** Example 1:  new item 
        Post post = new Post();
        repository().save(post);

        StatusUpdated statusUpdated = new StatusUpdated(post);
        statusUpdated.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(dealReserved.get???()).ifPresent(post->{
            
            post // do something
            repository().save(post);

            StatusUpdated statusUpdated = new StatusUpdated(post);
            statusUpdated.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void updateStatus(DealCanceled dealCanceled) {
        //implement business logic here:

        /** Example 1:  new item 
        Post post = new Post();
        repository().save(post);

        StatusUpdated statusUpdated = new StatusUpdated(post);
        statusUpdated.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(dealCanceled.get???()).ifPresent(post->{
            
            post // do something
            repository().save(post);

            StatusUpdated statusUpdated = new StatusUpdated(post);
            statusUpdated.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
