package tests;

import animals.AnimalType;
import animals.petstore.pet.attributes.Breed;
import animals.petstore.pet.attributes.Gender;
import animals.petstore.pet.attributes.Skin;
import animals.petstore.pet.types.Bird;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class BirdTest {

    @Test
    void testBirdCreation_fullCtor_setsAllFields() {
        Bird bird = new Bird(
                AnimalType.DOMESTIC,
                Skin.UNKNOWN,
                Gender.MALE,
                Breed.CARDINAL,
                new BigDecimal("199.99"),
                50
        );

        assertEquals(AnimalType.DOMESTIC, bird.getAnimalType());
        assertEquals(Skin.UNKNOWN, bird.getSkin());
        assertEquals(Gender.MALE, bird.getGender());
        assertEquals(Breed.CARDINAL, bird.getBreed());
        assertEquals(new BigDecimal("199.99"), bird.getCost());
        assertEquals(50, bird.getPetStoreId());
        assertEquals(2, bird.getLegs(), "Birds should have 2 legs");
    }

    @Test
    void testSpeak_domestic() {
        Bird bird = new Bird(AnimalType.DOMESTIC, Skin.UNKNOWN, Gender.FEMALE,
                Breed.ROBIN, new BigDecimal("49.99"), 60);
        assertEquals("tweet tweet!", bird.speak());
    }

    @Test
    void testSpeak_wild() {
        Bird bird = new Bird(AnimalType.WILD, Skin.UNKNOWN, Gender.FEMALE,
                Breed.HAWK, new BigDecimal("149.99"), 61);
        assertEquals("screech!", bird.speak());
    }

    @Test
    void testHypoallergenic_isFalse() {
        Bird bird = new Bird(AnimalType.DOMESTIC, Skin.UNKNOWN, Gender.MALE,
                Breed.SPARROW, new BigDecimal("10.00"), 62);
        assertFalse(bird.isHypoAllergenic());
    }
}
