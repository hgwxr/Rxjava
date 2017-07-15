package com.wl.rxjava;


import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.observables.StringObservable;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static rx.subscriptions.Subscriptions.from;

/**
 * Created by Administrator on 2017/6/2.
 */
public class Demo1 {
    // <T> Observable<T> just(T t1, T t2, T t3, T t4, T t5)
    public static void just() {
        Observable.just(1, 2, 3, 4, 5).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(final Throwable e) {
                System.err.println("onError: " + e);
            }

            @Override
            public void onNext(final Integer i) {
                System.out.println("onNext: " + i);
            }
        });
    }

    // <T> Observable<T> from(T[] array)
    public static void from1() {
        Integer[] data = new Integer[]{1, 2, 3, 4, 5};
        Observable.from(data).subscribe(
                new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted");
                    }

                    @Override
                    public void onError(final Throwable e) {
                        System.err.println("onError: " + e);
                    }

                    @Override
                    public void onNext(final Integer s) {
                        System.out.println("onNext: " + s);
                    }
                }
        );
    }

    // <T> Observable<T> from(Iterable<? extends T> iterable)
    public static void from2() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            list.add("List Item " + i);
        }
        Observable.from(list).timeout(1,TimeUnit.SECONDS).subscribe(
                new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted");
                    }

                    @Override
                    public void onError(final Throwable e) {
                        System.err.println("onError: " + e);
                    }

                    @Override
                    public void onNext(final String s) {
                        System.out.println("onNext: " + s);
                    }
                }
        );
    }

    // <T> Observable<T> from(Future<? extends T> future)
    public static void from3() {
        final ExecutorService executor = Executors.newCachedThreadPool();
        final Callable<String> callable = new Callable<String>() {

            @Override
            public String call() throws Exception {
                return "Callable";
            }
        };
        final Future<String> future = executor.submit(callable);
        Observable.from(future).subscribe(
                new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted");
                    }

                    @Override
                    public void onError(final Throwable e) {
                        System.err.println("onError: " + e);
                    }

                    @Override
                    public void onNext(final String s) {
                        System.out.println("onNext: " + s);
                    }
                }
        );
    }

    // Observable<T> repeat(long count)
    public static void repeat() {
        Observable.just("One")
                .repeat(5).subscribe(
                new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted");
                    }

                    @Override
                    public void onError(final Throwable e) {
                        System.err.println("onError: " + e);
                    }

                    @Override
                    public void onNext(final String s) {
                        System.out.println("onNext: " + s);
                    }
                }
        );
    }

    // Observable<T> repeatWhen(final Func1<? super Observable<? extends Void>,
    // ? extends Observable<?>> notificationHandler)
    public static void repeatWhen() {
        Observable.just("One").repeatWhen(
                new Func1<Observable<? extends Void>, Observable<?>>() {
                    @Override
                    public Observable<?> call(final Observable<? extends Void> observable) {
                        return observable.take(3);
                    }
                }).subscribe(
                new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted");
                    }

                    @Override
                    public void onError(final Throwable e) {
                        System.err.println("onError: " + e);
                    }

                    @Override
                    public void onNext(final String s) {
                        System.out.println("onNext: " + s);
                    }
                }
        );
    }

    // <T> Observable<T> Observable.create(Observable.OnSubscribe<T> f)
    // Subscription subscribe(final Observer<? super T> observer)
    public static void create1() {
        Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(final Subscriber<? super String> subscriber) {
                        try {
                            if (!subscriber.isUnsubscribed()) {
                                for (int i = 0; i < 5; i++) {
                                    subscriber.onNext("Item " + i);
                                }
                                subscriber.onCompleted();
                            }
                        } catch (Exception ex) {
                            subscriber.onError(ex);
                        }
                    }
                }
        ).subscribe(
                new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted");
                    }

                    @Override
                    public void onError(final Throwable e) {
                        System.err.println("onError: " + e);
                    }

                    @Override
                    public void onNext(final String s) {
                        System.out.println("onNext: " + s);
                    }
                }
        );
    }

    // <T> Observable<T> Observable.create(Observable.OnSubscribe<T> f)
    // Subscription subscribe(final Action1<? super T> onNext,
    // final Action1<Throwable> onError,
    // final Action0 onComplete)
    public static void create2() {
        Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(final Subscriber<? super String> subscriber) {
                        try {
                            if (!subscriber.isUnsubscribed()) {
                                for (int i = 0; i < 5; i++) {
                                    subscriber.onNext("Item " + i);
                                }
                                subscriber.onCompleted();
                            }
                        } catch (Exception ex) {
                            subscriber.onError(ex);
                        }
                    }
                }
        ).subscribe(
                new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted");
                    }

                    @Override
                    public void onError(final Throwable e) {
                        System.err.println("onError: " + e);
                    }

                    @Override
                    public void onNext(final String s) {
                        System.out.println("onNext: " + s);
                    }
                }
        );
    }

    // <T> Observable<T> defer(Func0<Observable<T>> observableFactory)
    public static void defer() {
        Observable<Integer> observable = Observable.defer(new Func0<Observable<Integer>>() {
            @Override
            public Observable<Integer> call() {
                return Observable.just(1, 2, 3, 4, 5);
            }
        });
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("emit nothing before subscribe");
        observable.subscribe(
                new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted");
                    }

                    @Override
                    public void onError(final Throwable e) {
                        System.err.println("onError: " + e);
                    }

                    @Override
                    public void onNext(final Integer s) {
                        System.out.println("onNext: " + s);
                    }
                }
        );
    }

    // Observable<Integer> range(int start, int count)
    public static void range() {
        Observable.range(100, 5).subscribe(
                new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted");
                    }

                    @Override
                    public void onError(final Throwable e) {
                        System.err.println("onError: " + e);
                    }

                    @Override
                    public void onNext(final Integer s) {
                        System.out.println("onNext: " + s);
                    }
                }
        );
    }

    // Observable<Long> interval(long interval, TimeUnit unit, Scheduler scheduler)
    public static void interval() {
        Observable.interval(2, TimeUnit.SECONDS, Schedulers.immediate()).take(3).subscribe(
                new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted");
                    }

                    @Override
                    public void onError(final Throwable e) {
                        System.err.println("onError: " + e);
                    }

                    @Override
                    public void onNext(final Long s) {
                        System.out.println("onNext: " + s + " time: "
                                + System.currentTimeMillis() / 1000);
                    }
                }
        );
    }


    public static void timer() {
        System.out.println("Start time: "
                + System.currentTimeMillis() / 1000);
        Observable.timer(5, TimeUnit.SECONDS, Schedulers.immediate()).subscribe(
                new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted");
                    }

                    @Override
                    public void onError(final Throwable e) {
                        System.err.println("onError: " + e);
                    }

                    @Override
                    public void onNext(final Long s) {
                        System.out.println("onNext: " + s + " time: "
                                + System.currentTimeMillis() / 1000);
                    }
                }
        );
    }

    public static void empty() {
        Observable.<Integer>empty().subscribe(
                new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted");
                    }

                    @Override
                    public void onError(final Throwable e) {
                        System.err.println("onError: " + e);
                    }

                    @Override
                    public void onNext(final Integer s) {
                        System.out.println("onNext: " + s);
                    }
                }
        );
    }

    public static void error() {
        Observable.<Integer>error(new RuntimeException("error operator test")).subscribe(
                new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted");
                    }

                    @Override
                    public void onError(final Throwable e) {
                        System.err.println("onError: " + e);
                    }

                    @Override
                    public void onNext(final Integer s) {
                        System.out.println("onNext: " + s);
                    }
                }
        );
    }

    public static void never() {
        Observable.<Integer>never().subscribe(
                new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted");
                    }

                    @Override
                    public void onError(final Throwable e) {
                        System.err.println("onError: " + e);
                    }

                    @Override
                    public void onNext(final Integer s) {
                        System.out.println("onNext: " + s);
                    }
                }
        );
    }
    public static void main(String[] args) {
        //create();
        //fromF();
        interval();
       // range();
//        timer();
//        defer();
//        from2();
    }

    private static void fromF() {
         Integer[] items={0,1,2,3,4,5};
        Observable<Integer> observable = Observable.from(items);
        observable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(integer);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                System.out.println("Error encountered: " + throwable.getMessage());
            }
        }, new Action0() {
            @Override
            public void call() {
                System.out.println("Sequence complete");
            }
        });
      /*  Integer[] items = { 0, 1, 2, 3, 4, 5 };
        Observable myObservable = Observable.from(items);

        myObservable.subscribe(
                new Action1<Integer>() {
                    @Override
                    public void call(Integer item) {
                        System.out.println(item);
                    }
                },
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable error) {
                        System.out.println("Error encountered: " + error.getMessage());
                    }
                },
                new Action0() {
                    @Override
                    public void call() {
                        System.out.println("Sequence complete");
                    }
                }
        );*/
    }

    private static void create() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        for (int i = 1; i < 5; i++) {
                            subscriber.onNext(i);
                        }
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("Sequence complete.");
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("Next: " + integer);
            }
        });
       /* Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> observer) {
                try {
                    if (!observer.isUnsubscribed()) {
                        for (int i = 1; i < 5; i++) {
                            observer.onNext(i);
                        }
                        observer.onCompleted();
                    }
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        } ).subscribe(new Subscriber<Integer>() {
            @Override
            public void onNext(Integer item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Sequence complete.");
            }
        });*/
    }
}
