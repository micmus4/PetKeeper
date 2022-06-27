package pl.petkeeper.ui.datemark;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDate;

import pl.petkeeper.R;
import pl.petkeeper.databinding.FragmentDatemarkBinding;
import pl.petkeeper.model.Animal;
import pl.petkeeper.ui.calendar.CalendarViewModel;

public class DatemarkFragment extends Fragment implements View.OnClickListener{

    private FragmentDatemarkBinding binding;
    private String date;
    private NavController navController;

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
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController( view );
        Button goBackButton = view.findViewById( R.id.goBackFromDatemarkToCalendarButton );
        goBackButton.setOnClickListener( this );
    }


    @Override
    public void onClick(View view) {
        if( view.getId() == R.id.goBackFromDatemarkToCalendarButton )
        {
            navController.navigate( R.id.action_navigation_datemark_to_navigation_calendar );
        }
    }

}