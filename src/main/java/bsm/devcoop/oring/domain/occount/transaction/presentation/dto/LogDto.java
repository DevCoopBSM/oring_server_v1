package bsm.devcoop.oring.domain.occount.transaction.presentation.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

public class LogDto {
    public enum LogType {
        CHARGE_LOG,
        PAY_LOG
    }

    @Builder
    @Getter
    public static class Log {
        private LogType logType;
        private String logName;
        private int logPrice;
        private int logAfterPoint;
        private String logDateTime;
    }

    @Builder
    @Getter
    public static class LogResponse {
        private Map<String, List<Log>> logMap;
    }
}
