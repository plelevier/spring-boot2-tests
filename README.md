# Spring Boot 2 WebFlux + HTTP/2 in Undertow

## GET example
- Works as expected
    - `nghttp -v https://localhost:8443`
    
## POST example
- Fails with a `StackOverflowError` on the server side and an error code 500
    - `nghttp -v --data=test.json https://localhost:8443`

## Server error Stacktrace :
```
23-03-2018 11:47:53.873 [XNIO-1 I/O-3] ERROR io.undertow.request.executeRootHandler - UT005071: Undertow request failed HttpServerExchange{ POST / request {accept=[*/*], accept-encoding=[gzip, deflate], user-agent=[nghttp2/1.31.0], content-length=[23], Host=[localhost:8443]} response HttpServerExchange{ POST / request {accept=[*/*], accept-encoding=[gzip, deflate], user-agent=[nghttp2/1.31.0], content-length=[23], Host=[localhost:8443]} response {}}}
java.lang.StackOverflowError: null
	at io.undertow.server.protocol.framed.AbstractFramedStreamSourceChannel.read(AbstractFramedStreamSourceChannel.java:496)
	at io.undertow.protocols.http2.Http2StreamSourceChannel.read(Http2StreamSourceChannel.java:147)
	at org.xnio.conduits.StreamSourceChannelWrappingConduit.read(StreamSourceChannelWrappingConduit.java:43)
	at org.xnio.conduits.ConduitStreamSourceChannel.read(ConduitStreamSourceChannel.java:127)
	at io.undertow.channels.DetachableStreamSourceChannel.read(DetachableStreamSourceChannel.java:209)
	at io.undertow.server.HttpServerExchange$ReadDispatchChannel.read(HttpServerExchange.java:2332)
	at org.springframework.http.server.reactive.UndertowServerHttpRequest$RequestBodyPublisher.read(UndertowServerHttpRequest.java:172)
	at org.springframework.http.server.reactive.UndertowServerHttpRequest$RequestBodyPublisher.read(UndertowServerHttpRequest.java:128)
	at org.springframework.http.server.reactive.AbstractListenerReadPublisher.readAndPublish(AbstractListenerReadPublisher.java:145)
	at org.springframework.http.server.reactive.AbstractListenerReadPublisher.access$1000(AbstractListenerReadPublisher.java:47)
	at org.springframework.http.server.reactive.AbstractListenerReadPublisher$State$4.onDataAvailable(AbstractListenerReadPublisher.java:317)
	at org.springframework.http.server.reactive.AbstractListenerReadPublisher.onDataAvailable(AbstractListenerReadPublisher.java:85)
	at org.springframework.http.server.reactive.UndertowServerHttpRequest$RequestBodyPublisher.checkOnDataAvailable(UndertowServerHttpRequest.java:156)
	at org.springframework.http.server.reactive.AbstractListenerReadPublisher.changeToDemandState(AbstractListenerReadPublisher.java:177)
	[... SAME STACK TRACE OVER HUNDRED TIMES ...]
	at org.springframework.http.server.reactive.AbstractListenerReadPublisher.access$900(AbstractListenerReadPublisher.java:47)
	at org.springframework.http.server.reactive.AbstractListenerReadPublisher$State$4.onDataAvailable(AbstractListenerReadPublisher.java:319)
	at org.springframework.http.server.reactive.AbstractListenerReadPublisher.onDataAvailable(AbstractListenerReadPublisher.java:85)
	at org.springframework.http.server.reactive.UndertowServerHttpRequest$RequestBodyPublisher.checkOnDataAvailable(UndertowServerHttpRequest.java:156)
	at org.springframework.http.server.reactive.AbstractListenerReadPublisher.changeToDemandState(AbstractListenerReadPublisher.java:177)
	at org.springframework.http.server.reactive.AbstractListenerReadPublisher.access$900(AbstractListenerReadPublisher.java:47)
	at org.springframework.http.server.reactive.AbstractListenerReadPublisher$State$4.onDataAvailable(AbstractListenerReadPublisher.java:319)
	at org.springframework.http.server.reactive.AbstractListenerReadPublisher.onDataAvailable(AbstractListenerReadPublisher.java:85)
	at org.springframework.http.server.reactive.UndertowServerHttpRequest$RequestBodyPublisher.checkOnDataAvailable(UndertowServerHttpRequest.java:156)
	at org.springframework.http.server.reactive.AbstractListenerReadPublisher.changeToDemandState(AbstractListenerReadPublisher.java:177)
```

## Client trace
```
nghttp -v --data=test.json https://localhost:8443
[  0.012] Connected
[WARNING] Certificate verification failed: self signed certificate
The negotiated protocol: h2
[  0.133] send SETTINGS frame <length=12, flags=0x00, stream_id=0>
          (niv=2)
          [SETTINGS_MAX_CONCURRENT_STREAMS(0x03):100]
          [SETTINGS_INITIAL_WINDOW_SIZE(0x04):65535]
[  0.133] send PRIORITY frame <length=5, flags=0x00, stream_id=3>
          (dep_stream_id=0, weight=201, exclusive=0)
[  0.133] send PRIORITY frame <length=5, flags=0x00, stream_id=5>
          (dep_stream_id=0, weight=101, exclusive=0)
[  0.133] send PRIORITY frame <length=5, flags=0x00, stream_id=7>
          (dep_stream_id=0, weight=1, exclusive=0)
[  0.133] send PRIORITY frame <length=5, flags=0x00, stream_id=9>
          (dep_stream_id=7, weight=1, exclusive=0)
[  0.133] send PRIORITY frame <length=5, flags=0x00, stream_id=11>
          (dep_stream_id=3, weight=1, exclusive=0)
[  0.133] send HEADERS frame <length=43, flags=0x24, stream_id=13>
          ; END_HEADERS | PRIORITY
          (padlen=0, dep_stream_id=11, weight=16, exclusive=0)
          ; Open new stream
          :method: POST
          :path: /
          :scheme: https
          :authority: localhost:8443
          accept: */*
          accept-encoding: gzip, deflate
          user-agent: nghttp2/1.31.0
          content-length: 23
[  0.133] send DATA frame <length=23, flags=0x01, stream_id=13>
          ; END_STREAM
[  0.139] recv SETTINGS frame <length=24, flags=0x00, stream_id=0>
          (niv=4)
          [SETTINGS_HEADER_TABLE_SIZE(0x01):4096]
          [SETTINGS_MAX_FRAME_SIZE(0x05):16384]
          [SETTINGS_INITIAL_WINDOW_SIZE(0x04):65535]
          [SETTINGS_MAX_CONCURRENT_STREAMS(0x03):1000]
[  0.139] send SETTINGS frame <length=0, flags=0x01, stream_id=0>
          ; ACK
          (niv=0)
[  0.139] recv SETTINGS frame <length=0, flags=0x01, stream_id=0>
          ; ACK
          (niv=0)
[  0.256] recv (stream_id=13) :status: 500
[  0.256] recv (stream_id=13, sensitive) content-length: 0
[  0.256] recv (stream_id=13, sensitive) date: Fri, 23 Mar 2018 10:58:58 GMT
[  0.256] recv HEADERS frame <length=30, flags=0x05, stream_id=13>
          ; END_STREAM | END_HEADERS
          (padlen=0)
          ; First response header
[  0.256] send GOAWAY frame <length=8, flags=0x00, stream_id=0>
          (last_stream_id=0, error_code=NO_ERROR(0x00), opaque_data(0)=[])
```