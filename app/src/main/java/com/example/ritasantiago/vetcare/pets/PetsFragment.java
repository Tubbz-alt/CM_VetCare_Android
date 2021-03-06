package com.example.ritasantiago.vetcare.pets;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ritasantiago.vetcare.R;
import com.example.ritasantiago.vetcare.db.entity.Animal;
import com.example.ritasantiago.vetcare.interfaces.PetClickListener;
import com.example.ritasantiago.vetcare.pets.adapters.RVPetAdapter;
import com.example.ritasantiago.vetcare.pets.profile.ProfilePetFragment;
import com.example.ritasantiago.vetcare.pets.vitalsigns.VitalSignsFragment;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import static com.example.ritasantiago.vetcare.db.entity.FirebaseFields.*;

/**
 * Created by raquelramos on 04-03-2018.
 */

public class PetsFragment extends Fragment implements PetClickListener {

    public static final String ANIMAL_BUNDLE_KEY = "animal_bundle";
    private RVPetAdapter adapter;

    private void initializeData(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Animals").get().addOnCompleteListener(task -> {
        if(task.isSuccessful()){
            QuerySnapshot query = task.getResult();
            List<DocumentSnapshot> data = query.getDocuments();
            for (int i = 0; i < data.size(); i++)
            {
                byte imagem[] = Base64.decode(data.get(i).get(IMAGE_ID).toString(),Base64.NO_WRAP | Base64.URL_SAFE);
                Bitmap bmp = BitmapFactory.decodeByteArray(imagem, 0, imagem.length);
                adapter.addAnimal(new Animal(data.get(i).get(NAME).toString(),
                                             data.get(i).get(SEX).toString(),
                                             bmp,
                                             data.get(i).get(WEIGHT).toString(),
                                             data.get(i).get(SPECIE).toString(),
                                             data.get(i).get(DOB).toString(),
                                             data.get(i).get(BREED).toString(),
                                             data.get(i).get(COAT).toString(),
                                             data.get(i).get(OWNER_NAME).toString(),
                                             data.get(i).get(PHONE_KEY).toString()));

            }
        }
        }).addOnFailureListener(e -> Log.d("TAG", e.toString()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View rootView = inflater.inflate(R.layout.fragment_pets, container, false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.animals);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        adapter = new RVPetAdapter(this);
        rv.setAdapter(adapter);

        initializeData();

        FloatingActionButton button = (FloatingActionButton) rootView.findViewById(R.id.button_addPet);
        button.setOnClickListener(v -> {
            Fragment fragment = new AddPetFragment();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.addToBackStack(null);
            ft.commit();
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("PetsFragment");
    }

    @Override
    public void onPetClick(Animal animal) {
        Fragment fragment = new ProfilePetFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ANIMAL_BUNDLE_KEY, animal);
        fragment.setArguments(bundle);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onPetSignalClick(Animal animal)
    {
        Fragment fragment = new VitalSignsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ANIMAL_BUNDLE_KEY, animal);
        fragment.setArguments(bundle);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
