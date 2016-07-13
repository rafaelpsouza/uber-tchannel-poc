package br.eng.rafaelpsouza;

import com.uber.tchannel.api.TChannel;
import com.uber.tchannel.api.TFuture;
import com.uber.tchannel.messages.RawRequest;
import com.uber.tchannel.messages.RawResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author Rafael Souza
 */
public class Client {
    
    public static void main(String[] args) throws UnknownHostException, InterruptedException, ExecutionException {
        // create another TChannel for client.
        TChannel client = new TChannel.Builder("ping-client").setInitTimeout(5000).build();
        RawRequest request = new RawRequest.Builder("ping-server", "ping-handler")
                .setHeader("Marco")
                .setBody("Ping!")
                .build();

        // make an asynchronous request
        TFuture<RawResponse> responseFuture = client
                .makeSubChannel("ping-server").send(
                request,
                InetAddress.getByName("127.0.0.1"),
                8080
        );

        // block and wait for the response
        try (RawResponse response = responseFuture.get()) {
            System.out.println(response);
        }
         client.shutdown();
        
    }
    
}
