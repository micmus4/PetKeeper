package pl.petkeeper.ui.datemark;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DatemarkViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public DatemarkViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is datemark fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}