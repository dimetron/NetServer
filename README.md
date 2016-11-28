##### Example application for Akka Steams and Akka Http

[![Join the chat at https://gitter.im/paypal/squbs](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/dimetron/NetServer)
[![Build Status](https://travis-ci.org/dimetron/NetServer.svg?branch=master)](https://travis-ci.org/dimetron/NetServer)
[![codecov](https://codecov.io/gh/dimetron/NetServer/branch/master/graph/badge.svg)](https://codecov.io/gh/dimetron/NetServer)
[![License](http://img.shields.io/:license-Apache%202-red.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)


Starting application

```bash
sbt \~re-start
```

Run tests with coverage
```bash
sbt clean coverage test coverageReport
```

Test for TCP

```bash
echo -e "Message1\nMessage2" | nc 127.0.0.1 8888
```

Test for HTTP

```bash
curl -H "Accept: application/json" 127.0.0.1:8080
```




