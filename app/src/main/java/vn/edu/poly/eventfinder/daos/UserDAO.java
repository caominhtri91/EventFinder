package vn.edu.poly.eventfinder.daos;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import vn.edu.poly.eventfinder.callbacks.UserDAOCallback;
import vn.edu.poly.eventfinder.entities.User;

/**
 * Created by nix on 12/29/16.
 */

public class UserDAO {

    private FirebaseUser user;
    private DatabaseReference mDatabase;

    public UserDAO() {
        this.user = FirebaseAuth.getInstance().getCurrentUser();
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public String getCurrentUserUID() {
        return user.getUid();
    }

    public String getCurrentUserEmail() {
        return user.getEmail();
    }

    public double[] getCurrentUserPosition() {
        final double[] i = {-1, -1};
        mDatabase.child("User").child(getCurrentUserUID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                User value = dataSnapshot.getValue(User.class);
                i[0] = value.getUserLatitude();
                i[1] = value.getUserLongitude();
                Log.d("ReadDB", "Location is: " + i[0] + ", " + i[1]);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ReadDB", "Failed to read value.", error.toException());
            }
        });
        return i;
    }

    public void getCurrentUserPhoneNumber(final UserDAOCallback cb) {
        mDatabase.child("User").child(getCurrentUserUID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                User value = dataSnapshot.getValue(User.class);
                cb.onSuccess(value);
                Log.d("ReadDB", "Telephone is: " + value.getPhoneNumber());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                cb.onError(error.toException());
                // Failed to read value
                Log.w("ReadDB", "Failed to read value.", error.toException());
            }
        });
    }

    public void writeUserLocation(double lat, double lng) {
        mDatabase.child("User").child(getCurrentUserUID()).child("userLatitude").setValue(lat);
        mDatabase.child("User").child(getCurrentUserUID()).child("userLongitude").setValue(lng);

    }

    public void writeUserPhoneNumber(String phoneNumber) {
        mDatabase.child("User").child(getCurrentUserUID()).child("phoneNumber").setValue(phoneNumber);
    }

    public void writeUser(User user) {
        mDatabase.child("User").child(getCurrentUserUID()).setValue(user);
    }
}
