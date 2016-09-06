package edu.cuhk.map.building;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

import idv.seventhmoon.cubuildingabbr.R;

//import com.google.analytics.tracking.android.EasyTracker;

/**
 * An activity representing a list of Buildings. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link BuildingDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link BuildingListFragment} and the item details (if present) is a
 * {@link BuildingDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link BuildingListFragment.Callbacks} interface to listen for item
 * selections.
 */
public class BuildingListActivity extends Activity implements
		AbbrListFragment.Callbacks {
	private AdView adView;
	private InterstitialAd interstitialAd;
	private GoogleMap googleMap;
	private int mItemClick = 0;
	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	private static final LatLng CUHK = new LatLng(22.4195, 114.2074);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_building_list);

		adView = (AdView) this.findViewById(R.id.ad);

		if (findViewById(R.id.map) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((AbbrListFragment) getFragmentManager().findFragmentById(
					R.id.list_pane)).setActivateOnItemClick(true);

			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();
			if (googleMap != null) {
				googleMap.moveCamera(CameraUpdateFactory
						.newLatLngZoom(CUHK, 16));
				googleMap.setMyLocationEnabled(true);

				// int position = getIntent().getIntExtra("position", 0);
				// ArrayList<HashMap<String, Object>> list =
				// DataLoader.loadData(this,
				// R.raw.abbr);
				// HashMap<String, Object> map = list.get(position);
				//
				//
				// double latitude =
				// Double.parseDouble(map.get("lat").toString());
				// double longitude =
				// Double.parseDouble(map.get("long").toString());
				// String abbr = map.get("abbr").toString();
				// String name = map.get("name_chi").toString();
				//
				// LatLng location = new LatLng(latitude, longitude);
				//
				// googleMap.clear();
				//
				// Marker marker = googleMap.addMarker(new MarkerOptions()
				// .draggable(false).position(location).title(abbr).snippet(name));
				// marker.showInfoWindow();
				// googleMap.animateCamera(CameraUpdateFactory
				// .newLatLngZoom(location, 16));

				// googleMap.getUiSettings().setTiltGesturesEnabled(false);
				// googleMap.getUiSettings().setAllGesturesEnabled(false);
			}
		}

		// TODO: If exposing deep links into your app, handle intents here.

		loadAd();
		prepareInterstitalAd();
	}

	private void prepareInterstitalAd() {
		// Create the interstitial.
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(getResources().getString(
				R.string.ad_unit_id_interstitial));

		// Create ad request.
		Builder builder = new AdRequest.Builder();
		if (googleMap != null) {
			Location currentLocation = googleMap.getMyLocation();
			if (currentLocation != null) {
				builder.setLocation(currentLocation);
			}
		}

		AdRequest adRequest = builder.build();

		// Begin loading your interstitial.
		interstitialAd.loadAd(adRequest);
	}

	private void showInterstitalAd() {
		if (interstitialAd.isLoaded()) {
			interstitialAd.show();
		}
	}

	/**
	 * Callback method from {@link AbbrListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	public void onItemSelected(int position) {

		if (mTwoPane) {
			GoogleMap googleMap = ((MapFragment) getFragmentManager()
					.findFragmentById(R.id.map)).getMap();

			ArrayList<HashMap<String, Object>> list = DataLoader.loadData(this,
					R.raw.abbr);
			HashMap<String, Object> map = list.get(position);

			double latitude = Double.parseDouble(map.get("lat").toString());
			double longitude = Double.parseDouble(map.get("long").toString());
			String abbr = map.get("abbr").toString();
			String name = map.get("name_chi").toString();

			LatLng location = new LatLng(latitude, longitude);

			googleMap.clear();

			Marker marker = googleMap.addMarker(new MarkerOptions()
					.draggable(false).position(location).title(abbr)
					.snippet(name));
			marker.showInfoWindow();
			googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,
					16));

		} else {
			Intent detailIntent = new Intent(this, BuildingDetailActivity.class);
			detailIntent.putExtra("position", position);
			startActivity(detailIntent);
		}

		if (this.mItemClick++ >= 5) {
			showInterstitalAd();
		}
	}

	// public void onStart() {
	// super.onStart();
	// // EasyTracker.getInstance().activityStart(this); // Add this method.
	// }
	//
	// public void onStop() {
	// super.onStop();
	// // EasyTracker.getInstance().activityStop(this); // Add this method.
	// }

	private void loadAd() {
		Builder builder = new AdRequest.Builder();
		if (googleMap != null) {
			builder.setLocation(googleMap.getMyLocation());
		}
		// adView = (AdView) this.findViewById(R.id.ad);
		AdRequest adRequest = builder.addTestDevice(
				AdRequest.DEVICE_ID_EMULATOR).build();
		// .addTestDevice("TEST_DEVICE_ID").build();
		if (adView != null) {
			adView.setAdListener(new AdListener() {
				@Override
				public void onAdFailedToLoad(int errorCode) {
					adView.setVisibility(AdView.GONE);
				}

				public void onAdLoaded() {
					adView.setVisibility(AdView.VISIBLE);
				}
			});
			adView.loadAd(adRequest);
		}
	}

	@Override
	public void onPause() {
		if (adView != null) {
			adView.pause();
		}
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
//		showInterstitalAd();
		if (adView != null) {
			adView.resume();
		}
	}

	@Override
	public void onDestroy() {
		if (adView != null) {
			adView.destroy();
		}
		super.onDestroy();
	}
}
