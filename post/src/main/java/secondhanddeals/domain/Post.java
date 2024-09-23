package secondhanddeals.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import secondhanddeals.PostApplication;
import secondhanddeals.domain.PostDeleted;
import secondhanddeals.domain.PostEdited;
import secondhanddeals.domain.PostHided;
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

    @ElementCollection
    private List<String> photos;

    private Date updateDt;

    @PrePersist
    public void prePersist(){
        if(this.status == null)
            this.status = "Created";
    }

    @PostPersist
    public void onPostPersist() {
        PostWrote postWrote = new PostWrote(this);
        postWrote.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate() {
        PostEdited postEdited = new PostEdited(this);
        postEdited.publishAfterCommit();

        PostHided postHided = new PostHided(this);
        postHided.publishAfterCommit();

        StatusUpdated statusUpdated = new StatusUpdated(this);
        statusUpdated.publishAfterCommit();
    }

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

    public void hidePost() {
        //implement business logic here:

        PostHided postHided = new PostHided(this);
        postHided.publishAfterCommit();
    }

    //<<< Clean Arch / Port Method
    public static void updateStatus(DealEnded dealEnded) {
        repository().findById(Long.valueOf(dealEnded.getPostId())).ifPresent(post->{
            post.setStatus("dealEnded");
            repository().save(post);
        });
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void updateStatus(DealReserved dealReserved) {
        repository().findById(Long.valueOf(dealReserved.getPostId())).ifPresent(post->{
            post.setStatus("dealReserved");
            repository().save(post);
        });
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void updateStatus(DealCanceled dealCanceled) {
        repository().findById(Long.valueOf(dealCanceled.getPostId())).ifPresent(post->{
            post.setStatus("Created");
            repository().save(post);
        });
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void updateStatus(PostHided postHided) {
        repository().findById(Long.valueOf(postHided.getPostId())).ifPresent(post->{
            post.setStatus("postHided");
            repository().save(post);
        });
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
