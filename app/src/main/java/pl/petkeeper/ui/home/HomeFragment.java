package pl.petkeeper.ui.home;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.petkeeper.R;
import pl.petkeeper.databinding.FragmentHomeBinding;
import pl.petkeeper.db.AnimalDatabase;
import pl.petkeeper.model.Alert;
import pl.petkeeper.model.Animal;
import pl.petkeeper.model.Species;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private FragmentHomeBinding binding;

    private GestureDetector gestureDetector;

    private Map< CardView, Integer > cardViewToAnimalIDMap;

    private AnimalDatabase animalDatabase;

    private NavController navController;

    private String speciesFilter;

    private String petTypeFilter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        gestureDetector = new GestureDetector(getContext(), new GestureListener());
        cardViewToAnimalIDMap = new HashMap<>();
        animalDatabase = Room.databaseBuilder( getContext(), AnimalDatabase.class, "animalDatabase" )
                .allowMainThreadQueries().build();

        Bundle bundle = getArguments();
        if (bundle != null) {
            speciesFilter = (String) bundle.getSerializable("speciesFilter");
            petTypeFilter = (String) bundle.getSerializable("petTypeFilter");
        }
        else {
            speciesFilter = "None";
            petTypeFilter = "None";
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initAnimalsOnFragment( view );
        navController = Navigation.findNavController( view );
        Button addButton = view.findViewById( R.id.mainWindowAddButton );
        Button calendarButton = view.findViewById( R.id.mainWindowCalendarButton );
        Button filterButton = view.findViewById( R.id.mainWindowFilterButton );
        addButton.setOnClickListener( this );
        calendarButton.setOnClickListener( this );
        filterButton.setOnClickListener( this );
    }


    private void initAnimalsOnFragment( View view )
    {
        getDemoData();
        final LinearLayout linearLayout = view.findViewById( R.id.mainWindowLinearLayout );
        for( final Animal animal : animalDatabase.getAnimalDAO().getAllAnimals() ) {
            if (petTypeFilter.equals("None") | animalDatabase.getSpeciesDAO().getSpecie(
                    animal.getSpecieId()).getType().equals( petTypeFilter )
            ) {
                if (speciesFilter.equals("None") | animalDatabase.getSpeciesDAO().getSpecie(
                        animal.getSpecieId()).getName().equals( speciesFilter )) {
                    final CardView cardView = new CardView(linearLayout.getContext());
                    final LinearLayout innerLinearLayout = new LinearLayout(cardView.getContext());
                    final TextView textView = new TextView(innerLinearLayout.getContext());
                    final ImageView imageView = new ImageView(innerLinearLayout.getContext());

                    final Integer resourceId =
                            getResources()
                                    .getIdentifier(animal.getPhotoName(), "drawable",
                                            getContext().getPackageName());

                    textView.setText(animal.getName());
                    imageView.setImageResource(resourceId);

                    innerLinearLayout.addView(imageView);
                    innerLinearLayout.addView(textView);

                    cardView.addView(innerLinearLayout);
                    cardViewToAnimalIDMap.put(cardView, animal.getId());
                    initializeOnDoubleClickListenerOnCardViews(cardView);

                    linearLayout.addView(cardView);
                }
            }
        }
    }

    private void getDemoData()
    {
        Animal animal1 =
                new Animal(1, "Stefan", "31/12/1998",
                        "ulany_boar", 1 );
        Animal animal2 =
                new Animal(2, "Maurycy", "31/12/1998",
                        "ulany_boar", 2 );
        Animal animal3 =
                new Animal(3, "Bogdan", "31/12/1998",
                        "ulany_boar", 3 );
        Animal animal4 =
                new Animal(4, "Genowefa", "31/12/1998",
                        "ulany_boar", 4 );
        Animal animal5 =
                new Animal(5, "Rumcajs", "31/12/1998",
                        "ulany_boar", 5 );

/*        Alert alert1 = new Alert(1, 1, "Nakarm", "Nakarm pieska", "31/12/1998");
        Alert alert2 = new Alert(2, 1, "Wyprowadz", "Wyprowadz pieska", "31/12/1998");
        Alert alert3 = new Alert(3, 1, "Odbierz", "Odbierz pieska", "31/12/1998");*/

        Species specie1 = new Species( 1, "Husky", "Dog");
        Species specie2 = new Species( 2, "Spaniel", "Dog");
        Species specie3 = new Species( 3, "Bulldog", "Dog");
        Species specie4 = new Species(4, "Collie", "Dog");

        Species specie5 = new Species(5, "Sphynx", "Cat");
        Species specie6 = new Species(6, "Peterbald", "Cat");
        Species specie7 = new Species(7, "Ragdoll", "Cat");
        Species specie8 = new Species(8, "Ocicat", "Cat");




        if( animalDatabase.getAnimalDAO().getAllAnimals().isEmpty() ) {
            animalDatabase.getAnimalDAO().insertAnimal(animal1);
            animalDatabase.getAnimalDAO().insertAnimal(animal2);
            animalDatabase.getAnimalDAO().insertAnimal(animal3);
            animalDatabase.getAnimalDAO().insertAnimal(animal4);
            animalDatabase.getAnimalDAO().insertAnimal(animal5);
        }

/*        if( animalDatabase.getAlertDAO().getAllAlerts().isEmpty() )
        {
            animalDatabase.getAlertDAO().insertAlert( alert1 );
            animalDatabase.getAlertDAO().insertAlert( alert2 );
            animalDatabase.getAlertDAO().insertAlert( alert3 );
        }*/

        if( animalDatabase.getSpeciesDAO().getAllSpecies().isEmpty() )
        {
            animalDatabase.getSpeciesDAO().insertSpecie( specie1 );
            animalDatabase.getSpeciesDAO().insertSpecie( specie2 );
            animalDatabase.getSpeciesDAO().insertSpecie( specie3 );
            animalDatabase.getSpeciesDAO().insertSpecie( specie4 );

            animalDatabase.getSpeciesDAO().insertSpecie( specie5 );
            animalDatabase.getSpeciesDAO().insertSpecie( specie6 );
            animalDatabase.getSpeciesDAO().insertSpecie( specie7 );
            animalDatabase.getSpeciesDAO().insertSpecie( specie8 );
        }


    }

    private void initializeOnDoubleClickListenerOnCardViews( final CardView aCardView )
    {
        aCardView.setOnTouchListener((view, motionEvent) -> {

            // we set animal id as source of event.
            motionEvent.setSource( cardViewToAnimalIDMap.get( aCardView ) );
            gestureDetector.onTouchEvent( motionEvent );
            return true;
        });
    }



    @Override
    public void onClick( final View aView )
    {
        if ( aView.getId() == R.id.mainWindowAddButton )
        {
            navController.navigate( R.id.action_navigation_home_to_navigation_dashboard );
        }
        else if ( aView.getId() == R.id.mainWindowCalendarButton )
        {
            navController.navigate( R.id.action_navigation_home_to_navigation_calendar );
        }
        else if ( aView.getId() == R.id.mainWindowFilterButton )
        {
            navController.navigate( R.id.action_navigation_home_to_navigation_filter );
        }
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Bundle bundle = new Bundle();
            bundle.putInt( "animalID", e.getSource() );
            getFragmentManager().setFragmentResult( "homeFragmentArgs", bundle );
            navController.navigate( R.id.action_navigation_home_to_navigation_animal_data, bundle );
            return true;
        }
    }
}