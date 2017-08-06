package com.siddhu.capp.network;

import android.util.Base64;
import android.util.Log;

import com.siddhu.capp.utils.AppConstants;
import com.siddhu.capp.utils.UrlUtility;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.SocketChannel;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.CertificatePinner;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Utility class for handling network call related common methods.
 * This class contains methods that creates instance of Retrofit client.
 * The client instance is used make HTTP request to remote server. The Retrofit client
 * contains instance of OkHttpClient.Builder. SSL certificate, certificate
 * pinning, connection timeout are set for that client.
 * <p>
 *     Retrofit is type-safe REST API client for Android.
 *     Retrofit turns your HTTP API into a Java interface. The Retrofit class generates an
 *     implementation of the {@link ApiService} interface. Each Call from the created
 *     {@link ApiService} can make a synchronous or asynchronous HTTP request to the remote web server.
 *     The advantage of using
 *     Retrofit is :
 *     1. Easy to use REST API
 *     2. Comes along with OKHttp
 *     3. Performs async and sync requests with automatic JSON parsing without any extra effort
 * </p>
 */
public class NetworkUtility {

    // Custom SSLSocketFactory to remove SSLV3 protocol and enable only TLS protocol
    private static final String TAG = NetworkUtility.class.getSimpleName();
    public static final long CONNECTION_TIMEOUT = 30000;

    /**
     * Generic method to create service instance for making network call.
     * Takes header info as input and forms request to create service instance.
     *
     * @param header key-value pair data structure contains header information.
     * @return Returns instance of IServiceAPI.
     */
    public static ApiService getServiceInstance(final Map<String, String> header) {
        //sets the log
        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);
        client.readTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                final Headers headers = Headers.of(header);
                final Request request = chain.request().newBuilder().headers(headers).build();
                return chain.proceed(request);
            }
        });
        // for https
