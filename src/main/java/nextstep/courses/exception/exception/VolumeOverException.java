package nextstep.courses.exception.exception;

public class VolumeOverException extends IllegalArgumentException {
    private static final long serialVersionUID = 1L;

    public VolumeOverException(String message) {
        super(message);
    }
}