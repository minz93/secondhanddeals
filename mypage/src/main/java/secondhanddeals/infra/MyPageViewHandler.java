package secondhanddeals.infra;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import secondhanddeals.config.kafka.KafkaProcessor;
import secondhanddeals.domain.*;

@Service
public class MyPageViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private MyPageRepository myPageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPostWrote_then_CREATE_1(@Payload PostWrote postWrote) {
        try {
            if (!postWrote.validate()) return;

            // view 객체 생성
            MyPage myPage = new MyPage();
            // view 객체에 이벤트의 Value 를 set 함
            myPage.setPostId(postWrote.getPostId());
            myPage.setUserId(postWrote.getUserId());
            myPage.setStatus(postWrote.getStatus());
            myPage.setCreateDt(postWrote.getCreateDt());
            myPage.setPrice(postWrote.getPrice());
            myPage.setGoods(postWrote.getGoods());
            // view 레파지 토리에 save
            myPageRepository.save(myPage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenDealReserved_then_UPDATE_1(
        @Payload DealReserved dealReserved
    ) {
        try {
            if (!dealReserved.validate()) return;
            // view 객체 조회

            List<MyPage> myPageList = myPageRepository.findByPostId(
                dealReserved.getPostId()
            );
            for (MyPage myPage : myPageList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                myPage.setStatus(dealReserved.getStatus());
                myPage.setUpdateDt(dealReserved.getUpdateDt());
                myPage.setPrice(dealReserved.getPrice());
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenDealCanceled_then_UPDATE_2(
        @Payload DealCanceled dealCanceled
    ) {
        try {
            if (!dealCanceled.validate()) return;
            // view 객체 조회

            List<MyPage> myPageList = myPageRepository.findByPostId(
                dealCanceled.getPostId()
            );
            for (MyPage myPage : myPageList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                myPage.setStatus(dealCanceled.getStatus());
                myPage.setUpdateDt(dealCanceled.getUpdateDt());
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenDealEnded_then_UPDATE_3(@Payload DealEnded dealEnded) {
        try {
            if (!dealEnded.validate()) return;
            // view 객체 조회

            List<MyPage> myPageList = myPageRepository.findByPostId(
                dealEnded.getPostId()
            );
            for (MyPage myPage : myPageList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                myPage.setStatus(dealEnded.getStatus());
                myPage.setUpdateDt(dealEnded.getUpdateDt());
                myPage.setPrice(dealEnded.getPrice());
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPostEdited_then_UPDATE_4(@Payload PostEdited postEdited) {
        try {
            if (!postEdited.validate()) return;
            // view 객체 조회

            List<MyPage> myPageList = myPageRepository.findByPostId(
                postEdited.getPostId()
            );
            for (MyPage myPage : myPageList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                myPage.setUpdateDt(postEdited.getUpdateDt());
                myPage.setPrice(postEdited.getPrice());
                myPage.setGoods(postEdited.getGoods());
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPostDeleted_then_DELETE_1(
        @Payload PostDeleted postDeleted
    ) {
        try {
            if (!postDeleted.validate()) return;
            // view 레파지 토리에 삭제 쿼리
            myPageRepository.deleteByPostId(postDeleted.getPostId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
