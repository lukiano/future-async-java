package com.lucho.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.OnComplete;
import akka.japi.Util;
import akka.pattern.Patterns;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

public class Main {

    public static final class CoolCompletion<T> extends OnComplete<T> {

        private final BiConsumer<Throwable, T> completer;

        public CoolCompletion(final BiConsumer<Throwable, T> completer) {
            this.completer = completer;
        }

        @Override
        public final void onComplete(Throwable failure, T success) throws Throwable {
            completer.accept(failure, success);
        }
    }

    public static final class FutureCompletion<T> extends OnComplete<T> {

        private final CompletableFuture<T> completer;

        public FutureCompletion(final CompletableFuture<T> completer) {
            this.completer = completer;
        }

        @Override
        public final void onComplete(Throwable failure, T success) throws Throwable {
            if (failure == null) {
                completer.complete(success);
            } else {
                completer.completeExceptionally(failure);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        //BiPredicate
        ActorSystem actorSystem = ActorSystem.create();
        try {
            ActorRef pinger = actorSystem.actorOf(Props.create(PingerActor.class));
            scala.concurrent.Future<Object> future = Patterns.ask(pinger, Message.PING, TimeUnit.MINUTES.toMillis(1));
            CompletableFuture<Message> javaFuture = new CompletableFuture<>();

            future.mapTo(Util.classTag(Message.class)).onComplete(new FutureCompletion<Message>(javaFuture), actorSystem.dispatcher());

            //javaFuture.thenAccept(System.out::println);
            javaFuture.thenAcceptAsync(System.out::println, (Executor) actorSystem.dispatcher());
            javaFuture.join();
        } finally {
            actorSystem.shutdown();
        }
    }
}
