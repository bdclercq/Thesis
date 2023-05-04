# Prebuild stage
FROM docker.normalizedsystems.org/nsx-tomee-builder:1.0.0 AS builder

# Copy pom file to download additional artifacts
COPY lib/pom-extra-libraries.xml pom-extra-libraries.xml

# Copy all files from the workspace to the docker image
COPY app-conf/ base/app-conf/
COPY conf/ base/conf/
COPY deploy/ base/deploy/
COPY sql/ base/sql/

RUN ./build.sh


# Application image stage
FROM docker.normalizedsystems.org/nsx-tomee-base:8.0.13-2.12.0

ENV APPLICATION_NAME OnlineCabBooking

# Copy application-specific content to image
COPY --from=builder /workspace/base/ ${CATALINA_BASE}/

# Copy prepare script which runs when the container starts
COPY prepare.sh /tmp
RUN dos2unix /tmp/prepare.sh

CMD [ "/tmp/prepare.sh" ]
