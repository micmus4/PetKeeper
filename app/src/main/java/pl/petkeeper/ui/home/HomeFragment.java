package pl.petkeeper.ui.home;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import pl.petkeeper.R;
import pl.petkeeper.databinding.FragmentHomeBinding;
import pl.petkeeper.model.Animal;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initAnimalsOnFragment( view );
    }

    private void initAnimalsOnFragment( View view )
    {
        final LinearLayout linearLayout = view.findViewById( R.id.mainWindowLinearLayout );
        for( final Animal animal : getDemoData() )
        {
            final CardView cardView = new CardView( linearLayout.getContext() );
            final LinearLayout innerLinearLayout = new LinearLayout( cardView.getContext() );
            final TextView textView = new TextView( innerLinearLayout.getContext() );
            final ImageView imageView = new ImageView( innerLinearLayout.getContext() );

            final Integer resourceId =
                    getResources()
                            .getIdentifier( animal.getPhotoName(), "drawable",
                                    getContext().getPackageName() );

            textView.setText( animal.getName() );
            imageView.setImageResource( resourceId );

            innerLinearLayout.addView( imageView );
            innerLinearLayout.addView( textView );

            cardView.addView( innerLinearLayout );

            linearLayout.addView( cardView );
        }
    }

    private List< Animal > getDemoData()
    {
        Animal animal1 =
                new Animal(1, "Stefan", new Date(2018, 1, 1),
                        "ulany_boar", null );
        Animal animal2 =
                new Animal(2, "Maurycy", new Date(2018, 1, 1),
                        "ulany_boar", null );
        Animal animal3 =
                new Animal(3, "Bogdan", new Date(2018, 1, 1),
                        "ulany_boar", null );
        Animal animal4 =
                new Animal(4, "Genowefa", new Date(2018, 1, 1),
                        "ulany_boar", null );
        Animal animal5 =
                new Animal(5, "Rumcajs", new Date(2018, 1, 1),
                        "ulany_boar", null );

        List< Animal > animals = new ArrayList<>();
        animals.add( animal1 );
        animals.add( animal2 );
        animals.add( animal3 );
        animals.add( animal4 );
        animals.add( animal5 );
        return animals;
    }
}