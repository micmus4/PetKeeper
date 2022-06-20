package pl.petkeeper.ui.animalData;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;

import pl.petkeeper.R;
import pl.petkeeper.databinding.FragmentAnimalDataBinding;

public class AnimalDataFragment extends Fragment {

    private FragmentAnimalDataBinding binding;

    private int animalId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AnimalDataViewModel animalDataViewModel =
                new ViewModelProvider(this).get(AnimalDataViewModel.class);

        binding = FragmentAnimalDataBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setAnimalId( root );
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
                TextView textView = aView.findViewById(R.id.animalNameTextView );
                textView.setText( "test" );
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}