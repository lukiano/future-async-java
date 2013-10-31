package com.lucho.akka;

import akka.actor.UntypedActor;

public class PingerActor extends UntypedActor {

    /**
     * To be implemented by concrete UntypedActor, this defines the behavior of the
     * UntypedActor.
     */
     @Override
     public void onReceive(Object message) throws Exception {
         if (message instanceof Message) {
             Message m = (Message) message;
             switch (m) {
                 case PING:
                     getSender().tell(Message.PONG, getSelf());
                     break;
                 default:
                     unhandled(message);
                     break;
             }
         } else {
             unhandled(message);
         }
     }

}