//
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType)
                                throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType)
                                throws CertificateException {
                        }

                        @Override

                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            };

            byte[] publicKeyHashByte = AppConstants.PUBLIC_KEY_HASH.getBytes();
            byte[] publicKeyHashEnByte = Base64.decode(AppConstants.PUBLIC_KEY_HASH, Base64.DEFAULT);
            String encodedPublicHashKey = new String(publicKeyHashEnByte);

            /*Certificate Pinning code for security purpose*/
            CertificatePinner certificatePinner = new CertificatePinner.Builder()
                    .add(AppConstants.HOST_URL, encodedPublicHashKey)
                    .build();
            client.certificatePinner(certificatePinner).build();

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("TLSv1", "TLSv2");

            sslContext.init(null, trustAllCerts, new SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            SSLSocketFactory NoSSLv3Factory = new NoSSLv3SocketFactory(sslContext.getSocketFactory());
            client.sslSocketFactory(NoSSLv3Factory);
            client.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
            Log.e(AppConstants.EXCPTN_TAG, e.getMessage(), e);
        }
        /*// add http log interceptor
        if (!Utility.isBuildFlavour(Utility.ESSO_PROD)) {
            client.addInterceptor(httpLoggingInterceptor);
        }*/
        final OkHttpClient okHttpClient = client.build();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlUtility.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(ApiService.class);
    }

    /**
     * getServiceInstance by passing header
     * @param header
     * @param baseUrl
     * @return
     */
    public static ApiService getServiceInstance(final Map<String, String> header, String baseUrl) {
        final OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                final Headers headers = Headers.of(header);
                final Request request = chain.request().newBuilder().headers(headers).build();
                return chain.proceed(request);
            }
        });
        final OkHttpClient okHttpClient = client.build();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(ApiService.class);
    }

    /**
     * call to get Address Lookup Service
     * @return
     */
   /* public static ApiService getAddressLookupServiceInstance() {
        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient.Builder client = new OkHttpClient.Builder();

        final OkHttpClient okHttpClient = client.build();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlUtility.ADDRESS_LOOKUP_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(ApiService.class);
    }*/

    /**
     * to get Address Autocomplete ServiceInstance
     * @param baseUrl
     * @return
     */
    public static ApiService getAddressAutocompleteServiceInstance(String baseUrl) {
        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(httpLoggingInterceptor);
        final OkHttpClient okHttpClient = client.build();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(ApiService.class);
    }

    public static class NoSSLv3SocketFactory extends SSLSocketFactory {
        private final SSLSocketFactory delegate;

        public NoSSLv3SocketFactory() {
            this.delegate = HttpsURLConnection.getDefaultSSLSocketFactory();
        }

        public NoSSLv3SocketFactory(SSLSocketFactory delegate) {
            this.delegate = delegate;
        }

        @Override
        public String[] getDefaultCipherSuites() {
            return delegate.getDefaultCipherSuites();
        }

        @Override
        public String[] getSupportedCipherSuites() {
            return delegate.getSupportedCipherSuites();
        }

        private Socket makeSocketSafe(Socket socket) {
            if (socket instanceof SSLSocket) {
                socket = new NoSSLv3SSLSocket((SSLSocket) socket);
            }
            return socket;
        }

        /**
         * createSocket
         * @param s
         * @param host
         * @param port
         * @param autoClose
         * @return
         * @throws IOException
         */

        @Override
        public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
            return makeSocketSafe(delegate.createSocket(s, host, port, autoClose));
        }

        @Override
        public Socket createSocket(String host, int port) throws IOException {
            return makeSocketSafe(delegate.createSocket(host, port));
        }

        @Override
        public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
            return makeSocketSafe(delegate.createSocket(host, port, localHost, localPort));
        }

        @Override
        public Socket createSocket(InetAddress host, int port) throws IOException {
            return makeSocketSafe(delegate.createSocket(host, port));
        }

        @Override
        public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
            return makeSocketSafe(delegate.createSocket(address, port, localAddress, localPort));
        }

        private class NoSSLv3SSLSocket extends DelegateSSLSocket {


            private NoSSLv3SSLSocket(SSLSocket delegate) {
                super(delegate);

            }

            /**
             * sets the names of the protocol versions which are currently enabled for use on this connection
             * @param protocols
             */
            @Override
            public void setEnabledProtocols(String[] protocols) {
                if (protocols != null && protocols.length == 1 && "SSLv3".equals(protocols[0])) {
                    List<String> enabledProtocols = new ArrayList<String>(Arrays.asList(delegate.getEnabledProtocols()));
                    if (enabledProtocols.size() > 1) {
                        enabledProtocols.remove("SSLv3");
                    }
                    protocols = enabledProtocols.toArray(new String[enabledProtocols.size()]);
                }

                super.setEnabledProtocols(protocols);
            }
        }

        public class DelegateSSLSocket extends SSLSocket {

            protected final SSLSocket delegate;

            DelegateSSLSocket(SSLSocket delegate) {
                this.delegate = delegate;
            }

            @Override
            public String[] getSupportedCipherSuites() {
                return delegate.getSupportedCipherSuites();
            }

            @Override
            public String[] getEnabledCipherSuites() {
                return delegate.getEnabledCipherSuites();
            }

            @Override
            public void setEnabledCipherSuites(String[] suites) {
                delegate.setEnabledCipherSuites(suites);
            }

            @Override
            public String[] getSupportedProtocols() {
                return delegate.getSupportedProtocols();
            }

            @Override
            public String[] getEnabledProtocols() {
                return delegate.getEnabledProtocols();
            }

            @Override
            public void setEnabledProtocols(String[] protocols) {
                delegate.setEnabledProtocols(protocols);
            }

            @Override
            public SSLSession getSession() {
                return delegate.getSession();
            }

            @Override
            public void addHandshakeCompletedListener(HandshakeCompletedListener listener) {
                delegate.addHandshakeCompletedListener(listener);
            }

            @Override
            public void removeHandshakeCompletedListener(HandshakeCompletedListener listener) {
                delegate.removeHandshakeCompletedListener(listener);
            }

            /**
             * Starts an SSL handshake on this connection
             * @throws IOException
             */
            @Override
            public void startHandshake() throws IOException {
                delegate.startHandshake();
            }

            @Override
            public boolean getUseClientMode() {
                return delegate.getUseClientMode();
            }

            /**
             * Configures the socket to use client (or server) mode when handshaking.
             * @param mode
             */
            @Override
            public void setUseClientMode(boolean mode) {
                delegate.setUseClientMode(mode);
            }

            @Override
            public boolean getNeedClientAuth() {
                return delegate.getNeedClientAuth();
            }

            /**
             * Configures the socket to require client authentication.
             * @param need
             */
            @Override
            public void setNeedClientAuth(boolean need) {
                delegate.setNeedClientAuth(need);
            }

            @Override
            public boolean getWantClientAuth() {
                return delegate.getWantClientAuth();
            }

            /**
             * Configures the socket to request client authentication.
             * @param want
             */
            @Override
            public void setWantClientAuth(boolean want) {
                delegate.setWantClientAuth(want);
            }

            @Override
            public boolean getEnableSessionCreation() {
                return delegate.getEnableSessionCreation();
            }

            @Override
            public void setEnableSessionCreation(boolean flag) {
                delegate.setEnableSessionCreation(flag);
            }

            /**
             * Binds the socket to a local address.
             * @param localAddr
             * @throws IOException
             */
            @Override
            public void bind(SocketAddress localAddr) throws IOException {
                delegate.bind(localAddr);
            }

            /**
             * Closes this socket.
             * @throws IOException
             */

            @Override
            public synchronized void close() throws IOException {
                delegate.close();
            }

            /**
             * Connects this socket to the server with a specified timeout value.
             * @param remoteAddr
             * @throws IOException
             */
            @Override
            public void connect(SocketAddress remoteAddr) throws IOException {
                delegate.connect(remoteAddr);
            }

            @Override
            public void connect(SocketAddress remoteAddr, int timeout) throws IOException {
                delegate.connect(remoteAddr, timeout);
            }

            /**
             * Returns the unique SocketChannel object associated with this socket, if any.
             * @return
             */
            @Override
            public SocketChannel getChannel() {
                return delegate.getChannel();
            }

            /**
             * Returns the address to which the socket is connected.
             * @return
             */
            @Override
            public InetAddress getInetAddress() {
                return delegate.getInetAddress();
            }

            /**
             * Returns an input stream for this socket.
             * @return
             * @throws IOException
             */
            @Override
            public InputStream getInputStream() throws IOException {
                return delegate.getInputStream();
            }

            /**
             * Tests if SO_KEEPALIVE is enabled.
             * @return
             * @throws SocketException
             */
            @Override
            public boolean getKeepAlive() throws SocketException {
                return delegate.getKeepAlive();
            }

            /**
             * Enable/disable SO_KEEPALIVE.
             * @param keepAlive
             * @throws SocketException
             */
            @Override
            public void setKeepAlive(boolean keepAlive) throws SocketException {
                delegate.setKeepAlive(keepAlive);
            }

            @Override
            public InetAddress getLocalAddress() {
                return delegate.getLocalAddress();
            }

            @Override
            public int getLocalPort() {
                return delegate.getLocalPort();
            }

            /**
             * Returns the address of the endpoint this socket is bound to.
             * @return
             */
            @Override
            public SocketAddress getLocalSocketAddress() {
                return delegate.getLocalSocketAddress();
            }

            /**
             * Tests if SO_OOBINLINE is enabled.
             * @return
             * @throws SocketException
             */
            @Override
            public boolean getOOBInline() throws SocketException {
                return delegate.getOOBInline();
            }

            /**
             * Enable/disable SO_OOBINLINE (receipt of TCP urgent data) By default, this option is disabled and TCP urgent data received on a socket is silently discarded.
             * @param oobinline
             * @throws SocketException
             */
            @Override
            public void setOOBInline(boolean oobinline) throws SocketException {
                delegate.setOOBInline(oobinline);
            }

            /**
             * Returns an output stream for this socket.
             * @return
             * @throws IOException
             */
            @Override
            public OutputStream getOutputStream() throws IOException {
                return delegate.getOutputStream();
            }

            /**
             * Returns the remote port number to which this socket is connected.
             * @return
             */
            @Override
            public int getPort() {
                return delegate.getPort();
            }

            @Override
            public synchronized int getReceiveBufferSize() throws SocketException {
                return delegate.getReceiveBufferSize();
            }

            /**
             * Sets the SO_RCVBUF option to the specified value for this Socket.
             * @param size
             * @throws SocketException
             */
            @Override
            public synchronized void setReceiveBufferSize(int size) throws SocketException {
                delegate.setReceiveBufferSize(size);
            }

            /**
             * Returns the address of the endpoint this socket is connected to, or null if it is unconnected.
             * @return
             */
            @Override
            public SocketAddress getRemoteSocketAddress() {
                return delegate.getRemoteSocketAddress();
            }

            /**
             * Tests if SO_REUSEADDR is enabled.
             * @return
             * @throws SocketException
             */
            @Override
            public boolean getReuseAddress() throws SocketException {
                return delegate.getReuseAddress();
            }

            /**
             * Enable/disable the SO_REUSEADDR socket option.
             * @param reuse
             * @throws SocketException
             */
            @Override
            public void setReuseAddress(boolean reuse) throws SocketException {
                delegate.setReuseAddress(reuse);
            }

            @Override
            public synchronized int getSendBufferSize() throws SocketException {
                return delegate.getSendBufferSize();
            }

            @Override
            public synchronized void setSendBufferSize(int size) throws SocketException {
                delegate.setSendBufferSize(size);
            }

            /**
             * Returns setting for SO_LINGER.
             * @return
             * @throws SocketException
             */
            @Override
            public int getSoLinger() throws SocketException {
                return delegate.getSoLinger();
            }

            /**
             * Returns setting for SO_TIMEOUT.
             * @return
             * @throws SocketException
             */
            @Override
            public synchronized int getSoTimeout() throws SocketException {
                return delegate.getSoTimeout();
            }

            /**
             * Enable/disable SO_TIMEOUT with the specified timeout, in milliseconds.
             * @param timeout
             * @throws SocketException
             */
            @Override
            public synchronized void setSoTimeout(int timeout) throws SocketException {
                delegate.setSoTimeout(timeout);
            }

            @Override
            public boolean getTcpNoDelay() throws SocketException {
                return delegate.getTcpNoDelay();
            }

            /**
             * Enable/disable TCP_NODELAY (disable/enable Nagle's algorithm).
             * @param on
             * @throws SocketException
             */
            @Override
            public void setTcpNoDelay(boolean on) throws SocketException {
                delegate.setTcpNoDelay(on);
            }

            /**
             * Gets traffic class or type-of-service in the IP header for packets sent from this Socket
             * @return
             * @throws SocketException
             */
            @Override
            public int getTrafficClass() throws SocketException {
                return delegate.getTrafficClass();
            }

            @Override
            public void setTrafficClass(int value) throws SocketException {
                delegate.setTrafficClass(value);
            }

            /**
             * Returns the binding state of the socket.
             * @return
             */
            @Override
            public boolean isBound() {
                return delegate.isBound();
            }

            /**
             * Returns the closed state of the socket.
             * @return
             */
            @Override
            public boolean isClosed() {
                return delegate.isClosed();
            }

            /**
             * Returns the connection state of the socket.
             * @return
             */
            @Override
            public boolean isConnected() {
                return delegate.isConnected();
            }

            /**
             * Returns whether the read-half of the socket connection is closed.
             * @return
             */
            @Override
            public boolean isInputShutdown() {
                return delegate.isInputShutdown();
            }

            /**
             * Returns whether the write-half of the socket connection is closed.
             * @return
             */
            @Override
            public boolean isOutputShutdown() {
                return delegate.isOutputShutdown();
            }

            @Override
            public void sendUrgentData(int value) throws IOException {
                delegate.sendUrgentData(value);
            }

            /**
             * Sets performance preferences for this socket.
             * @param connectionTime
             * @param latency
             * @param bandwidth
             */
            @Override
            public void setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
                delegate.setPerformancePreferences(connectionTime, latency, bandwidth);
            }

            /**
             * Enable/disable SO_LINGER with the specified linger time in seconds.
             * @param on
             * @param timeout
             * @throws SocketException
             */
            @Override
            public void setSoLinger(boolean on, int timeout) throws SocketException {
                delegate.setSoLinger(on, timeout);
            }

            /**
             * Places the input stream for this socket at "end of stream".
             * @throws IOException
             */
            @Override
            public void shutdownInput() throws IOException {
                delegate.shutdownInput();
            }

            /**
             * Disables the output stream for this socket.
             * @throws IOException
             */
            @Override
            public void shutdownOutput() throws IOException {
                delegate.shutdownOutput();
            }

            /**
             * Converts this socket to a String.
             * @return
             */
            @Override
            public String toString() {
                return delegate.toString();
            }

            @Override
            public boolean equals(Object o) {
                return delegate.equals(o);
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }
        }
    }


}
