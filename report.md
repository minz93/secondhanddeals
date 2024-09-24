![image](https://github.com/user-attachments/assets/38cf581d-492b-4633-84b2-ca69036b30d2)![image](https://github.com/minz93/secondhandtrading/blob/main/DaangnMarket_logo.png)
# 주제 - 중고거래

# Table of contents

- [중고거래](#---)
  - [서비스 시나리오](#서비스-시나리오)
  - [체크포인트](#체크포인트)
  - [분석/설계](#분석설계)
  - [구현:](#구현-)
    - [DDD 의 적용](#ddd-의-적용)
    - [폴리글랏 퍼시스턴스](#폴리글랏-퍼시스턴스)
    - [폴리글랏 프로그래밍](#폴리글랏-프로그래밍)
    - [동기식 호출 과 Fallback 처리](#동기식-호출-과-Fallback-처리)
    - [비동기식 호출 과 Eventual Consistency](#비동기식-호출-과-Eventual-Consistency)
  - [운영](#운영)
    - [CI/CD 설정](#cicd설정)
    - [동기식 호출 / 서킷 브레이킹 / 장애격리](#동기식-호출-서킷-브레이킹-장애격리)
    - [오토스케일 아웃](#오토스케일-아웃)
    - [무정지 재배포](#무정지-재배포)
  - [신규 개발 조직의 추가](#신규-개발-조직의-추가)

# 서비스 시나리오

기능적 요구사항
 - 핵심 기능
1. 판매자가 판매글을 작성한다.
2. 구매자가 물품을 구매하기 위해 판매자에게 메시지를 보낸다.
3. 메시지를 수신하면 알람이 발생한다.
4. 구매자가 거래를 희망하면 판매자는 게시글의 상태를 거래예정으로 변경한다.
5. 거래가 취소된 경우 게시글의 상태를 거래가능 상태로 변경한다.
6. 거래가 완료되면 게시글의 상태를 거래완료로 변경한다.
7. 판매자는 판매글을 수정할 수 있다.
8. 판매자는 판매글을 삭제할 수 있다.
9. 판매자는 판매글 목록을 조회할 수 있다.
   

 - 서브 기능
1. 구매자가 물품을 즐겨찾기에 등록한다.
2. 구매자가 물품을 즐겨찾기에서 삭제한다.
3. 구매자는 구매가격을 제안할 수 있다.
4. 가격제안이 접수되면 판매자는 가격제안 알람을 받는다.
6. 판매자는 제안된 가격을 보고 거래를 희망할 시 제안한 구매자에게 메시지를 보낸다.
7. 거래가 완료되면 거래 후기를 작성한다.
8. 구매자가 가격제안을 했으나 게시글이 거래완료 상태로 변경된 경우 제안은 자동 취소되어야한다.



비기능적 요구사항
1. 트랜잭션
    1. 거래가 완료된 게시글은 거래가 불가능해야한다. Sync 호출 
1. 장애격리
    1. 거래 기능이 수행되지 않더라도 게시글 기능은 365일 24시간 서비스될 수 있어야 한다.  Async (event-driven), Eventual Consistency
    1. 결제시스템이 과중되면 사용자를 잠시동안 받지 않고 결제를 잠시후에 하도록 유도한다.  Circuit breaker, fallback
1. 성능
    1. 판매자가 등록한 판매글을 판매게시글 목록(프론트엔드)에서 확인할 수 있어야한다. CQRS
    1. 메시지가 수신될 때마다 알림이 발생해야한다.  Event driven


# MSA 아키텍처 구성도

ㅇㅇㅇㅇ


# Event Storming

MSAEz 로 모델링한 이벤트스토밍 결과: https://www.msaez.io/#/181188513/storming/secondhandtrading

### 중고거래 이벤트 스토밍
![image](https://github.com/minz93/secondhandtrading/blob/main/event-storming.png)
1. post : 게시글 관리 기능
2. offer : 구매 요청 기능
3. deal : 거래 기능
4. mypage : 판매 목록 조회 기능

# 분산 트랜잭션 - Saga
1. post : 8082 port
### post 서비스 게시글 등록
```
$ http localhost:8088/posts userId="seller01" createDt="2024-09-24" goods="아이폰13 공기계" price=600000 address="방배동"
$ http localhost:8088/posts userId="seller02" createDt="2024-09-24" goods="무선마우스" price=10000 address="서초동"
# kafka consumer
{"eventType":"PostWrote","timestamp":1727139799825,"postId":1,"userId":"seller01","createDt":"2024-09-24T00:00:00.000+00:00","price":600000,"address":"방배동","photos":null,"status":"created","goods":"아이폰13 공기계"}
{"eventType":"PostWrote","timestamp":1727139847721,"postId":2,"userId":"seller02","createDt":"2024-09-24T00:00:00.000+00:00","price":10000,"address":"서초동","photos":null,"status":"created","goods":"무선마우스"}
```
![image](https://github.com/user-attachments/assets/317825d4-d58a-437c-8ccf-534e7a93cd88) ![image](https://github.com/user-attachments/assets/546fb10b-2776-4785-b5b5-0928ee2c162b)

2. offer : 8083 port
### offer 서비스 구매 요청
```
$ http localhost:8088/offers userId="buyer01" price=10000 postId=2 offerType="dealOffered"
$ http localhost:8088/offers userId="buyer02" price=600000 postId=1 offerType="priceNegotiated" offeredPrice=500000
$ http localhost:8088/offers userId="buyer03" price=600000 postId=1 offerType="priceNegotiated" offeredPrice=510000
```
![image](https://github.com/user-attachments/assets/974997b6-1f76-4ceb-99a4-4f772e262c7f) ![image](https://github.com/user-attachments/assets/50dd4ce0-4fe3-487c-b941-7a20a4f89e5f) ![image](https://github.com/user-attachments/assets/5d3e559a-c3b1-453a-9675-5f7d8ddf9d05)

### offer 서비스 가격제안 수락
```
$ http PATCH localhost:8088/offers/3 userId="buyer03" postId=1 offerStatus="offerAccepted" offeredPrice=510000
```
![image](https://github.com/user-attachments/assets/4073079a-a9d5-4823-9174-76fd8a819a9c)

3. deal : 8084 port
### deal 서비스 거래 예약
```
$ http localhost:8088/deals offerId=1 postId=2 userId="buyer01" price=10000 status="dealReserved" updateDt="2024-09-24"
$ http localhost:8088/deals offerId=3 postId=1 userId="buyer03" price=510000 status="dealReserved" updateDt="2024-09-24"
```
![image](https://github.com/user-attachments/assets/3bf9e6be-85fc-4a8c-9be8-b2b80513d02c) ![image](https://github.com/user-attachments/assets/74a5b1ae-1d7c-4048-8f72-da310c9d2bec)

### deal 서비스 거래 완료
```
$ http PATCH localhost:8088/deals/2 offeredId=3 postId=1 userId="buyer03" price=510000 status="dealEnded" updateDt="2024-09-25"
```
![image](https://github.com/user-attachments/assets/e3c42e8a-b12a-4401-a1b3-29e33d75b912)

### deal 서비스 거래 완료에 따른 가격제안 자동 취소
```

```
