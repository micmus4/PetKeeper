package pl.petkeeper.ui.animalData;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import pl.petkeeper.R;
import pl.petkeeper.databinding.FragmentAnimalDataBinding;
import pl.petkeeper.db.AnimalDatabase;
import pl.petkeeper.model.Animal;

public class AnimalDataFragment extends Fragment {

    private FragmentAnimalDataBinding binding;

    private int animalId;

    private AnimalDatabase animalDatabase;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AnimalDataViewModel animalDataViewModel =
                new ViewModelProvider(this).get(AnimalDataViewModel.class);

        binding = FragmentAnimalDataBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setAnimalId( root );
        animalDatabase = Room.databaseBuilder( getContext(), AnimalDatabase.class, "animalDatabase" )
                        .allowMainThreadQueries().build();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setAnimalId( View aView )
    {
        getFragmentManager().setFragmentResultListener("homeFragmentArgs", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                animalId = result.getInt( "animalID" );
                initializeAnimalOnScreen( animalDatabase.getAnimalDAO().getAnimal( animalId ), aView );
            }
        });
    }

    private void initializeAnimalOnScreen(Animal animal, View aView)
    {
        TextView animalName = aView.findViewById(R.id.animalNameTextView );
        TextView dateOfBirth = aView.findViewById(R.id.dateOfBirthTextView );
        TextView age = aView.findViewById(R.id.animalAge );
        TextView specie = aView.findViewById(R.id.specieTextView );
        ImageView image = aView.findViewById(R.id.animalImage );

        String[] dateParts = animal.getDateOfBirth().split( "/" );
        LocalDate animalDateOfBirth = LocalDate.of( Integer.parseInt( dateParts[2] ),
                Integer.parseInt( dateParts[1] ), Integer.parseInt( dateParts[0] ) );
        LocalDate nowDate = LocalDate.now();

        animalName.setText( animal.getName() );
        dateOfBirth.setText( animal.getDateOfBirth() );
        age.setText(String.valueOf( ChronoUnit.YEARS.between( animalDateOfBirth, nowDate ) ));
        image.setImageResource( getResources()
                .getIdentifier( animal.getPhotoName(), "drawable",
                        getContext().getPackageName() ) );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}