package secondhanddeals.infra;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import secondhanddeals.config.kafka.KafkaProcessor;
import secondhanddeals.domain.*;

@Service
public class MySalesListViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private MySalesListRepository mySalesListRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPostWrote_then_CREATE_1(@Payload PostWrote postWrote) {
        try {
            if (!postWrote.validate()) return;

            // view 객체 생성
            MySalesList mySalesList = new MySalesList();
            // view 객체에 이벤트의 Value 를 set 함
            mySalesList.setPostId(postWrote.getPostId());
            mySalesList.setUserId(postWrote.getUserId());
            mySalesList.setStatus(postWrote.getStatus());
            mySalesList.setCreateDt(postWrote.getCreateDt());
            mySalesList.setPrice(postWrote.getPrice());
            mySalesList.setPhotos(postWrote.getPhotos());
            mySalesList.setGoods(postWrote.getGoods());
            // view 레파지 토리에 save
            mySalesListRepository.save(mySalesList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPostEdited_then_UPDATE_1(@Payload PostEdited postEdited) {
        try {
            if (!postEdited.validate()) return;
            // view 객체 조회

            List<MySalesList> mySalesListList = mySalesListRepository.findByPostId(
                postEdited.getPostId()
            );
            for (MySalesList mySalesList : mySalesListList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                mySalesList.setUpdateDt(postEdited.getUpdateDt());
                mySalesList.setPrice(postEdited.getPrice());
                mySalesList.setPhotos(postEdited.getPhotos());
                mySalesList.setGoods(postEdited.getGoods());
                // view 레파지 토리에 save
                mySalesListRepository.save(mySalesList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenDealReserved_then_UPDATE_2(
        @Payload DealReserved dealReserved
    ) {
        try {
            if (!dealReserved.validate()) return;
            // view 객체 조회

            List<MySalesList> mySalesListList = mySalesListRepository.findByPostId(
                dealReserved.getPostId()
            );
            for (MySalesList mySalesList : mySalesListList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                mySalesList.setStatus(dealReserved.getStatus());
                mySalesList.setPrice(dealReserved.getPrice());
                mySalesList.setUpdateDt(dealReserved.getUpdateDt());
                // view 레파지 토리에 save
                mySalesListRepository.save(mySalesList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenDealCanceled_then_UPDATE_3(
        @Payload DealCanceled dealCanceled
    ) {
        try {
            if (!dealCanceled.validate()) return;
            // view 객체 조회

            List<MySalesList> mySalesListList = mySalesListRepository.findByPostId(
                dealCanceled.getPostId()
            );
            for (MySalesList mySalesList : mySalesListList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                mySalesList.setStatus(dealCanceled.getStatus());
                mySalesList.setUpdateDt(dealCanceled.getUpdateDt());
                // view 레파지 토리에 save
                mySalesListRepository.save(mySalesList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenDealEnded_then_UPDATE_4(@Payload DealEnded dealEnded) {
        try {
            if (!dealEnded.validate()) return;
            // view 객체 조회

            List<MySalesList> mySalesListList = mySalesListRepository.findByPostId(
                dealEnded.getPostId()
            );
            for (MySalesList mySalesList : mySalesListList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                mySalesList.setStatus(dealEnded.getStatus());
                mySalesList.setUpdateDt(dealEnded.getUpdateDt());
                // view 레파지 토리에 save
                mySalesListRepository.save(mySalesList);
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
            mySalesListRepository.deleteByPostId(postDeleted.getPostId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
