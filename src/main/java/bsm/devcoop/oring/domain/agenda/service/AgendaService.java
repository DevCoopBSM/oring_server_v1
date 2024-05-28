package bsm.devcoop.oring.domain.agenda.service;

import bsm.devcoop.oring.domain.agenda.Agenda;
import bsm.devcoop.oring.domain.agenda.AgendaId;
import bsm.devcoop.oring.domain.agenda.presentation.dto.*;
import bsm.devcoop.oring.domain.agenda.repository.AgendaRepository;
import bsm.devcoop.oring.domain.conference.Conference;
import bsm.devcoop.oring.domain.conference.repository.ConferenceRepository;
import bsm.devcoop.oring.global.exception.GlobalException;
import bsm.devcoop.oring.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AgendaService {
    private final ConferenceRepository conferenceRepository;
    private final AgendaRepository agendaRepository;

    // 안건 읽기
    @Transactional
    public Agenda read(LocalDate conferenceDate, int agendaNo) throws GlobalException {
        Conference conference = conferenceRepository.findByDate(conferenceDate);
        if (conference == null) {
            throw new GlobalException(ErrorCode.CONFERENCE_NOT_FOUND);
        }

        AgendaId agendaId = AgendaId.builder()
                .conferenceId(conferenceDate)
                .agendaNo(agendaNo)
                .build();

        Agenda agenda = agendaRepository.findById(agendaId)
                .orElseThrow(() -> new GlobalException(ErrorCode.AGENDA_NOT_FOUND));

        return agenda;
    }

    // 안건 만들기
    @Transactional
    public ResponseEntity<?> create(MakeAgendaRequestDto requestDto) throws GlobalException {
        int agendaNo = requestDto.getAgendaNo();
        LocalDate conferenceDate = requestDto.getConferenceDate();
        String agendaContent = requestDto.getAgendaContent();

        Conference conference = conferenceRepository.findByDate(conferenceDate);
        if (conference == null) {
            throw new GlobalException(ErrorCode.CONFERENCE_NOT_FOUND);
        } else if(agendaContent == null) {
            throw new NullPointerException("Content should NOT be NULL");
        }

        AgendaId agendaId = AgendaId.builder()
                .agendaNo(agendaNo)
                .conferenceId(conferenceDate)
                .build();

        if (agendaRepository.findById(agendaId).isPresent()) {
            throw new GlobalException(ErrorCode.DUPLICATE_DATA);
        }

        Agenda agenda = Agenda.builder()
                .id(agendaId)
                .agendaContent(agendaContent)
                .isPossible('0') // default : 투표 불가능, 0
                .build();

        conference.addAgenda(agenda);
        conferenceRepository.save(conference);

        MakeAgendaResponseDto responseDto = MakeAgendaResponseDto.builder()
                .agenda(agenda)
                .build();

        return ResponseEntity.ok(responseDto);
    }

    // 안건 내용 수정
    @Transactional
    public ResponseEntity<?> update(UpdateAgendaRequestDto requestDto) throws GlobalException {
        LocalDate conferenceDate = requestDto.getConferenceDate();
        int agendaNo = requestDto.getAgendaNo();
        String newAgendaContent = requestDto.getAgendaContent();

        if (conferenceRepository.findByDate(conferenceDate) == null) {
            throw new GlobalException(ErrorCode.CONFERENCE_NOT_FOUND);
        }

        Agenda agenda = read(conferenceDate, agendaNo);

        if (agenda == null) {
            throw new GlobalException(ErrorCode.AGENDA_NOT_FOUND);
        }

        agenda.setAgendaContent(newAgendaContent);
        agendaRepository.save(agenda);

        UpdateAgendaResponseDto responseDto = UpdateAgendaResponseDto.builder()
                .agendaContent(newAgendaContent)
                .build();

        return ResponseEntity.ok(responseDto);
    }

    // 안건 삭제
    @Transactional
    public ResponseEntity<?> delete(LocalDate conferenceDate, int agendaNo) throws GlobalException {
        Agenda agenda = read(conferenceDate, agendaNo);
        if(agenda == null) {
            throw new GlobalException(ErrorCode.AGENDA_NOT_FOUND);
        }

        AgendaId agendaId = AgendaId.builder()
                .conferenceId(conferenceDate)
                .agendaNo(agendaNo)
                .build();

        agendaRepository.deleteById(agendaId);

        return ResponseEntity.ok(true); // 삭제된 후 ID는 어떻게 해야 하는가???
    }

    // 투표 가능 여부 수정
    @Transactional
    public ResponseEntity<?> updateIsPossible(UpdateIsPossibleRequestDto requestDto) throws GlobalException {
        LocalDate conferenceDate = requestDto.getConferenceDate();
        int agendaNo = requestDto.getAgendaNo();

        Agenda agenda = read(conferenceDate, agendaNo);

        if (agenda == null) {
            throw new GlobalException(ErrorCode.AGENDA_NOT_FOUND);
        }

        char isPossible = requestDto.getIsPossible();

        if(agenda.getIsPossible() == isPossible) {
            return ResponseEntity.ok("변동 사항이 존재하지 않습니다.");
        }

        agenda.setIsPossible(isPossible);
        agendaRepository.save(agenda);

        UpdateIsPossibleResponseDto responseDto = UpdateIsPossibleResponseDto.builder()
                .agenda(agenda)
                .build();

        return ResponseEntity.ok(responseDto);
    }
}
