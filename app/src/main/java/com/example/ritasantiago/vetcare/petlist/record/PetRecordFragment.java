package com.example.ritasantiago.vetcare.petlist.record;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ritasantiago.vetcare.R;
import com.example.ritasantiago.vetcare.db.entity.Animal;
import com.example.ritasantiago.vetcare.db.entity.Procedure;
import com.example.ritasantiago.vetcare.petlist.hospitalisation.adapters.PetRecordAdapter;
import com.example.ritasantiago.vetcare.petlist.hospitalisation.adapters.ProcedureAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.ritasantiago.vetcare.db.entity.FirebaseFields.PROCEDURE_DATE_KEY;
import static com.example.ritasantiago.vetcare.db.entity.FirebaseFields.PROCEDURE_DOCTOR_KEY;
import static com.example.ritasantiago.vetcare.db.entity.FirebaseFields.PROCEDURE_KEY;


public class PetRecordFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseFirestore db;
    private List<Procedure> procedures = new ArrayList<>();
    public static final String ANIMAL_BUNDLE_KEY = "animal_bundle";
    private Animal animal;

    private void getProcedures (final String animalName){
        db = FirebaseFirestore.getInstance();
        db.collection("Procedures").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot query = task.getResult();
                    List<DocumentSnapshot> data = query.getDocuments();
                    for (int i = 0; i < data.size(); i++) {
                        if(data.get(i).get("Animal Associated").toString().equals(animalName)){
                            procedures.add(new Procedure(data.get(i).get(PROCEDURE_KEY).toString(),
                                    data.get(i).get(PROCEDURE_DATE_KEY).toString(),
                                    data.get(i).get(PROCEDURE_DOCTOR_KEY).toString()));
                        }
                    }
                    //mAdapter = new PetRecordAdapter(procedures);
                    //recyclerView.setAdapter(mAdapter);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", e.toString());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pet_record, container, false);
        Bundle args = getArguments();
        this.animal = (Animal) args.getSerializable(ANIMAL_BUNDLE_KEY);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_historic);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        List<String> input = new ArrayList<>();
        for(int i = 0 ; i<10; i++){
            input.add("teste " +i);
        }
        mAdapter = new PetRecordAdapter(input);
        recyclerView.setAdapter(mAdapter);

        //getProcedures("f");
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Pet Record");
    }
}
