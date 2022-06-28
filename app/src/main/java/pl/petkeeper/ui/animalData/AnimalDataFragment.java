package pl.petkeeper.ui.animalData;

import static java.lang.Boolean.TRUE;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.room.Room;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;

import pl.petkeeper.R;
import pl.petkeeper.databinding.FragmentAnimalDataBinding;
import pl.petkeeper.db.AnimalDatabase;
import pl.petkeeper.model.Alert;
import pl.petkeeper.model.Animal;

public class AnimalDataFragment extends Fragment implements View.OnClickListener
    {

    private FragmentAnimalDataBinding binding;

    private int animalId;

    private AnimalDatabase animalDatabase;

    private NavController navController;

    private Button datePickerToogleButton;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    private DatePickerDialog datePickerDialog;

    private Button hourPickerToggleButton;

    private TimePickerDialog.OnTimeSetListener hourSetListener;

    private TimePickerDialog hourPickerDialog;

    private String tag;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AnimalDataViewModel animalDataViewModel =
                new ViewModelProvider(this).get(AnimalDataViewModel.class);

        binding = FragmentAnimalDataBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        animalId = getArguments().getInt("animalID");
        tag = (String) getTag();
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
        TextView specieDescription = aView.findViewById( R.id.specieDescription );
        ImageView image = aView.findViewById(R.id.animalImage );

        String[] dateParts = animal.getDateOfBirth().split( "/" );
        LocalDate animalDateOfBirth = LocalDate.of( Integer.parseInt( dateParts[2] ),
                Integer.parseInt( dateParts[1] ), Integer.parseInt( dateParts[0] ) );
        LocalDate nowDate = LocalDate.now();

        specie.setText( animalDatabase.getSpeciesDAO().getSpecie( animal.getSpecieId() ).getName() );
        animalName.setText( animal.getName() );
        dateOfBirth.setText( animal.getDateOfBirth() );
        age.setText(String.valueOf( ChronoUnit.YEARS.between( animalDateOfBirth, nowDate ) ));
        specieDescription.setText( animalDatabase.getSpeciesDAO().getSpecie( animal.getSpecieId() ).getInfo() );
        image.setImageResource( getResources()
                .getIdentifier( animal.getPhotoName(), "drawable",
                        getContext().getPackageName() ) );
    }

        private void buildDatePicker()
        {
            datePickerToogleButton.setOnClickListener( this );
            Calendar calendar = Calendar.getInstance();

            dateSetListener = (datePicker, i, i1, i2)
                    -> datePickerToogleButton.setText( i2 + "/" + (i1+1) + "/" + i );

            int year = calendar.get( Calendar.YEAR );
            int month = calendar.get( Calendar.MONTH );
            int day = calendar.get( Calendar.DAY_OF_MONTH );

            datePickerDialog = new DatePickerDialog( getContext(), AlertDialog.THEME_HOLO_LIGHT,
                    dateSetListener, year, month, day  );
        }

        private void buildHourPicker()
        {
            hourPickerToggleButton.setOnClickListener( this );
            Calendar calendar = Calendar.getInstance();

            hourSetListener = (datePicker, i, i1)
                    -> hourPickerToggleButton.setText( i + ":" + i1 );

            int hour = calendar.get( Calendar.HOUR_OF_DAY );
            int minute = calendar.get( Calendar.MINUTE );

            hourPickerDialog = new TimePickerDialog( getContext(), AlertDialog.THEME_HOLO_LIGHT,
                    hourSetListener, hour, minute, TRUE);
        }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAnimalId( view );

        navController = Navigation.findNavController( view );
        datePickerToogleButton = view.findViewById( R.id.dateOfNotificationPicker);
        hourPickerToggleButton = view.findViewById( R.id.hourOfNotificationPicker );
        Button addAlertButton = view.findViewById( R.id.addAlert );
        Button goBackButton = view.findViewById( R.id.goBackFromAnimalDataToHomeFragmentButton );
        Button deleteButton = view.findViewById( R.id.deletePetButton );
        downloadAlerts( view );
        buildDatePicker();
        buildHourPicker();
        addAlertButton.setOnClickListener( this );
        goBackButton.setOnClickListener( this );
        deleteButton.setOnClickListener( this );
    }


        @Override
        public void onClick(View view)
        {
            if( view.getId() == R.id.goBackFromAnimalDataToHomeFragmentButton )
            {
                navController.navigate( R.id.action_navigation_notifications_to_navigation_home );
            }
            else if ( view.getId() == R.id.dateOfNotificationPicker)
            {
                datePickerDialog.show();
            }
            else if( view.getId() == R.id.hourOfNotificationPicker)
            {
                hourPickerDialog.show();
            }
            else if( view.getId() == R.id.addAlert )
            {
                if ( String.valueOf( datePickerToogleButton.getText() ).equals("Choose date") |
                 String.valueOf( hourPickerToggleButton.getText()).equals("Choose hour") )
                {
                    Toast.makeText(this.getActivity(), "Pick date and time first", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("date", String.valueOf(datePickerToogleButton.getText()));
                    bundle.putString("hour", String.valueOf(hourPickerToggleButton.getText()));
                    bundle.putInt("animalID", animalId);
                    getFragmentManager().setFragmentResult("animalDataFragmentArgs", bundle);
                    navController.navigate(R.id.action_navigation_notifications_to_navigation_addAlert);
                }
            }
            else if( view.getId() == R.id.deletePetButton )
            {
                deletePet();
            }
        }

        private void deletePet() {
            animalDatabase.getAnimalDAO().deleteAnimal(animalId);
            navController.navigate(R.id.action_navigation_notifications_to_navigation_home);
        }

        private void downloadAlerts(View view)
        {
            List<Alert> alerts = animalDatabase.getAlertDAO().getAlertsOnPet( animalId );
            final LinearLayout linearLayout = view.findViewById( R.id.alertsLayout );
            final LinearLayout.LayoutParams paramsLL = new LinearLayout
                    .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            for (Alert alert: alerts
            ){
                final LinearLayout innerLinearLayout = new LinearLayout(linearLayout.getContext());
                innerLinearLayout.setLayoutParams(paramsLL);
                final LinearLayout.LayoutParams paramsTV = new LinearLayout
                        .LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                final LinearLayout.LayoutParams paramsBT = new LinearLayout
                        .LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

                paramsTV.setMargins(10, 0, 10, 0);

                final TextView hourView = new TextView(innerLinearLayout.getContext());
                final TextView dateView = new TextView(innerLinearLayout.getContext());
                final TextView descriptionView = new TextView(innerLinearLayout.getContext());

                final Button deleteButton = new Button(innerLinearLayout.getContext());

                hourView.setText( alert.getDueHour() );
                hourView.setLayoutParams( paramsTV );

                dateView.setText( alert.getDueDate() );
                dateView.setLayoutParams( paramsTV );

                descriptionView.setText( alert.getDescription() );
                descriptionView.setLayoutParams( paramsTV );

                deleteButton.setText("X");
                deleteButton.setTextColor(Color.RED);
                deleteButton.setBackgroundColor(Color.WHITE);
                deleteButton.setLayoutParams( paramsBT );

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick (View aView) {
                        animalDatabase.getAlertDAO().deleteAlert( alert.getId() );
                    }
                });

                innerLinearLayout.addView( hourView );
                innerLinearLayout.addView( dateView );
                innerLinearLayout.addView( descriptionView );
                innerLinearLayout.addView( deleteButton );

                linearLayout.addView( innerLinearLayout );
            }
        }

        private void refreshFragment() {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (Build.VERSION.SDK_INT >= 26) {
                ft.setReorderingAllowed(false);
            }
            ft.detach(this).attach(this).commit();
        }
    }