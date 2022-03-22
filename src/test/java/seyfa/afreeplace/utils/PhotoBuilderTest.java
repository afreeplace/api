package seyfa.afreeplace.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seyfa.afreeplace.entities.business.Photo;
import seyfa.afreeplace.entities.business.Trade;
import seyfa.afreeplace.repositories.PhotoRepository;
import seyfa.afreeplace.repositories.TradeRepository;

import java.time.LocalDateTime;

public class PhotoBuilderTest {

    final static Logger logger = LoggerFactory.getLogger(TradeBuilderTest.class);

    public static Photo create(PhotoRepository photoRepository) {
        Photo photo = new Photo();

        photo.setUrl("www.google.fr");
        photo.setCreationDate(LocalDateTime.now());

        if(photoRepository != null) {
            photoRepository.save(photo);
            logger.info("Photo  {} registered for tests", photo.getId());
        }
        return photo;
    }

    public static void delete(int photoId, PhotoRepository photoRepository) {
        Photo photo = photoRepository.findById(photoId).orElse(null);
        if(photo != null) {
            photoRepository.delete(photoRepository.findById(photoId).get());
            logger.info("Deleted photo " + photoId);
        } else {
            logger.info("Deleted photo " + photoId);
        }
    }

}
