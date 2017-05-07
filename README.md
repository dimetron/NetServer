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

docker run --name database  -d -p 9042:9042 scylladb/scylla:1.7-rc2 --developer-mode 1

sbt clean coverage test coverageReport
```

**Check all dependencies up to date**

```bash
sbt dependencyUpdatesReport

sbt dependencyBrowseGraph

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
wrk:

    wrk -c 100 -t 100 -d 1m  http://127.0.0.1:8080/svc
    Running 1m test @ http://127.0.0.1:8080/svc
      100 threads and 100 connections
      Thread Stats   Avg      Stdev     Max   +/- Stdev
        Latency   134.36ms   54.42ms 486.86ms   71.71%
        Req/Sec     8.02      3.69    40.00     64.74%
      44889 requests in 1.00m, 9.55MB read
    Requests/sec:    746.88
    Transfer/sec:    162.65KB


    wrk -c 300 -t 100 -d 1m  http://127.0.0.1:8080/svc
    Running 1m test @ http://127.0.0.1:8080/svc
      100 threads and 200 connections
      Thread Stats   Avg      Stdev     Max   +/- Stdev
        Latency   275.73ms  112.82ms   1.80s    76.99%
        Req/Sec     7.84      3.61    50.00     66.87%
      43735 requests in 1.00m, 9.30MB read
      Socket errors: connect 0, read 163, write 0, timeout 0
    Requests/sec:    727.61
    Transfer/sec:    158.45KB


Gatling:

    ================================================================================
    ---- Global Information --------------------------------------------------------
    > request count                                      26189 (OK=26189  KO=0     )
    > min response time                                      3 (OK=3      KO=-     )
    > max response time                                   1033 (OK=1033   KO=-     )
    > mean response time                                    34 (OK=34     KO=-     )
    > std deviation                                         79 (OK=79     KO=-     )
    > response time 50th percentile                          8 (OK=8      KO=-     )
    > response time 75th percentile                         18 (OK=18     KO=-     )
    > response time 95th percentile                        165 (OK=165    KO=-     )
    > response time 99th percentile                        420 (OK=420    KO=-     )
    > mean requests/sec                                368.859 (OK=368.859 KO=-     )
    ---- Response Time Distribution ------------------------------------------------
    > t < 800 ms                                         26168 (100%)
    > 800 ms < t < 1200 ms                                  21 (  0%)
    > t > 1200 ms                                            0 (  0%)
    > failed                                                 0 (  0%)
    ================================================================================

```