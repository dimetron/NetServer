Example application for Akka Steams and Akka Http

[![Build Status](https://travis-ci.org/dimetron/NetServer.svg?branch=master)](https://travis-ci.org/dimetron/NetServer)


Starting application

```bash
sbt \~re-start
```

Test for TCP

```bash
echo -e "Message1\nMessage2" | nc 127.0.0.1 8888
```

Test for HTTP

```bash
curl -H "Accept: application/json" 127.0.0.1:8080
```




