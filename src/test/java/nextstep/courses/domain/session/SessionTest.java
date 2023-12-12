package nextstep.courses.domain.session;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.UUID;
import nextstep.courses.domain.image.Image;
import nextstep.courses.domain.image.ImageType;
import nextstep.courses.exception.CannotEnrollException;
import nextstep.courses.exception.InvalidImageException;
import nextstep.courses.exception.InvalidSessionDateException;
import nextstep.courses.exception.InvalidTypeException;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SessionTest {

    Date date;
    Image image;

    Session session;

    static Payment paymentByUserAndSessionId(NsUser user, Long sessionId) {
        return new Payment(UUID.randomUUID().toString(), sessionId, user.getId(), 800_000L);
    }

    @BeforeEach
    void setup() throws InvalidSessionDateException, InvalidTypeException, InvalidImageException {
        date = new Date(LocalDateTime.now(), LocalDateTime.now().plusMonths(1));
        image = new Image(1024, ImageType.value("gif"), 301, 201);
        session = new Session(1L, SessionType.PAID, SessionStatus.OPEN, 800_000L, image,
            10, date);
    }

    @Test
    @DisplayName("유료 강의는 강의 최대 수강 인원을 초과할 수 없다")
    void session_paid_lecture() {
        Session session_max_2 = new Session(1L, SessionType.PAID, SessionStatus.OPEN, 800_000L,
            image, 2, date);

        assertThatThrownBy(() -> {
            session_max_2.enroll(NsUserTest.JAVAJIGI,
                paymentByUserAndSessionId(NsUserTest.JAVAJIGI, 1L));
            session_max_2.enroll(NsUserTest.SANJIGI,
                paymentByUserAndSessionId(NsUserTest.SANJIGI, 1L));
            session_max_2.enroll(NsUserTest.PARK, paymentByUserAndSessionId(NsUserTest.PARK, 1L));
        }).isInstanceOf(CannotEnrollException.class);
    }

    @Test
    @DisplayName("강의는 중복 수강신청할 수 없다")
    void session_duplicate_student() {
        assertThatThrownBy(() -> {
            session.enroll(NsUserTest.JAVAJIGI, paymentByUserAndSessionId(NsUserTest.JAVAJIGI, 1L));
            session.enroll(NsUserTest.PARK, paymentByUserAndSessionId(NsUserTest.PARK, 1L));
            session.enroll(NsUserTest.SANJIGI, paymentByUserAndSessionId(NsUserTest.SANJIGI, 1L));
            session.enroll(NsUserTest.SANJIGI, paymentByUserAndSessionId(NsUserTest.SANJIGI, 1L));
        }).isInstanceOf(CannotEnrollException.class);
    }

    @Test
    @DisplayName("강의 수강신청은 강의 상태가 모집중일 때만 가능하다")
    void session_open_status_when_preparing() {
        Session sessionPreparing = new Session(1L, SessionType.PAID, SessionStatus.PREPARING,
            800_000L, image, 10, date);

        assertThatThrownBy(() -> {
            sessionPreparing.enroll(NsUserTest.JAVAJIGI,
                paymentByUserAndSessionId(NsUserTest.JAVAJIGI, 1L));
        }).isInstanceOf(CannotEnrollException.class);
    }

    @Test
    @DisplayName("강의 수강신청은 강의 상태가 모집중일 때만 가능하다")
    void session_open_status_when_close() {
        Session sessionClose = new Session(1L, SessionType.PAID, SessionStatus.CLOSE, 800_000L,
            image, 10, date);
        assertThatThrownBy(() -> {
            sessionClose.enroll(NsUserTest.JAVAJIGI,
                paymentByUserAndSessionId(NsUserTest.JAVAJIGI, 1L));
        }).isInstanceOf(CannotEnrollException.class);
    }

    @Test
    @DisplayName("유료 강의는 수강생이 결제한 금액과 수강료가 일치할 때 수강 신청이 가능하다")
    void session_paid_session() {
        NsUser user = NsUserTest.JAVAJIGI;
        Payment payment = new Payment(UUID.randomUUID().toString(), session.getSessionId(),
            user.getId(), 799_999L);

        Assertions.assertThatThrownBy(() -> {
            session.enroll(user, payment);
        }).isInstanceOf(CannotEnrollException.class);
    }
}
