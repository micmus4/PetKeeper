package pl.petkeeper.ui.vet;

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
import android.widget.Button;

import pl.petkeeper.R;
import pl.petkeeper.databinding.FragmentDatemarkBinding;
import pl.petkeeper.databinding.FragmentVetBinding;
import pl.petkeeper.db.AnimalDatabase;
import pl.petkeeper.ui.calendar.CalendarViewModel;

public class VetFragment extends Fragment {

    private FragmentVetBinding binding;
    private VetViewModel vetViewModel;
    private NavController navController;

    public VetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vetViewModel =
                new ViewModelProvider(this).get(VetViewModel.class);
        binding = FragmentVetBinding.inflate(inflater, container, false);


        View root = binding.getRoot();
        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController( view );
    }
}