/*
 * Copyright (C) 2011 Glowworm Software
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package mobi.glowworm.utils.debug;

import android.util.Log;

/**
 * A Basic logging class that allows ProGuard to strip out all logging artifacts,
 * including string concatenation which is left in the app when using the default logging SDK.
 * <p/>
 * Adapted from <a href="https://gist.github.com/mobiRic/9786897">mobiRic/Dbug.java on GitHub Gist</a>
 */
public class Dbug {
    /**
     * Tag for logcat output.
     */
    public static final String TAG = Dbug.class.getSimpleName();

    /**
     * Convenient debug log method that uses the application's default log tag. </p>
     *
     * @param text Array of Objects that create the debug message. This is preferable to
     *             concatenating many strings into a single parameter as that cannot be optimised
     *             away by ProGuard.
     * @see <a href="https://blogs.oracle.com/binublog/entry/debug_print_and_varargs">Varargs and debug print (Binu John's Weblog)</a>
     */
    @SuppressWarnings("JavadocReference")
    public static void log(Object... text) {
        logWithTag(TAG, text);
    }

    /**
     * Convenient debug log method that allows for custom log tags. </p>
     *
     * @param tag  Custom tag to appear in logcat
     * @param text Array of Objects that create the debug message. This is preferable to
     *             concatenating many strings into a single parameter as that cannot be optimised
     *             away by ProGuard.
     * @see #log(Object...)
     */
    public static void logWithTag(String tag, Object... text) {
        // release mode
//        if (!BuildConfig.LOGGING) {
//            return;
//        }

        // manually concatenate the various arguments
        StringBuilder sb = new StringBuilder();
        if (text != null) {
            for (Object object : text) {
                if (object != null) {
                    sb.append(object.toString());
                } else {
                    sb.append("<null>");
                }
            }
        }

        Log.d(tag, sb.toString());
    }

}
