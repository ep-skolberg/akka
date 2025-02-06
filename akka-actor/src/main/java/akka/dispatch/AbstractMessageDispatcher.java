/**
 * Copyright (C) 2009-2016 Lightbend Inc. <http://www.lightbend.com>
 */

package akka.dispatch;

import akka.util.Unsafe;

import java.util.concurrent.atomic.AtomicLong;

abstract class AbstractMessageDispatcher {
    final static long shutdownScheduleOffset;
    final static AtomicLong inhabitantsOffset = new AtomicLong();

    static {
        try {
          shutdownScheduleOffset = Unsafe.instance.objectFieldOffset(MessageDispatcher.class.getDeclaredField("_shutdownScheduleDoNotCallMeDirectly"));
          inhabitantsOffset.set(Unsafe.instance.objectFieldOffset(MessageDispatcher.class.getDeclaredField("_inhabitantsDoNotCallMeDirectly")));
        } catch(Throwable t){
            throw new ExceptionInInitializerError(t);
        }
    }
}
