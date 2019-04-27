package com.artemis.mynotes.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.artemis.mynotes.Model.NoteModel;
import com.artemis.mynotes.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNoteFragment extends Fragment {
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("MyNote");
    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

    EditText editNote;
    String noteId;
    NoteModel myNote;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_note, container, false);

        editNote = view.findViewById(R.id.addNote_edit_note);

        final Button editButton = view.findViewById(R.id.addNote_edit_button);

        editButton.setOnClickListener(v -> {
            String noteID = editNote.getText().toString();

            addNoteDatabase(noteID);
        });

        return view;
    }

    private void addNoteDatabase(String note) {
        myNote = new NoteModel("Test", note, "--");
        noteId = mRef.push().getKey();

        String mUid = mUser.getUid();
        if (noteId != null) {
            mRef.child("notes").child(mUid).child(noteId).setValue(myNote);
        }

        Toast.makeText(getContext(), "Note Added", Toast.LENGTH_SHORT).show();
        editNote.setText("");
    }

}
