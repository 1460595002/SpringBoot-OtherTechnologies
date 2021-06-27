package cn.jinronga.springwebflux.fluxMono;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @ClassName FluxMono
 * @Author 郭金荣
 * @Date 2021/6/26 18:59
 * @Description FluxMono
 * @Version 1.0
 */
public class FluxMono {
    public static void main(String[] args) {
        //just方法直接声明
        Flux.just(1, 2, 3, 4);
        Mono.just(1);

        //其他方法
        Integer[] array = {1, 2, 3, 4};
        Flux.fromArray(array);
        List<Integer> list = Arrays.asList(array);
        Flux.fromIterable(list);
        Stream<Integer> stream = list.stream();
        Flux.fromStream(stream);


    }
}
