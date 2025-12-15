package tests;

import animals.AnimalType;
import animals.petstore.pet.Pet;
import animals.petstore.pet.attributes.Breed;
import animals.petstore.pet.attributes.Gender;
import animals.petstore.pet.attributes.PetType;
import animals.petstore.pet.attributes.Skin;
import animals.petstore.pet.types.Bird;
import animals.petstore.pet.types.Cat;
import animals.petstore.pet.types.Dog;
import animals.petstore.store.DuplicatePetStoreRecordException;
import animals.petstore.store.PetNotFoundSaleException;
import animals.petstore.store.PetStore;
import number.Numbers;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class PetStoreTest {

    private PetStore petStore;

    @BeforeEach
    void setup() {
        petStore = new PetStore();
        petStore.init();
    }

    @Test
    @DisplayName("Inventory Count Test")
    void validateInventory() {
        assertEquals(5, petStore.getPetsForSale().size(), "Inventory counts are off!");
    }

    @Test
    @DisplayName("Print Inventory Test")
    void printInventoryTest() {
        petStore.printInventory();
    }

    @Test
    @DisplayName("Sale of Poodle removes item")
    void poodleSoldTest() throws DuplicatePetStoreRecordException, PetNotFoundSaleException {
        int expectedSize = petStore.getPetsForSale().size() - 1;

        Dog poodle = new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.POODLE,
                new BigDecimal("650.00"), 1);

        petStore.soldPetItem(poodle);

        assertEquals(expectedSize, petStore.getPetsForSale().size());
    }

    @Test
    @DisplayName("Poodle duplicate record throws exception")
    void poodleDupRecordExceptionTest() {
        petStore.addPetInventoryItem(new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.POODLE,
                new BigDecimal("650.00"), 1));

        Dog poodle = new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.POODLE,
                new BigDecimal("650.00"), 1);

        assertThrows(DuplicatePetStoreRecordException.class, () -> petStore.soldPetItem(poodle));
    }

    @Test
    @DisplayName("Sale of Sphynx removes correct item")
    void sphynxSoldTest() throws DuplicatePetStoreRecordException, PetNotFoundSaleException {
        int expectedSize = petStore.getPetsForSale().size() - 1;

        Cat sphynx = new Cat(AnimalType.DOMESTIC, Skin.UNKNOWN, Gender.FEMALE, Breed.SPHYNX,
                new BigDecimal("100.00"), 2);

        Pet removed = petStore.soldPetItem(sphynx);

        assertEquals(expectedSize, petStore.getPetsForSale().size());
        assertEquals(PetType.CAT, removed.getPetType());
        assertEquals(2, removed.getPetStoreId());
    }

    // =========================
    // PART 1 EXTRA TESTS
    // =========================

    @Test
    @DisplayName("Selling pet with store ID 0 throws PetNotFoundSaleException")
    void sellingPetWithZeroIdThrowsException() {
        Dog invalidDog = new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.MALTESE,
                new BigDecimal("300.00"), 0);

        assertThrows(PetNotFoundSaleException.class, () -> petStore.soldPetItem(invalidDog));
    }

    @Test
    void isEvenNumberTest() {
        assertTrue(Numbers.isEven(2));
        assertTrue(Numbers.isEven(4));
    }

    // =========================
    // PART 2 BIRD TESTS (store integration)
    // =========================

    @Test
    @DisplayName("Adding Bird increases inventory")
    void addBirdIncreasesInventory() {
        int before = petStore.getPetsForSale().size();

        Bird bird = new Bird(AnimalType.DOMESTIC, Skin.UNKNOWN, Gender.MALE,
                Breed.ROBIN, new BigDecimal("75.00"), 90);

        petStore.addPetInventoryItem(bird);

        assertEquals(before + 1, petStore.getPetsForSale().size());
        assertTrue(petStore.getPetsForSale().contains(bird));
    }

    @Test
    @DisplayName("Selling Bird removes correct Bird")
    void sellingBirdRemovesCorrectItem() throws DuplicatePetStoreRecordException, PetNotFoundSaleException {
        Bird b1 = new Bird(AnimalType.DOMESTIC, Skin.UNKNOWN, Gender.MALE, Breed.CARDINAL,
                new BigDecimal("50.00"), 300);

        Bird b2 = new Bird(AnimalType.DOMESTIC, Skin.UNKNOWN, Gender.MALE, Breed.CARDINAL,
                new BigDecimal("50.00"), 301);
        petStore.addPetInventoryItem(b1);
        petStore.addPetInventoryItem(b2);

        int before = petStore.getPetsForSale().size();

        Pet removed = petStore.soldPetItem(new Bird(AnimalType.DOMESTIC, Skin.UNKNOWN, Gender.MALE,
                Breed.CARDINAL, new BigDecimal("50.00"), 300));

        assertEquals(before - 1, petStore.getPetsForSale().size());
        assertEquals(PetType.BIRD, removed.getPetType());
        assertEquals(300, removed.getPetStoreId());
        assertTrue(petStore.getPetsForSale().contains(b2));
    }
}

