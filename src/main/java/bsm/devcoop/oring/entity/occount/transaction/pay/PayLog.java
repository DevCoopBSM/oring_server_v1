package bsm.devcoop.oring.entity.occount.transaction.pay;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "occount_payLog")
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PayLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payId;

    private String userCode;

    private LocalDateTime payDate; // 결제 날짜

    private String payType; // 결제 타입

    private Integer beforePoint; // 결제 전 포인트

    private int payedPoint; // 결제한 포인트

    private int afterPoint; // 결제 후 포인트

    private String managedEmail; // 담당 매점부 이메일
}
