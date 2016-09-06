package edu.cuhk.map.building;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import idv.seventhmoon.cubuildingabbr.R;

public class AbbrListFragment extends ListFragment {
	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * activated item position. Only used on tablets.
	 */
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	private Callbacks mCallbacks = sDummyCallbacks;
	private SimpleAdapter simpleAdapter;
	private int mActivatedPosition = ListView.INVALID_POSITION;
	private ArrayList<HashMap<String, Object>> list;

	/**
	 * The Fragment's UI is just a simple text view showing its instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.list, container, false);
		list = DataLoader.loadData(this.getActivity(), R.raw.abbr);
		// TextView countTV = (TextView) v.findViewById(R.id.count);
		// countTV.setText(list.size());
		// System.out.println(list);
		simpleAdapter = new AbbrArrayAdapter(this.getActivity(), list,
				R.layout.cell, new String[] { "name_chi", "name_eng", "abbr" },
				new int[] { R.id.name_chi, R.id.name_eng, R.id.abbr });
		setListAdapter(simpleAdapter);

		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Restore the previously serialized activated item position.
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState
					.getInt(STATE_ACTIVATED_POSITION));
		}
	}

	public interface Callbacks {

		public void onItemSelected(int position);
	}

	private static Callbacks sDummyCallbacks = new Callbacks() {
		public void onItemSelected(int position) {
		}
	};

	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);
		// mCallbacks.onItemSelected(DummyContent.ITEMS.get(position).id);
		mCallbacks.onItemSelected(position);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}

	public void setActivateOnItemClick(boolean activateOnItemClick) {
		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}

	public void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			// Serialize and persist the activated item position.
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = sDummyCallbacks;
	}

	/**
	 * Turns on activate-on-click mode. When this mode is on, list items will be
	 * given the 'activated' state when touched.
	 */

	// // @SuppressLint("ParserError")
	// public void onListItemClick(ListView l, View v, int position, long id) {
	//
	// HashMap<String, Object> map = list.get(position);
	// // String latlong = map.get("lat").toString() +"+"
	// // +map.get("long").toString();
	//
	// // Bundle args = new Bundle();
	// // args.putString("latlong", latlong);
	//
	// // MapWebViewFragment mapFragment =
	// // MapWebViewFragment.newInstance(map.get("lat").toString(),
	// // map.get("long").toString());
	// // //mapFragment.setArguments(args);
	// //
	// // android.support.v4.app.FragmentTransaction transaction =
	// // getFragmentManager()
	// // .beginTransaction();
	// //
	// // transaction.replace(R.id.fragment, mapFragment);
	// // transaction.addToBackStack(null);
	// //
	// // transaction.commit();
	// // String uri = "geo:0,0?z=16&q="+ map.get("lat").toString() +","+
	// // map.get("long").toString()+"("+ map.get("abbr").toString() + ")";
	//
	//
	// String uri = "geo:" + map.get("lat").toString() + ","
	// + map.get("long").toString() + "?z=16&q="
	// + map.get("lat").toString() + "," + map.get("long").toString()
	// + "(" + map.get("abbr").toString() +" " + map.get("name_chi")+ ")";
	// Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
	// l.getContext().startActivity(intent);
	//
	//
	// GoogleMap googleMap = ((SupportMapFragment) getSupportFragmentManager()
	// .findFragmentById(R.id.map)).getMap();
	// LatLng position = new LatLng(saver.getLatitude(),
	// saver.getLongitude());
	//
	// Marker marker = googleMap.addMarker(new MarkerOptions()
	// .draggable(false).position(position).title(contentText)
	// .snippet(bigText));
	// }
}
