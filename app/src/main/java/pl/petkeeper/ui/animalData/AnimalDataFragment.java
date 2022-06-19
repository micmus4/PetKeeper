package pl.petkeeper.ui.animalData;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import pl.petkeeper.databinding.FragmentAnimalDataBinding;

public class AnimalDataFragment extends Fragment {

    private FragmentAnimalDataBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AnimalDataViewModel animalDataViewModel =
                new ViewModelProvider(this).get(AnimalDataViewModel.class);

        binding = FragmentAnimalDataBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}