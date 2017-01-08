package vn.edu.poly.eventfinder.callbacks;

import vn.edu.poly.eventfinder.entities.User;

/**
 * Created by nix on 1/8/17.
 */

public interface UserDAOCallback extends DAOCallback<User> {
    void onSuccess(User user);
}
