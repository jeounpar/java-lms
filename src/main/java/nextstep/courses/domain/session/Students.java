package nextstep.courses.domain.session;

import java.util.ArrayList;
import java.util.List;
import nextstep.courses.exception.CannotEnrollException;
import nextstep.users.domain.NsUser;

public class Students {

    private int maxCountOfStudents;

    private List<NsUser> students;

    public Students(int maxCountOfStudents) {
        this.maxCountOfStudents = maxCountOfStudents;
        this.students = new ArrayList<>();
    }

    public boolean isFull() {
        return students.size() >= maxCountOfStudents;
    }

    public void addStudent(NsUser user) throws CannotEnrollException {
        if (this.students.contains(user)) {
            throw new CannotEnrollException("강의는 중복 수강신청할 수 없다");
        }
        this.students.add(user);
    }

}
