package se.andersrapp.brf.exception;

/**
 *
 * @author Anders
 */
public class InputNotDetectedException extends RuntimeException {

    private static final long serialVersionUID = 8473521959182190903L;

    public InputNotDetectedException(String errorMessage) {
        super(errorMessage);
    }

}
