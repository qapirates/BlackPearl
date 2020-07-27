package com.blackpearl.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blackpearl.Controllers.ApiConstants;
import com.blackpearl.Controllers.AppVariables;
import com.blackpearl.Controllers.DeviceConfiguration;
import com.blackpearl.Controllers.RetrofitCall;
import com.blackpearl.Controllers.Reusables;
import com.blackpearl.Models.ChartData;
import com.blackpearl.Models.ChartParameterRequest;
import com.blackpearl.Models.Device;
import com.blackpearl.Models.DeviceParameter;
import com.blackpearl.Models.ParameterChart;
import com.blackpearl.Models.User;
import com.blackpearl.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DeviceDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);

        //setting Toolbar
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        /*User user = RetrofitCall.getUserDetailsFromAPI();
        TextView activityTitle = (TextView) findViewById(R.id.text_deviceTitile);
        TextView lastUpdatedOn = (TextView) findViewById(R.id.text_lastUpdatedOn);
        Intent intent = getIntent();
        int devicePosition = intent.getIntExtra("devicePosition", 0);
        Device device = user.getDeviceDetail().get(devicePosition);

        activityTitle.setText(device.getDevice_Name() + " at " + device.getArea());
        lastUpdatedOn.setText(R.string.device_last_updated_on + device.getLastupdatedon());
        displayCurrentDetails(device);*/


        AsyncTaskRunner runner = new AsyncTaskRunner();
        String sleepTime = "3";
        runner.execute(sleepTime);

        /*ArrayList<Integer> activeParameteres = RetrofitCall.getActiveParametersForDevice(Integer.parseInt(device.getId()));
        Log.e("ACTIVE PARAMETERS", activeParameteres.toString());*/


        /*LineChart mChart_temperature = findViewById(R.id.chart_temperature);
        displayChartForParameter(device, ApiConstants.PARAMETER_ID_TEMPERATURE, mChart_temperature);


        LineChart mChart_phLevel = findViewById(R.id.chart_phLevel);
        displayChartForParameter(device, ApiConstants.PARAMETER_ID_PH, mChart_phLevel);


        LineChart mChart_TDS = findViewById(R.id.chart_particleLevel);
        displayChartForParameter(device, ApiConstants.PARAMETER_ID_TDS, mChart_TDS);


        LineChart mChart_TDO = findViewById(R.id.chart_oxygenLevel);
        displayChartForParameter(device, ApiConstants.PARAMETER_ID_TDO, mChart_TDO);


        LineChart mChart_salinity = findViewById(R.id.chart_salinityLevel);
        displayChartForParameter(device, ApiConstants.PARAMETER_ID_SALINITY, mChart_salinity);*/

    }

    private void displayChartForParameter(Device device, int parameterID, LineChart mChart) {
        mChart.setTouchEnabled(true);
        mChart.setPinchZoom(true);

        String deviceId = device.getId();
        Log.d("deviceId", deviceId);

        ChartParameterRequest chartParameterRequest = new ChartParameterRequest();
        chartParameterRequest.setDeviceID(Integer.parseInt(deviceId));
        chartParameterRequest.setParameterID(parameterID);
        chartParameterRequest.setType("Daily");

        ParameterChart parameterChart = RetrofitCall.getChartDataForParameter(chartParameterRequest);

        Log.d("NUMBER OF CHART VALUES", parameterChart.getParameterValues().size() + "");

        ArrayList<Entry> values = new ArrayList<>();

        final HashMap<Integer, String> hourMap = new HashMap<>();
        hourMap.put(1, "12 AM");
        hourMap.put(2, "1 AM");
        hourMap.put(3, "2 AM");
        hourMap.put(4, "3 AM");
        hourMap.put(5, "4 AM");
        hourMap.put(6, "5 AM");
        hourMap.put(7, "6 AM");
        hourMap.put(8, "7 AM");
        hourMap.put(9, "8 AM");
        hourMap.put(10, "9 AM");
        hourMap.put(11, "10 AM");
        hourMap.put(12, "11 AM");
        hourMap.put(13, "12 PM");
        hourMap.put(14, "1 PM");
        hourMap.put(15, "2 PM");
        hourMap.put(16, "3 PM");
        hourMap.put(17, "4 PM");
        hourMap.put(18, "5 PM");
        hourMap.put(19, "6 PM");
        hourMap.put(20, "7 PM");
        hourMap.put(21, "8 PM");
        hourMap.put(22, "9 PM");
        hourMap.put(23, "10 PM");
        hourMap.put(24, "11 PM");


        ArrayList<ChartData> dataArrayList = parameterChart.getParameterValues();
        for (int index = 0; index < dataArrayList.size(); index++) {
            ChartData chartData = dataArrayList.get(index);
            values.add(new Entry((index + 1), Float.parseFloat(chartData.getValue())));
        }

        String label = "";
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.layout_gradient_grey_white);
        if (parameterID == ApiConstants.PARAMETER_ID_TEMPERATURE) {
            label = "Temperature";
            drawable = ContextCompat.getDrawable(this, R.drawable.layout_gradient_grey_white);
        } else if (parameterID == ApiConstants.PARAMETER_ID_PH) {
            label = "PH Level";
            drawable = ContextCompat.getDrawable(this, R.drawable.gradient_amber_white_vertical);
        } else if (parameterID == ApiConstants.PARAMETER_ID_TDS) {
            label = "Total Dissolved Solids";
            drawable = ContextCompat.getDrawable(this, R.drawable.gradient_green_white_vertical);
        } else if (parameterID == ApiConstants.PARAMETER_ID_TDO) {
            label = "Total Dissolved Oxygen";
            drawable = ContextCompat.getDrawable(this, R.drawable.gradient_white_blue);
        } else if (parameterID == ApiConstants.PARAMETER_ID_SALINITY) {
            label = "Salinity";
            drawable = ContextCompat.getDrawable(this, R.drawable.gradient_red_white_vertical);
        }


        LineDataSet set1;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, label + " as on 18 July, 2020");
            set1.setDrawIcons(false);
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGraphGreen));
            set1.setCircleColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGraphGreen));
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);
            if (Utils.getSDKInt() >= 18) {

                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGraphGreen));
            }


            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);

            XAxis xAxis = mChart.getXAxis();

            xAxis.setValueFormatter(new IndexAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return hourMap.get((int) value);
                }
            });

            mChart.setData(data);


        }
    }

    private void displayCurrentDetails(Device device) {

        ArrayList<DeviceParameter> deviceParameters = device.getParameterValues();

        LinearLayout card_temperature = (LinearLayout) findViewById(R.id.layout_device_temperature);
        LinearLayout card_phLevel = (LinearLayout) findViewById(R.id.layout_device_phLevel);
        LinearLayout card_particalLevel = (LinearLayout) findViewById(R.id.layout_device_particalLevel);
        LinearLayout card_oxygenLevel = (LinearLayout) findViewById(R.id.layout_device_oxygenLevel);
        LinearLayout card_ammonia = (LinearLayout) findViewById(R.id.layout_device_ammonia_level);
        LinearLayout card_salinity = (LinearLayout) findViewById(R.id.layout_device_salinityLevel);

        TextView tv_currentValue_temperature = (TextView) findViewById(R.id.textView_currentValue_temperature);
        TextView tv_currentValue_phLevel = (TextView) findViewById(R.id.textView_currentValue_phLevel);
        TextView tv_currentValue_particalLevel = (TextView) findViewById(R.id.textView_currentValue_particalLevel);
        TextView tv_currentValue_oxygenLevel = (TextView) findViewById(R.id.textView_currentValue_oxygen);
        TextView tv_currentValue_ammonia = (TextView) findViewById(R.id.textView_currentValue_ammoniaLevel);
        TextView tv_currentValue_salinity = (TextView) findViewById(R.id.textView_currentValue_salinityLevel);

        TextView tv_currentStatus_temperature = (TextView) findViewById(R.id.textView_currentStatus_temperature);
        TextView tv_currentStatus_phLevel = (TextView) findViewById(R.id.textView_currentStatus_phLevel);
        TextView tv_currentStatus_particalLevel = (TextView) findViewById(R.id.textView_currentStatus_particalLevel);
        TextView tv_currentStatus_oxygenLevel = (TextView) findViewById(R.id.textView_currentStatus_oxygenLevel);
        TextView tv_currentStatus_salinity = (TextView) findViewById(R.id.textView_currentStatus_salinityLevel);


        for (DeviceParameter deviceParameter : deviceParameters) {
            String parameterValue = deviceParameter.getLastValue();
            if (deviceParameter.getName().equalsIgnoreCase("Temperature")) {
                tv_currentValue_temperature.setText(deviceParameter.getLastValue());
                setDeviceParameterBackground(card_temperature, deviceParameter.getColor());

            } else if (deviceParameter.getName().equalsIgnoreCase("PH Level")) {
                tv_currentValue_phLevel.setText(deviceParameter.getLastValue());
                setDeviceParameterBackground(card_phLevel, deviceParameter.getColor());

            } else if (deviceParameter.getName().equalsIgnoreCase("Total Dissolved Solid")) {
                tv_currentValue_particalLevel.setText(deviceParameter.getLastValue());
                setDeviceParameterBackground(card_particalLevel, deviceParameter.getColor());

            } else if (deviceParameter.getName().equalsIgnoreCase("Dissolved Oxygen")) {
                tv_currentValue_oxygenLevel.setText(deviceParameter.getLastValue());
                setDeviceParameterBackground(card_oxygenLevel, deviceParameter.getColor());

            } else if (deviceParameter.getName().equalsIgnoreCase("Ammonia Level")) {
                tv_currentValue_ammonia.setText(deviceParameter.getLastValue());
                setDeviceParameterBackground(card_ammonia, deviceParameter.getColor());

            } else if (deviceParameter.getName().equalsIgnoreCase("Salinity Level")) {
                tv_currentValue_salinity.setText(deviceParameter.getLastValue());
                setDeviceParameterBackground(card_salinity, deviceParameter.getColor());
            }
        }
    }

    private void setDeviceParameterBackground(LinearLayout parameterCard, String color) {

        if (color.equalsIgnoreCase("green")) {
            parameterCard.setBackgroundResource(R.drawable.gradient_green_white_vertical);
        } else if (color.equalsIgnoreCase("amber")) {
            parameterCard.setBackgroundResource(R.drawable.gradient_amber_white_vertical);
        } else if (color.equalsIgnoreCase("red")) {
            parameterCard.setBackgroundResource(R.drawable.gradient_red_white_vertical);
        }
    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Fetching data..."); // Calls onProgressUpdate()
            try {
                int time = Integer.parseInt(params[0]) * 1000;

                Thread.sleep(time);
                resp = "Slept for " + params[0] + " seconds";
            } catch (InterruptedException e) {
                e.printStackTrace();
                resp = e.getMessage();
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();
        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(DeviceDetail.this,
                    "Fecthing device details",
                    "Please wait.....");
        }


        @Override
        protected void onProgressUpdate(String... text) {
            //  finalResult.setText(text[0]);

            User user = RetrofitCall.getUserDetailsFromAPI();
            TextView activityTitle = (TextView) findViewById(R.id.text_deviceTitile);
            TextView lastUpdatedOn = (TextView) findViewById(R.id.text_lastUpdatedOn);
            Intent intent = getIntent();
            int devicePosition = intent.getIntExtra("devicePosition", 0);
            Device device = user.getDeviceDetail().get(devicePosition);

            activityTitle.setText(device.getDevice_Name() + " at " + device.getArea());
            lastUpdatedOn.setText(R.string.device_last_updated_on + device.getLastupdatedon());
            displayCurrentDetails(device);


            LineChart mChart_temperature = findViewById(R.id.chart_temperature);
            displayChartForParameter(device, ApiConstants.PARAMETER_ID_TEMPERATURE, mChart_temperature);
            Reusables.waitForSeconds(2);

            LineChart mChart_phLevel = findViewById(R.id.chart_phLevel);
            displayChartForParameter(device, ApiConstants.PARAMETER_ID_PH, mChart_phLevel);
            Reusables.waitForSeconds(2);

            LineChart mChart_TDS = findViewById(R.id.chart_particleLevel);
            displayChartForParameter(device, ApiConstants.PARAMETER_ID_TDS, mChart_TDS);
            Reusables.waitForSeconds(2);

            LineChart mChart_TDO = findViewById(R.id.chart_oxygenLevel);
            displayChartForParameter(device, ApiConstants.PARAMETER_ID_TDO, mChart_TDO);
            Reusables.waitForSeconds(2);

            LineChart mChart_salinity = findViewById(R.id.chart_salinityLevel);
            displayChartForParameter(device, ApiConstants.PARAMETER_ID_SALINITY, mChart_salinity);
        }
    }
}
