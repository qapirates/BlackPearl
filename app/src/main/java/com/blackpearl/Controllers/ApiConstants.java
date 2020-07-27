package com.blackpearl.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ApiConstants {

    public static final String BASE_URL = "https://piratesbay-chipper-roan-rs.eu-gb.mybluemix.net";
    public static final String GET_USER = "/api/UserView/";
    public static final String GET_GRAPH_DATA = "/api/DeviceGrpahs";
    public static final String GET_ACTIVE_PARAMETER_FOR_DEVICE = "/api/DeviceGrpahs/";


    public static final double THRESHOLD_TEMPERATURE = 50;
    public static final double THRESHOLD_PH_LEVEL = 8.5;
    public static final double THRESHOLD_PARTICAL_LEVEL = 500;
    public static final double THRESHOLD_OXYGEN_LEVEL = 80;
    public static final double THRESHOLD_SALINITY = 50;

    public static final ArrayList<String> DEVICE_PARAMETERS = new ArrayList<>(Arrays.asList("Temperature", "PH Level", "Particle Level", "Oxygen Level", "Salinity Level"));

    public static final int PARAMETER_ID_TEMPERATURE = 1;
    public static final int PARAMETER_ID_PH = 2;
    public static final int PARAMETER_ID_TDS = 3;
    public static final int PARAMETER_ID_TDO = 4;
    public static final int PARAMETER_ID_SALINITY = 5;




}
