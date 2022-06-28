package pl.petkeeper.ui.datemark;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.List;

import pl.petkeeper.R;
import pl.petkeeper.databinding.FragmentDatemarkBinding;
import pl.petkeeper.db.AnimalDatabase;
import pl.petkeeper.model.Animal;
import pl.petkeeper.model.Datemark;
import pl.petkeeper.ui.calendar.CalendarViewModel;

public class DatemarkFragment extends Fragment implements View.OnClickListener{

    private FragmentDatemarkBinding binding;
    private String date;
    private NavController navController;
    private AnimalDatabase animalDatabase;

    public DatemarkFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CalendarViewModel calendarViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);
        binding = FragmentDatemarkBinding.inflate(inflater, container, false);

        animalDatabase = Room.databaseBuilder( getContext(), AnimalDatabase.class, "animalDatabase" )
                .allowMainThreadQueries().build();

        View root = binding.getRoot();

        setDate( root );
        // Inflate the layout for this fragment
        return root;
    }

    private void setDate( View aView )
    {
        getFragmentManager().setFragmentResultListener("calendarFragmentArgs", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                date = result.getString( "date" );
                initializeDateOnScreen(date, aView);
            }
        });
    }

    private void initializeDateOnScreen(String date, View aView){
        this.date = date;
        TextView dateTV = aView.findViewById(R.id.datemarkDateTextView);
        dateTV.setText(date);
        EditText note = aView.findViewById(R.id.datemarkNote);
        Datemark datemark = animalDatabase.getDatemarkDAO().getDatemark(date);
        if (datemark!=null) {
            note.setText(datemark.getText());
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController( view );
        Button goBackButton = view.findViewById( R.id.goBackFromDatemarkToCalendarButton );
        Button saveButton = view.findViewById( R.id.saveDatemark );
        Button deleteButton = view.findViewById( R.id.deleteDatemark );

        goBackButton.setOnClickListener( this );
        saveButton.setOnClickListener( this );
        deleteButton.setOnClickListener( this );
    }


    @Override
    public void onClick(View view) {
        if( view.getId() == R.id.goBackFromDatemarkToCalendarButton )
        {
            navController.navigate( R.id.action_navigation_datemark_to_navigation_calendar );
        }
        else if( view.getId() == R.id.saveDatemark ){
            setDatemarkData();
        }
        else if ( view.getId() == R.id.deleteDatemark ){
            deleteDatemark();
        }
    }

    private void deleteDatemark() {
        animalDatabase.getDatemarkDAO().deleteDatemark( date );
        navController.navigate( R.id.action_navigation_datemark_to_navigation_calendar );
    }

    private void setDatemarkData() {
        animalDatabase.getDatemarkDAO().deleteDatemark( date );
        Datemark datemark = new Datemark();

        datemark.setDate(date);
        datemark.setText( ((EditText)this.getView().findViewById(R.id.datemarkNote)).getText().toString() );

        animalDatabase.getDatemarkDAO().insertDatemark( datemark );
        navController.navigate( R.id.action_navigation_datemark_to_navigation_home );

    }

}