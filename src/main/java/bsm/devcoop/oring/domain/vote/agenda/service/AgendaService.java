package bsm.devcoop.oring.domain.vote.agenda.service;

import bsm.devcoop.oring.domain.vote.agenda.Agenda;
import bsm.devcoop.oring.domain.vote.agenda.AgendaId;
import bsm.devcoop.oring.domain.agenda.presentation.dto.*;
import bsm.devcoop.oring.domain.vote.agenda.presentation.dto.*;
import bsm.devcoop.oring.domain.vote.agenda.repository.AgendaRepository;
import bsm.devcoop.oring.domain.vote.conference.Conference;
import bsm.devcoop.oring.domain.vote.conference.repository.ConferenceRepository;
import bsm.devcoop.oring.global.exception.GlobalException;
import bsm.devcoop.oring.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${my.token}")
    private String tokenKey;

    Boolean checkToken(String token) {
        return token.equals(tokenKey);
    }

    // 안건 읽기
    @Transactional(readOnly = true)
    public Agenda read(LocalDate conferenceDate, int agendaNo) throws GlobalException {
        Conference conference = conferenceRepository.findByDate(conferenceDate);
        if (conference == null) {
            throw new GlobalException(ErrorCode.DATA_NOT_FOUND);
        }

        AgendaId agendaId = AgendaId.builder()
                .conferenceId(conferenceDate)
                .agendaNo(agendaNo)
                .build();

        return agendaRepository.findById(agendaId)
                .orElseThrow(() -> new GlobalException(ErrorCode.DATA_NOT_FOUND));
    }

    // ID로 안건 읽기
    @Transactional(readOnly = true)
    public Agenda readById(AgendaId id) throws GlobalException {
        return agendaRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.DATA_NOT_FOUND));
    }

    // 안건 만들기
    @Transactional
    public ResponseEntity<?> create(String token, MakeAgendaRequestDto requestDto) throws GlobalException {
        if(!checkToken(token)) {
            return ResponseEntity.status(401).body(ErrorCode.FORBIDDEN);
        }

        int agendaNo = requestDto.getAgendaNo();
        LocalDate conferenceDate = requestDto.getConferenceDate();
        String agendaContent = requestDto.getAgendaContent();

        Conference conference = conferenceRepository.findByDate(conferenceDate);
        if (conference == null) {
            throw new GlobalException(ErrorCode.DATA_NOT_FOUND);
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
                .isPossible('0')
                .conference(conference)
                .build();

        agendaRepository.save(agenda);

        MakeAgendaResponseDto responseDto = MakeAgendaResponseDto.builder()
                .agenda(agenda)
                .build();

        return ResponseEntity.ok(responseDto);
    }

    // 안건 내용 수정
    @Transactional
    public ResponseEntity<?> update(String token, UpdateAgendaRequestDto requestDto) throws GlobalException {
        if(!checkToken(token)) {
            return ResponseEntity.status(401).body(ErrorCode.FORBIDDEN);
        }

        LocalDate conferenceDate = requestDto.getConferenceDate();
        int agendaNo = requestDto.getAgendaNo();
        String newAgendaContent = requestDto.getAgendaContent();

        if (conferenceRepository.findByDate(conferenceDate) == null) {
            throw new GlobalException(ErrorCode.DATA_NOT_FOUND);
        }

        Agenda agenda = read(conferenceDate, agendaNo);
        agenda.setAgendaContent(newAgendaContent);
        agendaRepository.save(agenda);

        UpdateAgendaResponseDto responseDto = UpdateAgendaResponseDto.builder()
                .agendaContent(newAgendaContent)
                .build();

        return ResponseEntity.ok(responseDto);
    }

    // 안건 삭제
    @Transactional
    public ResponseEntity<?> delete(String token, LocalDate conferenceDate, int agendaNo) throws GlobalException {
        if(!checkToken(token)) {
            return ResponseEntity.status(401).body(ErrorCode.FORBIDDEN);
        }

        Agenda agenda = read(conferenceDate, agendaNo);
        AgendaId agendaId = AgendaId.builder()
                .conferenceId(conferenceDate)
                .agendaNo(agendaNo)
                .build();

        agendaRepository.deleteById(agendaId);
        return ResponseEntity.ok(true);
    }

    // 투표 가능 여부 수정
    @Transactional
    public ResponseEntity<?> updateIsPossible(String token, UpdateIsPossibleRequestDto requestDto) throws GlobalException {
        if(!checkToken(token)) {
            return ResponseEntity.status(401).body(ErrorCode.FORBIDDEN);
        }

        LocalDate conferenceDate = requestDto.getConferenceDate();
        int agendaNo = requestDto.getAgendaNo();

        Agenda agenda = read(conferenceDate, agendaNo);
        char isPossible = requestDto.getIsPossible();

        if(agenda.getIsPossible() == isPossible) {
            return ResponseEntity.ok("Nothing change");
        }

        agenda.setIsPossible(isPossible);
        agendaRepository.save(agenda);

        UpdateIsPossibleResponseDto responseDto = UpdateIsPossibleResponseDto.builder()
                .agenda(agenda)
                .build();

        return ResponseEntity.ok(responseDto);
    }
}
