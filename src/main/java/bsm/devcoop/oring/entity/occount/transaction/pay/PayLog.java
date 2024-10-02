package bsm.devcoop.oring.entity.occount.transaction.pay;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "occount_payLog")
@Data
@Getter
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

    // 생성자
    @Builder
    public PayLog(
            Long payId, String userCode, LocalDateTime payDate,
            String payType, String eventType, int beforePoint,
            int payedPoint, int afterPoint, String managedEmail
    ) {
        this.payId = payId;
        this.userCode = userCode;
        this.payDate = payDate;
        this.payType = payType;
        this.beforePoint = beforePoint;
        this.payedPoint = payedPoint;
        this.afterPoint = afterPoint;
        this.managedEmail = managedEmail;
    }

}
