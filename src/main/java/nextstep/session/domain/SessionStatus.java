package nextstep.session.domain;

import nextstep.session.NotFoundStatusException;

import java.util.Arrays;

public enum SessionStatus {
    READY("ready"),
    RECRUITING("recruiting"),
    END("end");

    private String status;

    SessionStatus(String status) {
        this.status = status;
    }

    public static SessionStatus of(String status) throws NotFoundStatusException {
        return Arrays.stream(values())
                .filter(value -> value.status.equals(status))
                .findAny()
                .orElseThrow(() -> new NotFoundStatusException("상태가 존재하지 않습니다."));
    }

    public String getStatus() {
        return status;
    }
}
