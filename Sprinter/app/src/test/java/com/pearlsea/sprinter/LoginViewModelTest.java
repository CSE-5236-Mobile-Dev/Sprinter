package com.pearlsea.sprinter;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.pearlsea.sprinter.mvvm.LoginViewModel;

public class LoginViewModelTest {
    @Mock
    private Observer<String> statusObserver;

    @Mock
    private Observer<Boolean> isErrorObserver;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private LoginViewModel viewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        viewModel = new LoginViewModel();
        viewModel.getStatus().observeForever(statusObserver);
        viewModel.getIsError().observeForever(isErrorObserver);
    }

    @Test
    public void testSetStatus() {
        String status = "Success";
        boolean isError = false;
        viewModel.setStatus(status, isError);
        verify(statusObserver, times(1)).onChanged(status);
        verify(isErrorObserver, times(2)).onChanged(isError);
    }

    @Test
    public void testSetError() {
        String status = "Error";
        boolean isError = true;
        viewModel.setStatus(status, isError);
        verify(statusObserver, times(1)).onChanged(status);
        verify(isErrorObserver, times(1)).onChanged(isError);
    }

    @Test
    public void testGetStatus() {
        String status = "Success";
        boolean isError = false;
        viewModel.setStatus(status, isError);
        LiveData<String> liveStatus = viewModel.getStatus();
        String observedStatus = liveStatus.getValue();
        assertEquals(observedStatus, status);
    }
}
