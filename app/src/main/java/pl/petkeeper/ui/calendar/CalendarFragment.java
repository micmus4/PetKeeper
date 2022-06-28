package pl.petkeeper.ui.calendar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import pl.petkeeper.R;
import pl.petkeeper.databinding.FragmentCalendarBinding;
import pl.petkeeper.databinding.FragmentHomeBinding;
import pl.petkeeper.db.AnimalDatabase;
import pl.petkeeper.model.Datemark;
import pl.petkeeper.ui.home.HomeFragment;
import pl.petkeeper.ui.home.HomeViewModel;

public class CalendarFragment extends Fragment implements CalendarAdapter.OnItemListener {

    private FragmentCalendarBinding binding;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    private NavController navController;
    private AnimalDatabase animalDatabase;
    private List<Datemark> datemarks;

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CalendarViewModel calendarViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);
        binding = FragmentCalendarBinding.inflate(inflater, container, false);

        animalDatabase = Room.databaseBuilder( getContext(), AnimalDatabase.class, "animalDatabase" )
                .allowMainThreadQueries().build();

        View root = binding.getRoot();

        initWidgets(root);
        selectedDate = LocalDate.now();

        setDates();

        setMonthView();

        Button buttonPrev = (Button) root.findViewById(R.id.prevMonth);
        Button buttionNext = (Button) root.findViewById(R.id.nextMonth);
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prevMonthAction(root);
            }
        });
        buttionNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextMonthAction(root);
            }
        });

        // Inflate the layout for this fragment

        return root;
    }

    private void setDates() {
        this.datemarks = animalDatabase.getDatemarkDAO().getAllDatemarks();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController( view );
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, selectedDate,
                this, animalDatabase, datemarks);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

    }

    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++){
            if(i < dayOfWeek || i > daysInMonth + dayOfWeek - 1) {
                daysInMonthArray.add("");
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i+1 - dayOfWeek));
            }
        }
        return daysInMonthArray;
    }

    private String monthYearFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    private String dayMonthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        return date.format(formatter);
    }

    private void initWidgets(View root) {
        calendarRecyclerView = root.findViewById(R.id.calendarRecyclerView);
        monthYearText = root.findViewById(R.id.monthYear);
    }

    public void nextMonthAction(View view) {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    public void prevMonthAction(View view) {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, String dayText) {
        if(!dayText.equals("")){
            String message = "Clicked on " + dayText + " " + monthYearFromDate(selectedDate);
            Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
            selectedDate = selectedDate.withDayOfMonth( Integer.parseInt(dayText) );
            Bundle bundle = new Bundle();
            bundle.putString( "date", dayMonthYearFromDate( selectedDate ) );
            getFragmentManager().setFragmentResult( "calendarFragmentArgs", bundle );
            navController.navigate( R.id.action_navigation_calendar_to_navigation_datemark );
        }
    }


}