package seyfa.afreeplace.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import seyfa.afreeplace.entities.business.Trade;
import seyfa.afreeplace.utils.PhotoBuilderTest;
import seyfa.afreeplace.utils.TradeBuilderTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PhotoRepositoryTest {

    @Autowired
    PhotoRepository photoRepository;

    int photoId;

    @BeforeEach
    public void before() {
        photoId = PhotoBuilderTest.create(photoRepository).getId();
    }

    @AfterEach
    public void after() {
        PhotoBuilderTest.delete(photoId, photoRepository);
    }

    @Test
    public void ok() {
        assertNotNull(photoRepository);
    }

}
