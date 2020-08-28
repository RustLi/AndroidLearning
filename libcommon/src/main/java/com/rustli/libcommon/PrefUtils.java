/*
 * This file is part of Popcorn Time.
 *
 * Popcorn Time is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Popcorn Time is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Popcorn Time. If not, see <http://www.gnu.org/licenses/>.
 */

package com.rustli.libcommon;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class PrefUtils {

    /**
     * Clear the central {@link SharedPreferences}
     *
     * @param context Context
     */
    public static void clear(Context context) {
        getPrefs(context).edit().clear().apply();
    }

    public static boolean clearByCommit(Context context) {
        return getPrefs(context).edit().clear().commit();
    }

    /**
     * Save a string to the central {@link SharedPreferences}
     *
     * @param context Context
     * @param key     Key of the preference
     * @param value   Value of the preference
     */
    public static void save(Context context, String key, String value) {
        getPrefs(context).edit().putString(key, value).apply();
    }

    public static boolean saveByCommit(Context context, String key, String value) {
        return getPrefs(context).edit().putString(key, value).commit();
    }

    /**
     * Get a saved string from the central {@link SharedPreferences}
     *
     * @param context      Context
     * @param key          Key of the preference
     * @param defaultValue Default
     * @return The saved String
     */
    public static String get(Context context, String key, String defaultValue) {
        return getPrefs(context).getString(key, defaultValue);
    }

    /**
     * Save a boolean to the central {@link SharedPreferences}
     *
     * @param context Context
     * @param key     Key of the preference
     * @param value   Value of the preference
     */
    public static void save(Context context, String key, boolean value) {
        getPrefs(context).edit().putBoolean(key, value).apply();
    }

    /**
     * Get a saved boolean from the central {@link SharedPreferences}
     *
     * @param context      Context
     * @param key          Key of the preference
     * @param defaultValue Default
     * @return The saved bool
     */
    public static Boolean get(Context context, String key, boolean defaultValue) {
        return getPrefs(context).getBoolean(key, defaultValue);
    }

    /**
     * Save a long to the central {@link SharedPreferences}
     *
     * @param context Context
     * @param key     Key of the preference
     * @param value   Value of the preference
     */
    public static void save(Context context, String key, long value) {
        getPrefs(context).edit().putLong(key, value).apply();
    }

    /**
     * Get a saved long from the central {@link SharedPreferences}
     *
     * @param context      Context
     * @param key          Key of the preference
     * @param defaultValue Default
     * @return The saved long
     */
    public static long get(Context context, String key, long defaultValue) {
        return getPrefs(context).getLong(key, defaultValue);
    }

    /**
     * Save a int to the central {@link SharedPreferences}
     *
     * @param context Context
     * @param key     Key of the preference
     * @param value   Value of the preference
     */
    public static void save(Context context, String key, int value) {
        getPrefs(context).edit().putInt(key, value).apply();
    }

    /**
     * Get a saved integer from the central {@link SharedPreferences}
     *
     * @param context      Context
     * @param key          Key of the preference
     * @param defaultValue Default
     * @return The saved integer
     */
    public static int get(Context context, String key, int defaultValue) {
        return getPrefs(context).getInt(key, defaultValue);
    }

    public static void save(Context context, String key, float value) {
        getPrefs(context).edit().putFloat(key, value).apply();
    }

    /**
     * Get a saved integer from the central {@link SharedPreferences}
     *
     * @param context      Context
     * @param key          Key of the preference
     * @param defaultValue Default
     * @return The saved float
     */
    public static float get(Context context, String key, float defaultValue) {
        return getPrefs(context).getFloat(key, defaultValue);
    }

    /**
     * Check if the central {@link SharedPreferences} contains a preference that uses
     * that key
     *
     * @param context Context
     * @param key     Key
     * @return {@code true} if there exists a preference
     */
    public static Boolean contains(Context context, String key) {
        return getPrefs(context).contains(key);
    }

    /**
     * Remove item from the central {@link SharedPreferences} if it exists
     *
     * @param context Context
     * @param key     Key
     */
    public static void remove(Context context, String key) {
        getPrefs(context).edit().remove(key).apply();
    }

    /**
     * Get the central {@link SharedPreferences}
     *
     * @param context Context
     * @return {@link SharedPreferences}
     */
    public static SharedPreferences getPrefs(Context context) {
//        return new ObscuredSharedPreferences(context, PreferenceManager
//                .getDefaultSharedPreferences(context));
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

}