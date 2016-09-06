package edu.cuhk.map.building;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

import idv.seventhmoon.cubuildingabbr.R;

/**
 * An activity representing a single Building detail screen. This activity is
 * only used on handset devices. On tablet-size devices, item details are
 * presented side-by-side with a list of items in a {@link BuildingListActivity}
 * .
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link BuildingDetailFragment}.
 */
public class BuildingDetailActivity extends Activity {
	private AdView mAdView;
	private GoogleMap mGoogleMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_building_detail);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		int position = getIntent().getIntExtra("position", 0);

		mAdView = (AdView) this.findViewById(R.id.ad);

		mGoogleMap = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.map)).getMap();
		mGoogleMap.setMyLocationEnabled(true);

		ArrayList<HashMap<String, Object>> list = DataLoader.loadData(this,
				R.raw.abbr);
		HashMap<String, Object> map = list.get(position);

		double latitude = Double.parseDouble(map.get("lat").toString());
		double longitude = Double.parseDouble(map.get("long").toString());
		String abbr = map.get("abbr").toString();
		String name = map.get("name_chi").toString();

		LatLng location = new LatLng(latitude, longitude);

		mGoogleMap.clear();

		Marker marker = mGoogleMap.addMarker(new MarkerOptions()
				.draggable(false).position(location).title(abbr).snippet(name));
		marker.showInfoWindow();
		mGoogleMap.animateCamera(CameraUpdateFactory
				.newLatLngZoom(location, 16));

		// savedInstanceState is non-null when there is fragment state
		// saved from previous configurations of this activity
		// (e.g. when rotating the screen from portrait to landscape).
		// In this case, the fragment will automatically be re-added
		// to its container so we don't need to manually add it.
		// For more information, see the Fragments API guide at:
		//
		// http://developer.android.com/guide/components/fragments.html
		//
		if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString("position",
					getIntent().getStringExtra("position"));
			// BuildingDetailFragment fragment = new BuildingDetailFragment();
			// fragment.setArguments(arguments);
			// getSupportFragmentManager().beginTransaction()
			// .add(R.id.building_detail_container, fragment).commit();
		}

		loadAd();
	}

	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Toast.makeText(this, "test", Toast.LENGTH_LONG).show();
		// Bundle arguments = new Bundle();
		// arguments.putString("position",
		// getIntent().getStringExtra("position"));

		NavUtils.navigateUpTo(this,
				new Intent(this, BuildingListActivity.class));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpTo(this, new Intent(this,
					BuildingListActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void loadAd() {

		Builder builder = new AdRequest.Builder();
		if (mGoogleMap != null) {
			builder.setLocation(mGoogleMap.getMyLocation());
		}

		AdRequest adRequest = builder.addTestDevice(
				AdRequest.DEVICE_ID_EMULATOR).build();

		// .addTestDevice("TEST_DEVICE_ID").build();
		mAdView.setAdListener(new AdListener() {
			@Override
			public void onAdFailedToLoad(int errorCode) {
				mAdView.setVisibility(AdView.GONE);
			}

			public void onAdLoaded() {
				mAdView.setVisibility(AdView.VISIBLE);
			}
		});
		mAdView.loadAd(adRequest);
	}

	@Override
	public void onPause() {
		if (mAdView != null) {
			mAdView.pause();
		}
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mAdView != null) {
			mAdView.resume();
		}
	}

	@Override
	public void onDestroy() {
		if (mAdView != null) {
			mAdView.destroy();
		}
		super.onDestroy();
	}
}
