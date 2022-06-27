package pl.petkeeper.model;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)

public class SpeciesTest {
    @Test
    public void infoTest() {
        Species husky = new Species(1, "Husky", "wikipedia");
        String text = husky.getInfo(husky.getName() );
        assertTrue( text.contains("is a general term for a dog used in the polar regions") );
    }

}