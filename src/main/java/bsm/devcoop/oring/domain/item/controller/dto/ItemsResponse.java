package bsm.devcoop.oring.domain.item.controller.dto;

import bsm.devcoop.oring.domain.item.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemsResponse {
    private List<Item> itemList;
}
