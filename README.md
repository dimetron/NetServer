##### Example application for Akka Steams and Akka Http

[![Join the chat at https://gitter.im/paypal/squbs](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/dimetron/NetServer)
[![Build Status](https://travis-ci.org/dimetron/NetServer.svg?branch=master)](https://travis-ci.org/dimetron/NetServer)
[![codecov](https://codecov.io/gh/dimetron/NetServer/branch/master/graph/badge.svg)](https://codecov.io/gh/dimetron/NetServer)
[![Dependency Status](https://www.versioneye.com/user/projects/5882b7a2452b830054c173cf/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/5882b7a2452b830054c173cf)
[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/dimetron/NetServer/blob/master/LICENSE)


**Build & run local docker image | load testing**

```bash
sbt docker:publishLocal

docker-compose up -d

sbt gatling-it:test
```

**Starting application**

```bash
sbt \~re-start
```

**Run tests with coverage**

```bash

docker run -m512m --name database  --rm -d -p 9042:9042 -v `pwd`/volumes/scylladb/scylla.yaml:/etc/scylla/scylla.yaml scylladb/scylla

sbt clean coverage test coverageReport
```

**Check all dependencies up to date**

```bash
sbt dependencyUpdatesReport
```

**Test for TCP**

```bash
echo -e "Message1\nMessage2" | nc 127.0.0.1 8888
```

**Test for HTTP**

```bash
curl -H "Accept: application/json" 127.0.0.1:8080
```

**Gatling Load test results (server with 2 cores and 2GB RAM)**

```
    ================================================================================
    ---- Global Information --------------------------------------------------------
    > request count                                      13458 (OK=13398  KO=60    )
    > min response time                                      0 (OK=25     KO=0     )
    > max response time                                   8371 (OK=8371   KO=0     )
    > mean response time                                   289 (OK=290    KO=0     )
    > std deviation                                        868 (OK=870    KO=0     )
    > response time 50th percentile                         58 (OK=58     KO=0     )
    > response time 75th percentile                         79 (OK=79     KO=0     )
    > response time 95th percentile                       1465 (OK=1477   KO=0     )
    > response time 99th percentile                       4483 (OK=4488   KO=0     )
    > mean requests/sec                                168.225 (OK=167.475 KO=0.75  )
    ---- Response Time Distribution ------------------------------------------------
    > t < 800 ms                                         12378 ( 92%)
    > 800 ms < t < 1200 ms                                 260 (  2%)
    > t > 1200 ms                                          760 (  6%)
    > failed                                                60 (  0%)
```