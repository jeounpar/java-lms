package nextstep.courses.domain.session;

public enum SessionStatus {

    PREPARING("preparing"),
    OPEN("open"),
    CLOSE("close");

    private final String type;

    SessionStatus(String type) {
        this.type = type;
    }

    public boolean isOpen() {
        return this.type.equals(SessionStatus.OPEN.type);
    }
}
