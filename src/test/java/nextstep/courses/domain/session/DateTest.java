package nextstep.courses.domain.session;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import nextstep.courses.exception.InvalidSessionDateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DateTest {

    @Test
    @DisplayName("강의 시작일은 종료일보다 늦어질 수 없다")
    void session_start_and_end() {
        assertThatThrownBy(() -> {
            new Date(LocalDateTime.now().plusSeconds(1),
                LocalDateTime.now());
        }).isInstanceOf(InvalidSessionDateException.class);
    }
}