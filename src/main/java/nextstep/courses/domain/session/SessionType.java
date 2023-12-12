package nextstep.courses.domain.session;

public enum SessionType {
    PAID("paid"),
    FREE("free");

    private final String type;

    SessionType(String type) {
        this.type = type;
    }

    public boolean isPaid() {
        return this.type.equals(SessionType.PAID.type);
    }
}
