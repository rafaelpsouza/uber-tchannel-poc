package br.eng.rafaelpsouza;

import com.uber.tchannel.api.TChannel;
import com.uber.tchannel.api.TFuture;
import com.uber.tchannel.api.handlers.RawRequestHandler;
import com.uber.tchannel.messages.RawRequest;
import com.uber.tchannel.messages.RawResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Rafael Souza on 13/07/16.
 */
public class Server {

    public static void main(String[] args) throws InterruptedException, ExecutionException, UnknownHostException {

        TChannel server = new TChannel.Builder("ping-server")
                .setServerHost(InetAddress.getByName("localhost"))
                .setServerPort(8080).build();
        server.makeSubChannel("ping-server")
                .register("ping-handler", new RawRequestHandler() {
                    @Override
                    public RawResponse handleImpl(RawRequest request) {
                        return new RawResponse.Builder(request)
                                .setTransportHeaders(request.getTransportHeaders())
                                .setHeader("Polo")
                                .setBody("Pong!")
                                .build();
                    }
                });
        server.listen();
        //server.shutdown();
    }

}
