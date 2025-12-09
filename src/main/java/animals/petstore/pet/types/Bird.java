package animals.petstore.pet.types;

import animals.AnimalType;
import animals.petstore.pet.Pet;
import animals.petstore.pet.attributes.Breed;
import animals.petstore.pet.attributes.Gender;
import animals.petstore.pet.attributes.PetType;
import animals.petstore.pet.attributes.Skin;

import java.math.BigDecimal;

public class Bird extends Pet implements PetImpl {

    /* Properties */
    private int numberOfLegs;
    private Breed breed;

    /**
     * Constructor
     */
    public Bird(AnimalType animalType,
                Skin skinType,
                Gender gender,
                Breed breed)
    {
        this(animalType, skinType, gender, breed, new BigDecimal(0));
    }

    /**
     * Constructor
     */
    public Bird(AnimalType animalType,
                Skin skinType,
                Gender gender,
                Breed breed,
                BigDecimal cost)
    {
        this(animalType, skinType, gender, breed, cost, 0);
    }

    /**
     * Constructor (MAIN)
     */
    public Bird(AnimalType animalType,
                Skin skinType,
                Gender gender,
                Breed breed,
                BigDecimal cost,
                int petStoreId)
    {
        super(PetType.BIRD, cost, gender, petStoreId);
        super.skinType = skinType;
        super.animalType = animalType;
        this.numberOfLegs = 2;     // Birds have 2 legs
        this.breed = breed;
    }

    /** Bird hypoallergenic check (optional like Dog/Cat) */
    public String birdHypoallergenic() {
        return super.petHypoallergenic(this.skinType).replaceAll("pet", "bird");
    }

    /** Bird speak method (mirrors Cat & Dog pattern) */
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

    public int getNumberOfLegs() {
        return numberOfLegs;
    }

    public void setNumberOfLegs(int numberOfLegs) {
        this.numberOfLegs = numberOfLegs;
    }

    /** Required by PetImpl */
    @Override
    public Breed getBreed() {
        return this.breed;
    }

    public AnimalType getAnimalType() {
        return this.animalType;
    }

    @Override
    public String toString() {
        return super.toString() +
                "The bird is " + this.animalType + "!\n" +
                "The bird breed is " + this.getBreed() + "!\n" +
                this.birdHypoallergenic() + "!\n" +
                this.speak() + "\n" +
                "Birds have " + this.numberOfLegs + " legs!";
    }
}
