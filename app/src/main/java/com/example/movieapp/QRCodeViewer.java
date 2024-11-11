package com.example.movieapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRCodeViewer extends AppCompatActivity {

    private String title;
    private String qrcodeText;
    ImageView qrcodeImage;
    TextView appBarTitle;
    ImageView backArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_viewer);
        title = getIntent().getStringExtra("title");
        qrcodeText = getIntent().getStringExtra("qrcodeText");
        qrcodeImage = findViewById(R.id.qrcodeImage);
        appBarTitle = findViewById(R.id.appBarTitle);
        appBarTitle.setText(title);
        generateQRCode();
        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(v -> {
            finish();
        });
    }

    private void generateQRCode(){
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix matrix = writer.encode(qrcodeText, BarcodeFormat.QR_CODE,600,600);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            qrcodeImage.setImageBitmap(bitmap);
        }catch (WriterException exception){
            exception.printStackTrace();
        }
    }
}