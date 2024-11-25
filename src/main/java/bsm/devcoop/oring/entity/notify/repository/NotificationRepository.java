package bsm.devcoop.oring.entity.notify.repository;

import bsm.devcoop.oring.entity.notify.Notify;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notify, Integer> {
}
