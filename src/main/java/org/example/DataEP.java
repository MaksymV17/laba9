package org.example;


public class DataEP  {

    public DataEP(String endpoint, String key, String sheetName, String arguments) {
        this.endpoint = endpoint;
        this.key = key;
        this.sheetName = sheetName;
        this.arguments = arguments;
    }


    public String getEndpoint() { return endpoint; }
    public String getKey() { return key; }
    public String getSheetName() { return sheetName; }
    public String getArguments() { return arguments; }


    private String endpoint;
    private String key;
    private String sheetName;
    private String arguments;

}