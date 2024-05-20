/*  Copyright (c) 2011 The ORMMA.org project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree. All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */
package org.ormma.controller;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONObject;
import org.ormma.controller.util.NavigationStringEnum;
import org.ormma.controller.util.TransitionStringEnum;
import org.ormma.view.OrmmaView;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Abstract class fort all controller objects
 * Controller objects implent pieces of the java/javascript interface
 */
@SuppressWarnings("unused")
public abstract class OrmmaController {

	//view it is attached to
	protected OrmmaView ormmaView;
	//context it is in
	protected Context context;

	//class types for converting JSON
	private static final String STRING_TYPE = "class java.lang.String";
	private static final String INT_TYPE = "int";
	private static final String BOOLEAN_TYPE = "boolean";
	private static final String FLOAT_TYPE = "float";
	private static final String NAVIGATION_TYPE = "class com.ormma.NavigationStringEnum";
	private static final String TRANSITION_TYPE = "class com.ormma.TransitionStringEnum";

	public static final String FULL_SCREEN = "fullscreen";
	public static final String EXIT = "exit";
	public static final String STYLE_NORMAL = "normal";

	/**
	 * Contains audio and video properties
	 */
	public static class PlayerProperties extends ReflectedParcelable {

		public PlayerProperties() {
			autoPlay = showControl = true;
			doLoop = audioMuted = false;
			startStyle = stopStyle = STYLE_NORMAL;
			inline = false;
		}

		/**
		 * The Constant CREATOR.
		 */
		@SuppressWarnings("unused")
		public static final Parcelable.Creator<PlayerProperties> CREATOR = new Parcelable.Creator<PlayerProperties>() {
			public PlayerProperties createFromParcel(Parcel in) {
				try {
					return new PlayerProperties(in);
				} catch (OrmmaException e) {
					throw new RuntimeException(e);
				}
			}

			public PlayerProperties[] newArray(int size) {
				return new PlayerProperties[size];
			}
		};

		public PlayerProperties(Parcel in) throws OrmmaException {
			super(in);
		}

		/**
		 * Set stop style
		 *
		 * @param style - stop style (normal/full screen)
		 */
		@SuppressWarnings("UnusedDeclaration")
		public void setStopStyle(String style) {
			stopStyle = style;
		}

		public void setProperties(boolean audioMuted, boolean autoPlay, boolean controls, boolean inline, boolean loop, String startStyle, String stopStyle) {
			this.autoPlay = autoPlay;
			this.showControl = controls;
			this.doLoop = loop;
			this.audioMuted = audioMuted;
			this.startStyle = startStyle;
			this.stopStyle = stopStyle;
			this.inline = inline;

		}

		public void muteAudio() {
			audioMuted = true;
		}

		public boolean isAutoPlay() {
			return autoPlay;
		}

		public boolean showControl() {
			return showControl;
		}

		public boolean doLoop() {
			return doLoop;
		}

		public boolean doMute() {
			return audioMuted;
		}

		public boolean exitOnComplete() {
			return stopStyle.equalsIgnoreCase(EXIT);
		}

		public boolean isFullScreen() {
			return startStyle.equalsIgnoreCase(FULL_SCREEN);
		}

		public boolean autoPlay, showControl, doLoop, audioMuted, inline;
		public String stopStyle, startStyle;
	}

	/**
	 * The Class Dimensions.  Holds dimensions coming from javascript
	 */
	public static class Dimensions extends ReflectedParcelable {

		/**
		 * Instantiates a new dimensions.
		 */
		public Dimensions() {
			x = -1;
			y = -1;
			width = -1;
			height = -1;
		}

		/**
		 * The Constant CREATOR.
		 */
		@SuppressWarnings("unused")
		public static final Parcelable.Creator<Dimensions> CREATOR = new Parcelable.Creator<Dimensions>() {
			public Dimensions createFromParcel(Parcel in) {
				try {
					return new Dimensions(in);
				} catch (OrmmaException e) {
					throw new RuntimeException(e);
				}
			}

			public Dimensions[] newArray(int size) {
				return new Dimensions[size];
			}
		};

		/**
		 * Instantiates a new dimensions from a parcel.
		 *
		 * @param in the in
		 * @throws OrmmaException operation failed
		 */
		protected Dimensions(Parcel in) throws OrmmaException {
			super(in);
		}


		/**
		 * The dimenstion values
		 */
		public int x, y, width, height;

	}

	/**
	 * The Class Properties for holding properties coming from javascript
	 */
	public static class Properties extends ReflectedParcelable {

		/**
		 * Instantiates a new properties from a parcel
		 *
		 * @param in the in
		 * @throws OrmmaException operation failed
		 */
		protected Properties(Parcel in) throws OrmmaException {
			super(in);
		}

		/**
		 * Instantiates a new properties.
		 */
		public Properties() {
			useBackground = false;
			useCustomClose = false;
			backgroundColor = 0;
			backgroundOpacity = 0;
		}

