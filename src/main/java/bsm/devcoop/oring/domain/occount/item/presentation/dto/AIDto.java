package bsm.devcoop.oring.domain.occount.item.presentation.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class AIDto {
    @Builder
    @Getter
    public static class Request {
        private String userCode;
    }


    // Recommend

    @Getter
    public static class RecommendAIResponse {
        private List<String> item_name;
    }

    @Builder
    @Getter
    public static class Recommend {
        private int itemId;
        private String itemName;
        private String itemImage;
        private int itemPrice;
        private int itemQuantity;
    }

    @Builder
    @Getter
    public static class RecommendResponse {
        private List<Recommend> recommendList;
    }


    // Warning

    @Getter
    public static class WarningAIResponse {
        private String item_name;
    }

    @Builder
    @Getter
    public static class WarningResponse {
        private String itemName;
    }


    // PeakTime

    @Getter
    public static class PeakTimeAIResponse {
        private boolean warning;
    }
}
