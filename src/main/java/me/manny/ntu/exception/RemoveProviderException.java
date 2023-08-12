package me.manny.ntu.exception;

public class RemoveProviderException extends RuntimeException {

    private static final long serialVersionUID = 7657127025152001791L;

    public RemoveProviderException(String provider) {
        super("Cannot remove provider : " + provider);
    }
}
