package pl.petkeeper.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import pl.petkeeper.model.Alert;

public interface UpdateNotificationsIterface {
    public void updateNotifications();
}
