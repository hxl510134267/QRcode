package com.example.qrcodetest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.coderchoy.barcodereaderview.decode.BarcodeReaderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;


public class ScanActivity extends AppCompatActivity implements BarcodeReaderView.OnBarcodeReadListener {

    private BarcodeReaderView scan;

    public static void start(Context context) {
        Intent intent = new Intent(context, ScanActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        scan = (BarcodeReaderView) findViewById(R.id.scan);
        scan.setOnBarcodeReadListener(this);

        List<BarcodeFormat> barcodeFormats = new ArrayList<>();
        barcodeFormats.add(BarcodeFormat.QR_CODE);
        barcodeFormats.add(BarcodeFormat.CODE_128);
        scan.setDecodeFormats(barcodeFormats);
    }

    @Override
    protected void onResume() {
        super.onResume();
        scan.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scan.onPause();
    }

    @Override
    public void onCameraNotFound() {
        Toast.makeText(this, "Camera Not Found!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraInitError() {
        Toast.makeText(this, "Camera Init Error!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBarcodeRead(Result result, Bitmap barcode, float scaleFactor) {
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        String url = result.getText();
        editor.putString("url", url);
        editor.apply();
        Intent intent = new Intent(ScanActivity.this, WebActivity.class);
        startActivity(intent);
        finish();
    }

    public void onBack(View view) {
        finish();
    }
}
