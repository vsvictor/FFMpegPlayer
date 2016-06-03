package media.ffmpeg;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class Metadata
{
    public static final int STRING_VAL     = 1;
    public static final int INTEGER_VAL    = 2;
    public static final int BOOLEAN_VAL    = 3;
    public static final int LONG_VAL       = 4;
    public static final int DOUBLE_VAL     = 5;
    public static final int DATE_VAL       = 6;
    public static final int BYTE_ARRAY_VAL = 7;
    private HashMap<String, String> mParcel;
    public boolean parse(HashMap<String, String> metadata) {
    	if (metadata == null) {
    		return false;
    	} else {
    		mParcel = metadata;
    		return true;
    	}
    }
    public boolean has(final String metadataId) {
        if (!checkMetadataId(metadataId)) {
            throw new IllegalArgumentException("Invalid key: " + metadataId);
        }
        return mParcel.containsKey(metadataId);
    }
    public HashMap<String, String> getAll() {
        return mParcel;
    }
    public String getString(final String key) {
        checkType(key, STRING_VAL);
        return String.valueOf(mParcel.get(key));
    }
    public int getInt(final String key) {
        checkType(key, INTEGER_VAL);
        return Integer.valueOf(mParcel.get(key));
    }
    public boolean getBoolean(final String key) {
        checkType(key, BOOLEAN_VAL);
        return Integer.valueOf(mParcel.get(key)) == 1;
    }
    public long getLong(final String key) {
        checkType(key, LONG_VAL);
        return Long.valueOf(mParcel.get(key));
    }
    public double getDouble(final String key) {
        checkType(key, DOUBLE_VAL);
        return Double.valueOf(mParcel.get(key));
    }
    public byte[] getByteArray(final String key) {
        checkType(key, BYTE_ARRAY_VAL);
        return mParcel.get(key).getBytes();
    }
    public Date getDate(final String key) {
        checkType(key, DATE_VAL);
        final long timeSinceEpoch = Long.valueOf(mParcel.get(key));
        final String timeZone = mParcel.get(key);

        if (timeZone.length() == 0) {
            return new Date(timeSinceEpoch);
        } else {
            TimeZone tz = TimeZone.getTimeZone(timeZone);
            Calendar cal = Calendar.getInstance(tz);

            cal.setTimeInMillis(timeSinceEpoch);
            return cal.getTime();
        }
    }
    private boolean checkMetadataId(final String val) {
        return true;
    }
    private void checkType(final String key, final int expectedType) {
    	String type = mParcel.get(key);
    	
    	if (type == null) {
            throw new IllegalStateException("Wrong type " + expectedType + " but got " + type);
        }
    }
}
