package bsm.devcoop.oring.domain.vote.conference.service;

import bsm.devcoop.oring.domain.vote.conference.Conference;
import bsm.devcoop.oring.domain.vote.conference.presentation.dto.MakeConfRequestDto;
import bsm.devcoop.oring.domain.vote.conference.presentation.dto.MakeConfResponseDto;
import bsm.devcoop.oring.domain.vote.conference.presentation.dto.ReadConfResponseDto;
import bsm.devcoop.oring.domain.vote.conference.presentation.dto.ReadFileResponseDto;
import bsm.devcoop.oring.domain.vote.conference.repository.ConferenceRepository;
import bsm.devcoop.oring.domain.vote.agenda.presentation.dto.ReadAgendaResponseDto;
import bsm.devcoop.oring.global.exception.GlobalException;
import bsm.devcoop.oring.global.exception.enums.ErrorCode;
import bsm.devcoop.oring.domain.vote.agenda.Agenda;
import bsm.devcoop.oring.domain.vote.agenda.repository.AgendaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ConferenceService {
    private final ConferenceRepository conferenceRepository;
    private final AgendaRepository agendaRepository; 

    @Value("${jwt.secret}")
    private String tokenKey;

    Boolean checkToken(String token) {
        return token.equals(tokenKey);
    }

    // 회의 읽기
    @Transactional(readOnly = true)
    public ResponseEntity<?> readConf(String token, LocalDate date) {
        if (!checkToken(token)) {
            return ResponseEntity.status(401).body(ErrorCode.Forbidden);
        }
        Conference conf = conferenceRepository.findByDate(date);
        if (conf == null) {
            return ResponseEntity.ok(null);
        }

        ReadConfResponseDto response = ReadConfResponseDto.builder()
                .conference(conf)
                .build();

        return ResponseEntity.ok(response);
    }

    // 회의 만들기
    @Transactional
    public ResponseEntity<?> create(String token, MakeConfRequestDto requestDto) throws GlobalException {
        if (!checkToken(token)) {
            return ResponseEntity.status(401).body(ErrorCode.Forbidden);
        }
        LocalDate date = requestDto.getDate();

        if (date == null) {
            throw new GlobalException(ErrorCode.Bad_Request, "Null Data");
        }

        try {
            conferenceRepository.findByDate(date);

            Conference conf = Conference.builder()
                    .date(requestDto.getDate())
                    .fileLink(requestDto.getFileLink())
                    .build();
            conferenceRepository.save(conf);

            MakeConfResponseDto response = MakeConfResponseDto.builder()
                    .conference(conf)
                    .build();

            return ResponseEntity.ok(response);
        } catch (NullPointerException e) {
            throw new GlobalException(ErrorCode.Conflict, "");
        }
    }

    // file ( pdf ) 읽기 ; 현재 사용 X
    @Transactional
    public ResponseEntity<?> readFile() throws GlobalException {
        LocalDate date = LocalDate.of(2024, 5, 29);

        try {
            Conference conf = conferenceRepository.findByDate(date);

            String fileLink = conf.getFileLink();

            ReadFileResponseDto responseDto = ReadFileResponseDto.builder()
                    .fileLink(fileLink)
                    .build();

            return ResponseEntity.ok(responseDto);
        } catch (NullPointerException e) {
            throw  new GlobalException(ErrorCode.Not_Found, "");
        }
    }

    // 새로운 아젠다만 가져오는 메소드 추가
    @Transactional(readOnly = true)
    public ResponseEntity<?> readAgenda(LocalDate date) {
        Conference conf = conferenceRepository.findByDate(date);
        if (conf == null) {
            log.warn("No conference found for date {}", date);
            return ResponseEntity.noContent().build();
        }

        List<ReadAgendaResponseDto> agendaDtos = conf.getAgendaList().stream()
                .map(Agenda::toResponseDto)
                .collect(Collectors.toList());
        log.info("Found {} agendas for date {}", agendaDtos.size(), date);
        return ResponseEntity.ok(agendaDtos);
    }
}
