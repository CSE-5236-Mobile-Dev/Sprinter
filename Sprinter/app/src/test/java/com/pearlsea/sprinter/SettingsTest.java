import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.pearlsea.sprinter.RunActivity;
import com.pearlsea.sprinter.db.DatabaseInstanceSingleton;
import com.pearlsea.sprinter.db.operation_threads.DeleteUserThread;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@RunWith(AndroidJUnit4.class)
public class SettingsFragmentTest {

    @Mock
    private RunActivity mockActivity; // Mock RunActivity

    @Mock
    private DeleteUserThread mockDeleteUserThread; // Mock DeleteUserThread

    private SettingsFragment settingsFragment; // Fragment under test

    @Before
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.initMocks(this);

        // Create an instance of SettingsFragment with the mock RunActivity
        settingsFragment = new SettingsFragment(mockActivity);
    }

    @Test
    public void testHandleAccountDeletion() {
        // Mock DatabaseInstanceSingleton and DeleteUserThread
        DatabaseInstanceSingleton.activeUser = "testUser";
        Mockito.when(mockActivity.getContext()).thenReturn(ApplicationProvider.getApplicationContext());
        Mockito.when(mockDeleteUserThread.start()).thenReturn(null);

        // Call handle_account_deletion() method
        settingsFragment.handle_account_deletion();

        // Verify that DeleteUserThread was started with the expected arguments
        Mockito.verify(mockDeleteUserThread).start();
    }

    @Test
    public void testHandleAccountLogout() {
        // Mock DatabaseInstanceSingleton
        DatabaseInstanceSingleton.activeUser = "testUser";

        // Call handle_account_logout() method
        settingsFragment.handle_account_logout();

        // Verify that activeUser was set to null
        Assert.assertNull(DatabaseInstanceSingleton.activeUser);

        // Verify that RunActivity's transitionToWelcomeScreen() method was called
        Mockito.verify(mockActivity).transitionToWelcomeScreen();
    }

    @Test
    public void testOnClick_DeleteAccountButton() {
        // Mock View and TextView for delete account button
        View mockView = Mockito.mock(View.class);
        TextView mockDeleteButton = Mockito.mock(TextView.class);
        Mockito.when(mockView.getId()).thenReturn(R.id.delete_account_button);
        Mockito.when(settingsFragment.getView()).thenReturn(mockView);
        Mockito.when(mockView.findViewById(R.id.delete_account_button)).thenReturn(mockDeleteButton);

        // Call onClick() method with delete account button
        settingsFragment.onClick(mockDeleteButton);

        // Verify that handle_account_deletion() method was called
        Mockito.verify(settingsFragment).handle_account_deletion();
    }

    @Test
    public void testOnClick_LogoutButton() {
        // Mock View and TextView for logout button
        View mockView = Mockito.mock(View.class);
        TextView mockLogoutButton = Mockito.mock(TextView.class);
        Mockito.when(mockView.getId()).thenReturn(R.id.logout_button);
        Mockito.when(settingsFragment.getView()).thenReturn(mockView);
        Mockito.when(mockView.findViewById(R.id.logout_button)).thenReturn(mockLogoutButton);

        // Call onClick() method with logout button
        settingsFragment.onClick(mockLogoutButton);

        // Verify that handle_account_logout() method was called
        Mockito.verify(settingsFragment).handle_account_logout();
    }
}
