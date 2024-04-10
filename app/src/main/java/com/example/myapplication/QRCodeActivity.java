package com.example.myapplication;


        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;


        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.widget.Toast;

        import androidx.activity.result.ActivityResultCallback;
        import androidx.activity.result.ActivityResultLauncher;
        import androidx.activity.result.contract.ActivityResultContracts;
        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import com.google.firebase.database.Query;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.ValueEventListener;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

        import java.util.Objects;

public class QRCodeActivity extends AppCompatActivity {
    private DatabaseReference parentRef, userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase database reference
        parentRef = FirebaseDatabase.getInstance().getReference().child("parent");
        userRef = FirebaseDatabase.getInstance().getReference();
        // Call method to initiate QR code scanning
        startQRCodeScanning();
    }

    private ActivityResultLauncher<Intent> scanLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        String scannedData = data.getStringExtra("SCAN_RESULT");
                        // Handle the scanned data
                        handleScannedData(scannedData);
                    }
                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    // Handle case where user canceled the scan
                    Toast.makeText(this, "Scan canceled", Toast.LENGTH_SHORT).show();
                }
            });

    private void startQRCodeScanning() {
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        scanLauncher.launch(intent);
    }

    private void handleScannedData(String scannedData) {
        // Create a query to search for the scanned student ID in the parent database
        //Query query = parentRef.orderByChild("SId").equalTo(scannedData);
        // Add a ValueEventListener to listen for the query result
        parentRef.child("Parent").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean found = false; // Flag to track if the student ID is found
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String parentUid = childSnapshot.getKey();
                    String studentId = childSnapshot.child("SId").getValue(String.class);

                    if (studentId != null && studentId.equals(scannedData)) {
                        found = true;
                        Log.d("QRCodeActivity", "Student ID found for Parent with UID: " + parentUid);
                        Toast.makeText(QRCodeActivity.this, "Student ID found for Parent with UID: " + parentUid, Toast.LENGTH_SHORT).show();
                        // Handle the logic to display parent information associated with the student ID
                        // For example, navigate to the parent information activity
                        // You can access the parent data using childSnapshot.getValue() method
                        // Here you may want to start a new activity to display the parent information
                        break; // Exit the loop since the student ID is found
                    }
                }
                if (!found) {
                    Log.d("QRCodeActivity", "Student ID not found");
                    Toast.makeText(QRCodeActivity.this, "Student ID not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the database query cancellation or failure
                Toast.makeText(QRCodeActivity.this, "Database query canceled or failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
