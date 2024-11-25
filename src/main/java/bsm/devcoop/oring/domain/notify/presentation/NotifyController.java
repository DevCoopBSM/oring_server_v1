package bsm.devcoop.oring.domain.notify.presentation;

import bsm.devcoop.oring.domain.notify.service.NotifyService;
import bsm.devcoop.oring.entity.account.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping(value = "/notify")
@RequiredArgsConstructor
@Slf4j
public class NotifyController {
    private final NotifyService notificationService;

    @GetMapping(value = "/connect", produces = "text/event-stream; charset=utf-8")
    public SseEmitter subscribe(@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = userDetails.getUserEmail();

        return notificationService.subscribe(userEmail, lastEventId);
    }
}
