package com.artemis.mynotes.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.artemis.mynotes.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class NotesFragment extends Fragment {

    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = mDatabase.getReference("MyNote");

    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

    ListView listView;
    ArrayAdapter<String> arrayNotesAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_notes, container, false);

        listView = view.findViewById(R.id.notes_list_view);
        ImageButton imageButton = view.findViewById(R.id.notes_add);

        imageButton.setOnClickListener(v -> {

            if (getFragmentManager() != null) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_content, new AddNoteFragment());
                transaction.commit();
            }

        });

        ArrayList<String> noteList = getMyNotes();
        arrayNotesAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_list_item_1, android.R.id.text1, noteList);

        listView.setAdapter(arrayNotesAdapter);



        return view;
    }

    ArrayList<String> myNote;
    String note;

    private ArrayList<String> getMyNotes() {
        myNote = new ArrayList<>();

        String mUid = mUser.getUid();

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    note = Objects.requireNonNull(ds.child("notes").child(mUid).getValue()).toString();
                    myNote.add(note);
                }
                arrayNotesAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return myNote;
    }

}
