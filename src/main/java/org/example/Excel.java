package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;

public class Excel {

    public static void Convert(String jsonInput, String filename, String dataArrayKey, String sheetName) throws IOException, InvalidFormatException {
        Workbook workbook = getOrCreateWorkbook(filename);
        verifySheetDoesNotExist(workbook, sheetName);

        Sheet sheet = workbook.createSheet(sheetName);
        JSONArray dataArray = extractDataArrayFromJson(jsonInput, dataArrayKey);

        if (dataArray.length() > 0) {
            createAndFillHeaderRow(sheet, dataArray.getJSONObject(0));
            populateSheetWithData(sheet, dataArray);
            adjustColumnsWidth(sheet, dataArray.getJSONObject(0).keySet().size());
        }

        writeFile(workbook, filename + ".xlsx");
        workbook.close();
    }

    private static Workbook getOrCreateWorkbook(String filename) throws IOException {
        String filePath = filename + ".xlsx";
        File file = new File(filePath);
        if (file.exists()) {
            return new XSSFWorkbook(new FileInputStream(file));
        }
        return new XSSFWorkbook();
    }

    private static void verifySheetDoesNotExist(Workbook workbook, String sheetName) {
        if (workbook.getSheet(sheetName) != null) {
            throw new IllegalArgumentException("Sheet with name " + sheetName + " already exists.");
        }
    }

    private static JSONArray extractDataArrayFromJson(String jsonInput, String dataArrayKey) {
        JSONObject jsonObject = new JSONObject(jsonInput);
        return jsonObject.getJSONArray(dataArrayKey);
    }

    private static void createAndFillHeaderRow(Sheet sheet, JSONObject firstItem) {
        Row headerRow = sheet.createRow(0);
        int columnIndex = 0;
        for (String key : firstItem.keySet()) {
            Cell headerCell = headerRow.createCell(columnIndex++);
            headerCell.setCellValue(key);
        }
    }

    private static void populateSheetWithData(Sheet sheet, JSONArray dataArray) {
        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject dataItem = dataArray.getJSONObject(i);
            Row row = sheet.createRow(i + 1);
            int columnIndex = 0;
            for (String key : dataItem.keySet()) {
                Cell cell = row.createCell(columnIndex++);
                cell.setCellValue(dataItem.get(key).toString());
            }
        }
    }

    private static void adjustColumnsWidth(Sheet sheet, int columnCount) {
        for (int i = 0; i < columnCount; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private static void writeFile(Workbook workbook, String filePath) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }
    }
}