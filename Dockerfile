# Copyright (c) 2019-2020 Bastien Guerry <bzg@bzg.fr>
# SPDX-License-Identifier: EPL-2.0
# License-Filename: LICENSES/EPL-2.0.txt

FROM java:8-alpine
ENV MAILGUN_API_KEY ${MAILGUN_API_KEY}
ENV MAILGUN_LOGIN ${MAILGUN_LOGIN}
ENV MAILGUN_PASSWORD ${MAILGUN_PASSWORD}
ENV SUBSCRIBE_PORT ${SUBSCRIBE_PORT}
ENV SUBSCRIBE_BASEURL ${SUBSCRIBE_BASEURL}
ADD target/subscribe-standalone.jar /subscribe/subscribe-standalone.jar
CMD ["java", "-jar", "/subscribe/subscribe-standalone.jar"]
