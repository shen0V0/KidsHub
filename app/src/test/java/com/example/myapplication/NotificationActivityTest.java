package com.example.myapplication;

import android.content.Context;
import com.example.myapplication.NotificationActivity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class NotificationActivityTest {

    @Mock
    private Context mockContext; // Mocked Context

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        // No need to use ApplicationProvider.getApplicationContext() since you're mocking
    }

    @Test
    public void testShowConfirmationDialog() {
        // Example test using the mocked context
        NotificationActivity activity = new NotificationActivity();
        // Assume you can inject or somehow use mockContext in NotificationActivity
        // Perform your test logic here
    }
}
