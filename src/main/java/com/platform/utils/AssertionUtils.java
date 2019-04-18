package com.platform.utils;
import java.util.concurrent.TimeUnit;

public class AssertionUtils {


    public static void repeatWhenFailedForSeconds(int seconds, Runnable r)
    {
        long waitedSoFar = 0;
        AssertionError lastError = null;

        while(waitedSoFar < TimeUnit.SECONDS.toMillis(seconds)){
            long t0 = System.currentTimeMillis();
            if(waitedSoFar > 0){
                sleepSeconds(3);
            }

            try{
                r.run();
                return;
            }
            catch (AssertionError e)
            {
                waitedSoFar += System.currentTimeMillis() - t0;
                lastError = e;
            }
        }
        throw new AssertionError("Waited for too long", lastError);
    }

    private static void sleepSeconds(int seconds) {
        try
        {
            Thread.sleep(TimeUnit.SECONDS.toMillis(seconds));
        }
        catch (InterruptedException e)
        {
            throw new AssertionError(e);
        }
    }
}
