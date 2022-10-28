package com.qaprosoft.carina.demo;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.IAbstractTest;
import com.qaprosoft.carina.core.foundation.api.APIMethodPoller;
import com.qaprosoft.carina.demo.api.getAlbumMethod;
import com.qaprosoft.carina.demo.api.postAlbumMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class APIAlbumTest implements IAbstractTest {
    private static final Logger LOGGER = LogManager.getLogger(APIAlbumTest.class);

    @Test
    public void testCreateAlbum() {
        LOGGER.info("test post Album");
        postAlbumMethod api = new postAlbumMethod();
        api.setProperties("api/albums/album.properties");
        AtomicInteger counter = new AtomicInteger(0);
        api.callAPIWithRetry()
                .withLogStrategy(APIMethodPoller.LogStrategy.ALL)
                .peek(rs -> counter.getAndIncrement())
                .until(rs -> counter.get() == 4)
                .pollEvery(1, ChronoUnit.SECONDS)
                .stopAfter(10, ChronoUnit.SECONDS)
                .execute();
        api.validateResponse();
    }

    @Test(enabled = false)
    public void testCreateAlbumMissingField() {
        LOGGER.info("test create Album with missing field");
        postAlbumMethod api = new postAlbumMethod();
        api.setProperties("api/albums/album.properties");
        api.getProperties().remove("title");
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test
    public void testGetAlbum() {
        LOGGER.info("test get Album");
        getAlbumMethod getAlbumMethod = new getAlbumMethod();
        getAlbumMethod.callAPIExpectSuccess();
        getAlbumMethod.validateResponse(JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        getAlbumMethod.validateResponseAgainstSchema("api/albums/_get/rs.schema");
    }
}
