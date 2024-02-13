FROM eclipse-temurin:21.0.2_13-jre

COPY ./target/rinha-back-end-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT java \
#  -Dsun.management.jmxremote.level=FINEST \
#  -Dsun.management.jmxremote.handlers=java.util.logging.ConsoleHandler \
#  -Djava.util.logging.ConsoleHandler.level=FINEST \
#  -Dcom.sun.management.jmxremote.local.only=false \
#  -Dcom.sun.management.jmxremote.ssl=false \
#  -Dcom.sun.management.jmxremote.authenticate=false \
#  -Dcom.sun.management.jmxremote.port=$JMX_PORT \
#  -Dcom.sun.management.jmxremote.rmi.port=$JMX_PORT \
#  -Dcom.sun.management.jmxremote.host=0.0.0.0 \
#  -Djava.rmi.server.hostname=0.0.0.0 \
#  -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 \
  -XX:MaxRAMPercentage=95.0 \
  -XX:InitialRAMPercentage=95.0 \
  -XX:MinRAMPercentage=95.0 \
  -XX:+UseSerialGC \
  -jar /app/app.jar