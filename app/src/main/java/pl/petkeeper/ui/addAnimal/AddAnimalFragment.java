package pl.petkeeper.ui.addAnimal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import pl.petkeeper.databinding.FragmentAddAnimalBinding;

public class AddAnimalFragment extends Fragment {

    private FragmentAddAnimalBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddAnimalViewModel addAnimalViewModel =
                new ViewModelProvider(this).get(AddAnimalViewModel.class);

        binding = FragmentAddAnimalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}