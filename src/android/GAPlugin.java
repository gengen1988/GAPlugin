package com.adobe.plugins;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;

public class GAPlugin extends CordovaPlugin {
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callback) {
		Tracker tracker = EasyTracker.getInstance(cordova.getActivity());

		// origin
		if (action.equals("initGA")) {
			try {
				callback.success("initGA - id = " + args.getString(0) + "; interval = " + args.getInt(1) + " seconds");
				return true;
			} catch (final Exception e) {
				callback.error(e.getMessage());
			}
		} else if (action.equals("exitGA")) {
			try {
				callback.success("exitGA");
				return true;
			} catch (final Exception e) {
				callback.error(e.getMessage());
			}
		} else if (action.equals("trackEvent")) {
			try {
				// v3
				tracker.send(MapBuilder.createEvent(
					args.getString(0),
					args.getString(1),
					args.getString(2),
					args.getLong(3)
				).build());
				
				// v2
				// tracker.sendEvent(args.getString(0), args.getString(1), args.getString(2), args.getLong(3));
				callback.success("trackEvent - category = " + args.getString(0) + "; action = " + args.getString(1) + "; label = " + args.getString(2) + "; value = " + args.getInt(3));
				return true;
			} catch (final Exception e) {
				callback.error(e.getMessage());
			}
		} else if (action.equals("trackPage")) {
			try {
				// v3
				tracker.set(Fields.SCREEN_NAME, args.getString(0));
				tracker.send(MapBuilder.createAppView().build());
				
				// v2
				//tracker.sendView(args.getString(0));
				callback.success("trackPage - url = " + args.getString(0));
				return true;
			} catch (final Exception e) {
				callback.error(e.getMessage());
			}
		} else if (action.equals("setVariable")) {
			try {
				// v3
				tracker.set(Fields.customDimension(
					args.getInt(0)),
					args.getString(1)
				);
				
				// v2
				//tracker.setCustomDimension(args.getInt(0), args.getString(1));
				callback.success("setVariable passed - index = " + args.getInt(0) + "; value = " + args.getString(1));
				return true;
			} catch (final Exception e) {
				callback.error(e.getMessage());
			}
		} else if (action.equals("setDimension")) {
			try {
				// v3
				tracker.set(Fields.customDimension(
					args.getInt(0)),
					args.getString(1)
				);
				
				// v2
				//tracker.setCustomDimension(args.getInt(0), args.getString(1));
				callback.success("setDimension passed - index = " + args.getInt(0) + "; value = " + args.getString(1));
				return true;
			} catch (final Exception e) {
				callback.error(e.getMessage());
			}
		} else if (action.equals("setMetric")) {
			try {
				// v3
				tracker.set(Fields.customMetric(
					args.getInt(0)),
					args.getString(1)
				);
				
				// v2
				//tracker.setCustomMetric(args.getInt(0), args.getLong(1));
				callback.success("setVariable passed - index = " + args.getInt(2) + "; key = " + args.getString(0) + "; value = " + args.getString(1));
				return true;
			} catch (final Exception e) {
				callback.error(e.getMessage());
			}
		}

		// new
		if (action.equals("sendView")) {
			try {
				tracker.set(Fields.SCREEN_NAME, args.getString(0));
				tracker.send(MapBuilder.createAppView().build());
				callback.success("sendView - SCREEN_NAME = " + args.getString(0));
				return true;
			} catch (Exception e) {
				callback.error(e.getMessage());
			}
		} else if (action.equals("sendEvent")) {
			try {
				tracker.send(MapBuilder.createEvent(
					args.getString(0),
					args.getString(1),
					args.getString(2),
					args.getLong(3)
				).build());
				callback.success("sendEvent - category = " + args.getString(0) + "; action = " + args.getString(1) + "; label = " + args.getString(2) + "; value = " + args.getInt(3));
				return true;
			} catch (Exception e) {
				callback.error(e.getMessage());
			}
		}
		return false;
	}
}