package se.andersrapp.brf.exception;

/**
 *
 * @author Anders Rapp
 */
public class WrongInputException extends RuntimeException {

    private static final long serialVersionUID = 8473521959182190903L;

    public WrongInputException(String errorMessage) {
        super(errorMessage);
    }

}
