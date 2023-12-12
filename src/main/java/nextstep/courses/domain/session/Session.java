package nextstep.courses.domain.session;

import nextstep.courses.domain.image.Image;
import nextstep.courses.exception.CannotEnrollException;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class Session {

    private Long sessionId;

    private Long price;

    private Image image;

    private SessionType sessionType;

    private SessionStatus sessionStatus;

    private Students students;

    private Date date;

    public Session(Long sessionId, SessionType sessionType, SessionStatus sessionStatus, Long price,
        Image image, int maxCountOfStudents, Date date) {
        this.sessionId = sessionId;
        this.image = image;
        this.students = new Students(maxCountOfStudents);
        this.date = date;
        this.sessionType = sessionType;
        this.sessionStatus = sessionStatus;
        this.price = price;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public Session(Long sessionId, SessionType sessionType, SessionStatus sessionStatus, Date date,
        Image image) {
        this(sessionId, sessionType, sessionStatus, 0L, image, Integer.MAX_VALUE, date);
    }

    public void enroll(NsUser user, Payment payment) throws CannotEnrollException {
        if (!this.sessionStatus.isOpen()) {
            throw new CannotEnrollException("강의 수강신청은 강의 상태가 모집중일 때만 가능하다");
        }
        if (this.sessionType.isPaid() && this.students.isFull()) {
            throw new CannotEnrollException("유료 강의는 강의 최대 수강 인원을 초과할 수 없다");
        }
        if (this.sessionType.isPaid() && !payment.isValid(this.price)) {
            throw new CannotEnrollException("유료 강의는 수강생이 결제한 금액과 수강료가 일치할 때 수강 신청이 가능하다");
        }
        this.students.addStudent(user);
    }

}
