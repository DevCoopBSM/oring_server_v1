package bsm.devcoop.oring.domain.conference.service;

import bsm.devcoop.oring.domain.conference.Conference;
import bsm.devcoop.oring.domain.conference.presentation.dto.MakeConfRequestDto;
import bsm.devcoop.oring.domain.conference.presentation.dto.MakeConfResponseDto;
import bsm.devcoop.oring.domain.conference.presentation.dto.ReadConfRequestDto;
import bsm.devcoop.oring.domain.conference.presentation.dto.ReadConfResponseDto;
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
public class ConferenceService {
    private final ConferenceRepository conferenceRepository;

    // 회의 읽기
    @Transactional
    public ResponseEntity<?> read(ReadConfRequestDto requestDto) {
        LocalDate date = requestDto.getDate();

        Conference conf = conferenceRepository.findByDate(date);
        if(conf == null) {
            return ResponseEntity.ok(null);
        }

        ReadConfResponseDto response = ReadConfResponseDto.builder()
                .date(conf.getDate())
                .pdfLink(conf.getPdfLink())
                .agendas(conf.getAgendas())
                .build();

        return ResponseEntity.ok(response);
    }

    // 회의 만들기
    @Transactional
    public ResponseEntity<?> create(MakeConfRequestDto requestDto) throws GlobalException {
        if(requestDto.getDate() == null) {
            throw new GlobalException(ErrorCode.DATE_NOT_CORRECT);
        }
        Conference conf = Conference.builder()
                .date(requestDto.getDate())
                .pdfLink((requestDto.getPdfLink()))
                .build();
        conferenceRepository.save(conf);

        MakeConfResponseDto response = MakeConfResponseDto.builder()
                .conference(conf)
                .build();

        return ResponseEntity.ok(response);
    }
}
