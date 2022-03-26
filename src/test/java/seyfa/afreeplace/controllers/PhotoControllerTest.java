package seyfa.afreeplace.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import seyfa.afreeplace.entities.business.Address;
import seyfa.afreeplace.entities.business.Photo;
import seyfa.afreeplace.entities.business.Trade;
import seyfa.afreeplace.exceptions.ManagerException;
import seyfa.afreeplace.managers.PhotoManager;
import seyfa.afreeplace.repositories.PhotoRepository;
import seyfa.afreeplace.repositories.TradeRepository;
import seyfa.afreeplace.utils.PhotoBuilderTest;
import seyfa.afreeplace.utils.TradeBuilderTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class PhotoControllerTest {


    @Autowired
    TradeController photoController;

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
        assertNotNull(photoController);
    }

    @Test
    public void createWithNoTradeFails() {
        Photo photo = PhotoBuilderTest.create(null);

        assertThrows(ManagerException.class, () -> {
            BindingResult result = new BeanPropertyBindingResult(photo, "request");
            photoController.createPhoto(photo, result);
        });
    }

    @Test
    public void createWorks() {
        Trade trade = TradeBuilderTest.create(tradeRepository, "Seyfa Tech", Trade.Status.VALIDATED);

        Photo photo = PhotoBuilderTest.create(null);
        photo.setTrade(trade);

        BindingResult result = new BeanPropertyBindingResult(photo, "request");

        ResponseEntity<Map<String, Object>> response = photoController.createPhoto(photo, result);
        Map<String, Object> body = response.getBody();
        photoId = ((Photo) body.get("photo")).getId();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(photoRepository.findById(photoId).orElse(null));

        TradeBuilderTest.delete(trade.getId(), tradeRepository);
    }

    @Test
    public void deleteWorks() {
        photoController.deletePhoto(photoId);
        assertNull(photoRepository.findById(photoId).orElse(null));
    }

    @Test
    public void deleteWIthNoIdFails() {
        photoController.deletePhoto(photoId);
        assertNull(photoRepository.findById(photoId).orElse(null));
    }

}
