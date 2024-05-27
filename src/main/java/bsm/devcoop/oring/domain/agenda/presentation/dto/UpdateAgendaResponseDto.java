package bsm.devcoop.oring.domain.agenda.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UpdateAgendaResponseDto {
    // 어떤 데이터를 넘겨줘야 update 내용을 보여줄 수 있는지 FE와 논의할 필요 O
    private String agendaContent;
}
