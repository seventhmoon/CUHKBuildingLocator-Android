package edu.cuhk.map.building;

import android.content.Context;

import com.csvreader.CsvReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import edu.cuhk.map.building.model.Building;

public class DataLoader {

	public static ArrayList<Building> getBuildings(Context context, int rawResourceId){
		ArrayList<Building> list = new ArrayList<>();
		try {
			InputStream busIn = context.getResources().openRawResource(
					rawResourceId);
			CsvReader reader = new CsvReader(new InputStreamReader(busIn));
			reader.readHeaders();

			while (reader.readRecord()) {
				String abbr = reader.get("abbr").trim();
				String nameEng = reader.get("name_eng").trim();
				String nameChi = reader.get("name_chi").trim();
				double latitude = Double.valueOf(reader.get("lat").trim());
				double longitude = Double.valueOf(reader.get("long").trim());


//				HashMap<String, Object> map = new HashMap<String, Object>();
//				map.put("abbr", abbr);
//				map.put("name_eng", nameEng);
//				map.put("name_chi", nameChi);
//				map.put("lat", latitude);
//				map.put("long", longitude);
				list.add(new Building(abbr, nameEng, nameChi, latitude, longitude));
			}
			reader.close();
			busIn.close();

			// Collections.shuffle(list);
			// lists.add(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
        return list;
	}


	public static ArrayList<HashMap<String, Object>> loadData(Context context, int rawResourceId) {

		// ArrayList<ArrayList<HashMap<String, Object>>> lists = new
		// ArrayList<ArrayList<HashMap<String, Object>>>();
		// for (int i = 0; i < rawList.length; i++) {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		try {
			InputStream busIn = context.getResources().openRawResource(
					rawResourceId);
			CsvReader reader = new CsvReader(new InputStreamReader(busIn));
			reader.readHeaders();

			while (reader.readRecord()) {
				String abbr = reader.get("abbr").trim();
				String nameEng = reader.get("name_eng").trim();
				String nameChi = reader.get("name_chi").trim();
				String latitude = reader.get("lat").trim();
				String longitude = reader.get("long").trim();

				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("abbr", abbr);
				map.put("name_eng", nameEng);
				map.put("name_chi", nameChi);
				map.put("lat", latitude);
				map.put("long", longitude);
				list.add(map);
			}
			reader.close();
			busIn.close();

			// Collections.shuffle(list);
			// lists.add(list);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// }

		return list;

	}

}
