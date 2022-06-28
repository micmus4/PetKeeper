package pl.petkeeper.ui.animalData;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import pl.petkeeper.R;
import pl.petkeeper.databinding.FragmentAddAlertBinding;
import pl.petkeeper.db.AnimalDatabase;


public class AddAlertFragment extends Fragment implements View.OnClickListener{

    private FragmentAddAlertBinding binding;
    private AnimalDatabase animalDatabase;
    private String date;
    private String hour;
    private int animalID;

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

    }
}