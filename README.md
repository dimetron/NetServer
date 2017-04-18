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
---- Global Information --------------------------------------------------------
> request count                                       1270 (OK=1270   KO=0     )
> min response time                                     13 (OK=13     KO=-     )
> max response time                                   8256 (OK=8256   KO=-     )
> mean response time                                   288 (OK=288    KO=-     )
> std deviation                                        943 (OK=943    KO=-     )
> response time 50th percentile                         35 (OK=35     KO=-     )
> response time 75th percentile                         67 (OK=67     KO=-     )
> response time 95th percentile                       1418 (OK=1418   KO=-     )
> response time 99th percentile                       5858 (OK=5858   KO=-     )
> mean requests/sec                                 17.887 (OK=17.887 KO=-     )
---- Response Time Distribution ------------------------------------------------
> t < 800 ms                                          1161 ( 91%)
> 800 ms < t < 1200 ms                                  37 (  3%)
> t > 1200 ms                                           72 (  6%)
> failed                                                 0 (  0%)
================================================================================



```