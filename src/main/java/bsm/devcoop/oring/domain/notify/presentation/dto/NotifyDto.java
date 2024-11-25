package bsm.devcoop.oring.domain.notify.presentation.dto;

import bsm.devcoop.oring.entity.notify.Notify;
import lombok.Builder;
import lombok.Getter;

public class NotifyDto {
    @Builder
    @Getter
    public static class NewResponse {
        // private boolean isSuccess;
        private Notify notify;
    }
}
