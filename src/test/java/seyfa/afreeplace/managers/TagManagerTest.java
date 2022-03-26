package seyfa.afreeplace.managers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import seyfa.afreeplace.entities.business.Tag;
import seyfa.afreeplace.repositories.TagRepository;
import seyfa.afreeplace.utils.TagBuilderTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TagManagerTest {

    @Autowired
    TagManager tagManager;

    @Autowired
    TagRepository tagRepository;

    int tagId1, tagId2, tagId3;

    @BeforeEach
    public void before() throws Exception {
        tagId1 = TagBuilderTest.create(tagRepository, "Tag1").getId();
        tagId2 = TagBuilderTest.create(tagRepository, "Tag2").getId();
        tagId3 = TagBuilderTest.create(tagRepository, "Tag3").getId();
    }

    @AfterEach
    public void after() {
        TagBuilderTest.delete(tagId1, tagRepository);
        TagBuilderTest.delete(tagId3, tagRepository);
        TagBuilderTest.delete(tagId2, tagRepository);

        assertNull(tagRepository.findById(tagId1).orElse(null));
        assertNull(tagRepository.findById(tagId2).orElse(null));
        assertNull(tagRepository.findById(tagId3).orElse(null));
    }

    @Test
    public void testOk() {
        assertNotNull(tagManager);
    }

    @Test
    public void findAll() {
        List<Tag> tagList = tagManager.findAll();
        assertEquals(3, tagList.size());
    }

    @Test
    public void findById() {
        assertNotNull(tagManager.find(tagId1));
    }

}
