package animals.petstore.pet.types;

import animals.AnimalType;
import animals.petstore.pet.attributes.Breed;
import animals.petstore.pet.attributes.Gender;
import animals.petstore.pet.attributes.PetType;
import animals.petstore.pet.attributes.Skin;
import java.math.BigDecimal;

/**
 * Bird implements Pet via PetImpl. Uses existing enums only.
 * Skin is UNKNOWN (no FEATHERS enum), birds have 2 legs.
 */
public class Bird extends PetImpl {

    public Bird(AnimalType animalType,
                Skin skin,                // pass Skin.UNKNOWN in practice
                Gender gender,
                Breed breed,
                BigDecimal cost,
                int petStoreId) {
        super(animalType, skin, gender, PetType.BIRD, breed, cost, petStoreId);
        setLegs(2);
        // Birds don't have a defined skin type in this model; force UNKNOWN to be safe.
        setSkin(Skin.UNKNOWN);
    }

    @Override
    public String speak() {
        // mild variation to exercise a branch
        return getAnimalType() == AnimalType.DOMESTIC ? "tweet tweet!" : "screech!";
    }

    @Override
    public boolean isHypoAllergenic() {
        // keep it simple; no special-casing by breed in this project
        return false;
    }
}
