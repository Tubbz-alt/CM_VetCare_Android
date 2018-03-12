package com.example.ritasantiago.vetcare;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ritasantiago.vetcare.firebase.Animal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import static com.example.ritasantiago.vetcare.firebase.FirebaseFields.BREED;
import static com.example.ritasantiago.vetcare.firebase.FirebaseFields.COAT;
import static com.example.ritasantiago.vetcare.firebase.FirebaseFields.DOB;
import static com.example.ritasantiago.vetcare.firebase.FirebaseFields.IMAGE_ID;
import static com.example.ritasantiago.vetcare.firebase.FirebaseFields.NAME;
import static com.example.ritasantiago.vetcare.firebase.FirebaseFields.OWNER_NAME;
import static com.example.ritasantiago.vetcare.firebase.FirebaseFields.SEX;
import static com.example.ritasantiago.vetcare.firebase.FirebaseFields.SPECIE;
import static com.example.ritasantiago.vetcare.firebase.FirebaseFields.WEIGHT;

/**
 * Created by raquelramos on 04-03-2018.
 */

public class MedicineTabFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View rootView = inflater.inflate(R.layout.fragment_medicine_pet, container, false);

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("MedicinePetTabFragment");
    }
}
