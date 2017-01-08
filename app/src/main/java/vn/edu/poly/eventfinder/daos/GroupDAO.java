package vn.edu.poly.eventfinder.daos;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.eventfinder.entities.Group;

public class GroupDAO {
    private DatabaseReference mDatabase;

    public GroupDAO() {
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void getGroupFromDB() {
        mDatabase.child("Group").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                List<Group> groupList = new ArrayList<Group>();
                for (DataSnapshot groupSnapshot : dataSnapshot.getChildren()) {
                    Group value = groupSnapshot.getValue(Group.class);
                    groupList.add(value);
                }
                // Log.d("ReadDB", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ReadDB", "Failed to read value.", error.toException());
            }
        });
    }

    public void addGroupToDB(Group group) {
        mDatabase.child("Group").child(String.valueOf(group.getId())).setValue(group);
    }
}
