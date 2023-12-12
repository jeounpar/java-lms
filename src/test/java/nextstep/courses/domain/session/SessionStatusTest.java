package nextstep.courses.domain.session;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionStatusTest {

    @Test
    @DisplayName("강의 오픈 여부를 확인한다")
    void session_isOpen() {
        SessionStatus open = SessionStatus.OPEN;
        SessionStatus close = SessionStatus.CLOSE;
        SessionStatus preparing = SessionStatus.PREPARING;
        
        assertAll(
            () -> assertThat(open.isOpen()).isTrue(),
            () -> assertThat(close.isOpen()).isFalse(),
            () -> assertThat(preparing.isOpen()).isFalse()
        );
    }

}