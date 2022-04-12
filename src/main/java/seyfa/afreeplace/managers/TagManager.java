package seyfa.afreeplace.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seyfa.afreeplace.entities.business.Tag;
import seyfa.afreeplace.exceptions.ManagerException;
import seyfa.afreeplace.repositories.TagRepository;
import seyfa.afreeplace.utils.constants.ExceptionConstants;

import javax.persistence.criteria.CriteriaBuilder;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class TagManager {

    @Autowired
    TagRepository tagRepository;

    public Tag find(int tagId) {
        return tagRepository.findById(tagId).orElseThrow(() -> new ManagerException(ExceptionConstants.tagNotFound()));
    }

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

}
