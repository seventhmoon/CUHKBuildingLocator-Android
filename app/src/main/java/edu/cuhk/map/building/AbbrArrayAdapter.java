package edu.cuhk.map.building;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import android.widget.SimpleAdapter;

public class AbbrArrayAdapter extends SimpleAdapter implements SectionIndexer{

	private List<? extends Map<String, ?>>  list;
	private List<String> index;
	public AbbrArrayAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		this.list = data;
		
		index = new ArrayList<String>();
		for(Map<String, ?> map: list){
			index.add(map.get("abbr").toString());
		}
	}

	public int getPositionForSection(int section) {
		// TODO Auto-generated method stub
		//return 0;
		return section;
	}

	public int getSectionForPosition(int position) {
		// TODO Auto-generated method stub
		//return position;
		return 0;
	}

	public Object[] getSections() {
		// TODO Auto-generated method stub
		return index.toArray();
		//return null;
	}

}
