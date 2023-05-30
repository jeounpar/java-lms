package nextstep.courses.infrastructure;

import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.SessionStatus;
import nextstep.courses.fixture.SessionFixture;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class SessionRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
    }

    @Test
    void save() {
        Session session = SessionFixture.create(SessionStatus.PREPARING, 1);
        Session savedSession = sessionRepository.save(session, 1L);
        assertThat(savedSession).isNotNull();
    }

    @Test
    void findById() {
        Session session = SessionFixture.create(SessionStatus.PREPARING, 1);
        Session savedSession = sessionRepository.save(session, 1L);

        Session findSession = sessionRepository.findById(savedSession.getId());
        assertThat(findSession.getId()).isEqualTo(savedSession.getId());
    }

    @Test
    void findByCourseId() {
        sessionRepository.save(SessionFixture.create(SessionStatus.PREPARING, 1), 1L);
        sessionRepository.save(SessionFixture.create(SessionStatus.PREPARING, 1), 1L);

        List<Session> findSessions = sessionRepository.findByCourseId(1L);
        assertThat(findSessions).hasSize(2);
    }

    @Test
    void saveSessionUser() {
        NsUser nsUser = NsUserTest.JAVAJIGI;
        Session session = SessionFixture.create(SessionStatus.RECRUITING, 1);

        long id = sessionRepository.saveSessionUser(session, nsUser);
        assertThat(id).isPositive();
    }

    @Test
    void findAllUserBySessionId() {
        Session savedSession = sessionRepository.save(SessionFixture.create(SessionStatus.RECRUITING, 1), 1L);
        sessionRepository.saveSessionUser(savedSession, NsUserTest.JAVAJIGI);

        List<NsUser> nextStepUsers = sessionRepository.findAllUserBySessionId(savedSession.getId());
        assertThat(nextStepUsers).hasSize(1);
    }
}