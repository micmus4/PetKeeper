package pl.petkeeper.ui.filter;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import pl.petkeeper.R;
import pl.petkeeper.databinding.FragmentDatemarkBinding;
import pl.petkeeper.databinding.FragmentFilterBinding;
import pl.petkeeper.db.AnimalDatabase;
import pl.petkeeper.ui.calendar.CalendarViewModel;

public class FilterFragment extends Fragment implements View.OnClickListener{

    private FragmentFilterBinding binding;
    private NavController navController;

    private Spinner speciesSpinner;
    private Spinner petTypeSpinner;

    public FilterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FilterViewModel filterViewModel =
                new ViewModelProvider(this).get(FilterViewModel.class);
        binding = FragmentFilterBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        createSpinners(root);
        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController( view );
        Button goBackButton = view.findViewById( R.id.goBackFromFilterToHomeFragmentButton );
        Button saveButton = view.findViewById( R.id.saveFilter );

        goBackButton.setOnClickListener( this );
        saveButton.setOnClickListener( this );
    }

    private void createSpinners(View aView) {
        this.speciesSpinner = aView.findViewById( R.id.speciesSpinner );

        String[] species = new String[]{"None", "Husky", "Spaniel", "Bulldog", "Collie", "Sphynx",
        "Peterbald", "Ragdoll", "Ocicat"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(aView.getContext(), android.R.layout.simple_spinner_dropdown_item, species);
        this.speciesSpinner.setAdapter(adapter);

        this.petTypeSpinner = aView.findViewById( R.id.petTypeSpinner );

        String[] petTypes = new String[]{"None", "Dog", "Cat"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(aView.getContext(), android.R.layout.simple_spinner_dropdown_item, petTypes);
        this.petTypeSpinner.setAdapter(adapter2);

    }

    @Override
    public void onClick(View view) {
        if( view.getId() == R.id.goBackFromFilterToHomeFragmentButton )
        {
            navController.navigate( R.id.action_navigation_filter_to_navigation_home );
        }
        else if( view.getId() == R.id.saveFilter ){
            setFilter();
        }
    }

    private void setFilter() {
        Bundle bundle = new Bundle();
        bundle.putString("speciesFilter", speciesSpinner.getSelectedItem().toString() );
        bundle.putString("petTypeFilter", speciesSpinner.getSelectedItem().toString() );
        getFragmentManager().setFragmentResult( "filterFragmentArgs", bundle );
        navController.navigate( R.id.action_navigation_filter_to_navigation_home, bundle );
    }
}