package animals.petstore.pet.types;

import animals.AnimalType;
import animals.petstore.pet.Pet;
import animals.petstore.pet.attributes.Breed;
import animals.petstore.pet.attributes.Gender;
import animals.petstore.pet.attributes.PetType;
import animals.petstore.pet.attributes.Skin;

import java.math.BigDecimal;

/**
 * Bird pet type
 */
public class Bird extends Pet implements PetImpl {

    private int numberOfLegs;
    private Breed breed;

    /* ===== Constructors ===== */

    public Bird(AnimalType animalType, Skin skinType, Gender gender, Breed breed) {
        this(animalType, skinType, gender, breed, BigDecimal.ZERO);
    }

    public Bird(AnimalType animalType, Skin skinType, Gender gender, Breed breed, BigDecimal cost) {
        this(animalType, skinType, gender, breed, cost, 0);
    }

    public Bird(AnimalType animalType,
                Skin skinType,
                Gender gender,
                Breed breed,
                BigDecimal cost,
                int petStoreId) {

        super(PetType.BIRD, cost, gender, petStoreId);
        super.animalType = animalType;
        super.skinType = skinType;

        this.breed = breed;
        this.numberOfLegs = 2;
        this.hasLegs = true;
        this.isMammal = false;
    }

    /* ===== Getters used by tests ===== */

    public AnimalType getAnimalType() {
        return this.animalType;
    }

    public Skin getSkinType() {
        return this.skinType;
    }

    @Override
    public Breed getBreed() {
        return this.breed;
    }

    public int getNumberOfLegs() {
        return numberOfLegs;
    }

    /* ===== Behavior ===== */

    public String birdHypoallergenic() {
        return super.petHypoallergenic(this.skinType).replace("pet", "bird");
    }

    public String speak() {
        switch (this.animalType) {
            case DOMESTIC:
                return "The bird goes tweet! tweet!";
            case WILD:
                return "The bird goes screech! screech!";
            default:
                return "The bird goes " + super.getPetType().speak + "! " + super.getPetType().speak + "!";
        }
    }

    private String numberOfLegsAsString() {
        return "Birds have " + numberOfLegs + " legs!";
    }

    @Override
    public String toString() {
        return super.toString()
                + "The bird is " + this.animalType + "!\n"
                + "The bird breed is " + this.breed + "!\n"
                + this.birdHypoallergenic() + "!\n"
                + this.speak() + "\n"
                + numberOfLegsAsString();
    }
}
