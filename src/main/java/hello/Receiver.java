package hello;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

import com.ning.http.client.spring.NingAsyncClientHttpRequestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.AsyncClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.AsyncRestOperations;
import org.springframework.web.client.AsyncRestTemplate;

import reactor.core.Reactor;
import reactor.spring.annotation.Selector;

@Service
public class Receiver /*implements Consumer<Event<Integer>>*/ {

    @Autowired
    private CountDownLatch latch;

    @Autowired
    public Reactor reactor;
    
    //HttpComponentsAsyncClientHttpRequestFactory
    AsyncClientHttpRequestFactory asyncFactory = new NingAsyncClientHttpRequestFactory();
    ClientHttpRequestFactory syncFactory = new SimpleClientHttpRequestFactory();
    AsyncRestOperations restTemplate = new AsyncRestTemplate(asyncFactory, syncFactory);

    /*
    @Selector(value="jokes")
    //public void accept(Event<Integer> ev) {
    //    Integer jokeNumber = ev.getData();
    public void accept(Integer jokeNumber) {
        JokeResource jokeResource = restTemplate.getForObject("http://api.icndb.com/jokes/random", JokeResource.class);
        System.out.println("Joke " + jokeNumber + ": " + jokeResource.getValue().getJoke());
        latch.countDown();
    }
    */

    @Selector(value="jokes")
    public void accept(Integer jokeNumber) throws Exception {
        Future<ResponseEntity<JokeResource>> jokeResource = restTemplate.getForEntity("http://api.icndb.com/jokes/random", JokeResource.class);
        System.out.println("Joke " + jokeNumber + ": " + jokeResource.get().getBody().getValue().getJoke());
        latch.countDown();
    }

}