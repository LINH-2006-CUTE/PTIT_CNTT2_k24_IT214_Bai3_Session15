## 2. Cau truc du lieu JSON
Mau du lieu su kien don hang duoc gui qua Kafka:
```json
{
  "orderId": "ORD-171888",
  "productName": "Laptop Dell XPS 15",
  "amount": 1500.0,
  "customerEmail": "user@example.com",
  "status": "PENDING"
}
```

## 3. Cau truc thu muc ma nguon
```
src/main/java/com/example/bai3_it214_ss15/
├── config
│   └── KafkaTopicConfig.java
├── controller
│   └── OrderController.java
├── dto
│   └── OrderEvent.java
├── service
│   ├── NotificationConsumerService.java
│   ├── OrderProducerService.java
│   └── PaymentConsumerService.java
└── Bai3It214Ss15Application.java
```

## 4. Huong dan khoi chay Kafka
Su dung Docker Compose voi file docker-compose.yml:
```bash
docker compose up -d
```
Cac port duoc mo:
- Zookeeper: localhost:2181
- Kafka Broker: localhost:9092

Kiem tra container dang chay:
```bash
docker ps
```

## 5. Huong dan khoi chay ung dung Spring Boot
Chay bang lenh Gradle:
```bash
./gradlew bootRun
```
Ung dung se khoi chay tai cong 8080 va tu dong ket noi toi Kafka Broker localhost:9092.

## 6. Huong dan kiem thu API
Gui yeu cau tao don hang bang cURL:
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "orderId": "ORD-171888",
    "productName": "Laptop Dell XPS 15",
    "amount": 1500.0,
    "customerEmail": "user@example.com",
    "status": "PENDING"
  }'
```

Ket qua HTTP Response tra ve:
```text
Order created and event sent to Kafka successfully!
```

## 7. Ket qua Log Console ky vong
Khi goi API thanh cong, luong Saga Choreography se chay tuan tu qua 3 service va in ra cac dong log:
```text
[OrderService] Sent order event: OrderEvent(orderId=ORD-171888, productName=Laptop Dell XPS 15, amount=1500.0, customerEmail=user@example.com, status=PENDING)
[PaymentService] Received order event for orderId: ORD-171888
[PaymentService] Processing payment... Payment successful. Updated status: PAID
[PaymentService] Sent payment event for orderId: ORD-171888
[NotificationService] Received confirmation for order ORD-171888. Sending email to user@example.com
[NotificationService] Email sent successfully!
```

## 8. Giai thich ly thuyet Saga Choreography
- Dinh nghia: Trong mo hinh Choreography Saga, moi service thuc hien giao dich cuc bo (local transaction) cua chinh no va phat ra su kien (event). Cac service khac lang nghe su kien do va thuc hien giao dich tiep theo ma khong can mot service dieu phoi trung tam (Orchestrator).
- Uu diem:
  - Khop noi long (Loose Coupling): Cac service hoat dong doc lap, chi can quan tam den format su kien tren Kafka Topic.
  - De mo rong: Co the them cac consumer moi vao topic ma khong can sua doi logic cua producer hien tai.
  - Khong co diem nghen trung tam (No Single Point of Failure).
- Nhuoc diem:
  - Kho theo doi luong giao dich tong the khi he thong tro nen phuc tap voi hang chuc buoc.
  - Doi hoi can quan ly xu ly loi va co che bu tru (Compensating Transactions) can than khi co su co xay ra o cac buoc sau.
