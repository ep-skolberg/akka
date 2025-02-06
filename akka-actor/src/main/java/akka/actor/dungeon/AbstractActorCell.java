/**
 * Copyright (C) 2009-2016 Lightbend Inc. <http://www.lightbend.com>
 */

package akka.actor.dungeon;

import akka.actor.ActorCell;
import akka.util.Unsafe;

import java.util.concurrent.atomic.AtomicLong;

final class AbstractActorCell {
    final static long mailboxOffset;
    final static long childrenOffset;
    final static AtomicLong nextNameOffset = new AtomicLong();
    final static long functionRefsOffset;

    static {
        try {
          mailboxOffset = Unsafe.instance.objectFieldOffset(ActorCell.class.getDeclaredField("akka$actor$dungeon$Dispatch$$_mailboxDoNotCallMeDirectly"));
          childrenOffset = Unsafe.instance.objectFieldOffset(ActorCell.class.getDeclaredField("akka$actor$dungeon$Children$$_childrenRefsDoNotCallMeDirectly"));
          nextNameOffset.set(Unsafe.instance.objectFieldOffset(ActorCell.class.getDeclaredField("akka$actor$dungeon$Children$$_nextNameDoNotCallMeDirectly")));
          functionRefsOffset = Unsafe.instance.objectFieldOffset(ActorCell.class.getDeclaredField("akka$actor$dungeon$Children$$_functionRefsDoNotCallMeDirectly"));
        } catch(Throwable t){
            throw new ExceptionInInitializerError(t);
        }
    }
}
