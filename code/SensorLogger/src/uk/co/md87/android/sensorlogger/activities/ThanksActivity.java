/*
 * Copyright (c) 2009-2010 Chris Smith
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package uk.co.md87.android.sensorlogger.activities;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;

import uk.co.md87.android.sensorlogger.R;

/**
 *
 * @author chris
 */
public class ThanksActivity extends Activity {

    /** {@inheritDoc} */
    @Override
    public void onCreate(final Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.thanks);

        final String imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();
        final String code = "http://MD87.co.uk/android/p/" + getCode(imei);
        ((TextView) findViewById(R.id.thankslink)).setText(code);
        //Linkify.addLinks(((TextView) findViewById(R.id.viewcaption)), Linkify.WEB_URLS);
    }

    public String getCode(final String imei) {
        final String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-=_";
        final StringBuilder builder = new StringBuilder();

        long val = Long.decode(imei == null ? "0"
                : imei.matches("^[0-9]+$") ? imei : ("0x" + imei));

        while (val > 0) {
            final long bit = val % chars.length();
            val = val / chars.length();
            builder.insert(0, chars.charAt((int) bit));
        }

        while (builder.length() < 10) {
            builder.insert(0, "a");
        }

        return builder.toString();
    }

    /** {@inheritDoc} */
    @Override
    protected void onStart() {
        super.onStart();

        FlurryAgent.onStartSession(this, "TFBJJPQUQX3S1Q6IUHA6");
    }

    /** {@inheritDoc} */
    @Override
    protected void onStop() {
        super.onStop();

        FlurryAgent.onEndSession(this);
    }

}
