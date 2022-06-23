package pl.petkeeper.ui.home;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
    }

    private void initAnimalsOnFragment( View view )
    {
        getDemoData();
        final LinearLayout linearLayout = view.findViewById( R.id.mainWindowLinearLayout );
        for( final Animal animal : animalDatabase.getAnimalDAO().getAllAnimals() )
        {
            final CardView cardView = new CardView( linearLayout.getContext() );
            final LinearLayout innerLinearLayout = new LinearLayout( cardView.getContext() );
            final TextView textView = new TextView( innerLinearLayout.getContext() );
            final ImageView imageView = new ImageView( innerLinearLayout.getContext() );

            final Integer resourceId =
                    getResources()
                            .getIdentifier( animal.getPhotoName(), "drawable",
                                    getContext().getPackageName() );

            textView.setText( animal.getName() );
            imageView.setImageResource( resourceId );

            innerLinearLayout.addView( imageView );
            innerLinearLayout.addView( textView );

            cardView.addView( innerLinearLayout );
            cardViewToAnimalIDMap.put( cardView, animal.getId() );
            initializeOnDoubleClickListenerOnCardViews( cardView );

            linearLayout.addView( cardView );
        }
    }

    private void getDemoData()
    {
        Animal animal1 =
                new Animal(1, "Stefan", "31/12/1998",
                        "ulany_boar", null );
        Animal animal2 =
                new Animal(2, "Maurycy", "31/12/1998",
                        "ulany_boar", null );
        Animal animal3 =
                new Animal(3, "Bogdan", "31/12/1998",
                        "ulany_boar", null );
        Animal animal4 =
                new Animal(4, "Genowefa", "31/12/1998",
                        "ulany_boar", null );
        Animal animal5 =
                new Animal(5, "Rumcajs", "31/12/1998",
                        "ulany_boar", null );

        Alert alert1 = new Alert(1, 1, "Nakarm", "Nakarm pieska", "31/12/1998");
        Alert alert2 = new Alert(2, 1, "Wyprowadz", "Wyprowadz pieska", "31/12/1998");
        Alert alert3 = new Alert(3, 1, "Zajeb", "Zajeb pieska", "31/12/1998");

        Species specie1 = new Species( 1, "Husky", "wikipedia!!" );
        Species specie2 = new Species( 2, "York", "wikipedia!!" );
        Species specie3 = new Species( 3, "Chuj", "wikipedia!!" );



        if( animalDatabase.getAnimalDAO().getAllAnimals().isEmpty() ) {
            animalDatabase.getAnimalDAO().insertAnimal(animal1);
            animalDatabase.getAnimalDAO().insertAnimal(animal2);
            animalDatabase.getAnimalDAO().insertAnimal(animal3);
            animalDatabase.getAnimalDAO().insertAnimal(animal4);
            animalDatabase.getAnimalDAO().insertAnimal(animal5);
        }

        if( animalDatabase.getAlertDAO().getAllAlerts().isEmpty() )
        {
            animalDatabase.getAlertDAO().insertAlert( alert1 );
            animalDatabase.getAlertDAO().insertAlert( alert2 );
            animalDatabase.getAlertDAO().insertAlert( alert3 );
        }

        if( animalDatabase.getSpeciesDAO().getAllSpecies().isEmpty() )
        {
            animalDatabase.getSpeciesDAO().insertSpecie( specie1 );
            animalDatabase.getSpeciesDAO().insertSpecie( specie2 );
            animalDatabase.getSpeciesDAO().insertSpecie( specie3 );
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
//        if ( aView.getId() == R.id.addBoarButton )
//        {
//            navigateToAddDzikFragment();
//        }
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Bundle bundle = new Bundle();
            bundle.putInt( "animalID", e.getSource() );
            getFragmentManager().setFragmentResult( "homeFragmentArgs", bundle );
            return true;
        }
    }
}