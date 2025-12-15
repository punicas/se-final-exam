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

    // ===============================
    // CONSTRUCTOR TESTS
    // ===============================

    @Test
    void bird_min_constructor_defaults() {
        Bird bird = new Bird(
                AnimalType.DOMESTIC,
                Skin.UNKNOWN,
                Gender.MALE,
                Breed.ROBIN
        );

        assertEquals(AnimalType.DOMESTIC, bird.getAnimalType());
        assertEquals(Gender.MALE, bird.getGender());
        assertEquals(Breed.ROBIN, bird.getBreed());
        assertEquals(0, bird.getCost().compareTo(BigDecimal.ZERO));
        assertEquals(0, bird.getPetStoreId());
        assertEquals(2, bird.getNumberOfLegs());
    }

    @Test
    void bird_full_constructor_sets_all_fields() {
        Bird bird = new Bird(
                AnimalType.WILD,
                Skin.UNKNOWN,
                Gender.FEMALE,
                Breed.CARDINAL,
                new BigDecimal("125.50"),
                10
        );

        assertEquals(AnimalType.WILD, bird.getAnimalType());
        assertEquals(Gender.FEMALE, bird.getGender());
        assertEquals(Breed.CARDINAL, bird.getBreed());
        assertEquals(0, bird.getCost().compareTo(new BigDecimal("125.50")));
        assertEquals(10, bird.getPetStoreId());
        assertEquals(2, bird.getNumberOfLegs());
    }

    // ===============================
    // SPEAK BRANCH COVERAGE
    // ===============================

    @Test
    void speak_domestic_bird() {
        Bird bird = new Bird(
                AnimalType.DOMESTIC,
                Skin.UNKNOWN,
                Gender.FEMALE,
                Breed.ROBIN
        );

        String msg = bird.speak().toLowerCase();
        assertTrue(msg.contains("bird"));
        assertTrue(msg.contains("tweet"));
    }

    @Test
    void speak_wild_bird() {
        Bird bird = new Bird(
                AnimalType.WILD,
                Skin.UNKNOWN,
                Gender.MALE,
                Breed.HAWK
        );

        String msg = bird.speak().toLowerCase();
        assertTrue(msg.contains("bird"));
    }

    @Test
    void speak_unknown_animal_type() {
        Bird bird = new Bird(
                AnimalType.UNKNOWN,
                Skin.UNKNOWN,
                Gender.MALE,
                Breed.SPARROW
        );

        assertNotNull(bird.speak());
    }

    // ===============================
    // STRING + HELPER METHODS
    // ===============================

    @Test
    void toString_contains_expected_text() {
        Bird bird = new Bird(
                AnimalType.DOMESTIC,
                Skin.UNKNOWN,
                Gender.MALE,
                Breed.ROBIN,
                new BigDecimal("45.00"),
                7
        );

        String text = bird.toString();

        assertTrue(text.contains("BIRD"));
        assertTrue(text.contains("DOMESTIC"));
        assertTrue(text.contains("ROBIN"));
        assertTrue(text.contains("45.00"));
    }

    @Test
    void number_of_legs_message_is_correct() {
        Bird bird = new Bird(
                AnimalType.DOMESTIC,
                Skin.UNKNOWN,
                Gender.MALE,
                Breed.ROBIN
        );

        assertEquals(2, bird.getNumberOfLegs());
    }
}

