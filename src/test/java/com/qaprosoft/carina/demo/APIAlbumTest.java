package com.qaprosoft.carina.demo;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.IAbstractTest;
import com.qaprosoft.carina.core.foundation.api.APIMethodPoller;
import com.qaprosoft.carina.demo.api.GetAlbumMethod;
import com.qaprosoft.carina.demo.api.PostAlbumMethod;
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
        PostAlbumMethod api = new PostAlbumMethod();
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

    @Test
    public void testCreateAlbumMissingField() {
        LOGGER.info("test create Album with missing field");
        PostAlbumMethod api = new PostAlbumMethod();
        api.setProperties("api/albums/album.properties");
        api.getProperties().remove("title");
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test
    public void testGetAlbum() {
        LOGGER.info("test get Album");
        GetAlbumMethod getAlbumMethod = new GetAlbumMethod();
        getAlbumMethod.callAPIExpectSuccess();
        getAlbumMethod.validateResponse(JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        getAlbumMethod.validateResponseAgainstSchema("api/albums/_get/rs.schema");
    }
}
