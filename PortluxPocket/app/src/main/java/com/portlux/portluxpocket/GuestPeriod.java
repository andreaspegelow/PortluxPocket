package com.portlux.portluxpocket;

import org.joda.time.DateTime;
import org.joda.time.Instant;

import static org.joda.time.Instant.now;

/**
 * Created by Pegelow on 2015-08-03.
 */
public class GuestPeriod {
    private DateTime from;
    private DateTime until;

    public GuestPeriod(DateTime from, DateTime until) {
        this.from = from;
        this.until = until;
    }

    public DateTime getFrom() {
        return from;
    }

    public DateTime getUntil() {
        return until;
    }
    public boolean isNow(){
        return from.isBeforeNow() && until.isAfterNow();
    }
}
