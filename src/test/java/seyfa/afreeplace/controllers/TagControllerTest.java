package seyfa.afreeplace.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import seyfa.afreeplace.repositories.TagRepository;
import seyfa.afreeplace.utils.TagBuilderTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TagControllerTest {

    @Autowired
    TagController tagController;

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
    }

    @Test
    public void testOk() {
        assertNotNull(tagRepository);
    }

    public void findAll() {
        assertEquals(3, tagRepository.findAll());
    }

}
