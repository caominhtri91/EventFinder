package vn.edu.poly.eventfinder.callbacks;

/**
 * Created by nix on 1/8/17.
 */

public interface DAOCallback<T> {
    void onSuccess(T data);

    void onError(RuntimeException ex);
}
