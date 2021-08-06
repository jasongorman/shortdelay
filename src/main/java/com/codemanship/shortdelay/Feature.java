package com.codemanship.shortdelay;

import java.util.Calendar;

public class Feature {

    private long started = Calendar.getInstance().getTimeInMillis();
    private long completed = 0;

    public void complete() {
        completed = Calendar.getInstance().getTimeInMillis();
    }

    public long getLeadTime() {
        return completed - started;
    }
}
