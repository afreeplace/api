package seyfa.afreeplace.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import seyfa.afreeplace.entities.business.Photo;
import seyfa.afreeplace.entities.business.Trade;
import seyfa.afreeplace.exceptions.ManagerException;
import seyfa.afreeplace.repositories.PhotoRepository;
import seyfa.afreeplace.repositories.TradeRepository;
import seyfa.afreeplace.utils.constants.ExceptionConstants;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class PhotoManager implements IManager<Photo, Integer> {

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    TradeRepository tradeRepository;

    @Override
    public Photo find(Integer id) throws ManagerException {
        Photo photo = photoRepository.findById(id).orElseThrow(() -> new ManagerException(ExceptionConstants.photoNotFound()));
        return photo;
    }

    @Override
    public Integer create(Photo photo) throws ManagerException {
        if(photo.getTrade() == null) {
            throw new ManagerException(ExceptionConstants.tradeNotFound());
        }
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

        // TODO: cloud storage delete photos

        photoRepository.delete(photo);
    }
}
