package com.example.ritasantiago.vetcare.pets.hospitalisation;

import android.os.Bundle;
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
import com.example.ritasantiago.vetcare.pets.hospitalisation.adapters.MedicineAdapter;
import com.example.ritasantiago.vetcare.db.entity.Animal;
import com.example.ritasantiago.vetcare.db.entity.Medicine;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raquelramos on 04-03-2018.
 */

public class MedicineTabFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    public static final String ANIMAL_BUNDLE_KEY = "animal_bundle";
    private List<Medicine> medicines = new ArrayList<>();

    private void initData (final String animalName){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Medicines").get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                QuerySnapshot query = task.getResult();
                List<DocumentSnapshot> data = query.getDocuments();
                for (int i = 0; i < data.size(); i++) {
                    DocumentSnapshot doc = data.get(i);
                    if(doc.contains(animalName)){
                        medicines.add(new Medicine(doc.get(animalName).toString()));
                    }
                }
                mAdapter = new MedicineAdapter(medicines);
                recyclerView.setAdapter(mAdapter);
            }
        }).addOnFailureListener(e -> Log.d("TAG", e.toString()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View rootView = inflater.inflate(R.layout.fragment_medicine_tab, container, false);
        Bundle args = getArguments();
        Animal animal = (Animal) args.getSerializable(ANIMAL_BUNDLE_KEY);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_medicine);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        initData(animal.name);

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("MedicinePetTabFragment");
    }
}
