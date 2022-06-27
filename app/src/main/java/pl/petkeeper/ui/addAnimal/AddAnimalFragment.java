package pl.petkeeper.ui.addAnimal;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.room.Room;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import pl.petkeeper.MainActivity;
import pl.petkeeper.R;
import pl.petkeeper.databinding.FragmentAddAnimalBinding;
import pl.petkeeper.db.AnimalDatabase;
import pl.petkeeper.model.Animal;
import pl.petkeeper.model.Species;

public class AddAnimalFragment extends Fragment implements View.OnClickListener {

    private FragmentAddAnimalBinding binding;

    private Button datePickerToogleButton;

    private DatePickerDialog datePickerDialog;

    private AnimalDatabase animalDatabase;

    private String selectedImageName;

    private Integer specieId;

    private NavController navController;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    private final Map< ImageView, String > avatarsToFileNameMap = new HashMap<>();

    private static final int MAKE_IMAGE_DARKER = Color.argb( 150, 0, 0, 0 );

    private static final int REMOVE_DARKNESS_FROM_IMAGE = Color.argb( 0, 0, 0, 0 );

    List< ImageView > images;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddAnimalViewModel addAnimalViewModel =
                new ViewModelProvider(this).get(AddAnimalViewModel.class);

        binding = FragmentAddAnimalBinding.inflate(inflater, container, false);

        animalDatabase = Room.databaseBuilder( getContext(), AnimalDatabase.class, "animalDatabase" )
                .allowMainThreadQueries().build();
        View root = binding.getRoot();
        initSpeciesSpinner( root );
        initPhotos( root );
        Button addButton = root.findViewById( R.id.addAnimalButton );
        addButton.setOnClickListener( this );
        return root;
    }

    private void initSpeciesSpinner( View aView )
    {
        Spinner speciesDropdownList = aView.findViewById( R.id.spinner2 );
        List<Species> list = animalDatabase.getSpeciesDAO().getAllSpecies();
        ArrayAdapter<Species> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, list );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        speciesDropdownList.setAdapter(adapter);

        speciesDropdownList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                specieId = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initPhotos( View aView )
    {
        final List< String > imageNames = new ArrayList<>();
        imageNames.add( "inny_boar" );
        imageNames.add( "ulany_boar" );
        imageNames.add( "fajny_boar" );


        LinearLayout linearLayout = aView.findViewById( R.id.photoLayout );
        images = imageNames.stream().map( this::getImageFromFileName ).collect(Collectors.toList());
        images.forEach( image -> linearLayout.addView( image ) );
        images.forEach( this::initializeOnClickValidationListenerForAvatar );

    }

    private void initializeOnClickValidationListenerForAvatar( final ImageView aAvatar )
    {
        aAvatar.setOnClickListener( view ->
        {
            images.forEach( img -> img.setColorFilter( MAKE_IMAGE_DARKER ) );
            aAvatar.setColorFilter( REMOVE_DARKNESS_FROM_IMAGE );
            selectedImageName = avatarsToFileNameMap.get( aAvatar );
        } );
    }

    private ImageView getImageFromFileName(String aFileName ) {
        final Resources resources = getResources();
        final Integer resourceId =
                resources.getIdentifier(aFileName, "drawable", getContext().getPackageName());


        final ImageView imageView = new ImageView(getContext());

        imageView.setImageResource(resourceId);
        avatarsToFileNameMap.put( imageView, aFileName );
        return imageView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick( final View aView )
    {
        if ( aView.getId() == R.id.addAnimalButton )
        {
            Animal animal = new Animal();
            animal.setDateOfBirth( ((Button)this.getView().findViewById( R.id.dateOfBirthPicker )).getText().toString() );
            animal.setName( ((TextInputEditText)this.getView().findViewById( R.id.animalInputText )).getText().toString() );
            animal.setPhotoName( selectedImageName );
            animal.setSpecieId( specieId );

            animalDatabase.getAnimalDAO().insertAnimal( animal );
            navController.navigate( R.id.action_navigation_dashboard_to_navigation_home );
        }
        else if ( aView.getId() == R.id.cancelAddButton )
        {
            navController.navigate( R.id.action_navigation_dashboard_to_navigation_home );
        }
        else if( aView.getId() == R.id.dateOfBirthPicker )
        {
            datePickerDialog.show();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController( view );
        datePickerToogleButton = view.findViewById( R.id.dateOfBirthPicker );
        Button addButton = view.findViewById( R.id.addAnimalButton );
        Button cancelButton = view.findViewById( R.id.cancelAddButton );

        addButton.setOnClickListener( this );
        cancelButton.setOnClickListener( this );
        datePickerToogleButton.setOnClickListener( this );
        Calendar calendar = Calendar.getInstance();

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                datePickerToogleButton.setText( i2 + "/" + i1 + "/" + i );
            }
        };

        int year = calendar.get( Calendar.YEAR );
        int month = calendar.get( Calendar.MONTH );
        int day = calendar.get( Calendar.DAY_OF_MONTH );

        datePickerDialog = new DatePickerDialog( getContext(), AlertDialog.THEME_HOLO_LIGHT,
                dateSetListener, year, month, day  );




    }
}