package pl.petkeeper.ui.calendar;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import pl.petkeeper.R;
import pl.petkeeper.db.AnimalDatabase;
import pl.petkeeper.model.Datemark;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {

    private final ArrayList<String> daysOfMonth;
    private final OnItemListener onItemListener;
    private LocalDate date;
    private AnimalDatabase animalDatabase;
    private List<Datemark> datemarks;


    public CalendarAdapter(ArrayList<String> daysOfMonth, LocalDate date, OnItemListener onItemListener,
                           AnimalDatabase animalDatabase, List<Datemark> datemarks)
    {
        this.daysOfMonth = daysOfMonth;
        this.date = date;
        this.onItemListener = onItemListener;
        this.animalDatabase = animalDatabase;
        this.datemarks = datemarks;
    }
    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.16666666);
        return new CalendarViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        holder.dayOfMonth.setText(daysOfMonth.get(position));

        if (!daysOfMonth.get(position).equals("")) {
            for (Datemark datemarkFromDB : animalDatabase.getDatemarkDAO().getAllDatemarks()) {
                LocalDate dateBeingChecked = date.withDayOfMonth(Integer.parseInt(daysOfMonth.get(position)));
                if (dayMonthYearFromDate(dateBeingChecked).equals(datemarkFromDB.getDate())) {
                    holder.dayOfMonth.setTextColor(Color.RED);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    public interface OnItemListener{
        void onItemClick(int position, String dayText);
    }

    private String dayMonthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        return date.format(formatter);
    }
}
