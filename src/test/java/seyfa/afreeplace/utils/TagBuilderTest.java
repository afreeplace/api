package seyfa.afreeplace.utils;

import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seyfa.afreeplace.entities.business.Tag;
import seyfa.afreeplace.entities.business.User;
import seyfa.afreeplace.repositories.TagRepository;
import seyfa.afreeplace.repositories.UserRepository;

import java.time.LocalDateTime;

public class TagBuilderTest {

    static final Logger logger = LoggerFactory.getLogger(UserBuilderTest.class);

    public static Tag create(TagRepository tagRepository, String tagName) {
        Tag tag = new Tag();
        tag.setName(tagName);
        tag.setLogoUrl("www.google.fr");
        tag.setCreationDate(LocalDateTime.now());

        if(tagRepository != null) {
            tagRepository.save(tag);
            logger.info("Created tag " + tagName);
        }
        return tag;
    }

    public static void delete(int tagId, TagRepository tagRepository) {
        Tag tag = tagRepository.findById(tagId).orElse(null);
        if(tag != null) {
            tagRepository.delete(tagRepository.findById(tagId).get());
            logger.info("Deleted tag {}", tagId);
        } else {
            logger.info("Already deleted tag {}", tagId);
        }
    }

}
