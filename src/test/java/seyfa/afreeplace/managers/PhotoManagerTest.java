package seyfa.afreeplace.managers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import seyfa.afreeplace.entities.business.Address;
import seyfa.afreeplace.entities.business.Photo;
import seyfa.afreeplace.entities.business.Trade;
import seyfa.afreeplace.exceptions.ManagerException;
import seyfa.afreeplace.repositories.PhotoRepository;
import seyfa.afreeplace.repositories.TradeRepository;
import seyfa.afreeplace.utils.PhotoBuilderTest;
import seyfa.afreeplace.utils.TradeBuilderTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PhotoManagerTest {

    @Autowired
    PhotoManager photoManager;

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    PhotoRepository photoRepository;

    int photoId, secondPhotoId;

    @BeforeEach
    public void before() {
        photoId = PhotoBuilderTest.create(photoRepository).getId();
    }

    @AfterEach
    public void after() {
        PhotoBuilderTest.delete(photoId, photoRepository);
        PhotoBuilderTest.delete(secondPhotoId, photoRepository);
    }

    @Test
    public void ok() {
        assertNotNull(photoManager);
    }

    @Test
    public void createWithNoTradeFails() {
        Photo photo = PhotoBuilderTest.create(null);

        assertThrows(ManagerException.class, () -> {
            photoId = photoManager.create(photo);
        });
    }

    @Test
    public void createWorks() {
        Trade trade = TradeBuilderTest.create(tradeRepository, "Seyfa Tech", Trade.Status.VALIDATED);

        Photo photo = PhotoBuilderTest.create(null);
        photo.setTrade(trade);

        secondPhotoId = photoManager.create(photo);
        assertNotNull(photoRepository.findById(secondPhotoId).orElse(null));
        assertNotNull(photoRepository.findById(secondPhotoId).orElse(null).getTrade());

        TradeBuilderTest.delete(trade.getId(), tradeRepository);
    }

    @Test
    public void deleteWorks() {
        photoManager.delete(photoId);
        assertNull(photoRepository.findById(photoId).orElse(null));
    }

    @Test
    public void deleteWIthNoIdFails() {
        photoManager.delete(photoId);
        assertNull(photoRepository.findById(photoId).orElse(null));
    }

}
