package bsm.devcoop.oring.domain.conference.service;

import bsm.devcoop.oring.domain.conference.Conference;
import bsm.devcoop.oring.domain.conference.presentation.dto.MakeConfRequestDto;
import bsm.devcoop.oring.domain.conference.presentation.dto.MakeConfResponseDto;
import bsm.devcoop.oring.domain.conference.presentation.dto.ReadConfResponseDto;
import bsm.devcoop.oring.domain.conference.presentation.dto.ReadFileResponseDto;
import bsm.devcoop.oring.domain.conference.repository.ConferenceRepository;
import bsm.devcoop.oring.global.exception.GlobalException;
import bsm.devcoop.oring.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.GeneralSecurityException;
import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ConferenceService {
    private final ConferenceRepository conferenceRepository;

    @Value("${my.token}")
    private String tokenKey;

    Boolean checkToken(String token) {
        return token.equals(tokenKey);
    }

    // 회의 읽기
    @Transactional(readOnly = true)
    public ResponseEntity<?> readConf(LocalDate date) {
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
        if(checkToken(token)) throw new GlobalException(ErrorCode.FORBIDDEN);
        LocalDate date = requestDto.getDate();

        if (date == null) {
            throw new GlobalException(ErrorCode.DATE_NOT_CORRECT);
        } else if (conferenceRepository.findByDate(date) != null) {
            throw new GlobalException(ErrorCode.DUPLICATE_DATA);
        }

        Conference conf = Conference.builder()
                .date(requestDto.getDate())
                .fileLink((requestDto.getFileLink()))
                .build();
        conferenceRepository.save(conf);

        MakeConfResponseDto response = MakeConfResponseDto.builder()
                .conference(conf)
                .build();

        return ResponseEntity.ok(response);
    }

    // file ( pdf ) 읽기 ; 현재 사용 X
    @Transactional
    public ResponseEntity<?> readFile() throws GlobalException {
        LocalDate date = LocalDate.of(2024, 5, 29);
        Conference conf = conferenceRepository.findByDate(date);
        if (conf == null) {
            throw new GlobalException(ErrorCode.CONFERENCE_NOT_FOUND);
        }

        String fileLink = conf.getFileLink();

        ReadFileResponseDto responseDto = ReadFileResponseDto.builder()
                .fileLink(fileLink)
                .build();

        return ResponseEntity.ok(responseDto);
    }
}
