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

import pl.petkeeper.R;
import pl.petkeeper.databinding.FragmentHomeBinding;

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
        LinearLayout linearLayout = view.findViewById( R.id.mainWindowLinearLayout );

        // for loop will be replaced with for-each loop with Animal objects, but the mechanism
        // should remain mostly the same (needs frontend adjustments for sure)
        for( int i = 0; i < 3; i++ )
        {
            final CardView cardView = new CardView( linearLayout.getContext() );
            final LinearLayout innerLinearLayout = new LinearLayout( cardView.getContext() );
            final TextView textView = new TextView( innerLinearLayout.getContext() );
            final ImageView imageView = new ImageView( innerLinearLayout.getContext() );
            final Integer resourceId =
                    getResources()
                            .getIdentifier( "ulany_boar", "drawable",
                                    getContext().getPackageName() );

            textView.setText( "TEST" );
            imageView.setImageResource( resourceId );
            imageView.setForegroundGravity(Gravity.CENTER);

            innerLinearLayout.addView( imageView );
            innerLinearLayout.addView( textView );

            cardView.addView( innerLinearLayout );

            linearLayout.addView( cardView );
        }
    }
}