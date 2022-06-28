package pl.petkeeper.ui.animalData;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import pl.petkeeper.R;
import pl.petkeeper.databinding.FragmentAddAlertBinding;
import pl.petkeeper.db.AnimalDatabase;
import pl.petkeeper.model.Alert;
import pl.petkeeper.model.Datemark;


public class AddAlertFragment extends Fragment implements View.OnClickListener{

    private FragmentAddAlertBinding binding;
    private AnimalDatabase animalDatabase;
    private String date;
    private String hour;
    private int animalID;
    private NavController navController;

    public AddAlertFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddAlertBinding.inflate(inflater, container, false);

        animalDatabase = Room.databaseBuilder( getContext(), AnimalDatabase.class, "animalDatabase" )
                .allowMainThreadQueries().build();

        View root = binding.getRoot();

        setDateAndHour( root );
        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        Button cancelButton = view.findViewById( R.id.cancelSavingAlertButton );
        Button saveButton = view.findViewById( R.id.saveAlertButton );

        cancelButton.setOnClickListener( this );
        saveButton.setOnClickListener( this );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}


    private void setDateAndHour( View aView )
    {
        getFragmentManager().setFragmentResultListener("animalDataFragmentArgs", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                date = result.getString( "date" );
                hour = result.getString("hour");
                animalID = result.getInt( "animalID" );
                initializeDateOnScreen(date, hour, animalID, aView);
            }
        });
    }

    private void initializeDateOnScreen(String date, String hour, int animalID, View aView) {
        this.date = date;
        this.hour = hour;
        this.animalID = animalID;
        TextView dateTV = aView.findViewById( R.id.chosenDateTV );
        TextView hourTV = aView.findViewById( R.id.chosenHourTV );
        dateTV.setText( date );
        hourTV.setText( hour );
        EditText note = aView.findViewById( R.id.editTextAlertNote );
    }

    @Override
    public void onClick(View view) {
        if( view.getId() == R.id.cancelSavingAlertButton )
        {
            navController.navigate( R.id.action_navigation_addAlert_to_navigation_home );
        }
        if( view.getId() == R.id.saveAlertButton )
        {
            setAlertData();
        }

    }

    private void setAlertData() {
        Alert alert = new Alert();

        alert.setAnimalId( animalID );
        alert.setDueDate( date );
        alert.setDueHour( hour );
        alert.setDescription( ((EditText)this.getView().findViewById(R.id.editTextAlertNote)).getText().toString() );

        animalDatabase.getAlertDAO().insertAlert( alert );
        navController.navigate( R.id.action_navigation_addAlert_to_navigation_home );
    }
}