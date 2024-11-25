package bsm.devcoop.oring.entity.notify;

import bsm.devcoop.oring.entity.notify.types.NotifyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Table(name = "oring_notify")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notifyId;

    private String notifyTitle;

    private String notifyContent;

    @Enumerated(EnumType.STRING)
    private NotifyType notifyType;

    private String notifyUrl;

    private String receiveUserEmail;

    private boolean isRead;
}
