package org.example;

public class Main {
    public static void main(String[] args) {
        DataEP[] dataEndpoints = {
                new DataEP("/countries", "countries", "countries", ""),
                new DataEP("/cities", "cities", "cities", ""),
                new DataEP("/districts", "districts", "kyiv_districts", "&city_id=216"),
                new DataEP("/street/allstreets", "streets", "all_streets", ""),
                new DataEP("/street/streets", "streets", "kyiv_streets", "&city_id=216"),
                new DataEP("/metro/stations", "metro_stations", "kyiv_metro_stations", "&city_id=216"),
                new DataEP("/routes/bycity", "routes", "kyiv_routes", "&city_id=216"),
                new DataEP("/estatetypes/groups", "groups", "groups", ""),
                new DataEP("/estatetypes", "types", "kyiv_types", "group_id=216"),
                new DataEP("/finish/all", "finishes", "finishes", ""),
                new DataEP("/dictionary/values", "values", "cond", "&type=5"),
                new DataEP("/dictionary/values", "values", "water", "&type=12"),
                new DataEP("/dictionary/values", "values", "gas", "&type=14"),
                new DataEP("/dictionary/values", "values", "electro", "&type=10"),
                new DataEP("/dictionary/values", "values", "canal", "&type=11"),
                new DataEP("/estate/search", "results", "estate", "&id=city_title=Киев&min_price_meter=224&max_price_meter=250"),
                new DataEP("/estate/searchlastedited", "results", "changed_estate", "&id=city_title=Киев&min_price_meter=224&max_price_meter=250"),
                new DataEP("/agent/all", "agents", "agents", ""),
                new DataEP("/picture/EstateCoverPhoto", "pictures", "estate_cover_picture", "&estate_id=21&width=640&height=480&crop=1&watermark=0"),
                new DataEP("/picture/EstatePhoto", "pictures", "estate_pictures", "&estate_id=21&width=640&height=480&crop=1&watermark=0"),
                new DataEP("/picture/EstateLayout", "pictures", "layout_pictures", "&estate_id=21&width=640&height=480&crop=1&watermark=0"),
                new DataEP("/picture/BuildingPhoto", "pictures", "building_pictures", "&building_id=5&width=640&height=480&crop=1&watermark=0"),
                new DataEP("/contragent/getall", "contragents", "contragents", "&agent_id=13&type=2&name=Юлия")
        };

        for (DataEP endpoint : dataEndpoints) {
            String jsonResponse = callApiAndHandleExceptions(endpoint.getEndpoint(), endpoint.getArguments());
            convertJsonToExcelAndHandleExceptions(jsonResponse, "sheet", endpoint.getKey(), endpoint.getSheetName());
        }
    }

    private static String callApiAndHandleExceptions(String endpoint, String arguments) {
        try {
            String jsonResponse = Api.runApi(endpoint, arguments);
            System.out.println(jsonResponse);
            return jsonResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void convertJsonToExcelAndHandleExceptions(String jsonResponse, String filename, String dataArrayKey, String sheetName) {
        if (jsonResponse != null) {
            try {
                Excel.Convert(jsonResponse, filename, dataArrayKey, sheetName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}