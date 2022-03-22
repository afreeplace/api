package seyfa.afreeplace.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import seyfa.afreeplace.entities.business.Photo;
import seyfa.afreeplace.entities.business.Trade;
import seyfa.afreeplace.exceptions.ManagerException;
import seyfa.afreeplace.repositories.PhotoRepository;
import seyfa.afreeplace.repositories.TradeRepository;
import seyfa.afreeplace.utils.constants.ExceptionConstants;

import java.time.LocalDateTime;

public class PhotoManager implements IManager<Photo, Integer> {

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    TradeRepository tradeRepository;


    @Override
    public Photo find(Integer integer) throws ManagerException {
        return null;
    }

    @Override
    public Integer create(Photo photo) throws ManagerException {
        Trade owner = tradeRepository.findById(photo.getTrade().getId()).orElseThrow(() -> new ManagerException(ExceptionConstants.tradeNotFound()));

        Photo photoToCreate = new Photo();
        photoToCreate.setUrl(photo.getUrl());
        photoToCreate.setCreationDate(LocalDateTime.now());
        photoToCreate.setTrade(owner);

        photoRepository.save(photoToCreate);
        return photoToCreate.getId();
    }

    @Override
    public void update(Photo object) throws ManagerException {

    }

    @Override
    public void delete(Integer id) throws ManagerException {
        Photo photo = photoRepository.findById(id).orElseThrow(() -> new ManagerException(ExceptionConstants.photoNotFound()));
        photoRepository.delete(photo);
    }
}
