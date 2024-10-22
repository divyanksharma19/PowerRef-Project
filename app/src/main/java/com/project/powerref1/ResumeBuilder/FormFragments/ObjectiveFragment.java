package com.project.powerref1.ResumeBuilder.FormFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.project.powerref1.R;
import com.project.powerref1.ResumeBuilder.Profile;


public class ObjectiveFragment extends Fragment {
//
//    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//    FirebaseUser user = firebaseAuth.getCurrentUser();
//    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users/"+user.getUid()+"/profiles");

    Profile userProfile;
    String ProfileId;

    private TextInputEditText form_obj_et_obj;

    public ObjectiveFragment() {
        // Required empty public constructor
    }

    public static ObjectiveFragment newInstance() {
        ObjectiveFragment fragment = new ObjectiveFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EditDetailsActivity editDetailsActivity = (EditDetailsActivity) getActivity();
//        ProfileId = editDetailsActivity.getProfileId();
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_objective, container, false);
        form_obj_et_obj = view.findViewById(R.id.form_obj_et_obj);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        databaseReference.child(ProfileId)
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        userProfile = dataSnapshot.getValue(Profile.class);
//                        if(userProfile != null && userProfile.getObjective() != null && !userProfile.getObjective().isEmpty()){
//                            form_obj_et_obj.setText(userProfile.getObjective().toString());
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        throw databaseError.toException();
//                    }
//                });

        Button submitBtn = getView().findViewById(R.id.form_obj_btn_save);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });
    }

    public void submitData(){
        String objective = form_obj_et_obj.getText().toString().trim();

//        databaseReference.child(ProfileId).child("objective").setValue(objective);

        Toast.makeText(getContext(), "Data saved", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
