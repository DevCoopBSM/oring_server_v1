package bsm.devcoop.oring.entity.occount.receipt;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Entity
@Table(name = "occount_kioskReceipts")
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KioskReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiptId;

    private String itemCode;

    private String itemName;

    private String userCode; // 셀프 결제한 사용자의 유저 코드

    private int saleQty; // 판매 수량

    private int tradedPoint;

    private byte saleType; // 결제 타입 ( 0 : 정상 결제 / 1 : 환불 결제 )

    private LocalDate saleDate; // 판매 날짜

    private int dailyNum; // 동시 구매된 상품을 묶는 번호. * 컬럼명 변경 필요
}
