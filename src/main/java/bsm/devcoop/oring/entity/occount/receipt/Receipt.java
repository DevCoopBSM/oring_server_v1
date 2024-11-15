package bsm.devcoop.oring.entity.occount.receipt;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Entity
@Table(name = "occount_receipt")
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiptId;

    private int itemId;

    private String itemName;

    private int saleQty; // 판매 수량

    private Long tradedPoint; // 결제 금액

    private String saleType; // 결제 타입 ( 0 : 정상 결제 / 1 : 환불 결제 )

    private LocalDate saleDate; // 판매 날짜

    private int dailyNum; // 동시 구매된 상품을 묶는 번호. * 컬럼명 변경 필요
}