		/**
		 * The Constant CREATOR.
		 */
		@SuppressWarnings("unused")
		public static final Parcelable.Creator<Properties> CREATOR = new Parcelable.Creator<Properties>() {
			public Properties createFromParcel(Parcel in) {
				try {
					return new Properties(in);
				} catch (OrmmaException e) {
					throw new RuntimeException(e);
				}
			}

			public Properties[] newArray(int size) {
				return new Properties[size];
			}
		};

		//property values
		public boolean useBackground;
		public boolean useCustomClose;
		public int backgroundColor;
		public float backgroundOpacity;
	}

	/**
	 * Instantiates a new ormma controller.
	 *
	 * @param adView  the ad view
	 * @param context the context
	 */
	public OrmmaController(OrmmaView adView, Context context) {
		ormmaView = adView;
		this.context = context;
	}

	/**
	 * Constructs an object from json via reflection
	 *
	 * @param json the json
	 * @param c    the class to convert into
	 * @return the instance constructed
	 * @throws OrmmaException operation failed
	 */
	protected static Object getFromJSON(JSONObject json, Class<?> c) throws OrmmaException {
		Field[] fields;
		fields = c.getDeclaredFields();
		try {
			Object obj = c.newInstance();

			for (Field f : fields) {
				String name = f.getName();
				String JSONName = name.replace('_', '-');
				Type type = f.getType();
				String typeStr = type.toString();
				if (typeStr.equals(INT_TYPE)) {
					String value = json.getString(JSONName).toLowerCase();
					int iVal;
					if (value.startsWith("#")) {
						iVal = Color.WHITE;
						try {
							if (value.startsWith("#0x")) {
								iVal = Integer.decode(value.substring(1));
							} else {
								iVal = Integer.parseInt(value.substring(1), 16);
							}
						} catch (NumberFormatException e) {
							//Do nothing
						}
					} else {
						iVal = Integer.parseInt(value);
					}
					f.set(obj, iVal);
				} else if (typeStr.equals(STRING_TYPE)) {
					String value = json.getString(JSONName);
					f.set(obj, value);
				} else if (typeStr.equals(BOOLEAN_TYPE)) {
					boolean value = json.getBoolean(JSONName);
					f.set(obj, value);
				} else if (typeStr.equals(FLOAT_TYPE)) {
					float value = Float.parseFloat(json.getString(JSONName));
					f.set(obj, value);
				} else if (typeStr.equals(NAVIGATION_TYPE)) {
					NavigationStringEnum value = NavigationStringEnum.fromString(json.getString(JSONName));
					f.set(obj, value);
				} else if (typeStr.equals(TRANSITION_TYPE)) {
					TransitionStringEnum value = TransitionStringEnum.fromString(json.getString(JSONName));
					f.set(obj, value);
				}
			}

			return obj;
		} catch (Exception e) {
			throw new OrmmaException("Couldn't unpack JSON", e);
		}
	}

	/**
	 * The Class ReflectedParcelable.
	 */
	public static class ReflectedParcelable implements Parcelable {

		/**
		 * Instantiates a new reflected parcelable.
		 */
		public ReflectedParcelable() {

		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int describeContents() {
			return 0;
		}

		/**
		 * Instantiates a new reflected parcelable.
		 *
		 * @param in the in
		 * @throws OrmmaException operation failed
		 */
		protected ReflectedParcelable(Parcel in) throws OrmmaException {
			Field[] fields;
			Class<?> c = this.getClass();
			fields = c.getDeclaredFields();
			try {
				//Object obj = c.newInstance();
				Object obj = this;
				for (Field f : fields) {
					Class<?> type = f.getType();

					if (type.isEnum()) {
						String typeStr = type.toString();
						if (typeStr.equals(NAVIGATION_TYPE)) {
							f.set(obj, NavigationStringEnum.fromString(in.readString()));
						} else if (typeStr.equals(TRANSITION_TYPE)) {
							f.set(obj, TransitionStringEnum.fromString(in.readString()));
						}
					} else {
						Object dt = f.get(this);
						if (!(dt instanceof Creator<?>)) {
							f.set(obj, in.readValue(null));
						}
					}
				}

			} catch (Exception e) {
				throw new OrmmaException("Couldn't create parcel", e);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void writeToParcel(Parcel out, int flags1) {
			Field[] fields;
			Class<?> c = this.getClass();
			fields = c.getDeclaredFields();
			try {
				for (Field f : fields) {
					Class<?> type = f.getType();

					if (type.isEnum()) {
						String typeStr = type.toString();
						if (typeStr.equals(NAVIGATION_TYPE)) {
							out.writeString(((NavigationStringEnum) f.get(this)).getText());
						} else if (typeStr.equals(TRANSITION_TYPE)) {
							out.writeString(((TransitionStringEnum) f.get(this)).getText());
						}
					} else {
						Object dt = f.get(this);
						if (!(dt instanceof Creator<?>)) {
							out.writeValue(dt);
						}

					}
				}
			} catch (Exception e) {
				throw new RuntimeException("Couldn't write to parcel", e);
			}

		}
	}

	/**
	 * Stop all listeners.
	 */
	public abstract void stopAllListeners();
}
