package se.andersrapp.brf.exception;

/**
 *
 * @author Anders Rapp
 */
public class DataNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 8473521959182190903L;

    public DataNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
