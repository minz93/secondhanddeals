![image](https://github.com/minz93/secondhandtrading/blob/main/DaangnMarket_logo.png)
# 주제 - 중고거래

# Table of contents

- [중고거래](#---)
  - [서비스 시나리오](#서비스-시나리오)
  - [구현:](#구현-)
    - [Event Storming](#Event-Storming)
    - [동기식 호출 과 Fallback 처리](#동기식-호출-과-Fallback-처리)
    - [비동기식 호출 과 Eventual Consistency](#비동기식-호출-과-Eventual-Consistency)
  - [운영](#운영)
    - [CI/CD 설정](#cicd설정)
    - [동기식 호출 / 서킷 브레이킹 / 장애격리](#동기식-호출-서킷-브레이킹-장애격리)
    - [오토스케일 아웃](#오토스케일-아웃)
    - [무정지 재배포](#무정지-재배포)

# 서비스 시나리오

기능적 요구사항
 - 핵심 기능
1. 판매자가 판매글을 작성한다.
2. 구매자가 거래 요청을 보낸다.
3. 판매자는 게시글의 상태를 거래예정으로 변경한다.
4. 거래가 취소된 경우 게시글의 상태를 거래가능 상태로 변경한다.
5. 거래가 완료되면 게시글의 상태를 거래완료로 변경한다.
6. 판매자는 판매글을 수정할 수 있다.
7. 판매자는 판매글을 삭제할 수 있다.
8. 판매자는 판매글 목록을 조회할 수 있다.
   

 - 서브 기능
1. 구매자는 구매가격을 제안할 수 있다.
2. 판매자는 제안된 가격을 수락할 수 있다.
3. 판매자는 제안된 가격을 거절할 수 있다.
4. 구매자가 가격제안을 했으나 게시글이 거래완료 상태로 변경된 경우 제안은 자동 취소되어야한다.



비기능적 요구사항
1. 트랜잭션
    1. 거래가 완료된 게시글은 거래가 불가능해야한다. Sync 호출 
1. 장애격리
    1. 거래 기능이 수행되지 않더라도 게시글 기능은 365일 24시간 서비스될 수 있어야 한다.  Async (event-driven), Eventual Consistency
1. 성능
    1. 판매자가 등록한 판매글을 판매게시글 목록(프론트엔드)에서 확인할 수 있어야한다. CQRS

# 구현
# Event Storming
MSAEz 로 모델링한 이벤트스토밍 결과: https://www.msaez.io/#/181188513/storming/secondhandtrading

### 중고거래 이벤트 스토밍
![image](https://github.com/user-attachments/assets/ea21e8e6-17cb-4498-95f7-71d2bcdffdf9)

1. post : 게시글 관리 기능
2. offer : 구매 요청 기능
3. deal : 거래 기능
4. mypage : 판매 목록 조회 기능

# MSA 아키텍처 구성도

![image](https://github.com/user-attachments/assets/ab21658e-d862-4fe4-ad15-370dff73415b)


# 분산 트랜잭션 - Saga
1. post : 8082 port
### post 서비스 게시글 등록
```
$ http localhost:8088/posts userId="seller01" createDt="2024-09-24" goods="아이폰13 공기계" price=600000 address="방배동"
$ http localhost:8088/posts userId="seller02" createDt="2024-09-24" goods="무선마우스" price=10000 address="서초동"
# kafka consumer
{"eventType":"PostWrote","timestamp":1727196392362,"postId":1,"userId":"seller01","createDt":"2024-09-24T00:00:00.000+00:00","price":600000,"address":"방배동","status":"created","goods":"아이폰13 공기계"}
{"eventType":"PostWrote","timestamp":1727196886754,"postId":2,"userId":"seller02","createDt":"2024-09-24T00:00:00.000+00:00","price":10000,"address":"서초동","status":"created","goods":"무선마우스"}
```
![image](https://github.com/user-attachments/assets/7d647999-0a0c-46f6-a362-22ca589f9c6c)
![image](https://github.com/user-attachments/assets/e59e9870-5d6f-49f8-8dc4-86400efb59cd)

2. offer : 8083 port
### offer 서비스 구매 요청
```
$ http localhost:8088/offers userId="buyer01" price=10000 postId=2 offerType="dealOffered"
$ http localhost:8088/offers userId="buyer02" price=600000 postId=1 offerType="priceNegotiated" offeredPrice=500000
$ http localhost:8088/offers userId="buyer03" price=600000 postId=1 offerType="priceNegotiated" offeredPrice=510000
# kafaka consumer
{"eventType":"DealOffered","timestamp":1727196986188,"offerId":1,"userId":"buyer01","price":10000,"postId":2,"offerStatus":null,"offerType":"dealOffered"}
{"eventType":"PriceNegotiated","timestamp":1727197030689,"offerId":2,"userId":"buyer02","price":600000,"offeredPrice":500000,"postId":1,"offerStatus":null,"offerType":"priceNegotiated"}
{"eventType":"PriceNegotiated","timestamp":1727197056214,"offerId":3,"userId":"buyer03","price":600000,"offeredPrice":510000,"postId":1,"offerStatus":null,"offerType":"priceNegotiated"}
```
![image](https://github.com/user-attachments/assets/28ba30b3-f0fc-4a81-a463-a779beb21443)
![image](https://github.com/user-attachments/assets/165ad7ad-51db-4c9d-a9a6-5d54d4caa858)
![image](https://github.com/user-attachments/assets/0d33557d-843e-4ebe-8db1-da263a9dafb6)

### offer 서비스 가격제안 수락
```
$ http PATCH localhost:8088/offers/3 userId="buyer03" postId=1 offerStatus="offerAccepted" offeredPrice=510000
# kafka consumer
{"eventType":"OfferStatusUpdated","timestamp":1727197151383,"offerId":3,"updateDt":null,"offerStatus":"offerAccepted"}
```
![image](https://github.com/user-attachments/assets/74dbc957-67b6-48d1-ba48-50e0a568e7cc)

3. deal : 8084 port
### deal 서비스 거래 예약
```
$ http localhost:8088/deals offerId=1 postId=2 userId="buyer01" price=10000 status="dealReserved" updateDt="2024-09-24"
$ http localhost:8088/deals offerId=3 postId=1 userId="buyer03" price=510000 status="dealReserved" updateDt="2024-09-24"
```
![image](https://github.com/user-attachments/assets/8b57b9cf-bfff-4a8d-aa2e-f281ad8ccbd9)
![image](https://github.com/user-attachments/assets/52471bbd-2f4a-4e9f-a19c-0460f164bbe7)

### deal 서비스 거래 완료
deal 서비스 거래 완료에 따른 가격제안 자동 완료
```
$ http PATCH localhost:8088/deals/2 offeredId=3 postId=1 userId="buyer03" price=510000 status="dealEnded" updateDt="2024-09-25"
```
![image](https://github.com/user-attachments/assets/e30b2483-6a6f-4436-a512-4ffb93a99bf0)


# 보상처리 - Compensation
## offer 서비스 거래요청 중 게시글이 거래완료 상태로 변경되어 거래요청 rollback
```

```

# 단일 진입점 - Gateway
gateway port : 8088
 - gateway를 이용한 분산처리
```
# post 호출
http localhost:8088/posts userId="seller01" createDt="2024-09-24" goods="아이폰13 공기계" price=600000 address="방배동"

# offer 호출
http localhost:8088/offers userId="buyer01" price=10000 postId=2 offerType="dealOffered"

# deal 호출
http localhost:8088/deals offerId=1 postId=2 userId="buyer01" price=10000 status="dealReserved" updateDt="2024-09-24"
```

# 분산 데이터 프로젝션 - CQRS
## MyPage 서비스 확인
```
1. post 서비스 발생 시 myPage 조회
http localhost:8088/posts userId="seller01" createDt="2024-09-24" goods="아이폰13 공기계" price=600000 address="방배동"
http localhost:8085/myPages/1
2. deal 서비스 발생 시 myPage 조회
http localhost:8088/deals offerId=1 postId=2 userId="buyer01" price=10000 status="dealReserved" updateDt="2024-09-24"
http localhost:8085/myPages/2
```
![image](https://github.com/user-attachments/assets/26804667-f712-4033-839d-d814506f1327)
![image](https://github.com/user-attachments/assets/1698a1f2-0db0-4770-a57a-1471d71901a7)


# 운영
# 클라우드 배포 - Container 운영
## Container 환경에 배포 : Docker 및 Azure ACR 활용
1. gateway : LoadBalancer Type
2. post
3. offer
4. deal
5. mypage
```
mvn package -B -Dmaven.test.skip=true
docker build -t mink93/post:20240924 .
docker push mink93/post:20240924
kubectl apply -f kubernetes/deployment.yaml --namespace secondhanddeals
kubectl apply -f kubernetes/service.yaml --namespace secondhanddeals
```
![image](https://github.com/user-attachments/assets/4c7e25a6-eb95-40ab-8a92-f1f091a5981c)

## Pipeline : Jenkins 활용


# 컨테이너 자동확장 - HPA
## post 서비스 Auto Scale-Out 적용
1. post 서비스 배포
2. seige 서비스 pod 생성
```
kubectl apply -f - <<EOF
apiVersion: v1
kind: Pod
metadata:
  name: siege
  namespace: secondhanddeals
spec:
  containers:
  - name: siege
    image: apexacme/siege-nginx
EOF
```

3. Auto Scaling 설정
```
kubectl autoscale deployment post --namespace secondhanddeals --cpu-percent=50 --min=1 --max=3
```

4. deployment.yaml 배포파일에 CPU 요청에 대한 값을 지정
```
resources:
  requests:
    cpu: "200m"
```

5. deployment.yaml 재배포
```
kubectl delete -f kubernetes/deployment.yaml --namespace secondhanddeals
kubectl apply -f kubernetes/deployment.yaml --namespace secondhanddeals
```

6. seige 부하 발생
```
kubectl exec -it siege --namespace secondhanddeals -- /bin/bash
siege -c20 -t40S -v http://post:8080/posts
```
![image](https://github.com/user-attachments/assets/ec9a9937-dfeb-411a-bcbc-bb006900b226)

7. post pod Auto Scale-Out 검증
```
kubectl get po --namespace secondhanddeals -w
kubectl get hpa --namespace secondhanddeals -w
```
![image](https://github.com/user-attachments/assets/fa98a924-ee99-4b10-bb10-3d1dce74b955)


# 컨테이너로부터 환경분리 - ConfigMap

## post 서비스 deployment.yaml 수정
```
env:
  - name: ORDER_LOG_LEVEL
    valueFrom:
      configMapKeyRef:
        name: config-dev
        key: ORDER_LOG_LEVEL
```

## LOG LEVEL 설정 - DEBUG
### ConfigMap 생성 - Logging Level을 DEBUG로 설정
```
kubectl apply -f - <<EOF
apiVersion: v1
kind: ConfigMap
metadata:
  name: config-dev
  namespace: secondhanddeals
data:
  ORDER_DB_URL: jdbc:mysql://mysql:3306/connectdb1?serverTimezone=Asia/Seoul&useSSL=false
  ORDER_DB_USER: myuser
  ORDER_DB_PASS: mypass
  ORDER_LOG_LEVEL: DEBUG
EOF
```

### post 서비스 배포 후 Logging Level 확인
```
kubectl logs -l app=post --namespace secondhanddeals
```
![image](https://github.com/user-attachments/assets/df19beda-e5d4-48b1-84b1-80135716d724)

## LOG LEVEL 설정 변경 - INFO
### ConfigMap 변경 - Logging Level을 INFO로 설정
```
kubectl apply -f - <<EOF
apiVersion: v1
kind: ConfigMap
metadata:
  name: config-dev
  namespace: secondhanddeals
data:
  ORDER_DB_URL: jdbc:mysql://mysql:3306/connectdb1?serverTimezone=Asia/Seoul&useSSL=false
  ORDER_DB_USER: myuser
  ORDER_DB_PASS: mypass
  ORDER_LOG_LEVEL: INFO
EOF
```

### post 서비스 배포 후 Logging Level 확인 : INFO LOG 확인됨
```
kubectl logs -l app=post --namespace secondhanddeals
```
![image](https://github.com/user-attachments/assets/11e1eec3-956b-4b3b-8e36-a42a3d51e3ce)

## Configmap에서 각 Container로 전달된 환경정보 확인
![image](https://github.com/user-attachments/assets/bad6ed2a-684e-4f73-8a80-2829914bf9b5)


# 클라우드스토리지 활용 - PVC
## PVC(Persistence Volume Claim) 생성
```
kubectl apply -f - <<EOF
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: azurefile
spec:
  accessModes:
  - ReadWriteMany
  storageClassName: azurefile
  resources:
    requests:
      storage: 1Gi
EOF

kubectl get pvc
```
![image](https://github.com/user-attachments/assets/ae9ba496-337c-423c-ae2b-6f1e7247fbab)

## NFS 볼륨을 가지는 post 서비스 배포
```
volumes:
- name: volume
  persistentVolumeClaim:
    claimName: azurefile
```
### 파일시스템 마운트 확인
```
kubectl exec -it pod/post-dcb8f68bb-lxp4w --namespace secondhanddeals -- /bin/sh
cd /mnt/data
echo "NFS Strorage Test.. " > test.txt
```

### post 서비스 replica 확장 후 두번째 서비스에서 test.txt 확인
```
kubectl scale deploy post --replicas=2 --namespace secondhanddeals
kubectl exec -it pod/post-dcb8f68bb-zd96q --namespace secondhanddeals -- /bin/sh
cd /mnt/data
ls
```
![image](https://github.com/user-attachments/assets/29c0cb26-05c0-430e-83d3-ae8074ebddbb)


# 무정지배포 - Rediness Probe
post 서비스 배포 시 무정지배포 설정
```
siege -c1 -t60S -v http://post:8080/posts --delay=1S
```

### readinessProbe 설정 전
![image](https://github.com/user-attachments/assets/7cd35039-2856-492e-a056-103abcb395f4)

### readinessProbe 설정 후 
```
readinessProbe:
  httpGet:
    path: '/actuator/health'
    port: 8080
  initialDelaySeconds: 10
  timeoutSeconds: 2
  periodSeconds: 5
  failureThreshold: 10
```
![image](https://github.com/user-attachments/assets/99f01350-da11-4373-b055-623411653ae5)



# 서비스 메쉬 응용 - Mesh


# 통합 모니터링

