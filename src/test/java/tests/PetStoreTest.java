package tests;

import animals.AnimalType;
import animals.petstore.pet.attributes.Breed;
import animals.petstore.pet.attributes.Gender;
import animals.petstore.pet.attributes.Skin;
import animals.petstore.pet.types.Cat;
import animals.petstore.pet.types.Dog;
import animals.petstore.pet.types.Bird;   // <-- ADDED IMPORT
import animals.petstore.store.DuplicatePetStoreRecordException;
import animals.petstore.store.PetNotFoundSaleException;
import animals.petstore.store.PetStore;
import number.Numbers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class PetStoreTest
{
    private static PetStore petStore;

    @BeforeEach
    public void loadThePetStoreInventory()
    {
        petStore = new PetStore();
        petStore.init();
    }

    @Test
    @DisplayName("Inventory Count Test")
    public void validateInventory()
    {
        assertEquals(5, petStore.getPetsForSale().size(),"Inventory counts are off!");
    }

    @Test
    @DisplayName("Print Inventory Test")
    public void printInventoryTest()
    {
        petStore.printInventory();
    }

    @Test
    @DisplayName("Sale of Poodle Remove Item Test")
    public void poodleSoldTest() throws DuplicatePetStoreRecordException, PetNotFoundSaleException {
        int inventorySize = petStore.getPetsForSale().size() - 1;
        Dog poodle = new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.POODLE,
                new BigDecimal("650.00"), 1);

        petStore.soldPetItem(poodle);
        assertEquals(inventorySize, petStore.getPetsForSale().size(), "Expected inventory does not match actual");
    }

    @Test
    @DisplayName("Poodle Duplicate Record Exception Test")
    public void poodleDupRecordExceptionTest() {
        petStore.addPetInventoryItem(new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.POODLE,
                new BigDecimal("650.00"), 1));
        Dog poodle = new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.POODLE,
                new BigDecimal("650.00"), 1);

        String expectedMessage = "Duplicate Dog record store id [1]";
        Exception exception = assertThrows(DuplicatePetStoreRecordException.class, () ->{
            petStore.soldPetItem(poodle);});
        assertEquals(expectedMessage, exception.getMessage(), "DuplicateRecordExceptionTest was NOT encountered!");
    }

    @Test
    @DisplayName("Sale of Sphynx Remove Item Test")
    public void sphynxSoldTest() throws DuplicatePetStoreRecordException, PetNotFoundSaleException {
        int inventorySize = petStore.getPetsForSale().size() - 1;

        Cat sphynx = new Cat(AnimalType.DOMESTIC, Skin.UNKNOWN, Gender.FEMALE, Breed.SPHYNX,
                new BigDecimal("100.00"),2);
        Cat removedItem = (Cat) petStore.soldPetItem(sphynx);

        assertEquals(inventorySize, petStore.getPetsForSale().size(), "Expected inventory does not match actual");
        assertEquals(sphynx.getPetStoreId(), removedItem.getPetStoreId(), "The cat items are identical");
    }

    @TestFactory
    @DisplayName("Sale of Sphynx Remove Item Test2")
    public Stream<DynamicNode> sphynxSoldTest2() throws DuplicatePetStoreRecordException, PetNotFoundSaleException {
        int inventorySize = petStore.getPetsForSale().size() - 1;

        Cat sphynx = new Cat(AnimalType.DOMESTIC, Skin.UNKNOWN, Gender.FEMALE, Breed.SPHYNX,
                new BigDecimal("100.00"),2);
        Cat removedItem = (Cat) petStore.soldPetItem(sphynx);

        List<DynamicNode> nodes = new ArrayList<>();
        List<DynamicTest> dynamicTests = Arrays.asList(
                dynamicTest("Inventory Check Size Test ", () -> assertEquals(inventorySize,
                        petStore.getPetsForSale().size())),
                dynamicTest("The cat objects match ", () -> assertEquals(sphynx.toString(),
                        removedItem.toString()))
        );
        nodes.add(dynamicContainer("Cat Item 2 Test", dynamicTests));
        return nodes.stream();
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 4, 6, -10, 128, Integer.MIN_VALUE})
    void isNumberEven(int number)
    {
        assertTrue(Numbers.isEven(number));
    }

    @Test
    @DisplayName("Add new Bird increases inventory and is present")
    public void addNewBird_increasesInventory_andIsPresent() {
        int start = petStore.getPetsForSale().size();

        Bird robin = new Bird(
                AnimalType.DOMESTIC,
                Skin.UNKNOWN,
                Gender.FEMALE,
                Breed.ROBIN,
                new BigDecimal("79.00"),
                99
        );

        petStore.addPetInventoryItem(robin);

        assertEquals(start + 1, petStore.getPetsForSale().size(), "Inventory should increase by 1");
        assertTrue(petStore.getPetsForSale().contains(robin), "New bird should be present");
    }

    @Test
    @DisplayName("Sell Bird removes only matching store id (leaves the other)")
    public void sellRemovesOnlyMatchingId_bird()
            throws DuplicatePetStoreRecordException,
            PetNotFoundSaleException {

        Bird b1 = new Bird(
                AnimalType.DOMESTIC, Skin.UNKNOWN, Gender.MALE,
                Breed.CARDINAL, new BigDecimal("50.00"), 300);

        Bird b2 = new Bird(
                AnimalType.DOMESTIC, Skin.UNKNOWN, Gender.MALE,
                Breed.CARDINAL, new BigDecimal("50.00"), 301);

        petStore.addPetInventoryItem(b1);
        petStore.addPetInventoryItem(b2);

        int before = petStore.getPetsForSale().size();

        Bird removed = (Bird) petStore.soldPetItem(
                new Bird(
                        AnimalType.DOMESTIC, Skin.UNKNOWN, Gender.MALE,
                        Breed.CARDINAL, new BigDecimal("50.00"), 300));

        assertEquals(before - 1, petStore.getPetsForSale().size(), "Only one bird should be removed");
        assertEquals(300, removed.getPetStoreId());
        assertTrue(petStore.getPetsForSale().contains(b2), "Bird with id 301 should still remain");
    }
}
