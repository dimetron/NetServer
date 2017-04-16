##### Example application for Akka Steams and Akka Http

[![Join the chat at https://gitter.im/paypal/squbs](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/dimetron/NetServer)
[![Build Status](https://travis-ci.org/dimetron/NetServer.svg?branch=master)](https://travis-ci.org/dimetron/NetServer)
[![codecov](https://codecov.io/gh/dimetron/NetServer/branch/master/graph/badge.svg)](https://codecov.io/gh/dimetron/NetServer)
[![Dependency Status](https://www.versioneye.com/user/projects/5882b7a2452b830054c173cf/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/5882b7a2452b830054c173cf)
[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/dimetron/NetServer/blob/master/LICENSE)


**Build & run local docker image**

```bash
sbt docker:publishLocal

docker run --rm --name akka -p 8080:8080 -p 8888:8888 dimetron/netserver

```

**Starting application**

```bash
sbt \~re-start
```

**Run tests with coverage**

```bash
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