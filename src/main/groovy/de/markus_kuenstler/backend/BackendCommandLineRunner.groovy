package de.markus_kuenstler.backend

import de.markus_kuenstler.backend.datasource.AudioReleaseDataSource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

import javax.annotation.Resource

@Component
class BackendCommandLineRunner implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(BackendCommandLineRunner.class);

    @Resource
    final AudioReleaseDataSource audioReleaseDataSource

    @Override
    void run(String... args) throws Exception {

        if (args.size() == 0) {
            logger.error("No XML provided => extiting")
            return
        }


        String xmlFile = args[0];

        logger.info("Starting with data from ${xmlFile}")

        audioReleaseDataSource.audioReleases.each { logger.info(it.toString()) }
    }
}
