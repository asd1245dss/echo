package io.summer.tool.reactive;

import io.reactivex.Flowable;

/**
 * @author ChangWei Li
 * @version 2018-03-23 13:50
 */
public class ReactiveTest {

    public static void main(String[] args) {
        Flowable
                .just("Hello World")
                .subscribe(System.out::println);
    }

}
