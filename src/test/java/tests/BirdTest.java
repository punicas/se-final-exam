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
        assertEquals(Gender.MALE, bird.getGender());
        assertEquals(Breed.CARDINAL, bird.getBreed());
        assertEquals(new BigDecimal("199.99"), bird.getCost());
        assertEquals(50, bird.getPetStoreId());
        assertEquals("Birds have 2 legs!", "Birds have " + bird.getNumberOfLegs() + " legs!");
    }

    @Test
    void testSpeak_domestic() {
        Bird bird = new Bird(
                AnimalType.DOMESTIC,
                Skin.UNKNOWN,
                Gender.FEMALE,
                Breed.ROBIN,
                new BigDecimal("49.99"),
                60
        );

        assertEquals("The bird goes tweet! tweet!", bird.speak());
    }
}
