package brere.nat.omdbapi.api;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import brere.nat.omdbapi.model.SeriesResult;

class OMDBAPITest {
	
	private static final OMDBAPI API = new OMDBAPI("");
	private static final Logger LOG = LoggerFactory.getLogger(OMDBAPITest.class);

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void testGetSeriesByIMDBID() {
		try {
			SeriesResult vikings = API.getSeriesByIMDBID("tt2306299");
			LOG.info("Title :" + vikings.getTitle());
			LOG.info("Number Seasons :" + vikings.getTotalSeasons());
		} catch (URISyntaxException | IOException e) {
			LOG.error(e.getClass().getName(), e);
			fail("Exception when trying to get series");
		}
	}

}
