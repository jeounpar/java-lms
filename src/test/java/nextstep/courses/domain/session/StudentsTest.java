package nextstep.courses.domain.session;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.courses.exception.CannotEnrollException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudentsTest {

    @Test
    @DisplayName("강의는 중복 수강신청이 불가능 하다")
    void students_duplicate_user() {
        Students students = new Students(2);
        assertThatThrownBy(() -> {
            students.addStudent(NsUserTest.JAVAJIGI);
            students.addStudent(NsUserTest.JAVAJIGI);
        }).isInstanceOf(CannotEnrollException.class);
    }

    @Test
    @DisplayName("강의 최대 수강 인원을 초과할 수 없다")
    void students_full() throws CannotEnrollException {
        Students students = new Students(2);
        students.addStudent(NsUserTest.JAVAJIGI);
        students.addStudent(NsUserTest.PARK);
        assertThat(students.isFull()).isTrue();
    }

}